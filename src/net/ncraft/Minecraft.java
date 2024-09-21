
package net.ncraft;

import java.io.File;
import net.ncraft.block.Block;
import net.ncraft.block.BlockSand;
import net.ncraft.client.entity.PlayerController;
import net.ncraft.client.entity.PlayerControllerSP;
import net.ncraft.client.entity.PlayerControllerTest;
import net.ncraft.client.entity.model.ModelBiped;
import net.ncraft.client.font.FontRenderer;
import net.ncraft.client.gui.GuiGameOver;
import net.ncraft.client.gui.GuiIngame;
import net.ncraft.client.gui.GuiIngameMenu;
import net.ncraft.client.gui.GuiInventory;
import net.ncraft.client.gui.GuiMainMenu;
import net.ncraft.client.gui.GuiScreen;
import net.ncraft.client.gui.GuiUnused;
import net.ncraft.client.input.MouseHelper;
import net.ncraft.client.input.MovementInputFromOptions;
import net.ncraft.client.input.MovingObjectPosition;
import net.ncraft.client.renderer.EffectRenderer;
import net.ncraft.client.renderer.GLAllocation;
import net.ncraft.client.renderer.LoadingScreenRenderer;
import net.ncraft.client.renderer.Tessellator;
import net.ncraft.client.renderer.entity.RenderEngine;
import net.ncraft.client.renderer.entity.RenderGlobal;
import net.ncraft.client.settings.GameSettings;
import net.ncraft.client.sound.SoundManager;
import net.ncraft.client.texture.TextureFlamesFX;
import net.ncraft.client.texture.TextureGearsFX;
import net.ncraft.client.texture.TextureLavaFX;
import net.ncraft.client.texture.TextureLavaFlowFX;
import net.ncraft.client.texture.TextureWaterFX;
import net.ncraft.client.texture.TextureWaterFlowFX;
import net.ncraft.entity.EntityPlayerSP;
import net.ncraft.entity.EntityRenderer;
import net.ncraft.item.ItemStack;
import net.ncraft.util.AxisAlignedBB;
import net.ncraft.util.EnumOS2;
import net.ncraft.util.EnumOSMappingHelper;
import net.ncraft.util.MathHelper;
import net.ncraft.util.UnexpectedThrowable;
import net.ncraft.util.Vec3D;
import net.ncraft.world.World;
import net.ncraft.world.WorldRenderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft implements Runnable {

    public static long[] tickTimes;
    public static int numRecordedFrameTimes;
    private static File minecraftDir;
    private static Minecraft minecraft;
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
        Minecraft.tickTimes = new long[512];
        Minecraft.numRecordedFrameTimes = 0;
        Minecraft.minecraftDir = new File("minecraft");
        if (!Minecraft.minecraftDir.exists()) {
            Minecraft.minecraftDir.mkdir();
        }
    }

    public Minecraft(final int integer4, final int integer5, final boolean boolean6) {
        minecraft = this;
        minecraft.playerController = new PlayerControllerSP(minecraft);
        minecraft.fullscreen = false;
        minecraft.timer = new Timer(20.0f);
        minecraft.sessionData = new Session("Notch", "1488228");
        minecraft.hideQuitButton = true;
        minecraft.isGamePaused = false;
        minecraft.currentScreen = null;
        minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
        minecraft.entityRenderer = new EntityRenderer(minecraft);
        minecraft.ticksRan = 0;
        minecraft.leftClickCounter = 0;
        minecraft.objectMouseOverString = null;
        minecraft.rightClickDelayTimer = 0;
        minecraft.skipRenderWorld = false;
        minecraft.field_9242_w = new ModelBiped(0.0f);
        minecraft.objectMouseOver = null;
        minecraft.sndManager = new SoundManager();
        minecraft.textureWaterFX = new TextureWaterFX();
        minecraft.textureLavaFX = new TextureLavaFX();
        minecraft.running = true;
        minecraft.debug = "";
        minecraft.prevFrameTime = -1L;
        minecraft.inGameHasFocus = false;
        minecraft.mouseTicksRan = 0;
        minecraft.isRaining = false;
        minecraft.systemTime = System.currentTimeMillis();
        minecraft.tempDisplayWidth = integer4;
        minecraft.tempDisplayHeight = integer5;
        minecraft.fullscreen = boolean6;
        new ir(minecraft, "Timer hack thread");
        minecraft.displayWidth = integer4;
        minecraft.displayHeight = integer5;
        minecraft.fullscreen = boolean6;
    }

    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    public abstract void displayUnexpectedThrowable(final UnexpectedThrowable g);

    public void setServer(final String string, final int integer) {
    }

    public void startGame() throws LWJGLException {
        if (minecraft.fullscreen) {
            Display.setFullscreen(true);
            minecraft.displayWidth = Display.getDisplayMode().getWidth();
            minecraft.displayHeight = Display.getDisplayMode().getHeight();
            if (minecraft.displayWidth <= 0) {
                minecraft.displayWidth = 1;
            }
            if (minecraft.displayHeight <= 0) {
                minecraft.displayHeight = 1;
            }
        } else {
            Display.setDisplayMode(new DisplayMode(minecraft.displayWidth, minecraft.displayHeight));
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
        minecraft.mcDataDir = getMinecraftDir();
        minecraft.gameSettings = new GameSettings(minecraft, minecraft.mcDataDir);
        minecraft.renderEngine = new RenderEngine(minecraft.gameSettings);
        minecraft.fontRenderer = new FontRenderer(minecraft.gameSettings, "/assets/default.png", minecraft.renderEngine);
        minecraft.loadScreen();
        Keyboard.create();
        Mouse.create();
        minecraft.mouseHelper = new MouseHelper(null);
        try {
//            Controllers.create();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
        minecraft.checkGLError("Pre startup");
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
        minecraft.checkGLError("Startup");
        minecraft.glCapabilities = new OpenGlCapsChecker();
        minecraft.sndManager.loadSoundSettings(minecraft.gameSettings);
        minecraft.renderEngine.registerTextureFX(minecraft.textureLavaFX);
        minecraft.renderEngine.registerTextureFX(minecraft.textureWaterFX);
        minecraft.renderEngine.registerTextureFX(new TextureWaterFlowFX());
        minecraft.renderEngine.registerTextureFX(new TextureLavaFlowFX());
        minecraft.renderEngine.registerTextureFX(new TextureFlamesFX(0));
        minecraft.renderEngine.registerTextureFX(new TextureFlamesFX(1));
        minecraft.renderEngine.registerTextureFX(new TextureGearsFX(0));
        minecraft.renderEngine.registerTextureFX(new TextureGearsFX(1));
        minecraft.renderGlobal = new RenderGlobal(minecraft, minecraft.renderEngine);
        GL11.glViewport(0, 0, minecraft.displayWidth, minecraft.displayHeight);
        minecraft.displayGuiScreen(new GuiMainMenu());
        minecraft.effectRenderer = new EffectRenderer(minecraft.theWorld, minecraft.renderEngine);
        try {
            (minecraft.downloadResourcesThread = new ThreadDownloadResources(minecraft.mcDataDir, minecraft)).start();
        } catch (Exception ex4) {
            ex4.printStackTrace();
        }
        minecraft.checkGLError("Post startup");
        minecraft.ingameGUI = new GuiIngame(minecraft);
        minecraft.playerController.a();
    }

    private void loadScreen() throws LWJGLException {
        final ScaledResolution scaledResolution = new ScaledResolution(minecraft.displayWidth, minecraft.displayHeight);
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        GL11.glClear(16640);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, scaledWidth, scaledHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glViewport(0, 0, minecraft.displayWidth, minecraft.displayHeight);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glEnable(3553);
        final Tessellator instance = Tessellator.instance;
        GL11.glBindTexture(3553, minecraft.renderEngine.getTexture("/assets/dirt.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float n = 32.0f;
        instance.startDrawingQuads();
        instance.setColorOpaque_I(4210752);
        instance.addVertexWithUV(0.0, minecraft.displayHeight, 0.0, 0.0, minecraft.displayHeight / n + 0.0f);
        instance.addVertexWithUV(minecraft.displayWidth, minecraft.displayHeight, 0.0, minecraft.displayWidth / n, minecraft.displayHeight / n + 0.0f);
        instance.addVertexWithUV(minecraft.displayWidth, 0.0, 0.0, minecraft.displayWidth / n, 0.0);
        instance.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        instance.draw();
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        minecraft.fontRenderer.drawStringWithShadow2("Loading...", 8, minecraft.displayHeight / 2 - 16, -1);
        Display.swapBuffers();
    }


    public void displayGuiScreen(GuiScreen var_1_2B) {
        if (minecraft.currentScreen instanceof GuiUnused) {
            return;
        }
        if (minecraft.currentScreen != null) {
            minecraft.currentScreen.onGuiClosed();
        }
        if (var_1_2B == null && minecraft.theWorld == null) {
            var_1_2B = new GuiMainMenu();
        } else if (var_1_2B == null && minecraft.thePlayer.health <= 0) {
            var_1_2B = new GuiGameOver();
        }
        if ((minecraft.currentScreen = var_1_2B) != null) {
            minecraft.setIngameNotInFocus();
            final ScaledResolution scaledResolution = new ScaledResolution(minecraft.displayWidth, minecraft.displayHeight);
            var_1_2B.setWorldAndResolution(minecraft, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            minecraft.skipRenderWorld = false;
        } else {
            minecraft.setIngameFocus();
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
            if (minecraft.downloadResourcesThread != null) {
                minecraft.downloadResourcesThread.closeMinecraft();
            }
        } catch (Exception ex) {
        }
        try {
            System.out.println("Stopping!");
            minecraft.changeWorld1(null);
            try {
                GLAllocation.deleteTexturesAndDisplayLists();
            } catch (Exception ex2) {
            }
            minecraft.sndManager.closeMinecraft();
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
        minecraft.running = true;
        try {
            minecraft.startGame();
        } catch (Exception exception) {
            exception.printStackTrace();
            minecraft.displayUnexpectedThrowable(new UnexpectedThrowable("Failed to start game", exception));
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            int n = 0;
            while (minecraft.running) {
                AxisAlignedBB.clearBoundingBoxPool();
                Vec3D.initialize();
                if (Display.isCloseRequested()) {
                    minecraft.shutdown();
                }
                if (minecraft.isGamePaused) {
                    final float renderPartialTicks = minecraft.timer.renderPartialTicks;
                    minecraft.timer.updateTimer();
                    minecraft.timer.renderPartialTicks = renderPartialTicks;
                } else {
                    minecraft.timer.updateTimer();
                }
                //for (int i = 0; i < minecraft.timer.elapsedTicks; ++i) {
                //    ++minecraft.ticksRan;
                //    minecraft.runTick();
                //}
                for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j)
                {
                    ++minecraft.ticksRan;
                    this.runTick();
                }
                minecraft.checkGLError("Pre render");
                if (minecraft.isGamePaused) {
                    minecraft.timer.renderPartialTicks = 1.0f;
                }
                minecraft.sndManager.setListener(minecraft.thePlayer, minecraft.timer.renderPartialTicks);
                GL11.glEnable(3553);
                if (minecraft.theWorld != null) {
                    while (minecraft.theWorld.updatingLighting()) {
                    }
                }
                if (!minecraft.skipRenderWorld) {
                    minecraft.playerController.setPartialTime(minecraft.timer.renderPartialTicks);
                    minecraft.entityRenderer.updateCameraAndRender(minecraft.timer.renderPartialTicks);
                }
                if (!Display.isActive() && minecraft.fullscreen) {
                    minecraft.toggleFullscreen();
                }
                if (Keyboard.isKeyDown(64)) {
                    minecraft.displayDebugInfo();
                } else {
                    minecraft.prevFrameTime = System.nanoTime();
                }
                //Thread.yield();
                Display.update();
                if (!minecraft.fullscreen && (Display.getWidth() != minecraft.displayWidth || Display.getHeight() != minecraft.displayHeight)) {
                    minecraft.displayWidth = Display.getWidth();
                    minecraft.displayHeight = Display.getHeight();
                    if (minecraft.displayWidth <= 0) {
                        minecraft.displayWidth = 1;
                    }
                    if (minecraft.displayHeight <= 0) {
                        minecraft.displayHeight = 1;
                    }
                    minecraft.resize(minecraft.displayWidth, minecraft.displayHeight);
                }
                if (minecraft.gameSettings.limitFramerate) {
                    Thread.sleep(5L);
                }
                minecraft.checkGLError("Post render");
                ++n;
                minecraft.isGamePaused = (!minecraft.isMultiplayerWorld() && minecraft.currentScreen != null && minecraft.currentScreen.doesGuiPauseGame());
                while (System.currentTimeMillis() >= currentTimeMillis + 1000L) {
                    minecraft.debug = new StringBuilder().append(n).append(" fps, ").append(WorldRenderer.chunksUpdated).append(" chunk updates").toString();
                    WorldRenderer.chunksUpdated = 0;
                    currentTimeMillis += 1000L;
                    n = 0;
                }
            }
        } catch (MinecraftError minecraftError) {
        } catch (Exception exception2) {
            exception2.printStackTrace();
            minecraft.displayUnexpectedThrowable(new UnexpectedThrowable("Unexpected error", exception2));
        } finally {
            minecraft.shutdownMinecraftApplet();
        }
    }

    private void displayDebugInfo() {
        if (minecraft.prevFrameTime == -1L) {
            minecraft.prevFrameTime = System.nanoTime();
        }
        final long nanoTime = System.nanoTime();
        Minecraft.tickTimes[Minecraft.numRecordedFrameTimes++ & Minecraft.tickTimes.length - 1] = nanoTime - minecraft.prevFrameTime;
        minecraft.prevFrameTime = nanoTime;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, minecraft.displayWidth, minecraft.displayHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        final Tessellator instance = Tessellator.instance;
        instance.startDrawing(7);
        instance.setColorOpaque_I(538968064);
        instance.addVertex(0.0, minecraft.displayHeight - 100, 0.0);
        instance.addVertex(0.0, minecraft.displayHeight, 0.0);
        instance.addVertex(Minecraft.tickTimes.length, minecraft.displayHeight, 0.0);
        instance.addVertex(Minecraft.tickTimes.length, minecraft.displayHeight - 100, 0.0);
        instance.draw();
        long n = 0L;
        for (int i = 0; i < Minecraft.tickTimes.length; ++i) {
            n += Minecraft.tickTimes[i];
        }
        int i = (int) (n / 200000L / Minecraft.tickTimes.length);
        instance.startDrawing(7);
        instance.setColorOpaque_I(541065216);
        instance.addVertex(0.0, minecraft.displayHeight - i, 0.0);
        instance.addVertex(0.0, minecraft.displayHeight, 0.0);
        instance.addVertex(Minecraft.tickTimes.length, minecraft.displayHeight, 0.0);
        instance.addVertex(Minecraft.tickTimes.length, minecraft.displayHeight - i, 0.0);
        instance.draw();
        instance.startDrawing(1);
        for (int j = 0; j < Minecraft.tickTimes.length; ++j) {
            final int n2 = (j - Minecraft.numRecordedFrameTimes & Minecraft.tickTimes.length - 1) * 255 / Minecraft.tickTimes.length;
            int n3 = n2 * n2 / 255;
            n3 = n3 * n3 / 255;
            int n4 = n3 * n3 / 255;
            n4 = n4 * n4 / 255;
            instance.setColorOpaque_I(-16777216 + n4 + n3 * 256 + n2 * 65536);
            instance.addVertex(j + 0.5f, minecraft.displayHeight - Minecraft.tickTimes[j] / 200000L + 0.5f, 0.0);
            instance.addVertex(j + 0.5f, minecraft.displayHeight + 0.5f, 0.0);
        }
        instance.draw();
        GL11.glEnable(3553);
    }

    public void shutdown() {
        minecraft.running = false;
    }

    public void setIngameFocus() {
        if (!Display.isActive()) {
            return;
        }
        if (minecraft.inGameHasFocus) {
            return;
        }
        minecraft.inGameHasFocus = true;
        minecraft.mouseHelper.grabMouse();
        minecraft.displayGuiScreen(null);
        minecraft.mouseTicksRan = minecraft.ticksRan + 10000;
    }

    public void setIngameNotInFocus() {
        if (!minecraft.inGameHasFocus) {
            return;
        }
        if (minecraft.thePlayer != null) {
            minecraft.thePlayer.resetPlayerKeyState();
        }
        minecraft.inGameHasFocus = false;
        minecraft.mouseHelper.ungrabMouseCursor();
    }

    public void displayInGameMenu() {
        if (minecraft.currentScreen != null) {
            return;
        }
        minecraft.displayGuiScreen(new GuiIngameMenu());
    }

    private void func_6254_a(final int integer, final boolean boolean2) {
        if (minecraft.playerController.field_1064_b) {
            return;
        }
        if (integer == 0 && minecraft.leftClickCounter > 0) {
            return;
        }
        if (boolean2 && minecraft.objectMouseOver != null && minecraft.objectMouseOver.typeOfHit == 0 && integer == 0) {
            final int blockX = minecraft.objectMouseOver.blockX;
            final int blockY = minecraft.objectMouseOver.blockY;
            final int blockZ = minecraft.objectMouseOver.blockZ;
            minecraft.playerController.sendBlockRemoving(blockX, blockY, blockZ, minecraft.objectMouseOver.sideHit);
            minecraft.effectRenderer.addBlockHitEffects(blockX, blockY, blockZ, minecraft.objectMouseOver.sideHit);
        } else {
            minecraft.playerController.resetBlockRemoving();
        }
    }

    private void clickMouse(final int integer) {
        if (integer == 0 && minecraft.leftClickCounter > 0) {
            return;
        }
        if (integer == 0) {
            minecraft.entityRenderer.itemRenderer.resetEquippedProgress();
        }
        if (minecraft.objectMouseOver == null) {
            if (integer == 0 && !(minecraft.playerController instanceof PlayerControllerTest)) {
                minecraft.leftClickCounter = 10;
            }
        } else if (minecraft.objectMouseOver.typeOfHit == 1) {
            if (integer == 0) {
                minecraft.thePlayer.a(minecraft.objectMouseOver.entityHit);
            }
            if (integer == 1) {
                minecraft.thePlayer.c(minecraft.objectMouseOver.entityHit);
            }
        } else if (minecraft.objectMouseOver.typeOfHit == 0) {
            final int blockX = minecraft.objectMouseOver.blockX;
            final int n = minecraft.objectMouseOver.blockY;
            final int blockZ = minecraft.objectMouseOver.blockZ;
            final int sideHit = minecraft.objectMouseOver.sideHit;
            final Block block = Block.blocksList[minecraft.theWorld.getBlockId(blockX, n, blockZ)];
            if (integer == 0) {
                minecraft.theWorld.onBlockHit(blockX, n, blockZ, minecraft.objectMouseOver.sideHit);
                if (block != Block.bedrock || minecraft.thePlayer.unusedByte >= 100) {
                    minecraft.playerController.clickBlock(blockX, n, blockZ);
                }
            } else {
                final ItemStack currentItem = minecraft.thePlayer.inventory.getCurrentItem();
                final int blockId = minecraft.theWorld.getBlockId(blockX, n, blockZ);
                if (blockId > 0 && Block.blocksList[blockId].blockActivated(minecraft.theWorld, blockX, n, blockZ, minecraft.thePlayer)) {
                    return;
                }
                if (currentItem == null) {
                    return;
                }
                final int stackSize = currentItem.stackSize;
                if (currentItem.useItem(minecraft.thePlayer, minecraft.theWorld, blockX, n, blockZ, sideHit)) {
                    minecraft.entityRenderer.itemRenderer.resetEquippedProgress();
                }
                if (currentItem.stackSize == 0) {
                    minecraft.thePlayer.inventory.mainInventory[minecraft.thePlayer.inventory.currentItem] = null;
                } else if (currentItem.stackSize != stackSize) {
                    minecraft.entityRenderer.itemRenderer.b();
                }
            }
        }
        if (integer == 1) {
            final ItemStack currentItem2 = minecraft.thePlayer.inventory.getCurrentItem();
            if (currentItem2 != null) {
                final int n = currentItem2.stackSize;
                final ItemStack useItemRightClick = currentItem2.useItemRightClick(minecraft.theWorld, minecraft.thePlayer);
                if (useItemRightClick != currentItem2 || (useItemRightClick != null && useItemRightClick.stackSize != n)) {
                    minecraft.thePlayer.inventory.mainInventory[minecraft.thePlayer.inventory.currentItem] = useItemRightClick;
                    minecraft.entityRenderer.itemRenderer.d();
                    if (useItemRightClick.stackSize == 0) {
                        minecraft.thePlayer.inventory.mainInventory[minecraft.thePlayer.inventory.currentItem] = null;
                    }
                }
            }
        }
    }

    public void toggleFullscreen() {
        try {
            minecraft.fullscreen = !minecraft.fullscreen;
            System.out.println("Toggle fullscreen!");
            if (minecraft.fullscreen) {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                minecraft.displayWidth = Display.getDisplayMode().getWidth();
                minecraft.displayHeight = Display.getDisplayMode().getHeight();
                if (minecraft.displayWidth <= 0) {
                    minecraft.displayWidth = 1;
                }
                if (minecraft.displayHeight <= 0) {
                    minecraft.displayHeight = 1;
                }
            } else {

                minecraft.displayWidth = minecraft.tempDisplayWidth = Display.getWidth();
                minecraft.displayHeight = minecraft.tempDisplayHeight = Display.getHeight();
                if (minecraft.displayWidth <= 0) {
                    minecraft.displayWidth = 1;
                }
                if (minecraft.displayHeight <= 0) {
                    minecraft.displayHeight = 1;
                }
                Display.setDisplayMode(new DisplayMode(minecraft.tempDisplayWidth, minecraft.tempDisplayHeight));
            }
            minecraft.setIngameNotInFocus();
            Display.setFullscreen(minecraft.fullscreen);
            Display.update();
            Thread.sleep(1000L);
            if (minecraft.fullscreen) {
                minecraft.setIngameFocus();
            }
            if (minecraft.currentScreen != null) {
                minecraft.setIngameNotInFocus();
                minecraft.resize(minecraft.displayWidth, minecraft.displayHeight);
            }
            System.out.println(new StringBuilder().append("Size: ").append(minecraft.displayWidth).append(", ").append(minecraft.displayHeight).toString());
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
        minecraft.displayWidth = integer1;
        minecraft.displayHeight = integer2;
        if (minecraft.currentScreen != null) {
            final ScaledResolution scaledResolution = new ScaledResolution(integer1, integer2);
            minecraft.currentScreen.setWorldAndResolution(minecraft, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
    }

    private void clickMiddleMouseButton() {
        if (minecraft.objectMouseOver != null) {
            int integer = minecraft.theWorld.getBlockId(minecraft.objectMouseOver.blockX, minecraft.objectMouseOver.blockY, minecraft.objectMouseOver.blockZ);
            if (integer == Block.grass.blockID) {
                integer = Block.dirt.blockID;
            }
            if (integer == Block.slabDouble.blockID) {
                integer = Block.slabSingle.blockID;
            }
            if (integer == Block.bedrock.blockID) {
                integer = Block.stone.blockID;
            }
            minecraft.thePlayer.inventory.setCurrentItem(integer, minecraft.playerController instanceof PlayerControllerTest);
        }
    }

    public void runTick() {
        minecraft.ingameGUI.updateTick();
        if (!minecraft.isGamePaused && minecraft.theWorld != null) {
            minecraft.playerController.updateController();
        }
        GL11.glBindTexture(3553, minecraft.renderEngine.getTexture("/assets/terrain.png"));
        if (!minecraft.isGamePaused) {
            minecraft.renderEngine.updateDynamicTextures();
        }
        if (minecraft.currentScreen == null && minecraft.thePlayer != null && minecraft.thePlayer.health <= 0) {
            minecraft.displayGuiScreen(null);
        }

        if (minecraft.leftClickCounter > 0) {
            --minecraft.leftClickCounter;
        }

        if (minecraft.currentScreen == null || minecraft.currentScreen.allowUserInput) {
            while (Mouse.next()) {
                if (System.currentTimeMillis() - minecraft.systemTime > 200L) {
                    continue;
                }
                final int eventDWheel = Mouse.getEventDWheel();
                if (eventDWheel != 0) {
                    minecraft.thePlayer.inventory.changeCurrentItem(eventDWheel);
                }
                if (minecraft.currentScreen == null) {
                    if (!minecraft.inGameHasFocus && Mouse.getEventButtonState()) {
                        minecraft.setIngameFocus();
                    } else {
                        if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                            minecraft.clickMouse(0);
                            minecraft.mouseTicksRan = minecraft.ticksRan;
                        }
                        if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
                            minecraft.clickMouse(1);
                            minecraft.mouseTicksRan = minecraft.ticksRan;
                        }
                        if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) {
                            continue;
                        }
                        minecraft.clickMiddleMouseButton();
                    }
                } else {
                    if (minecraft.currentScreen == null) {
                        continue;
                    }
                    minecraft.currentScreen.f();
                }
            }

            while (Keyboard.next()) {
                minecraft.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == 87) {
                        minecraft.toggleFullscreen();
                    } else {
                        if (minecraft.currentScreen != null) {
                            minecraft.currentScreen.handleKeyboardInput();
                        } else {
                            if (Keyboard.getEventKey() == 1) {
                                minecraft.displayInGameMenu();
                            }
                            if (minecraft.playerController instanceof PlayerControllerTest) {
                                if (Keyboard.getEventKey() == minecraft.gameSettings.keyBindLoad.keyCode) {
                                }
                                if (Keyboard.getEventKey() == minecraft.gameSettings.keyBindSave.keyCode) {
                                }
                            }
                            if (Keyboard.getEventKey() == 63) {
                                minecraft.gameSettings.thirdPersonView = !minecraft.gameSettings.thirdPersonView;
                                minecraft.isRaining = !minecraft.isRaining;
                            }
                            if (Keyboard.getEventKey() == minecraft.gameSettings.keyBindInventory.keyCode) {
                                minecraft.displayGuiScreen(new GuiInventory(minecraft.thePlayer.inventory));
                            }
                            if (Keyboard.getEventKey() == minecraft.gameSettings.keyBindDrop.keyCode) {
                                minecraft.thePlayer.dropPlayerItemWithRandomChoice(minecraft.thePlayer.inventory.decrStackSize(minecraft.thePlayer.inventory.currentItem, 1), false);
                            }
                        }
                        for (int i = 0; i < 9; ++i) {
                            if (Keyboard.getEventKey() == 2 + i) {
                                minecraft.thePlayer.inventory.currentItem = i;
                            }
                        }
                        if (Keyboard.getEventKey() != minecraft.gameSettings.keyBindToggleFog.keyCode) {
                            continue;
                        }
                        minecraft.gameSettings.setOptionFloatValue(4, (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) ? -1 : 1);
                    }
                }
            }
            if (minecraft.currentScreen == null) {
                if (Mouse.isButtonDown(0) && minecraft.ticksRan - minecraft.mouseTicksRan >= minecraft.timer.tps / 4.0f && minecraft.inGameHasFocus) {
                    minecraft.clickMouse(0);
                    minecraft.mouseTicksRan = minecraft.ticksRan;
                }
                if (Mouse.isButtonDown(1) && minecraft.ticksRan - minecraft.mouseTicksRan >= minecraft.timer.tps / 4.0f && minecraft.inGameHasFocus) {
                    minecraft.clickMouse(1);
                    minecraft.mouseTicksRan = minecraft.ticksRan;
                }
            }
            minecraft.func_6254_a(0, minecraft.currentScreen == null && Mouse.isButtonDown(0) && minecraft.inGameHasFocus);
        }
        if (minecraft.currentScreen != null) {
            minecraft.mouseTicksRan = minecraft.ticksRan + 10000;
        }
        if (minecraft.currentScreen != null) {
            minecraft.currentScreen.e();
            if (minecraft.currentScreen != null) {
                minecraft.currentScreen.updateScreen();
            }
        }
        if (minecraft.theWorld != null) {
            minecraft.theWorld.difficultySetting = minecraft.gameSettings.difficulty;
            if (!minecraft.isGamePaused) {
                minecraft.entityRenderer.updateRenderer();
            }
            if (!minecraft.isGamePaused) {
                minecraft.renderGlobal.updateClouds();
            }
            if (!minecraft.isGamePaused) {
                minecraft.theWorld.updateEntities();
            }
            if (!minecraft.isGamePaused && !minecraft.isMultiplayerWorld()) {
                minecraft.theWorld.tick();
            }
            if (!minecraft.isGamePaused) {
                minecraft.theWorld.randomDisplayUpdates(MathHelper.floor_double(minecraft.thePlayer.posX), MathHelper.floor_double(minecraft.thePlayer.posY), MathHelper.floor_double(minecraft.thePlayer.posZ));
            }
            if (!minecraft.isGamePaused) {
                minecraft.effectRenderer.updateEffects();
            }
        }
        minecraft.systemTime = System.currentTimeMillis();
    }

    public boolean isMultiplayerWorld() {
        return false;
    }

    public void startWorld(final String string) {
        minecraft.changeWorld1(null);
        System.gc();
        final World world = new World(new File(getMinecraftDir(), "saves"), string);
        if (world.isNewWorld) {
            minecraft.changeWorld2(world, "Generating level");
        } else {
            minecraft.changeWorld2(world, "Loading level");
        }
    }

    public void changeWorld1(final World fe) {
        minecraft.changeWorld2(fe, "");
    }

    public void changeWorld2(final World fe, final String string) {
        if (minecraft.theWorld != null) {
            minecraft.theWorld.saveWorldIndirectly(minecraft.loadingScreen);
        }
        if ((minecraft.theWorld = fe) != null) {
            minecraft.playerController.func_717_a(fe);
            fe.h = minecraft.fontRenderer;
            if (!minecraft.isMultiplayerWorld()) {
                minecraft.thePlayer = (EntityPlayerSP) fe.func_4085_a(EntityPlayerSP.class);
                fe.player = minecraft.thePlayer;
            } else if (minecraft.thePlayer != null) {
                minecraft.thePlayer.preparePlayerToSpawn();
                if (fe != null) {
                    fe.player = minecraft.thePlayer;
                    fe.entityJoinedWorld(minecraft.thePlayer);
                }
            }
            minecraft.func_6255_d(string);
            if (minecraft.thePlayer == null) {
                (minecraft.thePlayer = new EntityPlayerSP(minecraft, fe, minecraft.sessionData)).preparePlayerToSpawn();
                minecraft.playerController.flipPlayer(minecraft.thePlayer);
            }
            minecraft.thePlayer.movementInput = new MovementInputFromOptions(minecraft.gameSettings);
            if (minecraft.renderGlobal != null) {
                minecraft.renderGlobal.changeWorld(fe);
            }
            if (minecraft.effectRenderer != null) {
                minecraft.effectRenderer.clearEffects(fe);
            }
            minecraft.playerController.func_6473_b(minecraft.thePlayer);
            fe.player = minecraft.thePlayer;
            fe.spawnPlayerWithLoadedChunks();
            if (fe.isNewWorld) {
                fe.saveWorldIndirectly(minecraft.loadingScreen);
            }
        }
        System.gc();
        minecraft.systemTime = 0L;
        this.sndManager.currentMusicTheme = "ingame";
        this.sndManager.ticksBeforeMusic = 0;
        this.sndManager.stopSound("BgMusic");
    }

    private void func_6255_d(final String string) {
        minecraft.loadingScreen.printText(string);
        minecraft.loadingScreen.displayLoadingString("Building terrain");
        final int n = 128;
        int n2 = 0;
        int n3 = n * 2 / 16 + 1;
        n3 *= n3;
        for (int i = -n; i <= n; i += 16) {
            int x = minecraft.theWorld.x;
            int z = minecraft.theWorld.z;
            if (minecraft.theWorld.player != null) {
                x = (int) minecraft.theWorld.player.posX;
                z = (int) minecraft.theWorld.player.posZ;
            }
            for (int j = -n; j <= n; j += 16) {
                minecraft.loadingScreen.setLoadingProgress(n2++ * 100 / n3);
                minecraft.theWorld.getBlockId(x + i, 64, z + j);
                while (minecraft.theWorld.updatingLighting()) {
                }
            }
        }
        minecraft.loadingScreen.displayLoadingString("Simulating world for a bit");
        n3 = 2000;
        BlockSand.fallInstantly = true;
        for (int i = 0; i < n3; ++i) {
            minecraft.theWorld.TickUpdates(true);
        }
        minecraft.theWorld.func_656_j();
        BlockSand.fallInstantly = false;
    }

    public void installResource(String string, final File file) {
        final int index = string.indexOf("/");
        final String substring = string.substring(0, index);
        string = string.substring(index + 1);
        if (substring.equalsIgnoreCase("sound")) {
            minecraft.sndManager.addSound(string, file);
        } else if (substring.equalsIgnoreCase("newsound")) {
            minecraft.sndManager.addSound(string, file);
        } else if (substring.equalsIgnoreCase("music")) {
            minecraft.sndManager.addIngameMusic(string, file);
        } else if (substring.equalsIgnoreCase("newmusic")) {
            minecraft.sndManager.addIngameMusic(string, file);
        } else if (substring.equalsIgnoreCase("menumusic")) {
            minecraft.sndManager.addMenuMusic(string, file);
        }
    }

    public OpenGlCapsChecker getOpenGlCapsChecker() {
        return minecraft.glCapabilities;
    }

    public String debugInfoRenders() {
        return minecraft.renderGlobal.getDebugInfoRenders();
    }

    public String func_6262_n() {
        return minecraft.renderGlobal.getDebugInfoEntities();
    }

    public String debugInfoEntities() {
        return "P: " + minecraft.effectRenderer.getStatistics() + ". T: " + minecraft.theWorld.func_687_d();
    }

    public void respawn() {
        if (minecraft.thePlayer != null && minecraft.theWorld != null) {
            minecraft.theWorld.setEntityDead(minecraft.thePlayer);
        }
        minecraft.theWorld.a();
        (minecraft.thePlayer = new EntityPlayerSP(minecraft, minecraft.theWorld, minecraft.sessionData)).preparePlayerToSpawn();
        minecraft.playerController.flipPlayer(minecraft.thePlayer);
        if (minecraft.theWorld != null) {
            minecraft.theWorld.player = minecraft.thePlayer;
            minecraft.theWorld.spawnPlayerWithLoadedChunks();
        }
        minecraft.thePlayer.movementInput = new MovementInputFromOptions(minecraft.gameSettings);
        minecraft.playerController.func_6473_b(minecraft.thePlayer);
        minecraft.func_6255_d("Respawning");
    }

    public static File getMinecraftDir() {
        if (Minecraft.minecraftDir == null) {
            Minecraft.minecraftDir = getAppDir("minecraft");
        }
        return Minecraft.minecraftDir;
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

    public static Minecraft getMinecraft() {
    	return Minecraft.minecraft;
    }

    public float getTickDelta() {
        return this.timer.tickDelta;
    }
}
