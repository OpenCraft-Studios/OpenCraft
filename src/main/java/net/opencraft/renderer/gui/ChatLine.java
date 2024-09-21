
package net.opencraft.renderer.gui;

public class ChatLine {

    public String message;
    public int updateCounter;

    public ChatLine(final String string) {
        this.message = string;
        this.updateCounter = 0;
    }
}
