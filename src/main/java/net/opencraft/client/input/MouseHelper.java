
package net.opencraft.client.input;

import java.awt.Component;
import org.lwjgl.input.Mouse;

public class MouseHelper {

    public MouseHelper(final Component component) {
    }

    public void grabMouse() {
        Mouse.setGrabbed(true);
    }

    public void ungrabMouseCursor() {
        Mouse.setGrabbed(false);
    }

    public void mouseXYChange() {
    }
}
