/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.ncraft.client;

import java.io.File;
import net.ncraft.Minecraft;
import net.ncraft.util.UnexpectedThrowable;


/**
 *
 * @author LWJGL2
 */
public class MinecraftRunner {

    public static void main(String[] args) throws Exception {
        System.setProperty("org.lwjgl.librarypath", new File("./natives").getAbsolutePath());
        Minecraft minecraft = new Minecraft(800, 480, false) {
            @Override
            public void displayUnexpectedThrowable(UnexpectedThrowable g) {
                g.exception.printStackTrace();
            }
        };
        new Thread(minecraft).start();
    }

}
