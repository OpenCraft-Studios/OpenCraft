package net.opencraft.client.sound;

import static net.opencraft.jobs.DownloadResourcesJob.*;
import static org.joml.Math.*;

import net.opencraft.client.config.GameSettings;
import net.opencraft.entity.EntityLiving;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import java.net.URL;
import java.util.Random;

public class SoundManager {

	private SoundSystem sndSystem;
	private SoundPool soundPoolSounds = new SoundPool();
	private SoundPool soundPoolStreaming = new SoundPool();
	private SoundPool soundPoolIngameMusic = new SoundPool();
	private SoundPool soundPoolMenuMusic = new SoundPool();
	private int playedSoundsCount = 0;
	private GameSettings options;
	private boolean loaded = false;
	private Random rand = new Random();
	public int ticksBeforeMusic = this.rand.nextInt(12000);
	public String currentMusicTheme = "menu";

	public enum SoundType {

		MENUMUSIC,
		SOUND("newsound", "sound3"),
		MUSIC("newmusic"),
		STREAMING;

		public final String[] alternateNames;

		SoundType(String... alternateNames) {
			if (alternateNames == null) {
				this.alternateNames = new String[0];
			} else {
				this.alternateNames = alternateNames;
			}
		}

		public static SoundType fromString(String name) {
			for ( SoundType type : values() ) {
				if (name.contains(type.name().toLowerCase())) {
					return type;
				}
				for ( String alternateName : type.alternateNames ) {
					if (name.contains(alternateName)) {
						return type;
					}
				}
			}
			return null;
		}

	}

	public boolean containsSound(String soundName) {
		return soundPoolSounds.contains(soundName) || soundPoolStreaming.contains(soundName) || soundPoolIngameMusic.contains(soundName) || soundPoolMenuMusic.contains(soundName);
	}

	public void loadSoundSettings(GameSettings var1) {
		this.soundPoolStreaming.isGetRandomSound = false;
		this.options = var1;
		if (!this.loaded && (var1 == null || var1.sound || var1.music)) {
			this.tryToSetLibraryAndCodecs();
		}

	}

	private void tryToSetLibraryAndCodecs() {
		try {
			boolean var1 = this.options.sound;
			boolean var2 = this.options.music;
			this.options.sound = false;
			this.options.music = false;
			this.options.saveOptions();
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setCodec("mus", CodecMus.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			this.sndSystem = new SoundSystem();
			this.options.sound = var1;
			this.options.music = var2;
			this.options.saveOptions();
		} catch(Throwable var3) {
			var3.printStackTrace();
			System.err.println("error linking with the LibraryJavaSound plug-in");
		}

		this.loaded = true;
	}

	public void onSoundOptionsChanged() {
		if (!this.loaded && (this.options.music || this.options.sound)) {
			this.tryToSetLibraryAndCodecs();
		}

		if (!this.options.music) {
			this.sndSystem.stop("BgMusic");
		}

	}

	public void shutdown() {
		if (!this.loaded)
			return;

		this.sndSystem.cleanup();
	}

	public void registerSound(final URL resourceURL) {
		String name = resourceURL.getPath().substring(resourceURL.getPath().lastIndexOf(SOUNDS_PATH) + SOUNDS_PATH.length());
		name = name.substring(name.indexOf("/") + 1).replace("%20", " ").replace("/", ".").replaceAll("[0-9]", "");
		final String path = resourceURL.getPath();

		if (!switch(SoundType.fromString(path)) {
			case MENUMUSIC -> addMenuMusic(name, resourceURL);
			case SOUND -> addSound(name, resourceURL);
			case MUSIC -> addIngameMusic(name, resourceURL);
			case STREAMING -> true; // TODO: investigate streaming sounds
			case null, default -> {
				System.err.println("Unknown sound type: " + path);
				yield false;
			}
		}) {
			System.err.println("Failed to load sound from " + resourceURL.getPath());
		}
	}

	public boolean addSound(String name, URL resourceURL) {
		return soundPoolSounds.addSound(name, resourceURL) != null;
	}

	public boolean addIngameMusic(String name, URL resourceURL) {
		return soundPoolIngameMusic.addSound(name, resourceURL) != null;
	}

	public boolean addMenuMusic(String name, URL resourceURL) {
		return soundPoolMenuMusic.addSound(name, resourceURL) != null;
	}

	public void playRandomMusicIfReady() {
		if (this.loaded && this.options.music) {
			if (!this.sndSystem.playing("BgMusic") && !this.sndSystem.playing("streaming")) {
				if (this.currentMusicTheme.equals("menu")) {
					SoundPoolEntry var1 = this.soundPoolMenuMusic.getRandomSound();
					if (var1 != null) {
						this.ticksBeforeMusic = 12000; // Improved random ticks
						this.sndSystem.stop("BgMusic");
						this.sndSystem.backgroundMusic("BgMusic", var1.soundUrl, var1.soundName, false);
						this.sndSystem.play("BgMusic");
					} else {
						System.err.println("No menu music found!");
					}
				} else if (this.currentMusicTheme.equals("ingame")) {
					if (this.ticksBeforeMusic > 0) {
						--this.ticksBeforeMusic;
						return;
					}
					SoundPoolEntry var1 = this.soundPoolIngameMusic.getRandomSound();
					if (var1 != null) {
						this.ticksBeforeMusic = this.rand.nextInt(12000) + 12000; // Improved random ticks
						this.sndSystem.stop("BgMusic");
						this.sndSystem.backgroundMusic("BgMusic", var1.soundUrl, var1.soundName, false);
						this.sndSystem.play("BgMusic");
					}
				}
			}
		}
	}

	public void stopSound(String var1) {
		if (sndSystem == null)
			return; // TODO: Temporary fix to OpenCraft problem

		this.sndSystem.stop(var1);
	}

	public void setListener(EntityLiving var1, float var2) {
		if (this.loaded && this.options.sound) {
			if (var1 != null) {
				float var3 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2;
				double var4 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double) var2;
				double var6 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double) var2;
				double var8 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double) var2;
				float var10 = cos(toRadians(-var3) - PI_f);
				float var11 = sin(toRadians(-var3) - PI_f);
				float var12 = -var11;
				float var13 = 0.0F;
				float var14 = -var10;
				float var15 = 0.0F;
				float var16 = 1.0F;
				float var17 = 0.0F;
				this.sndSystem.setListenerPosition((float) var4, (float) var6, (float) var8);
				this.sndSystem.setListenerOrientation(var12, var13, var14, var15, var16, var17);
			}
		}
	}

	public void playStreaming(String var1, float var2, float var3, float var4, float var5, float var6) {
		if (this.loaded && this.options.music) {
			String var7 = "streaming";
			if (this.sndSystem.playing("streaming")) {
				this.sndSystem.stop("streaming");
			}

			if (var1 != null) {
				SoundPoolEntry var8 = this.soundPoolStreaming.getRandomSoundFromSoundPool(var1);
				if (var8 != null && var5 > 0.0F) {
					if (this.sndSystem.playing("BgMusic")) {
						this.sndSystem.stop("BgMusic");
					}

					float var9 = 16.0F;
					this.sndSystem.newStreamingSource(true, var7, var8.soundUrl, var8.soundName, false, var2, var3, var4, 2, var9 * 4.0F);
					this.sndSystem.setVolume(var7, 0.5F);
					this.sndSystem.play(var7);
				}

			}
		}
	}

	public void playSound(String var1, float var2, float var3, float var4, float var5, float var6) {
		if (this.loaded && this.options.sound) {
			if (!soundPoolSounds.contains(var1)) {
				System.err.println("Sound not found: " + var1);
			}
			SoundPoolEntry var7 = this.soundPoolSounds.getRandomSoundFromSoundPool(var1);
			if (var7 != null && var5 > 0.0F) {
				this.playedSoundsCount = (this.playedSoundsCount + 1) % 256;
				String var8 = "sound_" + this.playedSoundsCount;
				float var9 = 16.0F;
				if (var5 > 1.0F) {
					var9 *= var5;
				}

				this.sndSystem.newSource(var5 > 1.0F, var8, var7.soundUrl, var7.soundName, false, var2, var3, var4, 2, var9);
				this.sndSystem.setPitch(var8, var6);
				if (var5 > 1.0F) {
					var5 = 1.0F;
				}

				this.sndSystem.setVolume(var8, var5);
				this.sndSystem.play(var8);
			}

		}
	}

	public void playSoundFX(String var1, float var2, float var3) {
		if (this.loaded && this.options.sound) {
			SoundPoolEntry var4 = this.soundPoolSounds.getRandomSoundFromSoundPool(var1);
			if (var4 != null) {
				this.playedSoundsCount = (this.playedSoundsCount + 1) % 256;
				String var5 = "sound_" + this.playedSoundsCount;
				this.sndSystem.newSource(false, var5, var4.soundUrl, var4.soundName, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
				if (var2 > 1.0F) {
					var2 = 1.0F;
				}

				var2 *= 0.25F;
				this.sndSystem.setPitch(var5, var3);
				this.sndSystem.setVolume(var5, var2);
				this.sndSystem.play(var5);
			}

		}
	}

}
