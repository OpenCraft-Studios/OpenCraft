package net.opencraft.client;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.opencraft.OpenCraft;

/**
 *
 * @author LWJGL2
 */
public class Main {

	public static final ResourcesDescriptor RESOURCES = describeResources();
	public static final String NATIVES_PATH = "natives/";
	public static final int EXPECTED_NATIVES_COUNT = 10;
	public static final float INITIAL_WINDOW_SIZE_FACTOR = 0.8f;

	public static ResourcesDescriptor describeResources() {
		final String classPath = "/" + Main.class.getName().replace('.', '/') + ".class";
		final File root;
		final File jarFile;
		URL classURL = Main.class.getResource(classPath);
		if (classURL.getProtocol().equals("jar")) {
			JarURLConnection jarConn = null;
			try {
				jarConn = (JarURLConnection)classURL.openConnection();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
			jarFile = new File(jarConn.getJarFileURL().getFile());
			root = jarFile.getParentFile();
		}
		else if (classURL.getProtocol().equals("file")) {
			root = new File(classURL.getFile().replace(classPath, ""));
			jarFile = null;
		} else {
			throw new IllegalStateException("Unsupported URL protocol: " + classURL.getProtocol());
		}
		return new ResourcesDescriptor(root, jarFile);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Starting...");
		System.setProperty("user.dir", RESOURCES.resourcesRoot.getAbsolutePath());

		System.out.print("Extracting Native Libraries...");
		File nativesDir = new File(RESOURCES.resourcesRoot, NATIVES_PATH);
		System.out.println(nativesDir.getAbsolutePath());
		if(nativesDir.exists() && Objects.requireNonNull(nativesDir.listFiles()).length == EXPECTED_NATIVES_COUNT) {
				System.out.println("already extracted!");
		} else {
			// TODO: close resource input streams
			for(URL resource : resourcesAt(NATIVES_PATH)) {
				String filename = new File(resource.getFile()).getName();
				final File destination = new File(RESOURCES.resourcesRoot, NATIVES_PATH + filename);
				destination.getParentFile().mkdirs();
				Files.write(destination.toPath(), resource.openStream().readAllBytes());
			}
			System.out.println("done!");
		}

		bindNatives();
		enableLegacySorting();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		OpenCraft oc = new OpenCraft((int) (screenSize.width * INITIAL_WINDOW_SIZE_FACTOR), (int) (screenSize.height * INITIAL_WINDOW_SIZE_FACTOR), false);
		new Thread(oc).start();
	}

	// TODO: deduplicate this method with the one in DownloadResourcesJob
	// A lambda might be appropriate
	public static List<URL> resourcesAt(String prefix) throws IOException {
		ZipInputStream zip = null;
		zip = new ZipInputStream(RESOURCES.jarFile.toURI().toURL().openStream());
		List<URL> resources = new ArrayList<>();
		while(true) {
			ZipEntry entry = zip.getNextEntry();
			if (entry == null) {
				break;
			}
			final String name = entry.getName();
			if(name.startsWith(prefix) && !entry.isDirectory()) {
				resources.add(Main.class.getClassLoader().getResource(name));
			}
		}
		zip.close();
		return resources;
	}

	private static void enableLegacySorting() {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
	}

	private static void bindNatives() {
		System.setProperty("org.lwjgl.librarypath", new File(RESOURCES.resourcesRoot, NATIVES_PATH).getAbsolutePath());
	}

}
