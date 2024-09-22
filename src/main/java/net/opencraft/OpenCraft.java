package net.opencraft;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.SandBlock;
import net.opencraft.client.config.GameSettings;
import net.opencraft.client.entity.*;
import net.opencraft.client.entity.models.ModelBiped;
import net.opencraft.client.input.*;
import net.opencraft.client.sound.SoundManager;
import net.opencraft.entity.EntityPlayerSP;
import net.opencraft.entity.EntityRenderer;
import net.opencraft.item.ItemStack;
import net.opencraft.physics.AABB;
import net.opencraft.renderer.*;
import net.opencraft.renderer.entity.RenderGlobal;
import net.opencraft.renderer.entity.Renderer;
import net.opencraft.renderer.font.FontRenderer;
import net.opencraft.renderer.gui.*;
import net.opencraft.renderer.texture.*;
import net.opencraft.tests.DownloadResourcesJob;
import net.opencraft.util.*;
import net.opencraft.world.World;
import net.opencraft.world.WorldRenderer;

public class OpenCraft implements Runnable {

	public static OpenCraft oc;

	public static long[] tickTimes;
	public static int numRecordedFrameTimes;
	private static File gameDir;
	public PlayerController playerController;
	private boolean fullscreen;
	public int width;
	public int height;
	private OpenGlCapsChecker glCapabilities;
	private Timer timer;
	public World world;
	public RenderGlobal renderGlobal;
	public EntityPlayerSP player;
	public EffectRenderer effectRenderer;
	public Session sessionData;
	public String minecraftUri;
	public boolean hideQuitButton;
	public volatile boolean isGamePaused;
	public Renderer renderer;
	public FontRenderer font;
	public GuiScreen currentScreen;
	public LoadingScreenRenderer loadingScreen;
	public EntityRenderer entityRenderer;
	private int ticksRan;
	private int leftClickCounter;
	private int tempDisplayWidth;
	private int tempDisplayHeight;
	public String objectMouseOverString;
	public int rightClickDelayTimer;
	public GuiIngame ingameGUI;
	public boolean skipRenderWorld;
	public ModelBiped field_9242_w;
	public MovingObjectPosition objectMouseOver;
	public GameSettings options;
	public SoundManager sndManager;
	public MouseHelper mouseHelper;
	public File mcDataDir;
	private TextureWaterFX textureWaterFX;
	private TextureLavaFX textureLavaFX;
	public volatile boolean running;
	public String debug;
	long prevFrameTime;
	public boolean inGameHasFocus;
	private int mouseTicksRan;
	public boolean isRaining;
	long systemTime;

	static {
		OpenCraft.tickTimes = new long[512];
		OpenCraft.numRecordedFrameTimes = 0;
		OpenCraft.gameDir = new File("opencraft");
		if (!OpenCraft.gameDir.exists()) {
			OpenCraft.gameDir.mkdir();
		}
	}

	public OpenCraft(final int width, final int height, final boolean boolean6) {
		oc = this;
		this.playerController = new PlayerControllerSP(oc);
		this.fullscreen = false;
		this.timer = new Timer(20.0f);
		this.sessionData = new Session("Notch", "1488228");
		this.hideQuitButton = true;
		this.isGamePaused = false;
		this.currentScreen = null;
		this.loadingScreen = new LoadingScreenRenderer(oc);
		this.entityRenderer = new EntityRenderer(oc);
		this.ticksRan = 0;
		this.leftClickCounter = 0;
		this.objectMouseOverString = null;
		this.rightClickDelayTimer = 0;
		this.skipRenderWorld = false;
		this.field_9242_w = new ModelBiped(0.0f);
		this.objectMouseOver = null;
		this.sndManager = new SoundManager();
		this.textureWaterFX = new TextureWaterFX();
		this.textureLavaFX = new TextureLavaFX();
		this.running = true;
		this.debug = "";
		this.prevFrameTime = -1L;
		this.inGameHasFocus = false;
		this.mouseTicksRan = 0;
		this.isRaining = false;
		this.systemTime = System.currentTimeMillis();
		this.tempDisplayWidth = width;
		this.tempDisplayHeight = height;
		this.fullscreen = boolean6;
		(new SleepingForeverThread("Timer hack thread")).start();
		this.width = width;
		this.height = height;
		this.fullscreen = boolean6;
	}

	public static long getSystemTime() {
		return Sys.getTime() * 1000L / Sys.getTimerResolution();
	}

	public void displayUnexpectedThrowable(final UnexpectedThrowable t) {
		t.exception.printStackTrace();
	}

	public void setServer(final String string, final int integer) {
	}

	public void init() throws LWJGLException {
		if (fullscreen) {
			Display.setFullscreen(true);
			width = Display.getDisplayMode().getWidth();
			height = Display.getDisplayMode().getHeight();
			if (width <= 0) {
				width = 1;
			}
			if (height <= 0) {
				height = 1;
			}
		} else {
			Display.setDisplayMode(new DisplayMode(width, height));
		}
		Display.setTitle("OpenCraft ".concat(SharedConstants.VERSION_STRING));
		Display.setResizable(true);
		try {
			PixelFormat pixelformat = new PixelFormat();
			pixelformat = pixelformat.withDepthBits(24);
			Display.create(pixelformat);
		} catch (LWJGLException ex) {
			System.exit(1);
		}
		mcDataDir = getMinecraftDir();
		options = new GameSettings(oc, mcDataDir);
		renderer = new Renderer(options);
		font = new FontRenderer(options, "/assets/default.png", renderer);
		loadScreen();
		Keyboard.create();
		Mouse.create();
		mouseHelper = new MouseHelper(null);
		try {
//            Controllers.create();
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		checkGLError();
		glEnable(GL_TEXTURE_2D);
		glShadeModel(7425);
		glClearDepth(1.0);
		glEnable(2929);
		glDepthFunc(515);
		glEnable(3008);
		glAlphaFunc(516, 0.1f);
		glCullFace(1029);
		glMatrixMode(5889);
		glLoadIdentity();
		glMatrixMode(5888);
		checkGLError();
		glCapabilities = new OpenGlCapsChecker();
		sndManager.loadSoundSettings(options);
		renderer.registerTextureFX(textureLavaFX);
		renderer.registerTextureFX(textureWaterFX);
		renderer.registerTextureFX(new TextureWaterFlowFX());
		renderer.registerTextureFX(new TextureLavaFlowFX());
		renderer.registerTextureFX(new TextureFlamesFX(0));
		renderer.registerTextureFX(new TextureFlamesFX(1));
		renderer.registerTextureFX(new TextureGearsFX(0));
		renderer.registerTextureFX(new TextureGearsFX(1));
		renderGlobal = new RenderGlobal(oc, renderer);
		glViewport(0, 0, width, height);
		displayGuiScreen(new GuiMainMenu());
		effectRenderer = new EffectRenderer(world, renderer);
		try {
			DownloadResourcesJob job = new DownloadResourcesJob(mcDataDir);
			job.start();
		} catch (Exception ex4) {
			ex4.printStackTrace();
		}
		checkGLError();
		ingameGUI = new GuiIngame(oc);
		playerController.a();
	}

	private void loadScreen() throws LWJGLException {
		final ScaledResolution scaledResolution = new ScaledResolution(width, height);
		final int scaledWidth = scaledResolution.getScaledWidth();
		final int scaledHeight = scaledResolution.getScaledHeight();
		glClear(16640);
		glMatrixMode(5889);
		glLoadIdentity();
		glOrtho(0.0, scaledWidth, scaledHeight, 0.0, 1000.0, 3000.0);
		glMatrixMode(5888);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -2000.0f);
		glViewport(0, 0, width, height);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glDisable(2896);
		glDisable(2912);
		glEnable(3553);
		final Tessellator t = Tessellator.instance;
		glBindTexture(3553, renderer.getTexture("/assets/dirt.png"));
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		final float n = 32.0f;
		t.beginQuads();
		t.setColorOpaque_I(4210752);
		t.vertexUV(0.0, height, 0.0, 0.0, height / n + 0.0f);
		t.vertexUV(width, height, 0.0, width / n, height / n + 0.0f);
		t.vertexUV(width, 0.0, 0.0, width / n, 0.0);
		t.vertexUV(0.0, 0.0, 0.0, 0.0, 0.0);
		t.draw();
		glEnable(3008);
		glAlphaFunc(516, 0.1f);
		font.drawStringWithShadow2("Loading...", 8, height / 2 - 16, -1);
		Display.swapBuffers();
	}

	public void displayGuiScreen(GuiScreen screen) {
		if (currentScreen instanceof GuiEmptyScreen)
			return;

		if (currentScreen != null)
			currentScreen.onGuiClosed();

		if (screen == null) {
			if (world == null)
				screen = new GuiMainMenu();
			else if (player.health <= 0)
				screen = new GuiGameOver();
		}

		if ((currentScreen = screen) != null) {
			setIngameNotInFocus();
			final ScaledResolution scaledResolution = new ScaledResolution(width, height);
			screen.setWorldAndResolution(oc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
			skipRenderWorld = false;
		} else {
			setIngameFocus();
		}
	}

	private void checkGLError() {
		final int err = glGetError();
		if (err == 0)
			return;

		throw new IllegalStateException("GL ERROR: ".concat(gluErrorString(err)));
	}

	public void stop() {
		try {
			System.out.println("Stopping!");
			changeWorld1(null);
			try {
				GLAllocation.deleteTexturesAndDisplayLists();
			} catch (Exception ex2) {
			}
			sndManager.shutdown();
			Mouse.destroy();
			Keyboard.destroy();
		} finally {
			Display.destroy();
		}
		Thread.currentThread().interrupt();
		System.gc();
		System.exit(0);
	}

	@Override
	public void run() {
		running = true;
		try {
			init();
		} catch (Exception exception) {
			exception.printStackTrace();
			displayUnexpectedThrowable(new UnexpectedThrowable("Failed to start game", exception));
			return;
		}
		try {
			long currentTimeMillis = System.currentTimeMillis();
			int n = 0;
			while (running) {
				AABB.clearBoundingBoxPool();
				if (Display.isCloseRequested()) {
					shutdown();
				}
				if (isGamePaused) {
					final float renderPartialTicks = timer.renderPartialTicks;
					timer.updateTimer();
					timer.renderPartialTicks = renderPartialTicks;
				} else {
					timer.updateTimer();
				}
				// for (int i = 0; i < minecraft.timer.elapsedTicks; ++i) {
				// ++minecraft.ticksRan;
				// minecraft.runTick();
				// }
				for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j) {
					++ticksRan;
					this.runTick();
				}
				checkGLError();
				if (isGamePaused)
					timer.renderPartialTicks = 1.0f;

				sndManager.setListener(player, timer.renderPartialTicks);
				glEnable(GL_TEXTURE_2D);
				if (world != null)
					while (world.updatingLighting());
				
				if (!skipRenderWorld) {
					playerController.setPartialTime(timer.renderPartialTicks);
					entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
				}
				if (!Display.isActive() && fullscreen)
					toggleFullscreen();
				
				if (Keyboard.isKeyDown(64))
					displayDebugInfo();
				else
					prevFrameTime = System.nanoTime();
				
				// Thread.yield();
				Display.update();
				if (Display.wasResized()) {
					width = Display.getWidth();
					height = Display.getHeight();
					if (width <= 0) {
						width = 1;
					}
					if (height <= 0) {
						height = 1;
					}
					resize(width, height);
				}
				if (options.limitFramerate)
					Thread.sleep(5L);
				
				checkGLError();
				++n;
				isGamePaused = (!isMultiplayerWorld() && currentScreen != null
						&& currentScreen.doesGuiPauseGame());
				while (System.currentTimeMillis() >= currentTimeMillis + 1000L) {
					debug = new StringBuilder().append(n).append(" fps, ").append(WorldRenderer.chunksUpdated)
							.append(" chunk updates").toString();
					WorldRenderer.chunksUpdated = 0;
					currentTimeMillis += 1000L;
					n = 0;
				}
			}
		} catch (OpenCraftError openCraftError) {
		} catch (Exception exception2) {
			exception2.printStackTrace();
			displayUnexpectedThrowable(new UnexpectedThrowable("Unexpected error", exception2));
		} finally {
			stop();
		}
	}

	private void displayDebugInfo() {
		if (prevFrameTime == -1L) {
			prevFrameTime = System.nanoTime();
		}
		final long nanoTime = System.nanoTime();
		OpenCraft.tickTimes[OpenCraft.numRecordedFrameTimes++ & OpenCraft.tickTimes.length - 1] = nanoTime
				- prevFrameTime;
		prevFrameTime = nanoTime;
		glClear(256);
		glMatrixMode(5889);
		glLoadIdentity();
		glOrtho(0.0, width, height, 0.0, 1000.0, 3000.0);
		glMatrixMode(5888);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -2000.0f);
		glLineWidth(1.0f);
		glDisable(3553);
		final Tessellator instance = Tessellator.instance;
		instance.begin(7);
		instance.setColorOpaque_I(538968064);
		instance.vertex(0.0, height - 100, 0.0);
		instance.vertex(0.0, height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, height - 100, 0.0);
		instance.draw();
		long n = 0L;
		for (int i = 0; i < OpenCraft.tickTimes.length; ++i) {
			n += OpenCraft.tickTimes[i];
		}
		int i = (int) (n / 200000L / OpenCraft.tickTimes.length);
		instance.begin(7);
		instance.setColorOpaque_I(541065216);
		instance.vertex(0.0, height - i, 0.0);
		instance.vertex(0.0, height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, height - i, 0.0);
		instance.draw();
		instance.begin(1);
		for (int j = 0; j < OpenCraft.tickTimes.length; ++j) {
			final int n2 = (j - OpenCraft.numRecordedFrameTimes & OpenCraft.tickTimes.length - 1) * 255
					/ OpenCraft.tickTimes.length;
			int n3 = n2 * n2 / 255;
			n3 = n3 * n3 / 255;
			int n4 = n3 * n3 / 255;
			n4 = n4 * n4 / 255;
			instance.setColorOpaque_I(-16777216 + n4 + n3 * 256 + n2 * 65536);
			instance.vertex(j + 0.5f, height - OpenCraft.tickTimes[j] / 200000L + 0.5f, 0.0);
			instance.vertex(j + 0.5f, height + 0.5f, 0.0);
		}
		instance.draw();
		glEnable(3553);
	}

	public void shutdown() {
		running = false;
	}

	public void setIngameFocus() {
		if (!Display.isActive()) {
			return;
		}
		if (inGameHasFocus) {
			return;
		}
		inGameHasFocus = true;
		mouseHelper.grabMouse();
		displayGuiScreen(null);
		mouseTicksRan = ticksRan + 10000;
	}

	public void setIngameNotInFocus() {
		if (!inGameHasFocus) {
			return;
		}
		if (player != null) {
			player.resetPlayerKeyState();
		}
		inGameHasFocus = false;
		mouseHelper.ungrabMouseCursor();
	}

	public void displayInGameMenu() {
		if (currentScreen != null) {
			return;
		}
		displayGuiScreen(new GuiIngameMenu());
	}

	private void func_6254_a(final int integer, final boolean boolean2) {
		if (playerController.field_1064_b) {
			return;
		}
		if (integer == 0 && leftClickCounter > 0) {
			return;
		}
		if (boolean2 && objectMouseOver != null && objectMouseOver.typeOfHit == 0 && integer == 0) {
			final int blockX = objectMouseOver.blockX;
			final int blockY = objectMouseOver.blockY;
			final int blockZ = objectMouseOver.blockZ;
			playerController.sendBlockRemoving(blockX, blockY, blockZ, objectMouseOver.sideHit);
			effectRenderer.addBlockHitEffects(blockX, blockY, blockZ, objectMouseOver.sideHit);
		} else {
			playerController.resetBlockRemoving();
		}
	}

	private void clickMouse(final int integer) {
		if (integer == 0 && leftClickCounter > 0) {
			return;
		}
		if (integer == 0) {
			entityRenderer.itemRenderer.resetEquippedProgress();
		}
		if (objectMouseOver == null) {
			if (integer == 0 && !(playerController instanceof PlayerControllerTest)) {
				leftClickCounter = 10;
			}
		} else if (objectMouseOver.typeOfHit == 1) {
			if (integer == 0) {
				player.a(objectMouseOver.entityHit);
			}
			if (integer == 1) {
				player.c(objectMouseOver.entityHit);
			}
		} else if (objectMouseOver.typeOfHit == 0) {
			final int blockX = objectMouseOver.blockX;
			final int n = objectMouseOver.blockY;
			final int blockZ = objectMouseOver.blockZ;
			final int sideHit = objectMouseOver.sideHit;
			final Block block = Block.blocksList[world.getBlockId(blockX, n, blockZ)];
			if (integer == 0) {
				world.onBlockHit(blockX, n, blockZ, objectMouseOver.sideHit);
				if (block != Block.bedrock || player.unusedByte >= 100) {
					playerController.clickBlock(blockX, n, blockZ);
				}
			} else {
				final ItemStack currentItem = player.inventory.getCurrentItem();
				final int blockId = world.getBlockId(blockX, n, blockZ);
				if (blockId > 0 && Block.blocksList[blockId].blockActivated(world, blockX, n, blockZ, player)) {
					return;
				}
				if (currentItem == null) {
					return;
				}
				final int stackSize = currentItem.stackSize;
				if (currentItem.useItem(player, world, blockX, n, blockZ, sideHit)) {
					entityRenderer.itemRenderer.resetEquippedProgress();
				}
				if (currentItem.stackSize == 0) {
					player.inventory.mainInventory[player.inventory.currentItem] = null;
				} else if (currentItem.stackSize != stackSize) {
					entityRenderer.itemRenderer.b();
				}
			}
		}
		if (integer == 1) {
			final ItemStack currentItem2 = player.inventory.getCurrentItem();
			if (currentItem2 != null) {
				final int n = currentItem2.stackSize;
				final ItemStack useItemRightClick = currentItem2.useItemRightClick(world, player);
				if (useItemRightClick != currentItem2
						|| (useItemRightClick != null && useItemRightClick.stackSize != n)) {
					player.inventory.mainInventory[player.inventory.currentItem] = useItemRightClick;
					entityRenderer.itemRenderer.d();
					if (useItemRightClick.stackSize == 0) {
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					}
				}
			}
		}
	}

	public void toggleFullscreen() {
		try {
			fullscreen = !fullscreen;
			System.out.println("Toggle fullscreen!");
			if (fullscreen) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				width = Display.getDisplayMode().getWidth();
				height = Display.getDisplayMode().getHeight();
				if (width <= 0) {
					width = 1;
				}
				if (height <= 0) {
					height = 1;
				}
			} else {

				width = tempDisplayWidth = Display.getWidth();
				height = tempDisplayHeight = Display.getHeight();
				if (width <= 0) {
					width = 1;
				}
				if (height <= 0) {
					height = 1;
				}
				Display.setDisplayMode(new DisplayMode(tempDisplayWidth, tempDisplayHeight));
			}
			setIngameNotInFocus();
			Display.setFullscreen(fullscreen);
			Display.update();
			Thread.sleep(1000L);
			if (fullscreen) {
				setIngameFocus();
			}
			if (currentScreen != null) {
				setIngameNotInFocus();
				resize(width, height);
			}
			System.out.println(
					new StringBuilder().append("Size: ").append(width).append(", ").append(height).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void resize(int integer1, int integer2) {
		if (integer1 <= 0) {
			integer1 = 1;
		}
		if (integer2 <= 0) {
			integer2 = 1;
		}
		width = integer1;
		height = integer2;
		if (currentScreen != null) {
			final ScaledResolution scaledResolution = new ScaledResolution(integer1, integer2);
			currentScreen.setWorldAndResolution(oc, scaledResolution.getScaledWidth(),
					scaledResolution.getScaledHeight());
		}
	}

	private void clickMiddleMouseButton() {
		if (objectMouseOver != null) {
			int integer = world.getBlockId(objectMouseOver.blockX, objectMouseOver.blockY,
					objectMouseOver.blockZ);
			if (integer == Block.grass.blockID) {
				integer = Block.dirt.blockID;
			}
			if (integer == Block.slabDouble.blockID) {
				integer = Block.slabSingle.blockID;
			}
			if (integer == Block.bedrock.blockID) {
				integer = Block.stone.blockID;
			}
			player.inventory.setCurrentItem(integer, playerController instanceof PlayerControllerTest);
		}
	}

	public void runTick() {
		ingameGUI.updateTick();
		if (!isGamePaused && world != null) {
			playerController.updateController();
		}
		glBindTexture(3553, renderer.getTexture("/assets/terrain.png"));
		if (!isGamePaused) {
			renderer.updateDynamicTextures();
		}
		if (currentScreen == null && player != null && player.health <= 0) {
			displayGuiScreen(null);
		}

		if (leftClickCounter > 0) {
			--leftClickCounter;
		}

		if (currentScreen == null || currentScreen.allowUserInput) {
			while (Mouse.next()) {
				if (System.currentTimeMillis() - systemTime > 200L) {
					continue;
				}
				final int eventDWheel = Mouse.getEventDWheel();
				if (eventDWheel != 0) {
					player.inventory.changeCurrentItem(eventDWheel);
				}
				if (currentScreen == null) {
					if (!inGameHasFocus && Mouse.getEventButtonState()) {
						setIngameFocus();
					} else {
						if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
							clickMouse(0);
							mouseTicksRan = ticksRan;
						}
						if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
							clickMouse(1);
							mouseTicksRan = ticksRan;
						}
						if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) {
							continue;
						}
						clickMiddleMouseButton();
					}
				} else {
					if (currentScreen == null) {
						continue;
					}
					currentScreen.f();
				}
			}

			while (Keyboard.next()) {
				player.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == 87) {
						toggleFullscreen();
					} else {
						if (currentScreen != null) {
							currentScreen.handleKeyboardInput();
						} else {
							if (Keyboard.getEventKey() == 1) {
								displayInGameMenu();
							}
							if (playerController instanceof PlayerControllerTest) {
								if (Keyboard.getEventKey() == options.keyBindLoad.keyCode) {
								}
								if (Keyboard.getEventKey() == options.keyBindSave.keyCode) {
								}
							}
							if (Keyboard.getEventKey() == 63) {
								options.thirdPersonView = !options.thirdPersonView;
								isRaining = !isRaining;
							}
							if (Keyboard.getEventKey() == options.keyBindInventory.keyCode) {
								displayGuiScreen(new GuiInventory(player.inventory));
							}
							if (Keyboard.getEventKey() == options.keyBindDrop.keyCode) {
								player.dropPlayerItemWithRandomChoice(
										player.inventory.decrStackSize(player.inventory.currentItem, 1), false);
							}
						}
						for (int i = 0; i < 9; ++i) {
							if (Keyboard.getEventKey() == 2 + i) {
								player.inventory.currentItem = i;
							}
						}
						if (Keyboard.getEventKey() != options.keyBindToggleFog.keyCode) {
							continue;
						}
						options.setOptionFloatValue(4,
								(Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) ? -1 : 1);
					}
				}
			}
			if (currentScreen == null) {
				if (Mouse.isButtonDown(0) && ticksRan - mouseTicksRan >= timer.tps / 4.0f
						&& inGameHasFocus) {
					clickMouse(0);
					mouseTicksRan = ticksRan;
				}
				if (Mouse.isButtonDown(1) && ticksRan - mouseTicksRan >= timer.tps / 4.0f
						&& inGameHasFocus) {
					clickMouse(1);
					mouseTicksRan = ticksRan;
				}
			}
			func_6254_a(0, currentScreen == null && Mouse.isButtonDown(0) && inGameHasFocus);
		}
		if (currentScreen != null) {
			mouseTicksRan = ticksRan + 10000;
		}
		if (currentScreen != null) {
			currentScreen.e();
			if (currentScreen != null) {
				currentScreen.updateScreen();
			}
		}
		if (world != null) {
			world.difficultySetting = options.difficulty;
			if (!isGamePaused) {
				entityRenderer.updateRenderer();
			}
			if (!isGamePaused) {
				renderGlobal.updateClouds();
			}
			if (!isGamePaused) {
				world.updateEntities();
			}
			if (!isGamePaused && !isMultiplayerWorld()) {
				world.tick();
			}
			if (!isGamePaused) {
				world.randomDisplayUpdates(Mth.floor_double(player.posX), Mth.floor_double(player.posY),
						Mth.floor_double(player.posZ));
			}
			if (!isGamePaused) {
				effectRenderer.updateEffects();
			}
		}
		systemTime = System.currentTimeMillis();
	}

	public boolean isMultiplayerWorld() {
		return false;
	}

	public void startWorld(final String string) {
		changeWorld1(null);
		System.gc();
		final World world = new World(new File(getMinecraftDir(), "saves"), string);
		if (world.isNewWorld) {
			changeWorld2(world, "Generating level");
		} else {
			changeWorld2(world, "Loading level");
		}
	}

	public void changeWorld1(final World fe) {
		changeWorld2(fe, "");
	}

	public void changeWorld2(final World fe, final String string) {
		if (world != null) {
			world.saveWorldIndirectly(loadingScreen);
		}
		if ((world = fe) != null) {
			playerController.func_717_a(fe);
			fe.h = font;
			if (!isMultiplayerWorld()) {
				player = (EntityPlayerSP) fe.func_4085_a(EntityPlayerSP.class);
				fe.player = player;
			} else if (player != null) {
				player.preparePlayerToSpawn();
				if (fe != null) {
					fe.player = player;
					fe.entityJoinedWorld(player);
				}
			}
			func_6255_d(string);
			if (player == null) {
				(player = new EntityPlayerSP(oc, fe, sessionData)).preparePlayerToSpawn();
				playerController.flipPlayer(player);
			}
			player.movementInput = new MovementInputFromOptions(options);
			if (renderGlobal != null) {
				renderGlobal.changeWorld(fe);
			}
			if (effectRenderer != null) {
				effectRenderer.clearEffects(fe);
			}
			playerController.func_6473_b(player);
			fe.player = player;
			fe.spawnPlayerWithLoadedChunks();
			if (fe.isNewWorld) {
				fe.saveWorldIndirectly(loadingScreen);
			}
		}
		System.gc();
		systemTime = 0L;
		this.sndManager.currentMusicTheme = "ingame";
		this.sndManager.ticksBeforeMusic = 0;
		this.sndManager.stopSound("BgMusic");
	}

	private void func_6255_d(final String string) {
		loadingScreen.printText(string);
		loadingScreen.displayLoadingString("Building terrain");
		final int n = 128;
		int n2 = 0;
		int n3 = n * 2 / 16 + 1;
		n3 *= n3;
		for (int i = -n; i <= n; i += 16) {
			int x = world.x;
			int z = world.z;
			if (world.player != null) {
				x = (int) world.player.posX;
				z = (int) world.player.posZ;
			}
			for (int j = -n; j <= n; j += 16) {
				loadingScreen.setLoadingProgress(n2++ * 100 / n3);
				world.getBlockId(x + i, 64, z + j);
				while (world.updatingLighting()) {
				}
			}
		}
		loadingScreen.displayLoadingString("Simulating world for a bit");
		n3 = 2000;
		SandBlock.fallInstantly = true;
		for (int i = 0; i < n3; ++i) {
			world.TickUpdates(true);
		}
		world.func_656_j();
		SandBlock.fallInstantly = false;
	}

	public void installResource(String string, final File file) {
		final int index = string.indexOf("/");
		final String substring = string.substring(0, index);
		string = string.substring(index + 1);
		if (substring.equalsIgnoreCase("sound")) {
			sndManager.addSound(string, file);
		} else if (substring.equalsIgnoreCase("newsound")) {
			sndManager.addSound(string, file);
		} else if (substring.equalsIgnoreCase("music")) {
			sndManager.addIngameMusic(string, file);
		} else if (substring.equalsIgnoreCase("newmusic")) {
			sndManager.addIngameMusic(string, file);
		} else if (substring.equalsIgnoreCase("menumusic")) {
			sndManager.addMenuMusic(string, file);
		}
	}

	public OpenGlCapsChecker getOpenGlCapsChecker() {
		return glCapabilities;
	}

	public String debugInfoRenders() {
		return renderGlobal.getDebugInfoRenders();
	}

	public String func_6262_n() {
		return renderGlobal.getDebugInfoEntities();
	}

	public String debugInfoEntities() {
		return "P: " + effectRenderer.getStatistics() + ". T: " + world.func_687_d();
	}

	public void respawn() {
		if (player != null && world != null) {
			world.setEntityDead(player);
		}
		world.a();
		(player = new EntityPlayerSP(oc, world, sessionData)).preparePlayerToSpawn();
		playerController.flipPlayer(player);
		if (world != null) {
			world.player = player;
			world.spawnPlayerWithLoadedChunks();
		}
		player.movementInput = new MovementInputFromOptions(options);
		playerController.func_6473_b(player);
		func_6255_d("Respawning");
	}

	public static File getMinecraftDir() {
		if (OpenCraft.gameDir == null) {
			OpenCraft.gameDir = getAppDir("minecraft");
		}
		return OpenCraft.gameDir;
	}

	public static File getAppDir(final String string) {
		final String property = System.getProperty("user.home", ".");
		File file = null;
		switch (EnumOSMappingHelper.enumOSMappingArray[Platform.getOs().ordinal()]) {
			case 1:
			case 2: {
				file = new File(property, '.' + string + '/');
				break;
			}

			case 3: {
				final String getenv = System.getenv("APPDATA");
				if (getenv != null) {
					file = new File(getenv, "." + string + '/');
					break;
				}
				file = new File(property, '.' + string + '/');
				break;
			}

			case 4: {
				file = new File(property, "Library/Application Support/" + string);
				break;
			}

			default: {
				file = new File(property, string + '/');
				break;
			}
		}
		if (!file.exists() && !file.mkdirs()) {
			throw new RuntimeException(
					new StringBuilder().append("The working directory could not be created: ").append(file).toString());
		}
		return file;
	}

	public float getTickDelta() {
		return this.timer.tickDelta;
	}
}
