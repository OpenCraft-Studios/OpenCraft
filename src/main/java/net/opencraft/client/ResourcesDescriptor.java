package net.opencraft.client;

import java.io.File;

public class ResourcesDescriptor {

	public final File resourcesRoot;
	public final File jarFile;

	public ResourcesDescriptor(File resourcesRoot, File jarFile) {
		this.resourcesRoot = resourcesRoot;
		this.jarFile = jarFile;
	}

	public boolean isJar() {
		return jarFile != null;
	}

}
