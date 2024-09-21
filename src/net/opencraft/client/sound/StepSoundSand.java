
package net.opencraft.client.sound;

public class StepSoundSand extends StepSound {

    public StepSoundSand(final String string, final float nya1, final float nya2) {
        super(string, nya1, nya2);
    }

    @Override
    public String stepSoundDir() {
        return "step.gravel";
    }
}
