package net.opencraft.client.config;

import static net.opencraft.OpenCraft.*;
import static org.lwjgl.glfw.GLFW.*;

import java.io.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

import net.opencraft.client.config.option.BooleanOption;
import net.opencraft.world.IWorldAccess;

public class GameSettings {

	private static final String[] RENDER_DISTANCES;
	private static final String[] DIFFICULTIES;

	public BooleanOption music = new BooleanOption(true);
	public BooleanOption sound = new BooleanOption(true);

	public BooleanOption invertMouse = new BooleanOption(false);
	public BooleanOption showDebugInfo = new BooleanOption(false);

	public BooleanOption viewBobbing = new BooleanOption(true);
	public BooleanOption anaglyph = new BooleanOption(false);

	public boolean limitFramerate;

	// TODO: Create GraphicsConfiguration enum
	public boolean fancyGraphics;

	public int renderDistance;
	public BiMap<PlayerInput, Integer> keyBindings = EnumHashBiMap.create(PlayerInput.class);
	private final File optionsFile;
	public int numberOfOptions;
	public int difficulty;
	public boolean thirdPersonView;
	public float fov;
	public float minimumBrightness;

	static {
		RENDER_DISTANCES = new String[] { "FAR", "NORMAL", "SHORT", "TINY" };
		DIFFICULTIES = new String[] { "Peaceful", "Easy", "Normal", "Hard" };
	}

	public enum PlayerInput {
		FORWARD, BACKWARD, LEFT, RIGHT, JUMP, INVENTORY, DROP, CHAT, TOGGLE_FOG
	}

	public GameSettings(final File file) {
		setupKeybinds();

		this.renderDistance = 0;
		this.limitFramerate = false;
		this.fancyGraphics = true;
		this.numberOfOptions = 10;
		this.difficulty = 2;
		this.thirdPersonView = false;
		this.fov = 100.0F;
		this.minimumBrightness = 0.0F;
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

	public void setOptionFloatValue(float key, float value) {
		switch ((int) key) {
			case 0:
				this.music.toggle();
				oc.sndManager.onSoundOptionsChanged();
				break;

			case 1:
				this.sound.toggle();
				oc.sndManager.onSoundOptionsChanged();
				break;

			case 2:
				this.invertMouse.toggle();
				break;

			case 3:
				this.showDebugInfo.toggle();
				break;

			case 4:
				this.renderDistance = (this.renderDistance + (int) value & 0x3);
				break;

			case 5:
				this.viewBobbing.toggle();
				break;

			case 6:
				this.anaglyph.toggle();
				oc.renderer.refreshTextures();
				break;

			case 7:
				this.limitFramerate = !this.limitFramerate;
				break;

			case 8:
				this.difficulty = (this.difficulty + (int) value & 0x3);
				break;

			case 9:
				this.fancyGraphics = !this.fancyGraphics;
				oc.renderGlobal.fancyGraphics();
				break;

			case 10:
				this.fov = value;
				break;

			case 11:
				this.minimumBrightness = value;
				if (oc.world == null) 
					break;
				
				for (int i = 0; i < oc.world.worldAccesses.size(); ++i) {
					((IWorldAccess) oc.world.worldAccesses.get(i)).updateAllRenderers();
				}
				
				break;
		}
		saveOptions();
	}

	public String getKeyBinding(final int integer) {
		return switch (integer) {
			case 0 -> "Music: " + music.either("ON", "OFF");
			case 1 -> "Sound: " + sound.either("ON", "OFF");
			case 2 -> "Invert mouse: " + invertMouse.either("ON", "OFF");
			case 3 -> "Show FPS: " + showDebugInfo.either("ON", "OFF");
			case 4 -> "Render distance: " + GameSettings.RENDER_DISTANCES[this.renderDistance];
			case 5 -> "View bobbing: " + this.viewBobbing.either("ON", "OFF");
			case 6 -> "3d anaglyph: " + this.anaglyph.either("ON", "OFF");
			case 7 -> "Limit framerate: " + (this.limitFramerate ? "ON" : "OFF");
			case 8 -> "Difficulty: " + GameSettings.DIFFICULTIES[this.difficulty];
			case 9 -> "Graphics: " + (this.fancyGraphics ? "FANCY" : "FAST");
			case 101 -> "Test: " + this.fov;
			default -> "";
		};
	}

	public void loadOptions() {
		if (!this.optionsFile.exists()) {
			return;
		}
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.optionsFile))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				final String[] tokens = line.split(":");
				if (tokens[0].equals("music")) {
					music.parse(tokens[1]);
				}
				if (tokens[0].equals("sound")) {
					sound.parse(tokens[1]);
				}
				if (tokens[0].equals("invertYMouse")) {
					invertMouse.parse(tokens[1]);
				}
				if (tokens[0].equals("showFrameRate")) {
					showDebugInfo.parse(tokens[1]);
				}
				if (tokens[0].equals("viewDistance")) {
					this.renderDistance = Integer.parseInt(tokens[1]);
				}
				if (tokens[0].equals("bobView")) {
					this.viewBobbing.parse(tokens[1]);
				}
				if (tokens[0].equals("anaglyph3d")) {
					this.anaglyph.parse(tokens[1]);
				}
				if (tokens[0].equals("limitFramerate")) {
					this.limitFramerate = tokens[1].equals("true");
				}
				if (tokens[0].equals("difficulty")) {
					this.difficulty = Integer.parseInt(tokens[1]);
				}
				if (tokens[0].equals("fancyGraphics")) {
					this.fancyGraphics = tokens[1].equals("true");
				}
				if (tokens[0].equals("FOV")) {
					this.fov = Float.parseFloat(tokens[1]);
				}
				if (tokens[0].equals("minimumBrightness")) {
					this.minimumBrightness = Float.parseFloat(tokens[1]);
				}
				for (PlayerInput input : PlayerInput.values()) {
					if (tokens[0].equals("key_" + input.name())) {
						this.keyBindings.put(input, Integer.parseInt(tokens[1]));
					}
				}
			}
		} catch (Exception ex) {
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
			for (PlayerInput input : PlayerInput.values()) {
				s.println("key_" + input.name() + ":" + keyBindings.get(input));
			}
		} catch (Exception ex) {
			System.out.println("Failed to save options");
			ex.printStackTrace();
		}
	}

}
