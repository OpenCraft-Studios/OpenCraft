package net.opencraft.client;

import java.io.File;
import net.opencraft.OpenCraft;
import net.opencraft.util.UnexpectedThrowable;

/**
 *
 * @author LWJGL2
 */
public class Main {

    public static void main(String[] args) throws Exception {
        bindNatives();
        enableLegacySorting();
        
        OpenCraft minecraft = new OpenCraft(800, 480, false) {
            @Override
            public void displayUnexpectedThrowable(UnexpectedThrowable g) {
                g.exception.printStackTrace();
            }
        };
        new Thread(minecraft).start();
    }

    private static void enableLegacySorting() {
    	System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }
    
	private static void bindNatives() {
		System.setProperty("org.lwjgl.librarypath", new File("./natives").getAbsolutePath());
	}

}
