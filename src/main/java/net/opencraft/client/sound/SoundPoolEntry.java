
package net.opencraft.client.sound;


import java.net.URL;

public class SoundPoolEntry {

    public String soundName;

    public String soundNameNoExt;
    public URL soundUrl;

    public SoundPoolEntry(final String string, final URL uRL) {
        this.soundName = string;
        this.soundUrl = uRL;
        this.soundNameNoExt = string.substring(0, string.length() - 4);
    }
}
