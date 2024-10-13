package net.opencraft;

import static net.opencraft.SharedConstants.*;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.opencraft.client.Main;
import net.opencraft.client.ResourcesDescriptor;

public class SharedConstants {

	public static final ResourcesDescriptor RESOURCES = describeResources();
	public static final String GIT_INFO = "git.properties";
	public static final String VERSION = version();
	
	public static final String TITLE = "OpenCraft " + VERSION;
	
	public static final String SPLASHES_URL = "https://opencraft-studios.github.io/splashes.txt";

	public static List<URL> resourcesAt(String prefix) throws IOException {
		ZipInputStream zip = new ZipInputStream(RESOURCES.jarFile.toURI().toURL().openStream());
		List<URL> resources = new ArrayList<>();
		
		ZipEntry entry;
		while ((entry = zip.getNextEntry()) != null) {
			final String name = entry.getName();
			if (name.startsWith(prefix) && !entry.isDirectory())
				resources.add(SharedConstants.class.getResource("/" + name));
		}
		
		zip.close();
		return resources;
	}
	
	public static String version() {
		String commitId = "COMMIT_ID_UNKNOWN";
		if (RESOURCES.jarFile != null) {
			try {
				String result = new BufferedReader(new InputStreamReader(resourcesAt(GIT_INFO).getFirst().openStream())).lines().collect(Collectors.joining("\n"));
				for ( String line : result.split("\n") ) {
					commitId = updateVersionIfInString(commitId, line);
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			File gitProperties = new File(RESOURCES.resourcesRoot, GIT_INFO);
			if (gitProperties.exists()) {
				try {
					List<String> lines = Files.readAllLines(gitProperties.toPath());
					for ( String line : lines ) {
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
		if (line.startsWith("git.commit.id.abbrev")) {
			return line.split("=")[1];
		} else {
			return version;
		}
	}
	
	public static ResourcesDescriptor describeResources() {
		final String classPath = "/" + Main.class.getName().replace('.', '/') + ".class";
		final File root;
		final File jarFile;
		URL classURL = Main.class.getResource(classPath);
		if (classURL.getProtocol().equals("jar")) {
			JarURLConnection jarConn = null;
			try {
				jarConn = (JarURLConnection) classURL.openConnection();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
			jarFile = new File(jarConn.getJarFileURL().getFile());
			root = jarFile.getParentFile();
		} else if (classURL.getProtocol().equals("file")) {
			root = new File(classURL.getFile().replace(classPath, ""));
			jarFile = null;
		} else {
			throw new IllegalStateException("Unsupported URL protocol: " + classURL.getProtocol());
		}
		return new ResourcesDescriptor(root, jarFile);
	}
	
	private SharedConstants() {
	}

}
