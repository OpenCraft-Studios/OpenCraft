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
	public FontRenderer fontRenderer;
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
		if (oc.fullscreen) {
			Display.setFullscreen(true);
			oc.width = Display.getDisplayMode().getWidth();
			oc.height = Display.getDisplayMode().getHeight();
			if (oc.width <= 0) {
				oc.width = 1;
			}
			if (oc.height <= 0) {
				oc.height = 1;
			}
		} else {
			Display.setDisplayMode(new DisplayMode(oc.width, oc.height));
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
		oc.mcDataDir = getMinecraftDir();
		oc.options = new GameSettings(oc, oc.mcDataDir);
		oc.renderer = new Renderer(oc.options);
		oc.fontRenderer = new FontRenderer(oc.options, "/assets/default.png", oc.renderer);
		oc.loadScreen();
		Keyboard.create();
		Mouse.create();
		oc.mouseHelper = new MouseHelper(null);
		try {
//            Controllers.create();
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		oc.checkGLError();
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
		oc.checkGLError();
		oc.glCapabilities = new OpenGlCapsChecker();
		oc.sndManager.loadSoundSettings(oc.options);
		oc.renderer.registerTextureFX(oc.textureLavaFX);
		oc.renderer.registerTextureFX(oc.textureWaterFX);
		oc.renderer.registerTextureFX(new TextureWaterFlowFX());
		oc.renderer.registerTextureFX(new TextureLavaFlowFX());
		oc.renderer.registerTextureFX(new TextureFlamesFX(0));
		oc.renderer.registerTextureFX(new TextureFlamesFX(1));
		oc.renderer.registerTextureFX(new TextureGearsFX(0));
		oc.renderer.registerTextureFX(new TextureGearsFX(1));
		oc.renderGlobal = new RenderGlobal(oc, oc.renderer);
		glViewport(0, 0, oc.width, oc.height);
		oc.displayGuiScreen(new GuiMainMenu());
		oc.effectRenderer = new EffectRenderer(oc.world, oc.renderer);
		try {
			DownloadResourcesJob job = new DownloadResourcesJob(mcDataDir);
			job.start();
		} catch (Exception ex4) {
			ex4.printStackTrace();
		}
		oc.checkGLError();
		oc.ingameGUI = new GuiIngame(oc);
		oc.playerController.a();
	}

	private void loadScreen() throws LWJGLException {
		final ScaledResolution scaledResolution = new ScaledResolution(oc.width, oc.height);
		final int scaledWidth = scaledResolution.getScaledWidth();
		final int scaledHeight = scaledResolution.getScaledHeight();
		glClear(16640);
		glMatrixMode(5889);
		glLoadIdentity();
		glOrtho(0.0, scaledWidth, scaledHeight, 0.0, 1000.0, 3000.0);
		glMatrixMode(5888);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -2000.0f);
		glViewport(0, 0, oc.width, oc.height);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glDisable(2896);
		glDisable(2912);
		glEnable(3553);
		final Tessellator t = Tessellator.instance;
		glBindTexture(3553, oc.renderer.getTexture("/assets/dirt.png"));
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		final float n = 32.0f;
		t.beginQuads();
		t.setColorOpaque_I(4210752);
		t.vertexUV(0.0, oc.height, 0.0, 0.0, oc.height / n + 0.0f);
		t.vertexUV(oc.width, oc.height, 0.0, oc.width / n, oc.height / n + 0.0f);
		t.vertexUV(oc.width, 0.0, 0.0, oc.width / n, 0.0);
		t.vertexUV(0.0, 0.0, 0.0, 0.0, 0.0);
		t.draw();
		glEnable(3008);
		glAlphaFunc(516, 0.1f);
		oc.fontRenderer.drawStringWithShadow2("Loading...", 8, oc.height / 2 - 16, -1);
		Display.swapBuffers();
	}

	public void displayGuiScreen(GuiScreen screen) {
		if (oc.currentScreen instanceof GuiEmptyScreen)
			return;

		if (oc.currentScreen != null)
			oc.currentScreen.onGuiClosed();

		if (screen == null) {
			if (oc.world == null)
				screen = new GuiMainMenu();
			else if (oc.player.health <= 0)
				screen = new GuiGameOver();
		}

		if ((oc.currentScreen = screen) != null) {
			oc.setIngameNotInFocus();
			final ScaledResolution scaledResolution = new ScaledResolution(oc.width, oc.height);
			screen.setWorldAndResolution(oc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
			oc.skipRenderWorld = false;
		} else {
			oc.setIngameFocus();
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
			oc.changeWorld1(null);
			try {
				GLAllocation.deleteTexturesAndDisplayLists();
			} catch (Exception ex2) {
			}
			oc.sndManager.shutdown();
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
		oc.running = true;
		try {
			oc.init();
		} catch (Exception exception) {
			exception.printStackTrace();
			oc.displayUnexpectedThrowable(new UnexpectedThrowable("Failed to start game", exception));
			return;
		}
		try {
			long currentTimeMillis = System.currentTimeMillis();
			int n = 0;
			while (oc.running) {
				AABB.clearBoundingBoxPool();
				if (Display.isCloseRequested()) {
					oc.shutdown();
				}
				if (oc.isGamePaused) {
					final float renderPartialTicks = oc.timer.renderPartialTicks;
					oc.timer.updateTimer();
					oc.timer.renderPartialTicks = renderPartialTicks;
				} else {
					oc.timer.updateTimer();
				}
				// for (int i = 0; i < minecraft.timer.elapsedTicks; ++i) {
				// ++minecraft.ticksRan;
				// minecraft.runTick();
				// }
				for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j) {
					++oc.ticksRan;
					this.runTick();
				}
				oc.checkGLError();
				if (oc.isGamePaused)
					oc.timer.renderPartialTicks = 1.0f;

				oc.sndManager.setListener(oc.player, oc.timer.renderPartialTicks);
				glEnable(GL_TEXTURE_2D);
				if (oc.world != null)
					while (oc.world.updatingLighting());
				
				if (!oc.skipRenderWorld) {
					oc.playerController.setPartialTime(oc.timer.renderPartialTicks);
					oc.entityRenderer.updateCameraAndRender(oc.timer.renderPartialTicks);
				}
				if (!Display.isActive() && oc.fullscreen)
					oc.toggleFullscreen();
				
				if (Keyboard.isKeyDown(64))
					oc.displayDebugInfo();
				else
					oc.prevFrameTime = System.nanoTime();
				
				// Thread.yield();
				Display.update();
				if (Display.wasResized()) {
					oc.width = Display.getWidth();
					oc.height = Display.getHeight();
					if (oc.width <= 0) {
						oc.width = 1;
					}
					if (oc.height <= 0) {
						oc.height = 1;
					}
					oc.resize(oc.width, oc.height);
				}
				if (oc.options.limitFramerate)
					Thread.sleep(5L);
				
				oc.checkGLError();
				++n;
				oc.isGamePaused = (!oc.isMultiplayerWorld() && oc.currentScreen != null
						&& oc.currentScreen.doesGuiPauseGame());
				while (System.currentTimeMillis() >= currentTimeMillis + 1000L) {
					oc.debug = new StringBuilder().append(n).append(" fps, ").append(WorldRenderer.chunksUpdated)
							.append(" chunk updates").toString();
					WorldRenderer.chunksUpdated = 0;
					currentTimeMillis += 1000L;
					n = 0;
				}
			}
		} catch (OpenCraftError openCraftError) {
		} catch (Exception exception2) {
			exception2.printStackTrace();
			oc.displayUnexpectedThrowable(new UnexpectedThrowable("Unexpected error", exception2));
		} finally {
			oc.stop();
		}
	}

	private void displayDebugInfo() {
		if (oc.prevFrameTime == -1L) {
			oc.prevFrameTime = System.nanoTime();
		}
		final long nanoTime = System.nanoTime();
		OpenCraft.tickTimes[OpenCraft.numRecordedFrameTimes++ & OpenCraft.tickTimes.length - 1] = nanoTime
				- oc.prevFrameTime;
		oc.prevFrameTime = nanoTime;
		glClear(256);
		glMatrixMode(5889);
		glLoadIdentity();
		glOrtho(0.0, oc.width, oc.height, 0.0, 1000.0, 3000.0);
		glMatrixMode(5888);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -2000.0f);
		glLineWidth(1.0f);
		glDisable(3553);
		final Tessellator instance = Tessellator.instance;
		instance.begin(7);
		instance.setColorOpaque_I(538968064);
		instance.vertex(0.0, oc.height - 100, 0.0);
		instance.vertex(0.0, oc.height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, oc.height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, oc.height - 100, 0.0);
		instance.draw();
		long n = 0L;
		for (int i = 0; i < OpenCraft.tickTimes.length; ++i) {
			n += OpenCraft.tickTimes[i];
		}
		int i = (int) (n / 200000L / OpenCraft.tickTimes.length);
		instance.begin(7);
		instance.setColorOpaque_I(541065216);
		instance.vertex(0.0, oc.height - i, 0.0);
		instance.vertex(0.0, oc.height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, oc.height, 0.0);
		instance.vertex(OpenCraft.tickTimes.length, oc.height - i, 0.0);
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
			instance.vertex(j + 0.5f, oc.height - OpenCraft.tickTimes[j] / 200000L + 0.5f, 0.0);
			instance.vertex(j + 0.5f, oc.height + 0.5f, 0.0);
		}
		instance.draw();
		glEnable(3553);
	}

	public void shutdown() {
		oc.running = false;
	}

	public void setIngameFocus() {
		if (!Display.isActive()) {
			return;
		}
		if (oc.inGameHasFocus) {
			return;
		}
		oc.inGameHasFocus = true;
		oc.mouseHelper.grabMouse();
		oc.displayGuiScreen(null);
		oc.mouseTicksRan = oc.ticksRan + 10000;
	}

	public void setIngameNotInFocus() {
		if (!oc.inGameHasFocus) {
			return;
		}
		if (oc.player != null) {
			oc.player.resetPlayerKeyState();
		}
		oc.inGameHasFocus = false;
		oc.mouseHelper.ungrabMouseCursor();
	}

	public void displayInGameMenu() {
		if (oc.currentScreen != null) {
			return;
		}
		oc.displayGuiScreen(new GuiIngameMenu());
	}

	private void func_6254_a(final int integer, final boolean boolean2) {
		if (oc.playerController.field_1064_b) {
			return;
		}
		if (integer == 0 && oc.leftClickCounter > 0) {
			return;
		}
		if (boolean2 && oc.objectMouseOver != null && oc.objectMouseOver.typeOfHit == 0 && integer == 0) {
			final int blockX = oc.objectMouseOver.blockX;
			final int blockY = oc.objectMouseOver.blockY;
			final int blockZ = oc.objectMouseOver.blockZ;
			oc.playerController.sendBlockRemoving(blockX, blockY, blockZ, oc.objectMouseOver.sideHit);
			oc.effectRenderer.addBlockHitEffects(blockX, blockY, blockZ, oc.objectMouseOver.sideHit);
		} else {
			oc.playerController.resetBlockRemoving();
		}
	}

	private void clickMouse(final int integer) {
		if (integer == 0 && oc.leftClickCounter > 0) {
			return;
		}
		if (integer == 0) {
			oc.entityRenderer.itemRenderer.resetEquippedProgress();
		}
		if (oc.objectMouseOver == null) {
			if (integer == 0 && !(oc.playerController instanceof PlayerControllerTest)) {
				oc.leftClickCounter = 10;
			}
		} else if (oc.objectMouseOver.typeOfHit == 1) {
			if (integer == 0) {
				oc.player.a(oc.objectMouseOver.entityHit);
			}
			if (integer == 1) {
				oc.player.c(oc.objectMouseOver.entityHit);
			}
		} else if (oc.objectMouseOver.typeOfHit == 0) {
			final int blockX = oc.objectMouseOver.blockX;
			final int n = oc.objectMouseOver.blockY;
			final int blockZ = oc.objectMouseOver.blockZ;
			final int sideHit = oc.objectMouseOver.sideHit;
			final Block block = Block.blocksList[oc.world.getBlockId(blockX, n, blockZ)];
			if (integer == 0) {
				oc.world.onBlockHit(blockX, n, blockZ, oc.objectMouseOver.sideHit);
				if (block != Block.bedrock || oc.player.unusedByte >= 100) {
					oc.playerController.clickBlock(blockX, n, blockZ);
				}
			} else {
				final ItemStack currentItem = oc.player.inventory.getCurrentItem();
				final int blockId = oc.world.getBlockId(blockX, n, blockZ);
				if (blockId > 0 && Block.blocksList[blockId].blockActivated(oc.world, blockX, n, blockZ, oc.player)) {
					return;
				}
				if (currentItem == null) {
					return;
				}
				final int stackSize = currentItem.stackSize;
				if (currentItem.useItem(oc.player, oc.world, blockX, n, blockZ, sideHit)) {
					oc.entityRenderer.itemRenderer.resetEquippedProgress();
				}
				if (currentItem.stackSize == 0) {
					oc.player.inventory.mainInventory[oc.player.inventory.currentItem] = null;
				} else if (currentItem.stackSize != stackSize) {
					oc.entityRenderer.itemRenderer.b();
				}
			}
		}
		if (integer == 1) {
			final ItemStack currentItem2 = oc.player.inventory.getCurrentItem();
			if (currentItem2 != null) {
				final int n = currentItem2.stackSize;
				final ItemStack useItemRightClick = currentItem2.useItemRightClick(oc.world, oc.player);
				if (useItemRightClick != currentItem2
						|| (useItemRightClick != null && useItemRightClick.stackSize != n)) {
					oc.player.inventory.mainInventory[oc.player.inventory.currentItem] = useItemRightClick;
					oc.entityRenderer.itemRenderer.d();
					if (useItemRightClick.stackSize == 0) {
						oc.player.inventory.mainInventory[oc.player.inventory.currentItem] = null;
					}
				}
			}
		}
	}

	public void toggleFullscreen() {
		try {
			oc.fullscreen = !oc.fullscreen;
			System.out.println("Toggle fullscreen!");
			if (oc.fullscreen) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				oc.width = Display.getDisplayMode().getWidth();
				oc.height = Display.getDisplayMode().getHeight();
				if (oc.width <= 0) {
					oc.width = 1;
				}
				if (oc.height <= 0) {
					oc.height = 1;
				}
			} else {

				oc.width = oc.tempDisplayWidth = Display.getWidth();
				oc.height = oc.tempDisplayHeight = Display.getHeight();
				if (oc.width <= 0) {
					oc.width = 1;
				}
				if (oc.height <= 0) {
					oc.height = 1;
				}
				Display.setDisplayMode(new DisplayMode(oc.tempDisplayWidth, oc.tempDisplayHeight));
			}
			oc.setIngameNotInFocus();
			Display.setFullscreen(oc.fullscreen);
			Display.update();
			Thread.sleep(1000L);
			if (oc.fullscreen) {
				oc.setIngameFocus();
			}
			if (oc.currentScreen != null) {
				oc.setIngameNotInFocus();
				oc.resize(oc.width, oc.height);
			}
			System.out.println(
					new StringBuilder().append("Size: ").append(oc.width).append(", ").append(oc.height).toString());
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
		oc.width = integer1;
		oc.height = integer2;
		if (oc.currentScreen != null) {
			final ScaledResolution scaledResolution = new ScaledResolution(integer1, integer2);
			oc.currentScreen.setWorldAndResolution(oc, scaledResolution.getScaledWidth(),
					scaledResolution.getScaledHeight());
		}
	}

	private void clickMiddleMouseButton() {
		if (oc.objectMouseOver != null) {
			int integer = oc.world.getBlockId(oc.objectMouseOver.blockX, oc.objectMouseOver.blockY,
					oc.objectMouseOver.blockZ);
			if (integer == Block.grass.blockID) {
				integer = Block.dirt.blockID;
			}
			if (integer == Block.slabDouble.blockID) {
				integer = Block.slabSingle.blockID;
			}
			if (integer == Block.bedrock.blockID) {
				integer = Block.stone.blockID;
			}
			oc.player.inventory.setCurrentItem(integer, oc.playerController instanceof PlayerControllerTest);
		}
	}

	public void runTick() {
		oc.ingameGUI.updateTick();
		if (!oc.isGamePaused && oc.world != null) {
			oc.playerController.updateController();
		}
		glBindTexture(3553, oc.renderer.getTexture("/assets/terrain.png"));
		if (!oc.isGamePaused) {
			oc.renderer.updateDynamicTextures();
		}
		if (oc.currentScreen == null && oc.player != null && oc.player.health <= 0) {
			oc.displayGuiScreen(null);
		}

		if (oc.leftClickCounter > 0) {
			--oc.leftClickCounter;
		}

		if (oc.currentScreen == null || oc.currentScreen.allowUserInput) {
			while (Mouse.next()) {
				if (System.currentTimeMillis() - oc.systemTime > 200L) {
					continue;
				}
				final int eventDWheel = Mouse.getEventDWheel();
				if (eventDWheel != 0) {
					oc.player.inventory.changeCurrentItem(eventDWheel);
				}
				if (oc.currentScreen == null) {
					if (!oc.inGameHasFocus && Mouse.getEventButtonState()) {
						oc.setIngameFocus();
					} else {
						if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
							oc.clickMouse(0);
							oc.mouseTicksRan = oc.ticksRan;
						}
						if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
							oc.clickMouse(1);
							oc.mouseTicksRan = oc.ticksRan;
						}
						if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) {
							continue;
						}
						oc.clickMiddleMouseButton();
					}
				} else {
					if (oc.currentScreen == null) {
						continue;
					}
					oc.currentScreen.f();
				}
			}

			while (Keyboard.next()) {
				oc.player.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == 87) {
						oc.toggleFullscreen();
					} else {
						if (oc.currentScreen != null) {
							oc.currentScreen.handleKeyboardInput();
						} else {
							if (Keyboard.getEventKey() == 1) {
								oc.displayInGameMenu();
							}
							if (oc.playerController instanceof PlayerControllerTest) {
								if (Keyboard.getEventKey() == oc.options.keyBindLoad.keyCode) {
								}
								if (Keyboard.getEventKey() == oc.options.keyBindSave.keyCode) {
								}
							}
							if (Keyboard.getEventKey() == 63) {
								oc.options.thirdPersonView = !oc.options.thirdPersonView;
								oc.isRaining = !oc.isRaining;
							}
							if (Keyboard.getEventKey() == oc.options.keyBindInventory.keyCode) {
								oc.displayGuiScreen(new GuiInventory(oc.player.inventory));
							}
							if (Keyboard.getEventKey() == oc.options.keyBindDrop.keyCode) {
								oc.player.dropPlayerItemWithRandomChoice(
										oc.player.inventory.decrStackSize(oc.player.inventory.currentItem, 1), false);
							}
						}
						for (int i = 0; i < 9; ++i) {
							if (Keyboard.getEventKey() == 2 + i) {
								oc.player.inventory.currentItem = i;
							}
						}
						if (Keyboard.getEventKey() != oc.options.keyBindToggleFog.keyCode) {
							continue;
						}
						oc.options.setOptionFloatValue(4,
								(Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) ? -1 : 1);
					}
				}
			}
			if (oc.currentScreen == null) {
				if (Mouse.isButtonDown(0) && oc.ticksRan - oc.mouseTicksRan >= oc.timer.tps / 4.0f
						&& oc.inGameHasFocus) {
					oc.clickMouse(0);
					oc.mouseTicksRan = oc.ticksRan;
				}
				if (Mouse.isButtonDown(1) && oc.ticksRan - oc.mouseTicksRan >= oc.timer.tps / 4.0f
						&& oc.inGameHasFocus) {
					oc.clickMouse(1);
					oc.mouseTicksRan = oc.ticksRan;
				}
			}
			oc.func_6254_a(0, oc.currentScreen == null && Mouse.isButtonDown(0) && oc.inGameHasFocus);
		}
		if (oc.currentScreen != null) {
			oc.mouseTicksRan = oc.ticksRan + 10000;
		}
		if (oc.currentScreen != null) {
			oc.currentScreen.e();
			if (oc.currentScreen != null) {
				oc.currentScreen.updateScreen();
			}
		}
		if (oc.world != null) {
			oc.world.difficultySetting = oc.options.difficulty;
			if (!oc.isGamePaused) {
				oc.entityRenderer.updateRenderer();
			}
			if (!oc.isGamePaused) {
				oc.renderGlobal.updateClouds();
			}
			if (!oc.isGamePaused) {
				oc.world.updateEntities();
			}
			if (!oc.isGamePaused && !oc.isMultiplayerWorld()) {
				oc.world.tick();
			}
			if (!oc.isGamePaused) {
				oc.world.randomDisplayUpdates(Mth.floor_double(oc.player.posX), Mth.floor_double(oc.player.posY),
						Mth.floor_double(oc.player.posZ));
			}
			if (!oc.isGamePaused) {
				oc.effectRenderer.updateEffects();
			}
		}
		oc.systemTime = System.currentTimeMillis();
	}

	public boolean isMultiplayerWorld() {
		return false;
	}

	public void startWorld(final String string) {
		oc.changeWorld1(null);
		System.gc();
		final World world = new World(new File(getMinecraftDir(), "saves"), string);
		if (world.isNewWorld) {
			oc.changeWorld2(world, "Generating level");
		} else {
			oc.changeWorld2(world, "Loading level");
		}
	}

	public void changeWorld1(final World fe) {
		oc.changeWorld2(fe, "");
	}

	public void changeWorld2(final World fe, final String string) {
		if (oc.world != null) {
			oc.world.saveWorldIndirectly(oc.loadingScreen);
		}
		if ((oc.world = fe) != null) {
			oc.playerController.func_717_a(fe);
			fe.h = oc.fontRenderer;
			if (!oc.isMultiplayerWorld()) {
				oc.player = (EntityPlayerSP) fe.func_4085_a(EntityPlayerSP.class);
				fe.player = oc.player;
			} else if (oc.player != null) {
				oc.player.preparePlayerToSpawn();
				if (fe != null) {
					fe.player = oc.player;
					fe.entityJoinedWorld(oc.player);
				}
			}
			oc.func_6255_d(string);
			if (oc.player == null) {
				(oc.player = new EntityPlayerSP(oc, fe, oc.sessionData)).preparePlayerToSpawn();
				oc.playerController.flipPlayer(oc.player);
			}
			oc.player.movementInput = new MovementInputFromOptions(oc.options);
			if (oc.renderGlobal != null) {
				oc.renderGlobal.changeWorld(fe);
			}
			if (oc.effectRenderer != null) {
				oc.effectRenderer.clearEffects(fe);
			}
			oc.playerController.func_6473_b(oc.player);
			fe.player = oc.player;
			fe.spawnPlayerWithLoadedChunks();
			if (fe.isNewWorld) {
				fe.saveWorldIndirectly(oc.loadingScreen);
			}
		}
		System.gc();
		oc.systemTime = 0L;
		this.sndManager.currentMusicTheme = "ingame";
		this.sndManager.ticksBeforeMusic = 0;
		this.sndManager.stopSound("BgMusic");
	}

	private void func_6255_d(final String string) {
		oc.loadingScreen.printText(string);
		oc.loadingScreen.displayLoadingString("Building terrain");
		final int n = 128;
		int n2 = 0;
		int n3 = n * 2 / 16 + 1;
		n3 *= n3;
		for (int i = -n; i <= n; i += 16) {
			int x = oc.world.x;
			int z = oc.world.z;
			if (oc.world.player != null) {
				x = (int) oc.world.player.posX;
				z = (int) oc.world.player.posZ;
			}
			for (int j = -n; j <= n; j += 16) {
				oc.loadingScreen.setLoadingProgress(n2++ * 100 / n3);
				oc.world.getBlockId(x + i, 64, z + j);
				while (oc.world.updatingLighting()) {
				}
			}
		}
		oc.loadingScreen.displayLoadingString("Simulating world for a bit");
		n3 = 2000;
		SandBlock.fallInstantly = true;
		for (int i = 0; i < n3; ++i) {
			oc.world.TickUpdates(true);
		}
		oc.world.func_656_j();
		SandBlock.fallInstantly = false;
	}

	public void installResource(String string, final File file) {
		final int index = string.indexOf("/");
		final String substring = string.substring(0, index);
		string = string.substring(index + 1);
		if (substring.equalsIgnoreCase("sound")) {
			oc.sndManager.addSound(string, file);
		} else if (substring.equalsIgnoreCase("newsound")) {
			oc.sndManager.addSound(string, file);
		} else if (substring.equalsIgnoreCase("music")) {
			oc.sndManager.addIngameMusic(string, file);
		} else if (substring.equalsIgnoreCase("newmusic")) {
			oc.sndManager.addIngameMusic(string, file);
		} else if (substring.equalsIgnoreCase("menumusic")) {
			oc.sndManager.addMenuMusic(string, file);
		}
	}

	public OpenGlCapsChecker getOpenGlCapsChecker() {
		return oc.glCapabilities;
	}

	public String debugInfoRenders() {
		return oc.renderGlobal.getDebugInfoRenders();
	}

	public String func_6262_n() {
		return oc.renderGlobal.getDebugInfoEntities();
	}

	public String debugInfoEntities() {
		return "P: " + oc.effectRenderer.getStatistics() + ". T: " + oc.world.func_687_d();
	}

	public void respawn() {
		if (oc.player != null && oc.world != null) {
			oc.world.setEntityDead(oc.player);
		}
		oc.world.a();
		(oc.player = new EntityPlayerSP(oc, oc.world, oc.sessionData)).preparePlayerToSpawn();
		oc.playerController.flipPlayer(oc.player);
		if (oc.world != null) {
			oc.world.player = oc.player;
			oc.world.spawnPlayerWithLoadedChunks();
		}
		oc.player.movementInput = new MovementInputFromOptions(oc.options);
		oc.playerController.func_6473_b(oc.player);
		oc.func_6255_d("Respawning");
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
		switch (EnumOSMappingHelper.enumOSMappingArray[getOs().ordinal()]) {
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

	private static EnumOS2 getOs() {
		final String lowerCase = System.getProperty("os.name").toLowerCase();
		if (lowerCase.contains("win")) {
			return EnumOS2.windows;
		}
		if (lowerCase.contains("mac")) {
			return EnumOS2.macos;
		}
		if (lowerCase.contains("solaris")) {
			return EnumOS2.solaris;
		}
		if (lowerCase.contains("sunos")) {
			return EnumOS2.solaris;
		}
		if (lowerCase.contains("linux")) {
			return EnumOS2.linux;
		}
		if (lowerCase.contains("unix")) {
			return EnumOS2.linux;
		}
		return EnumOS2.unknown;
	}

	public static OpenCraft getOpenCraft() {
		return OpenCraft.oc;
	}

	public float getTickDelta() {
		return this.timer.tickDelta;
	}
}
