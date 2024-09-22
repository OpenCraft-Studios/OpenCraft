package net.opencraft.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.opencraft.OpenCraft;

import static net.opencraft.OpenCraft.oc;

/**
 *
 * @author LWJGL2
 */
public class Main {

	public static final URL RESOURCE_URL = Main.class.getProtectionDomain().getCodeSource().getLocation();
	public static final File RESOURCE_FILE = new File(RESOURCE_URL.getFile());
	public static final String NATIVES_PATH = "natives/";

    public static void main(String[] args) throws Exception {
		System.out.print("Extracting Native Libraries...");
		// TODO: close resource input streams
		for(URL resource : resourcesAt(NATIVES_PATH)) {
			String filename = new File(resource.getFile()).getName();
			final File destination = new File(RESOURCE_FILE.getParent(),  NATIVES_PATH + filename);
			destination.getParentFile().mkdirs();
			Files.write(destination.toPath(), resource.openStream().readAllBytes());
		}
		System.out.println("done!");

		bindNatives();
		enableLegacySorting();

        OpenCraft oc = new OpenCraft(800, 480, false);
        new Thread(oc).start();
    }

	// TODO: deduplicate this method with the one in DownloadResourcesJob
	// A lambda might be appropriate
	public static List<URL> resourcesAt(String prefix) throws IOException {
		ZipInputStream zip = null;
		zip = new ZipInputStream(RESOURCE_URL.openStream());
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
		System.setProperty("org.lwjgl.librarypath", new File("./natives").getAbsolutePath());
	}

}
