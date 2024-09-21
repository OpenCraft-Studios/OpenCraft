/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.opencraft.client;

import java.io.File;
import net.opencraft.OpenCraft;
import net.opencraft.util.UnexpectedThrowable;


/**
 *
 * @author LWJGL2
 */
public class OpenCraftRunner {

    public static void main(String[] args) throws Exception {
        System.setProperty("org.lwjgl.librarypath", new File("./natives").getAbsolutePath());
        OpenCraft minecraft = new OpenCraft(800, 480, false) {
            @Override
            public void displayUnexpectedThrowable(UnexpectedThrowable g) {
                g.exception.printStackTrace();
            }
        };
        new Thread(minecraft).start();
    }

}
