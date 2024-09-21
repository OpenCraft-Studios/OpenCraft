
package net.opencraft.client.sound;

public class StepSound {

    public final String soundName;
    public final float volume;
    public final float pitch;

    public StepSound(final String string, final float nya1, final float nya2) {
        this.soundName = string;
        this.volume = nya1;
        this.pitch = nya2;
    }

    public float soundVolume() {
        return this.volume;
    }

    public float soundPitch() {
        return this.pitch;
    }

    public String stepSoundDir() {
        return "step." + this.soundName;
    }

    public String stepSoundDir2() {
        return "step." + this.soundName;
    }
}
