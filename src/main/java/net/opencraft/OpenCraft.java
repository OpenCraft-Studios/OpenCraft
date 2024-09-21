
package net.opencraft;

import java.io.File;
import net.opencraft.block.Block;
import net.opencraft.block.BlockSand;
import net.opencraft.client.entity.PlayerController;
import net.opencraft.client.entity.PlayerControllerSP;
import net.opencraft.client.entity.PlayerControllerTest;
import net.opencraft.client.entity.model.ModelBiped;
import net.opencraft.client.font.FontRenderer;
import net.opencraft.client.gui.GuiGameOver;
import net.opencraft.client.gui.GuiIngame;
import net.opencraft.client.gui.GuiIngameMenu;
import net.opencraft.client.gui.GuiInventory;
import net.opencraft.client.gui.GuiMainMenu;
import net.opencraft.client.gui.GuiScreen;
import net.opencraft.client.gui.GuiUnused;
import net.opencraft.client.input.MouseHelper;
import net.opencraft.client.input.MovementInputFromOptions;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.client.renderer.EffectRenderer;
import net.opencraft.client.renderer.GLAllocation;
import net.opencraft.client.renderer.LoadingScreenRenderer;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.client.renderer.entity.RenderEngine;
import net.opencraft.client.renderer.entity.RenderGlobal;
import net.opencraft.client.settings.GameSettings;
import net.opencraft.client.sound.SoundManager;
import net.opencraft.client.texture.TextureFlamesFX;
import net.opencraft.client.texture.TextureGearsFX;
import net.opencraft.client.texture.TextureLavaFX;
import net.opencraft.client.texture.TextureLavaFlowFX;
import net.opencraft.client.texture.TextureWaterFX;
import net.opencraft.client.texture.TextureWaterFlowFX;
import net.opencraft.entity.EntityPlayerSP;
import net.opencraft.entity.EntityRenderer;
import net.opencraft.item.ItemStack;
import net.opencraft.util.AxisAlignedBB;
import net.opencraft.util.EnumOS2;
import net.opencraft.util.EnumOSMappingHelper;
import net.opencraft.util.MathHelper;
import net.opencraft.util.UnexpectedThrowable;
import net.opencraft.util.Vec3D;
import net.opencraft.world.World;
import net.opencraft.world.WorldRenderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public abstract class OpenCraft implements Runnable {

    public static long[] tickTimes;
    public static int numRecordedFrameTimes;
    private static File minecraftDir;
    private static OpenCraft opencraft;
    public PlayerController playerController;
    private boolean fullscreen;
    public int displayWidth;
    public int displayHeight;
    private OpenGlCapsChecker glCapabilities;
    private Timer timer;
    public World theWorld;
    public RenderGlobal renderGlobal;
    public EntityPlayerSP thePlayer;
    public EffectRenderer effectRenderer;
    public Session sessionData;
    public String minecraftUri;
    public boolean hideQuitButton;
    public volatile boolean isGamePaused;
    public RenderEngine renderEngine;
    public FontRenderer fontRenderer;
    public GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;
    private ThreadDownloadResources downloadResourcesThread;
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
    public GameSettings gameSettings;
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
        OpenCraft.minecraftDir = new File("opencraft");
        if (!OpenCraft.minecraftDir.exists()) {
            OpenCraft.minecraftDir.mkdir();
        }
    }

    public OpenCraft(final int integer4, final int integer5, final boolean boolean6) {
        opencraft = this;
        opencraft.playerController = new PlayerControllerSP(opencraft);
        opencraft.fullscreen = false;
        opencraft.timer = new Timer(20.0f);
        opencraft.sessionData = new Session("Notch", "1488228");
        opencraft.hideQuitButton = true;
        opencraft.isGamePaused = false;
        opencraft.currentScreen = null;
        opencraft.loadingScreen = new LoadingScreenRenderer(opencraft);
        opencraft.entityRenderer = new EntityRenderer(opencraft);
        opencraft.ticksRan = 0;
        opencraft.leftClickCounter = 0;
        opencraft.objectMouseOverString = null;
        opencraft.rightClickDelayTimer = 0;
        opencraft.skipRenderWorld = false;
        opencraft.field_9242_w = new ModelBiped(0.0f);
        opencraft.objectMouseOver = null;
        opencraft.sndManager = new SoundManager();
        opencraft.textureWaterFX = new TextureWaterFX();
        opencraft.textureLavaFX = new TextureLavaFX();
        opencraft.running = true;
        opencraft.debug = "";
        opencraft.prevFrameTime = -1L;
        opencraft.inGameHasFocus = false;
        opencraft.mouseTicksRan = 0;
        opencraft.isRaining = false;
        opencraft.systemTime = System.currentTimeMillis();
        opencraft.tempDisplayWidth = integer4;
        opencraft.tempDisplayHeight = integer5;
        opencraft.fullscreen = boolean6;
        new ir(opencraft, "Timer hack thread");
        opencraft.displayWidth = integer4;
        opencraft.displayHeight = integer5;
        opencraft.fullscreen = boolean6;
    }

    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    public abstract void displayUnexpectedThrowable(final UnexpectedThrowable g);

    public void setServer(final String string, final int integer) {
    }

    public void startGame() throws LWJGLException {
        if (opencraft.fullscreen) {
            Display.setFullscreen(true);
            opencraft.displayWidth = Display.getDisplayMode().getWidth();
            opencraft.displayHeight = Display.getDisplayMode().getHeight();
            if (opencraft.displayWidth <= 0) {
                opencraft.displayWidth = 1;
            }
            if (opencraft.displayHeight <= 0) {
                opencraft.displayHeight = 1;
            }
        } else {
            Display.setDisplayMode(new DisplayMode(opencraft.displayWidth, opencraft.displayHeight));
        }
        Display.setTitle("nCraft");
        Display.setResizable(true);
        try {
            PixelFormat pixelformat = new PixelFormat();
            pixelformat = pixelformat.withDepthBits(24);
            Display.create(pixelformat);
        } catch (LWJGLException ex) {
            ex.printStackTrace();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ex3) {
            }
            Display.create();
        }
        opencraft.mcDataDir = getMinecraftDir();
        opencraft.gameSettings = new GameSettings(opencraft, opencraft.mcDataDir);
        opencraft.renderEngine = new RenderEngine(opencraft.gameSettings);
        opencraft.fontRenderer = new FontRenderer(opencraft.gameSettings, "/assets/default.png", opencraft.renderEngine);
        opencraft.loadScreen();
        Keyboard.create();
        Mouse.create();
        opencraft.mouseHelper = new MouseHelper(null);
        try {
//            Controllers.create();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
        opencraft.checkGLError("Pre startup");
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glClearDepth(1.0);
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glCullFace(1029);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        opencraft.checkGLError("Startup");
        opencraft.glCapabilities = new OpenGlCapsChecker();
        opencraft.sndManager.loadSoundSettings(opencraft.gameSettings);
        opencraft.renderEngine.registerTextureFX(opencraft.textureLavaFX);
        opencraft.renderEngine.registerTextureFX(opencraft.textureWaterFX);
        opencraft.renderEngine.registerTextureFX(new TextureWaterFlowFX());
        opencraft.renderEngine.registerTextureFX(new TextureLavaFlowFX());
        opencraft.renderEngine.registerTextureFX(new TextureFlamesFX(0));
        opencraft.renderEngine.registerTextureFX(new TextureFlamesFX(1));
        opencraft.renderEngine.registerTextureFX(new TextureGearsFX(0));
        opencraft.renderEngine.registerTextureFX(new TextureGearsFX(1));
        opencraft.renderGlobal = new RenderGlobal(opencraft, opencraft.renderEngine);
        GL11.glViewport(0, 0, opencraft.displayWidth, opencraft.displayHeight);
        opencraft.displayGuiScreen(new GuiMainMenu());
        opencraft.effectRenderer = new EffectRenderer(opencraft.theWorld, opencraft.renderEngine);
        try {
            (opencraft.downloadResourcesThread = new ThreadDownloadResources(opencraft.mcDataDir, opencraft)).start();
        } catch (Exception ex4) {
            ex4.printStackTrace();
        }
        opencraft.checkGLError("Post startup");
        opencraft.ingameGUI = new GuiIngame(opencraft);
        opencraft.playerController.a();
    }

    private void loadScreen() throws LWJGLException {
        final ScaledResolution scaledResolution = new ScaledResolution(opencraft.displayWidth, opencraft.displayHeight);
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        GL11.glClear(16640);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, scaledWidth, scaledHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glViewport(0, 0, opencraft.displayWidth, opencraft.displayHeight);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glEnable(3553);
        final Tessellator instance = Tessellator.instance;
        GL11.glBindTexture(3553, opencraft.renderEngine.getTexture("/assets/dirt.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float n = 32.0f;
        instance.startDrawingQuads();
        instance.setColorOpaque_I(4210752);
        instance.addVertexWithUV(0.0, opencraft.displayHeight, 0.0, 0.0, opencraft.displayHeight / n + 0.0f);
        instance.addVertexWithUV(opencraft.displayWidth, opencraft.displayHeight, 0.0, opencraft.displayWidth / n, opencraft.displayHeight / n + 0.0f);
        instance.addVertexWithUV(opencraft.displayWidth, 0.0, 0.0, opencraft.displayWidth / n, 0.0);
        instance.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        instance.draw();
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        opencraft.fontRenderer.drawStringWithShadow2("Loading...", 8, opencraft.displayHeight / 2 - 16, -1);
        Display.swapBuffers();
    }


    public void displayGuiScreen(GuiScreen var_1_2B) {
        if (opencraft.currentScreen instanceof GuiUnused) {
            return;
        }
        if (opencraft.currentScreen != null) {
            opencraft.currentScreen.onGuiClosed();
        }
        if (var_1_2B == null && opencraft.theWorld == null) {
            var_1_2B = new GuiMainMenu();
        } else if (var_1_2B == null && opencraft.thePlayer.health <= 0) {
            var_1_2B = new GuiGameOver();
        }
        if ((opencraft.currentScreen = var_1_2B) != null) {
            opencraft.setIngameNotInFocus();
            final ScaledResolution scaledResolution = new ScaledResolution(opencraft.displayWidth, opencraft.displayHeight);
            var_1_2B.setWorldAndResolution(opencraft, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            opencraft.skipRenderWorld = false;
        } else {
            opencraft.setIngameFocus();
        }
    }

    private void checkGLError(final String string) {
        final int glGetError = GL11.glGetError();
        if (glGetError != 0) {
            final String gluErrorString = GLU.gluErrorString(glGetError);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + string);
            System.out.println(new StringBuilder().append(glGetError).append(": ").append(gluErrorString).toString());
            System.exit(0);
        }
    }

    public void shutdownMinecraftApplet() {
        try {
            if (opencraft.downloadResourcesThread != null) {
                opencraft.downloadResourcesThread.closeMinecraft();
            }
        } catch (Exception ex) {
        }
        try {
            System.out.println("Stopping!");
            opencraft.changeWorld1(null);
            try {
                GLAllocation.deleteTexturesAndDisplayLists();
            } catch (Exception ex2) {
            }
            opencraft.sndManager.closeMinecraft();
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
        opencraft.running = true;
        try {
            opencraft.startGame();
        } catch (Exception exception) {
            exception.printStackTrace();
            opencraft.displayUnexpectedThrowable(new UnexpectedThrowable("Failed to start game", exception));
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            int n = 0;
            while (opencraft.running) {
                AxisAlignedBB.clearBoundingBoxPool();
                Vec3D.initialize();
                if (Display.isCloseRequested()) {
                    opencraft.shutdown();
                }
                if (opencraft.isGamePaused) {
                    final float renderPartialTicks = opencraft.timer.renderPartialTicks;
                    opencraft.timer.updateTimer();
                    opencraft.timer.renderPartialTicks = renderPartialTicks;
                } else {
                    opencraft.timer.updateTimer();
                }
                //for (int i = 0; i < minecraft.timer.elapsedTicks; ++i) {
                //    ++minecraft.ticksRan;
                //    minecraft.runTick();
                //}
                for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j)
                {
                    ++opencraft.ticksRan;
                    this.runTick();
                }
                opencraft.checkGLError("Pre render");
                if (opencraft.isGamePaused) {
                    opencraft.timer.renderPartialTicks = 1.0f;
                }
                opencraft.sndManager.setListener(opencraft.thePlayer, opencraft.timer.renderPartialTicks);
                GL11.glEnable(3553);
                if (opencraft.theWorld != null) {
                    while (opencraft.theWorld.updatingLighting()) {
                    }
                }
                if (!opencraft.skipRenderWorld) {
                    opencraft.playerController.setPartialTime(opencraft.timer.renderPartialTicks);
                    opencraft.entityRenderer.updateCameraAndRender(opencraft.timer.renderPartialTicks);
                }
                if (!Display.isActive() && opencraft.fullscreen) {
                    opencraft.toggleFullscreen();
                }
                if (Keyboard.isKeyDown(64)) {
                    opencraft.displayDebugInfo();
                } else {
                    opencraft.prevFrameTime = System.nanoTime();
                }
                //Thread.yield();
                Display.update();
                if (!opencraft.fullscreen && (Display.getWidth() != opencraft.displayWidth || Display.getHeight() != opencraft.displayHeight)) {
                    opencraft.displayWidth = Display.getWidth();
                    opencraft.displayHeight = Display.getHeight();
                    if (opencraft.displayWidth <= 0) {
                        opencraft.displayWidth = 1;
                    }
                    if (opencraft.displayHeight <= 0) {
                        opencraft.displayHeight = 1;
                    }
                    opencraft.resize(opencraft.displayWidth, opencraft.displayHeight);
                }
                if (opencraft.gameSettings.limitFramerate) {
                    Thread.sleep(5L);
                }
                opencraft.checkGLError("Post render");
                ++n;
                opencraft.isGamePaused = (!opencraft.isMultiplayerWorld() && opencraft.currentScreen != null && opencraft.currentScreen.doesGuiPauseGame());
                while (System.currentTimeMillis() >= currentTimeMillis + 1000L) {
                    opencraft.debug = new StringBuilder().append(n).append(" fps, ").append(WorldRenderer.chunksUpdated).append(" chunk updates").toString();
                    WorldRenderer.chunksUpdated = 0;
                    currentTimeMillis += 1000L;
                    n = 0;
                }
            }
        } catch (OpenCraftError openCraftError) {
        } catch (Exception exception2) {
            exception2.printStackTrace();
            opencraft.displayUnexpectedThrowable(new UnexpectedThrowable("Unexpected error", exception2));
        } finally {
            opencraft.shutdownMinecraftApplet();
        }
    }

    private void displayDebugInfo() {
        if (opencraft.prevFrameTime == -1L) {
            opencraft.prevFrameTime = System.nanoTime();
        }
        final long nanoTime = System.nanoTime();
        OpenCraft.tickTimes[OpenCraft.numRecordedFrameTimes++ & OpenCraft.tickTimes.length - 1] = nanoTime - opencraft.prevFrameTime;
        opencraft.prevFrameTime = nanoTime;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, opencraft.displayWidth, opencraft.displayHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        final Tessellator instance = Tessellator.instance;
        instance.startDrawing(7);
        instance.setColorOpaque_I(538968064);
        instance.addVertex(0.0, opencraft.displayHeight - 100, 0.0);
        instance.addVertex(0.0, opencraft.displayHeight, 0.0);
        instance.addVertex(OpenCraft.tickTimes.length, opencraft.displayHeight, 0.0);
        instance.addVertex(OpenCraft.tickTimes.length, opencraft.displayHeight - 100, 0.0);
        instance.draw();
        long n = 0L;
        for (int i = 0; i < OpenCraft.tickTimes.length; ++i) {
            n += OpenCraft.tickTimes[i];
        }
        int i = (int) (n / 200000L / OpenCraft.tickTimes.length);
        instance.startDrawing(7);
        instance.setColorOpaque_I(541065216);
        instance.addVertex(0.0, opencraft.displayHeight - i, 0.0);
        instance.addVertex(0.0, opencraft.displayHeight, 0.0);
        instance.addVertex(OpenCraft.tickTimes.length, opencraft.displayHeight, 0.0);
        instance.addVertex(OpenCraft.tickTimes.length, opencraft.displayHeight - i, 0.0);
        instance.draw();
        instance.startDrawing(1);
        for (int j = 0; j < OpenCraft.tickTimes.length; ++j) {
            final int n2 = (j - OpenCraft.numRecordedFrameTimes & OpenCraft.tickTimes.length - 1) * 255 / OpenCraft.tickTimes.length;
            int n3 = n2 * n2 / 255;
            n3 = n3 * n3 / 255;
            int n4 = n3 * n3 / 255;
            n4 = n4 * n4 / 255;
            instance.setColorOpaque_I(-16777216 + n4 + n3 * 256 + n2 * 65536);
            instance.addVertex(j + 0.5f, opencraft.displayHeight - OpenCraft.tickTimes[j] / 200000L + 0.5f, 0.0);
            instance.addVertex(j + 0.5f, opencraft.displayHeight + 0.5f, 0.0);
        }
        instance.draw();
        GL11.glEnable(3553);
    }

    public void shutdown() {
        opencraft.running = false;
    }

    public void setIngameFocus() {
        if (!Display.isActive()) {
            return;
        }
        if (opencraft.inGameHasFocus) {
            return;
        }
        opencraft.inGameHasFocus = true;
        opencraft.mouseHelper.grabMouse();
        opencraft.displayGuiScreen(null);
        opencraft.mouseTicksRan = opencraft.ticksRan + 10000;
    }

    public void setIngameNotInFocus() {
        if (!opencraft.inGameHasFocus) {
            return;
        }
        if (opencraft.thePlayer != null) {
            opencraft.thePlayer.resetPlayerKeyState();
        }
        opencraft.inGameHasFocus = false;
        opencraft.mouseHelper.ungrabMouseCursor();
    }

    public void displayInGameMenu() {
        if (opencraft.currentScreen != null) {
            return;
        }
        opencraft.displayGuiScreen(new GuiIngameMenu());
    }

    private void func_6254_a(final int integer, final boolean boolean2) {
        if (opencraft.playerController.field_1064_b) {
            return;
        }
        if (integer == 0 && opencraft.leftClickCounter > 0) {
            return;
        }
        if (boolean2 && opencraft.objectMouseOver != null && opencraft.objectMouseOver.typeOfHit == 0 && integer == 0) {
            final int blockX = opencraft.objectMouseOver.blockX;
            final int blockY = opencraft.objectMouseOver.blockY;
            final int blockZ = opencraft.objectMouseOver.blockZ;
            opencraft.playerController.sendBlockRemoving(blockX, blockY, blockZ, opencraft.objectMouseOver.sideHit);
            opencraft.effectRenderer.addBlockHitEffects(blockX, blockY, blockZ, opencraft.objectMouseOver.sideHit);
        } else {
            opencraft.playerController.resetBlockRemoving();
        }
    }

    private void clickMouse(final int integer) {
        if (integer == 0 && opencraft.leftClickCounter > 0) {
            return;
        }
        if (integer == 0) {
            opencraft.entityRenderer.itemRenderer.resetEquippedProgress();
        }
        if (opencraft.objectMouseOver == null) {
            if (integer == 0 && !(opencraft.playerController instanceof PlayerControllerTest)) {
                opencraft.leftClickCounter = 10;
            }
        } else if (opencraft.objectMouseOver.typeOfHit == 1) {
            if (integer == 0) {
                opencraft.thePlayer.a(opencraft.objectMouseOver.entityHit);
            }
            if (integer == 1) {
                opencraft.thePlayer.c(opencraft.objectMouseOver.entityHit);
            }
        } else if (opencraft.objectMouseOver.typeOfHit == 0) {
            final int blockX = opencraft.objectMouseOver.blockX;
            final int n = opencraft.objectMouseOver.blockY;
            final int blockZ = opencraft.objectMouseOver.blockZ;
            final int sideHit = opencraft.objectMouseOver.sideHit;
            final Block block = Block.blocksList[opencraft.theWorld.getBlockId(blockX, n, blockZ)];
            if (integer == 0) {
                opencraft.theWorld.onBlockHit(blockX, n, blockZ, opencraft.objectMouseOver.sideHit);
                if (block != Block.bedrock || opencraft.thePlayer.unusedByte >= 100) {
                    opencraft.playerController.clickBlock(blockX, n, blockZ);
                }
            } else {
                final ItemStack currentItem = opencraft.thePlayer.inventory.getCurrentItem();
                final int blockId = opencraft.theWorld.getBlockId(blockX, n, blockZ);
                if (blockId > 0 && Block.blocksList[blockId].blockActivated(opencraft.theWorld, blockX, n, blockZ, opencraft.thePlayer)) {
                    return;
                }
                if (currentItem == null) {
                    return;
                }
                final int stackSize = currentItem.stackSize;
                if (currentItem.useItem(opencraft.thePlayer, opencraft.theWorld, blockX, n, blockZ, sideHit)) {
                    opencraft.entityRenderer.itemRenderer.resetEquippedProgress();
                }
                if (currentItem.stackSize == 0) {
                    opencraft.thePlayer.inventory.mainInventory[opencraft.thePlayer.inventory.currentItem] = null;
                } else if (currentItem.stackSize != stackSize) {
                    opencraft.entityRenderer.itemRenderer.b();
                }
            }
        }
        if (integer == 1) {
            final ItemStack currentItem2 = opencraft.thePlayer.inventory.getCurrentItem();
            if (currentItem2 != null) {
                final int n = currentItem2.stackSize;
                final ItemStack useItemRightClick = currentItem2.useItemRightClick(opencraft.theWorld, opencraft.thePlayer);
                if (useItemRightClick != currentItem2 || (useItemRightClick != null && useItemRightClick.stackSize != n)) {
                    opencraft.thePlayer.inventory.mainInventory[opencraft.thePlayer.inventory.currentItem] = useItemRightClick;
                    opencraft.entityRenderer.itemRenderer.d();
                    if (useItemRightClick.stackSize == 0) {
                        opencraft.thePlayer.inventory.mainInventory[opencraft.thePlayer.inventory.currentItem] = null;
                    }
                }
            }
        }
    }

    public void toggleFullscreen() {
        try {
            opencraft.fullscreen = !opencraft.fullscreen;
            System.out.println("Toggle fullscreen!");
            if (opencraft.fullscreen) {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                opencraft.displayWidth = Display.getDisplayMode().getWidth();
                opencraft.displayHeight = Display.getDisplayMode().getHeight();
                if (opencraft.displayWidth <= 0) {
                    opencraft.displayWidth = 1;
                }
                if (opencraft.displayHeight <= 0) {
                    opencraft.displayHeight = 1;
                }
            } else {

                opencraft.displayWidth = opencraft.tempDisplayWidth = Display.getWidth();
                opencraft.displayHeight = opencraft.tempDisplayHeight = Display.getHeight();
                if (opencraft.displayWidth <= 0) {
                    opencraft.displayWidth = 1;
                }
                if (opencraft.displayHeight <= 0) {
                    opencraft.displayHeight = 1;
                }
                Display.setDisplayMode(new DisplayMode(opencraft.tempDisplayWidth, opencraft.tempDisplayHeight));
            }
            opencraft.setIngameNotInFocus();
            Display.setFullscreen(opencraft.fullscreen);
            Display.update();
            Thread.sleep(1000L);
            if (opencraft.fullscreen) {
                opencraft.setIngameFocus();
            }
            if (opencraft.currentScreen != null) {
                opencraft.setIngameNotInFocus();
                opencraft.resize(opencraft.displayWidth, opencraft.displayHeight);
            }
            System.out.println(new StringBuilder().append("Size: ").append(opencraft.displayWidth).append(", ").append(opencraft.displayHeight).toString());
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
        opencraft.displayWidth = integer1;
        opencraft.displayHeight = integer2;
        if (opencraft.currentScreen != null) {
            final ScaledResolution scaledResolution = new ScaledResolution(integer1, integer2);
            opencraft.currentScreen.setWorldAndResolution(opencraft, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
    }

    private void clickMiddleMouseButton() {
        if (opencraft.objectMouseOver != null) {
            int integer = opencraft.theWorld.getBlockId(opencraft.objectMouseOver.blockX, opencraft.objectMouseOver.blockY, opencraft.objectMouseOver.blockZ);
            if (integer == Block.grass.blockID) {
                integer = Block.dirt.blockID;
            }
            if (integer == Block.slabDouble.blockID) {
                integer = Block.slabSingle.blockID;
            }
            if (integer == Block.bedrock.blockID) {
                integer = Block.stone.blockID;
            }
            opencraft.thePlayer.inventory.setCurrentItem(integer, opencraft.playerController instanceof PlayerControllerTest);
        }
    }

    public void runTick() {
        opencraft.ingameGUI.updateTick();
        if (!opencraft.isGamePaused && opencraft.theWorld != null) {
            opencraft.playerController.updateController();
        }
        GL11.glBindTexture(3553, opencraft.renderEngine.getTexture("/assets/terrain.png"));
        if (!opencraft.isGamePaused) {
            opencraft.renderEngine.updateDynamicTextures();
        }
        if (opencraft.currentScreen == null && opencraft.thePlayer != null && opencraft.thePlayer.health <= 0) {
            opencraft.displayGuiScreen(null);
        }

        if (opencraft.leftClickCounter > 0) {
            --opencraft.leftClickCounter;
        }

        if (opencraft.currentScreen == null || opencraft.currentScreen.allowUserInput) {
            while (Mouse.next()) {
                if (System.currentTimeMillis() - opencraft.systemTime > 200L) {
                    continue;
                }
                final int eventDWheel = Mouse.getEventDWheel();
                if (eventDWheel != 0) {
                    opencraft.thePlayer.inventory.changeCurrentItem(eventDWheel);
                }
                if (opencraft.currentScreen == null) {
                    if (!opencraft.inGameHasFocus && Mouse.getEventButtonState()) {
                        opencraft.setIngameFocus();
                    } else {
                        if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                            opencraft.clickMouse(0);
                            opencraft.mouseTicksRan = opencraft.ticksRan;
                        }
                        if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
                            opencraft.clickMouse(1);
                            opencraft.mouseTicksRan = opencraft.ticksRan;
                        }
                        if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) {
                            continue;
                        }
                        opencraft.clickMiddleMouseButton();
                    }
                } else {
                    if (opencraft.currentScreen == null) {
                        continue;
                    }
                    opencraft.currentScreen.f();
                }
            }

            while (Keyboard.next()) {
                opencraft.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == 87) {
                        opencraft.toggleFullscreen();
                    } else {
                        if (opencraft.currentScreen != null) {
                            opencraft.currentScreen.handleKeyboardInput();
                        } else {
                            if (Keyboard.getEventKey() == 1) {
                                opencraft.displayInGameMenu();
                            }
                            if (opencraft.playerController instanceof PlayerControllerTest) {
                                if (Keyboard.getEventKey() == opencraft.gameSettings.keyBindLoad.keyCode) {
                                }
                                if (Keyboard.getEventKey() == opencraft.gameSettings.keyBindSave.keyCode) {
                                }
                            }
                            if (Keyboard.getEventKey() == 63) {
                                opencraft.gameSettings.thirdPersonView = !opencraft.gameSettings.thirdPersonView;
                                opencraft.isRaining = !opencraft.isRaining;
                            }
                            if (Keyboard.getEventKey() == opencraft.gameSettings.keyBindInventory.keyCode) {
                                opencraft.displayGuiScreen(new GuiInventory(opencraft.thePlayer.inventory));
                            }
                            if (Keyboard.getEventKey() == opencraft.gameSettings.keyBindDrop.keyCode) {
                                opencraft.thePlayer.dropPlayerItemWithRandomChoice(opencraft.thePlayer.inventory.decrStackSize(opencraft.thePlayer.inventory.currentItem, 1), false);
                            }
                        }
                        for (int i = 0; i < 9; ++i) {
                            if (Keyboard.getEventKey() == 2 + i) {
                                opencraft.thePlayer.inventory.currentItem = i;
                            }
                        }
                        if (Keyboard.getEventKey() != opencraft.gameSettings.keyBindToggleFog.keyCode) {
                            continue;
                        }
                        opencraft.gameSettings.setOptionFloatValue(4, (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) ? -1 : 1);
                    }
                }
            }
            if (opencraft.currentScreen == null) {
                if (Mouse.isButtonDown(0) && opencraft.ticksRan - opencraft.mouseTicksRan >= opencraft.timer.tps / 4.0f && opencraft.inGameHasFocus) {
                    opencraft.clickMouse(0);
                    opencraft.mouseTicksRan = opencraft.ticksRan;
                }
                if (Mouse.isButtonDown(1) && opencraft.ticksRan - opencraft.mouseTicksRan >= opencraft.timer.tps / 4.0f && opencraft.inGameHasFocus) {
                    opencraft.clickMouse(1);
                    opencraft.mouseTicksRan = opencraft.ticksRan;
                }
            }
            opencraft.func_6254_a(0, opencraft.currentScreen == null && Mouse.isButtonDown(0) && opencraft.inGameHasFocus);
        }
        if (opencraft.currentScreen != null) {
            opencraft.mouseTicksRan = opencraft.ticksRan + 10000;
        }
        if (opencraft.currentScreen != null) {
            opencraft.currentScreen.e();
            if (opencraft.currentScreen != null) {
                opencraft.currentScreen.updateScreen();
            }
        }
        if (opencraft.theWorld != null) {
            opencraft.theWorld.difficultySetting = opencraft.gameSettings.difficulty;
            if (!opencraft.isGamePaused) {
                opencraft.entityRenderer.updateRenderer();
            }
            if (!opencraft.isGamePaused) {
                opencraft.renderGlobal.updateClouds();
            }
            if (!opencraft.isGamePaused) {
                opencraft.theWorld.updateEntities();
            }
            if (!opencraft.isGamePaused && !opencraft.isMultiplayerWorld()) {
                opencraft.theWorld.tick();
            }
            if (!opencraft.isGamePaused) {
                opencraft.theWorld.randomDisplayUpdates(MathHelper.floor_double(opencraft.thePlayer.posX), MathHelper.floor_double(opencraft.thePlayer.posY), MathHelper.floor_double(opencraft.thePlayer.posZ));
            }
            if (!opencraft.isGamePaused) {
                opencraft.effectRenderer.updateEffects();
            }
        }
        opencraft.systemTime = System.currentTimeMillis();
    }

    public boolean isMultiplayerWorld() {
        return false;
    }

    public void startWorld(final String string) {
        opencraft.changeWorld1(null);
        System.gc();
        final World world = new World(new File(getMinecraftDir(), "saves"), string);
        if (world.isNewWorld) {
            opencraft.changeWorld2(world, "Generating level");
        } else {
            opencraft.changeWorld2(world, "Loading level");
        }
    }

    public void changeWorld1(final World fe) {
        opencraft.changeWorld2(fe, "");
    }

    public void changeWorld2(final World fe, final String string) {
        if (opencraft.theWorld != null) {
            opencraft.theWorld.saveWorldIndirectly(opencraft.loadingScreen);
        }
        if ((opencraft.theWorld = fe) != null) {
            opencraft.playerController.func_717_a(fe);
            fe.h = opencraft.fontRenderer;
            if (!opencraft.isMultiplayerWorld()) {
                opencraft.thePlayer = (EntityPlayerSP) fe.func_4085_a(EntityPlayerSP.class);
                fe.player = opencraft.thePlayer;
            } else if (opencraft.thePlayer != null) {
                opencraft.thePlayer.preparePlayerToSpawn();
                if (fe != null) {
                    fe.player = opencraft.thePlayer;
                    fe.entityJoinedWorld(opencraft.thePlayer);
                }
            }
            opencraft.func_6255_d(string);
            if (opencraft.thePlayer == null) {
                (opencraft.thePlayer = new EntityPlayerSP(opencraft, fe, opencraft.sessionData)).preparePlayerToSpawn();
                opencraft.playerController.flipPlayer(opencraft.thePlayer);
            }
            opencraft.thePlayer.movementInput = new MovementInputFromOptions(opencraft.gameSettings);
            if (opencraft.renderGlobal != null) {
                opencraft.renderGlobal.changeWorld(fe);
            }
            if (opencraft.effectRenderer != null) {
                opencraft.effectRenderer.clearEffects(fe);
            }
            opencraft.playerController.func_6473_b(opencraft.thePlayer);
            fe.player = opencraft.thePlayer;
            fe.spawnPlayerWithLoadedChunks();
            if (fe.isNewWorld) {
                fe.saveWorldIndirectly(opencraft.loadingScreen);
            }
        }
        System.gc();
        opencraft.systemTime = 0L;
        this.sndManager.currentMusicTheme = "ingame";
        this.sndManager.ticksBeforeMusic = 0;
        this.sndManager.stopSound("BgMusic");
    }

    private void func_6255_d(final String string) {
        opencraft.loadingScreen.printText(string);
        opencraft.loadingScreen.displayLoadingString("Building terrain");
        final int n = 128;
        int n2 = 0;
        int n3 = n * 2 / 16 + 1;
        n3 *= n3;
        for (int i = -n; i <= n; i += 16) {
            int x = opencraft.theWorld.x;
            int z = opencraft.theWorld.z;
            if (opencraft.theWorld.player != null) {
                x = (int) opencraft.theWorld.player.posX;
                z = (int) opencraft.theWorld.player.posZ;
            }
            for (int j = -n; j <= n; j += 16) {
                opencraft.loadingScreen.setLoadingProgress(n2++ * 100 / n3);
                opencraft.theWorld.getBlockId(x + i, 64, z + j);
                while (opencraft.theWorld.updatingLighting()) {
                }
            }
        }
        opencraft.loadingScreen.displayLoadingString("Simulating world for a bit");
        n3 = 2000;
        BlockSand.fallInstantly = true;
        for (int i = 0; i < n3; ++i) {
            opencraft.theWorld.TickUpdates(true);
        }
        opencraft.theWorld.func_656_j();
        BlockSand.fallInstantly = false;
    }

    public void installResource(String string, final File file) {
        final int index = string.indexOf("/");
        final String substring = string.substring(0, index);
        string = string.substring(index + 1);
        if (substring.equalsIgnoreCase("sound")) {
            opencraft.sndManager.addSound(string, file);
        } else if (substring.equalsIgnoreCase("newsound")) {
            opencraft.sndManager.addSound(string, file);
        } else if (substring.equalsIgnoreCase("music")) {
            opencraft.sndManager.addIngameMusic(string, file);
        } else if (substring.equalsIgnoreCase("newmusic")) {
            opencraft.sndManager.addIngameMusic(string, file);
        } else if (substring.equalsIgnoreCase("menumusic")) {
            opencraft.sndManager.addMenuMusic(string, file);
        }
    }

    public OpenGlCapsChecker getOpenGlCapsChecker() {
        return opencraft.glCapabilities;
    }

    public String debugInfoRenders() {
        return opencraft.renderGlobal.getDebugInfoRenders();
    }

    public String func_6262_n() {
        return opencraft.renderGlobal.getDebugInfoEntities();
    }

    public String debugInfoEntities() {
        return "P: " + opencraft.effectRenderer.getStatistics() + ". T: " + opencraft.theWorld.func_687_d();
    }

    public void respawn() {
        if (opencraft.thePlayer != null && opencraft.theWorld != null) {
            opencraft.theWorld.setEntityDead(opencraft.thePlayer);
        }
        opencraft.theWorld.a();
        (opencraft.thePlayer = new EntityPlayerSP(opencraft, opencraft.theWorld, opencraft.sessionData)).preparePlayerToSpawn();
        opencraft.playerController.flipPlayer(opencraft.thePlayer);
        if (opencraft.theWorld != null) {
            opencraft.theWorld.player = opencraft.thePlayer;
            opencraft.theWorld.spawnPlayerWithLoadedChunks();
        }
        opencraft.thePlayer.movementInput = new MovementInputFromOptions(opencraft.gameSettings);
        opencraft.playerController.func_6473_b(opencraft.thePlayer);
        opencraft.func_6255_d("Respawning");
    }

    public static File getMinecraftDir() {
        if (OpenCraft.minecraftDir == null) {
            OpenCraft.minecraftDir = getAppDir("minecraft");
        }
        return OpenCraft.minecraftDir;
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
            throw new RuntimeException(new StringBuilder().append("The working directory could not be created: ").append(file).toString());
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
    	return OpenCraft.opencraft;
    }

    public float getTickDelta() {
        return this.timer.tickDelta;
    }
}
