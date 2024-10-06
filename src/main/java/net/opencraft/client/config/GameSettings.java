
package net.opencraft.client.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import net.opencraft.OpenCraft;
import net.opencraft.client.input.MovementInput;
import net.opencraft.world.IWorldAccess;

import javax.swing.text.JTextComponent;

import static org.lwjgl.glfw.GLFW.*;

public class GameSettings {

    private static final String[] RENDER_DISTANCES;
    private static final String[] DIFFICULTIES;
    
    public boolean music;
    public boolean sound;
    public boolean invertMouse;
    public boolean showDebugInfo;
    public int renderDistance;
    public boolean viewBobbing;
    public boolean anaglyph;
    public boolean limitFramerate;
    public boolean fancyGraphics;
    public BiMap<PlayerInput, Integer> keyBindings = EnumHashBiMap.create(PlayerInput.class);
    protected OpenCraft mc;
    private final File optionsFile;
    public int numberOfOptions;
    public int difficulty;
    public boolean thirdPersonView;
    public float fov;
    public float minimumBrightness;

    static {
        RENDER_DISTANCES = new String[]{"FAR", "NORMAL", "SHORT", "TINY"};
        DIFFICULTIES = new String[]{"Peaceful", "Easy", "Normal", "Hard"};
    }

    public enum PlayerInput {
    	FORWARD,
    	BACKWARD,
        LEFT,
    	RIGHT,
    	JUMP,
    	INVENTORY,
    	DROP,
    	CHAT,
    	TOGGLE_FOG
    }
    
    public GameSettings(final OpenCraft aw, final File file) {
    	setupKeybinds();

    	this.music = true;
        this.sound = true;
        this.invertMouse = false;
        this.showDebugInfo = false;
        this.renderDistance = 0;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.limitFramerate = false;
        this.fancyGraphics = true;
        this.numberOfOptions = 10;
        this.difficulty = 2;
        this.thirdPersonView = false;
        this.fov = 100.0F;
        this.minimumBrightness = 0.0F;
        this.mc = aw;
        this.optionsFile = new File(file, "options.txt");
        this.loadOptions();
    }

	private void setupKeybinds() {
		// Keybinds
        keyBindings.put(PlayerInput.FORWARD, GLFW_KEY_W);
        keyBindings.put(PlayerInput.BACKWARD, GLFW_KEY_S);
        keyBindings.put(PlayerInput.LEFT, GLFW_KEY_A);
        keyBindings.put(PlayerInput.RIGHT, GLFW_KEY_D);
        keyBindings.put(PlayerInput.JUMP, GLFW_KEY_SPACE);
        keyBindings.put(PlayerInput.INVENTORY, GLFW_KEY_E);
        keyBindings.put(PlayerInput.DROP, GLFW_KEY_Q);
        keyBindings.put(PlayerInput.CHAT, GLFW_KEY_T);
        keyBindings.put(PlayerInput.TOGGLE_FOG, GLFW_KEY_F);
	}

    public String getOptionDisplayString(PlayerInput input) {
        return input.name() + ": " + glfwGetKeyName(keyBindings.get(input), glfwGetKeyScancode(keyBindings.get(input)));
    }

    public String getOptionDisplayString(final int integer) {
        return getOptionDisplayString(PlayerInput.values()[integer]);
    }

    public void setKeyBinding(final int integer, final int keyCode) {
        setKeyBinding(PlayerInput.values()[integer], keyCode);
    }

    public void setKeyBinding(PlayerInput input, final int keyCode) {
        this.keyBindings.forcePut(input, keyCode);
        this.saveOptions();
    }

    public void setOptionFloatValue(final float key, float value) {
        if (key == 0) {
            this.music = !this.music;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (key == 1) {
            this.sound = !this.sound;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (key == 2) {
            this.invertMouse = !this.invertMouse;
        }
        if (key == 3) {
            this.showDebugInfo = !this.showDebugInfo;
        }
        if (key == 4) {
            this.renderDistance = (this.renderDistance + (int) value & 0x3);
        }
        if (key == 5) {
            this.viewBobbing = !this.viewBobbing;
        }
        if (key == 6) {
            this.anaglyph = !this.anaglyph;
            this.mc.renderer.refreshTextures();
        }
        if (key == 7) {
            this.limitFramerate = !this.limitFramerate;
        }
        if (key == 8) {
            this.difficulty = (this.difficulty + (int) value & 0x3);
        }
        if (key == 9) {
            this.fancyGraphics = !this.fancyGraphics;
            this.mc.renderGlobal.fancyGraphics();
        }
        if (key == 10) {
            this.fov = value;
        }
        if (key == 11) {
            this.minimumBrightness = value;
            if (mc.world != null) {
                for (int i = 0; i < mc.world.worldAccesses.size(); ++i) {
                    ((IWorldAccess) mc.world.worldAccesses.get(i)).updateAllRenderers();
                }
            }
        }
        this.saveOptions();
    }

    public String getKeyBinding(final int integer) {
        if (integer == 0) {
            return new StringBuilder().append("Music: ").append(this.music ? "ON" : "OFF").toString();
        }
        if (integer == 1) {
            return new StringBuilder().append("Sound: ").append(this.sound ? "ON" : "OFF").toString();
        }
        if (integer == 2) {
            return new StringBuilder().append("Invert mouse: ").append(this.invertMouse ? "ON" : "OFF").toString();
        }
        if (integer == 3) {
            return new StringBuilder().append("Show FPS: ").append(this.showDebugInfo ? "ON" : "OFF").toString();
        }
        if (integer == 4) {
            return "Render distance: " + GameSettings.RENDER_DISTANCES[this.renderDistance];
        }
        if (integer == 5) {
            return new StringBuilder().append("View bobbing: ").append(this.viewBobbing ? "ON" : "OFF").toString();
        }
        if (integer == 6) {
            return new StringBuilder().append("3d anaglyph: ").append(this.anaglyph ? "ON" : "OFF").toString();
        }
        if (integer == 7) {
            return new StringBuilder().append("Limit framerate: ").append(this.limitFramerate ? "ON" : "OFF").toString();
        }
        if (integer == 8) {
            return "Difficulty: " + GameSettings.DIFFICULTIES[this.difficulty];
        }
        if (integer == 9) {
            return new StringBuilder().append("Graphics: ").append(this.fancyGraphics ? "FANCY" : "FAST").toString();
        }
        if (integer == 101) {
            return new StringBuilder().append("Test: ").append(this.fov).toString();
        }
        return "";
    }

    public void loadOptions() {
        if(!this.optionsFile.exists()) {
            return;
        }
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.optionsFile))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                final String[] split = line.split(":");
                if(split[0].equals("music")) {
                    this.music = split[1].equals("true");
                }
                if(split[0].equals("sound")) {
                    this.sound = split[1].equals("true");
                }
                if(split[0].equals("invertYMouse")) {
                    this.invertMouse = split[1].equals("true");
                }
                if(split[0].equals("showFrameRate")) {
                    this.showDebugInfo = split[1].equals("true");
                }
                if(split[0].equals("viewDistance")) {
                    this.renderDistance = Integer.parseInt(split[1]);
                }
                if(split[0].equals("bobView")) {
                    this.viewBobbing = split[1].equals("true");
                }
                if(split[0].equals("anaglyph3d")) {
                    this.anaglyph = split[1].equals("true");
                }
                if(split[0].equals("limitFramerate")) {
                    this.limitFramerate = split[1].equals("true");
                }
                if(split[0].equals("difficulty")) {
                    this.difficulty = Integer.parseInt(split[1]);
                }
                if(split[0].equals("fancyGraphics")) {
                    this.fancyGraphics = split[1].equals("true");
                }
                if(split[0].equals("FOV")) {
                    this.fov = Float.parseFloat(split[1]);
                }
                if(split[0].equals("minimumBrightness")) {
                    this.minimumBrightness = Float.parseFloat(split[1]);
                }
                for(PlayerInput input : PlayerInput.values()) {
                    if(split[0].equals("key_" + input.name())) {
                        this.keyBindings.put(input, Integer.parseInt(split[1]));
                    }
                }
            }
        } catch(Exception ex) {
            System.out.println("Failed to load options");
            ex.printStackTrace();
        }
    }

    public void saveOptions() {
    	try (PrintWriter s = new PrintWriter(new FileWriter(this.optionsFile))) {
    		s.println("music:" + music);
            s.println("sound:" + sound);
            s.println("invertYMouse:" + invertMouse);
            s.println("showFrameRate:" + showDebugInfo);
            s.println("viewDistance:" + renderDistance);
            s.println("bobView:" + viewBobbing);
            s.println("anaglyph3d:" + anaglyph);
            s.println("limitFramerate:" + limitFramerate);
            s.println("difficulty:" + difficulty);
            s.println("fancyGraphics:" + fancyGraphics);
            s.println("FOV:" + fov);
            s.println("minimumBrightness:" + this.minimumBrightness);
            for(PlayerInput input : PlayerInput.values()) {
                s.println("key_" + input.name() + ":" + keyBindings.get(input));
            }
        } catch (Exception ex) {
            System.out.println("Failed to save options");
            ex.printStackTrace();
        }
    }

}
