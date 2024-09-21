
package net.opencraft.client.settings;

public class KeyBinding {

    public String description;
    public int keyCode;

    public KeyBinding(final String string, final int integer) {
        this.description = string;
        this.keyCode = integer;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("key_");
    	sb.append(description);
    	sb.append(":");
    	sb.append(keyCode);
    	
    	return sb.toString();
    }
}
