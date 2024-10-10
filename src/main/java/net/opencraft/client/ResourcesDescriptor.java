package net.opencraft.client;

import java.io.File;

import javax.annotation.Nullable;

public class ResourcesDescriptor {

	@Nullable
	public final File resourcesRoot;
	
	@Nullable
	public final File jarFile;

	public ResourcesDescriptor(File resourcesRoot, File jarFile) {
		this.resourcesRoot = resourcesRoot;
		this.jarFile = jarFile;
	}

	public boolean isJar() {
		return jarFile != null;
	}

}
