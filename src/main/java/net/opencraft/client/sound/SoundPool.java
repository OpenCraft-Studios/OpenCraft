
package net.opencraft.client.sound;

import java.net.URL;
import java.util.*;

public class SoundPool {
    private Random rand = new Random();
    private Map<String, ArrayList<SoundPoolEntry>> nameToSoundPoolEntriesMapping = new HashMap<>();
    public List<SoundPoolEntry> allSoundPoolEntries = new ArrayList<>();
    public int numberOfSoundPoolEntries = 0;
    public boolean isGetRandomSound = true;

    public SoundPoolEntry addSound(String soundName, URL resourceURL) {
        SoundPoolEntry sound = new SoundPoolEntry(soundName, resourceURL);

        if(!this.nameToSoundPoolEntriesMapping.containsKey(sound.soundNameNoExt)) {
            this.nameToSoundPoolEntriesMapping.put(sound.soundNameNoExt, new ArrayList<>());
        }

        this.nameToSoundPoolEntriesMapping.get(sound.soundNameNoExt).add(sound);
        // System.out.println("Added sound: " + sound.soundNameNoExt + " (" + sound.soundName + ") from " + resourceURL + " to the sound pool.");
        this.allSoundPoolEntries.add(sound);
        ++this.numberOfSoundPoolEntries;
        return sound;
    }

    public boolean contains(String soundName) {
        return nameToSoundPoolEntriesMapping.containsKey(soundName);
    }

    public SoundPoolEntry getRandomSoundFromSoundPool(String var1) {
        List<SoundPoolEntry> var2 = this.nameToSoundPoolEntriesMapping.get(var1);
        return var2 == null ? null : var2.get(this.rand.nextInt(var2.size()));
    }

    public SoundPoolEntry getRandomSound() {
        return this.allSoundPoolEntries.isEmpty() ? null : this.allSoundPoolEntries.get(this.rand.nextInt(this.allSoundPoolEntries.size()));
    }
}
