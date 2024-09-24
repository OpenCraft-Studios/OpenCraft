
package net.opencraft.client.sound;

import java.net.URL;
import java.util.*;

public class SoundPool {
    private static Random RANDOM = new Random();
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

    /**
     * Gets a sound that matches the given name
     * @return null if sound not found
     */
    public SoundPoolEntry getRandomSoundFromSoundPool(String name) {
        List<SoundPoolEntry> entriesMatchingName = this.nameToSoundPoolEntriesMapping.get(name);
        if(entriesMatchingName == null) {
            System.err.println("Can't play random sound" + name + "! sound pool is empty!");
            return null;
        } else {
            return entriesMatchingName.get(RANDOM.nextInt(entriesMatchingName.size()));
        }
    }

    public SoundPoolEntry getRandomSound() {
        if(allSoundPoolEntries.isEmpty()) {
            System.err.println("Can't play random sound! sound pool is empty!");
            return null;
        } else {
            return allSoundPoolEntries.get(RANDOM.nextInt(allSoundPoolEntries.size()));
        }
    }
}
