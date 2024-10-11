package net.opencraft.client;

import java.io.File;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ResourcesDescriptor {

	@Nonnull
	public final File resourcesRoot;

	@Nullable
	public final File jarFile;

	public ResourcesDescriptor(File resourcesRoot, File jarFile) {
		this.resourcesRoot = Objects.requireNonNull(resourcesRoot, "resources root must NEVER be null!");
		this.jarFile = jarFile;
	}

	public boolean isJar() {
		return jarFile != null;
	}

}
