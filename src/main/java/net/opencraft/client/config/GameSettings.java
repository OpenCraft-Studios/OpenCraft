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
	public BooleanOption rawMouseInput = new BooleanOption(false);

	public BooleanOption showDebugInfo = new BooleanOption(false);
	public BooleanOption viewBobbing = new BooleanOption(true);
	public BooleanOption anaglyph = new BooleanOption(false);
	public BooleanOption limitFramerate = new BooleanOption(false);

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
		registerKeybinds();

		renderDistance = 0;
		fancyGraphics = true;
		numberOfOptions = 10;
		difficulty = 2;
		thirdPersonView = false;
		fov = 70F;
		minimumBrightness = 0.0F;
		optionsFile = new File(file, "options.txt");
		this.loadOptions();
	}

	private void registerKeybinds() {
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
		keyBindings.forcePut(input, keyCode);
	}

	public void setOptionFloatValue(int key, float value) {
		switch (key) {
			case 0:
				music.toggle();
				oc.sndManager.onSoundOptionsChanged();
				break;

			case 1:
				sound.toggle();
				oc.sndManager.onSoundOptionsChanged();
				break;

			case 2:
				invertMouse.toggle();
				break;

			case 3:
				showDebugInfo.toggle();
				break;

			case 4:
				renderDistance = renderDistance + (int) value & 0x3;
				break;

			case 5:
				viewBobbing.toggle();
				break;

			case 6:
				anaglyph.toggle();
				oc.renderer.refreshTextures();
				break;

			case 7:
				limitFramerate.toggle();
				break;

			case 8:
				difficulty = difficulty + (int) value & 0x3;
				break;

			case 9:
				fancyGraphics = !fancyGraphics;
				oc.renderGlobal.fancyGraphics();
				break;

			case 10:
				fov = value;
				break;

			case 11:
				minimumBrightness = value;
				if (oc.world == null)
					break;

				for (int i = 0; i < oc.world.worldAccesses.size(); ++i)
					((IWorldAccess) oc.world.worldAccesses.get(i)).updateAllRenderers();

				break;
		}
	}

	public String getKeyBinding(final int integer) {
		return switch (integer) {
			case 0 -> "Music: " + music.either("ON", "OFF");
			case 1 -> "Sound: " + sound.either("ON", "OFF");
			case 2 -> "Invert mouse: " + invertMouse.either("ON", "OFF");
			case 3 -> "Show FPS: " + showDebugInfo.either("ON", "OFF");
			case 4 -> "Render distance: " + GameSettings.RENDER_DISTANCES[renderDistance];
			case 5 -> "View bobbing: " + viewBobbing.either("ON", "OFF");
			case 6 -> "3d anaglyph: " + anaglyph.either("ON", "OFF");
			case 7 -> "Limit framerate: " + limitFramerate.either("ON", "OFF");
			case 8 -> "Difficulty: " + GameSettings.DIFFICULTIES[difficulty];
			case 9 -> "Graphics: " + (fancyGraphics ? "FANCY" : "FAST");
			case 101 -> "Test: " + fov;
			default -> "";
		};
	}

	public void loadOptions() {
		if (!optionsFile.exists())
			return;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(optionsFile))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				final String[] tokens = line.split(":");
				String name = tokens[0];
				String value = tokens[1];

				switch (name) {
					case "music":
						music.parse(value);
						break;

					case "sound":
						sound.parse(value);
						break;

					case "invertYMouse":
						invertMouse.parse(value);
						break;

					case "showFrameRate":
						showDebugInfo.parse(value);
						break;

					case "viewDistance":
						renderDistance = Integer.parseInt(value);
						break;

					case "bobView":
						viewBobbing.parse(value);
						break;

					case "anaglyph3d":
						anaglyph.parse(value);
						break;

					case "limitFramerate":
						limitFramerate.parse(value);
						break;

					case "difficulty":
						difficulty = Integer.parseInt(value);
						break;

					case "fancyGraphics":
						fancyGraphics = value.equals("true");
						break;

					case "FOV":
						fov = Float.parseFloat(value);
						break;

					case "minimumBrightness":
						minimumBrightness = Float.parseFloat(value);
						break;

					case "rawMouseInput":
						rawMouseInput.parse(value);
						if (rawMouseInput.get() && glfwRawMouseMotionSupported())
							glfwSetInputMode(oc.window, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
						break;
				}

				for (PlayerInput input : PlayerInput.values())
					if (name.equals("key_" + input.name()))
						keyBindings.put(input, Integer.parseInt(value));
			}
		} catch (Exception ex) {
			System.out.println("Failed to load options");
			ex.printStackTrace();
		}
	}

	public void saveOptions() {
		try (PrintWriter s = new PrintWriter(new FileWriter(optionsFile))) {
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
			s.println("minimumBrightness:" + minimumBrightness);
			s.println("rawMouseInput:" + rawMouseInput);
			for (PlayerInput input : PlayerInput.values())
				s.println("key_" + input.name() + ":" + keyBindings.get(input));
		} catch (Exception ex) {
			System.err.println("Failed to save options");
			ex.printStackTrace();
		}
	}

}
