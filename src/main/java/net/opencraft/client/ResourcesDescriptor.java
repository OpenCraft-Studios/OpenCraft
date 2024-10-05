package net.opencraft.client;

import java.io.File;
import java.util.Optional;

public class ResourcesDescriptor {

	public final Optional<File> resourcesRoot;
	public final Optional<File> jarFile;

	public ResourcesDescriptor(File resourcesRoot, File jarFile) {
		this.resourcesRoot = Optional.ofNullable(resourcesRoot);
		this.jarFile = Optional.ofNullable(jarFile);
	}

	public boolean isJar() {
		return jarFile.isPresent();
	}

}
