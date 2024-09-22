package net.opencraft.client;

import java.io.File;
import net.opencraft.OpenCraft;

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
