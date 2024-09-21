
package net.opencraft.client.settings;

public class KeyBinding {

    public String keyDescription;
    public int keyCode;

    public KeyBinding(final String string, final int integer) {
        this.keyDescription = string;
        this.keyCode = integer;
    }
}
