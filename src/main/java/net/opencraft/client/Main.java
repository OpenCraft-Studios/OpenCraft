package net.opencraft.client;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;

import net.opencraft.OpenCraft;

public class Main {

	public static final ResourcesDescriptor RESOURCES = describeResources();
	public static final String NATIVES_PATH = "natives/";
	public static final int EXPECTED_NATIVES_COUNT = 10;
	public static final float INITIAL_WINDOW_SIZE_FACTOR = 0.8f;
	public static final String GIT_INFO = "git.properties";
	public static final String VERSION = version();
	public static final String TITLE = "OpenCraft " + VERSION;

	private static GLFWErrorCallback errorCallback;

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

		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		OpenCraft oc = new OpenCraft((int) (screenSize.width * INITIAL_WINDOW_SIZE_FACTOR), (int) (screenSize.height * INITIAL_WINDOW_SIZE_FACTOR), false);
		System.out.println("Running on thread " + Thread.currentThread().threadId() + " / " + Thread.currentThread().getName());
		oc.run();
	}

	public static String version() {
		String commitId = "COMMIT_ID_UNKNOWN";
		if(RESOURCES.jarFile != null) {
			try {
				String result = new BufferedReader(new InputStreamReader(resourcesAt(GIT_INFO).getFirst().openStream())).lines().collect(Collectors.joining("\n"));
				for(String line : result.split("\n")) {
					commitId = updateVersionIfInString(commitId, line);
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			File gitProperties = new File(RESOURCES.resourcesRoot, GIT_INFO);
			if(gitProperties.exists()) {
				try {
					List<String> lines = Files.readAllLines(gitProperties.toPath());
					for(String line : lines) {
						commitId = updateVersionIfInString(commitId, line);
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return commitId;
	}

	public static String updateVersionIfInString(String version, String line) {
		if(line.startsWith("git.commit.id.abbrev")) {
			return line.split("=")[1];
		} else {
			return version;
		}
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

}
