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

	public static void main(String[] args) throws Exception {
		bindNatives();
		enableLegacySorting();

		OpenCraft oc = new OpenCraft(800, 480, false);
		new Thread(oc).start();
	}

	private static void enableLegacySorting() {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
	}

	private static void bindNatives() {
		System.setProperty("org.lwjgl.librarypath", new File("./natives").getAbsolutePath());
	}

}
