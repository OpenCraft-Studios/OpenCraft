package net.opencraft;

import static net.opencraft.SharedConstants.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.File;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.SandBlock;
import net.opencraft.client.config.GameSettings;
import net.opencraft.client.entity.PlayerController;
import net.opencraft.client.entity.PlayerControllerSP;
import net.opencraft.client.entity.models.ModelBiped;
import net.opencraft.client.input.*;
import net.opencraft.client.sound.SoundManager;
import net.opencraft.entity.EntityPlayerSP;
import net.opencraft.entity.EntityRenderer;
import net.opencraft.item.ItemStack;
import net.opencraft.jobs.DownloadResourcesJob;
import net.opencraft.physics.AABB;
import net.opencraft.renderer.*;
import net.opencraft.renderer.entity.RenderGlobal;
import net.opencraft.renderer.entity.Renderer;
import net.opencraft.renderer.font.FontRenderer;
import net.opencraft.renderer.gui.*;
import net.opencraft.renderer.texture.*;
import net.opencraft.util.Mth;
import net.opencraft.world.World;
import net.opencraft.world.WorldRenderer;

public class OpenCraft implements Runnable, GLFWFramebufferSizeCallbackI {

    public static final String PROJECT_NAME_LOWERCASE = "opencraft";

    @Nonnull
    public static OpenCraft oc;

    public static long[] tickTimes;
    public static int numRecordedFrameTimes;
    private static File gameDir;
    public long window;
    public PlayerController playerController;
    public int width;
    public int height;
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
    public String objectMouseOverString;
    public int rightClickDelayTimer;
    public GuiIngame ingameGUI;
    public boolean skipRenderWorld;
    public ModelBiped field_9242_w;
    public MovingObjectPosition hitResult;
    public GameSettings options;
    public SoundManager sndManager;
    public File mcDataDir;
    private final TextureWaterFX textureWaterFX;
    private final TextureLavaFX textureLavaFX;
    public volatile boolean running;
    long prevFrameTime;
    public boolean inGameHasFocus;
    private int mouseTicksRan;
    public boolean isRaining;
    long systemTime;

    /**
     * Holds the function called when the window is resized, otherwise the function
     * would be garbage collected
     */
    private GLFWWindowFocusCallback windowFocusCallback;
    public MouseHandler mouse;
    public KeyboardInput keyboard;

    public String fpsString;
    private int fps = 0;

    static {
        OpenCraft.tickTimes = new long[512];
        OpenCraft.numRecordedFrameTimes = 0;
        OpenCraft.gameDir = new File("opencraft");
        if (!OpenCraft.gameDir.exists())
            OpenCraft.gameDir.mkdir();
    }

    public OpenCraft(int width, int height) {
        playerController = new PlayerControllerSP();
        timer = null;
        sessionData = new Session("Notch", "1488228");
        hideQuitButton = true;
        isGamePaused = false;
        currentScreen = null;
        loadingScreen = new LoadingScreenRenderer();
        entityRenderer = new EntityRenderer();
        ticksRan = 0;
        leftClickCounter = 0;
        objectMouseOverString = null;
        rightClickDelayTimer = 0;
        skipRenderWorld = false;
        field_9242_w = new ModelBiped(0.0f);
        hitResult = null;
        sndManager = new SoundManager();
        textureWaterFX = new TextureWaterFX();
        textureLavaFX = new TextureLavaFX();
        running = true;
        fpsString = "";
        prevFrameTime = -1L;
        inGameHasFocus = false;
        mouseTicksRan = 0;
        isRaining = false;
        systemTime = System.currentTimeMillis();
        // why? (new SleepingForeverThread("Timer hack thread")).start();
        this.width = width;
        this.height = height;
    }

    public static long getSystemTime() {
        return glfwGetTimerValue() * 1000L / glfwGetTimerFrequency();
    }

    public void init() {
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW!");

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_DEPTH_BITS, 24); // Request 24 bits rendering

        window = glfwCreateWindow(width, height, TITLE, NULL, NULL);
        if (window == NULL)
            throw new IllegalStateException("Failed to create the window!");

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int winX = (videoMode.width() - width) / 2;
        int winY = (videoMode.height() - height) / 2;

        glfwSetWindowPos(window, winX, winY);

        glfwMakeContextCurrent(window);
        Objects.requireNonNull(GL.createCapabilities(), "Failed to create OpenGL capabilities");
        glfwShowWindow(window);

        glfwSetFramebufferSizeCallback(window, this);
        GLUtil.setupDebugMessageCallback();

        mouse = new MouseHandler(window);
        keyboard = new KeyboardInput(window);

        timer = new Timer(20.0f);
        invoke(NULL, width, height);
        mcDataDir = getGameDir();
        options = new GameSettings(mcDataDir);
        renderer = new Renderer(options);
        font = new FontRenderer(options, "/assets/default.png", renderer);
        loadScreen();
        checkGLError();
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glClearDepth(1.0);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glDisable(GL_DEBUG_OUTPUT);
        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER, 0.1f);
        glCullFace(GL_BACK);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        checkGLError();
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
        new DownloadResourcesJob().run();
        checkGLError();
        ingameGUI = new GuiIngame(oc);
        playerController.a();
    }

    private void loadScreen() {
        final ScaledResolution scaledResolution = new ScaledResolution(width, height);
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, scaledWidth, scaledHeight, 0.0, 1000.0, 3000.0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glTranslatef(0.0f, 0.0f, -2000.0f);
        glViewport(0, 0, width, height);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glDisable(GL_LIGHTING);
        glDisable(GL_FOG);
        glEnable(GL_TEXTURE_2D);
        final Tessellator t = Tessellator.instance;
        renderer.bindTexture("/assets/dirt.png");
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float n = 32.0f;
        t.beginQuads();
        t.color(0x404040);
        {
            t.vertexUV(0.0, height, 0.0, 0.0, height / n + 0.0f);
            t.vertexUV(width, height, 0.0, width / n, height / n + 0.0f);
            t.vertexUV(width, 0.0, 0.0, width / n, 0.0);
            t.vertexUV(0.0, 0.0, 0.0, 0.0, 0.0);
        }
        t.render();

        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER, 0.1f);
        String strLoad = "Starting opencraft, this may take a while...";
        font.drawShadow(strLoad, (scaledWidth - font.width(strLoad)) / 2, scaledHeight / 2 - 30, 0xFFFFFF);
        glfwSwapBuffers(window);
    }

    public void displayGuiScreen(GuiScreen screen) {
        if (currentScreen instanceof GuiEmptyScreen)
            return;

        if (currentScreen != null)
            currentScreen.onGuiClosed();

        if (screen == null)
            if (world == null)
                screen = new GuiMainMenu();
            else if (player.health <= 0)
                screen = new GuiGameOver();

        if ((currentScreen = screen) != null) {
            setIngameNotInFocus();
            final ScaledResolution scaledResolution = new ScaledResolution(width, height);
            screen.setWorldAndResolution(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            skipRenderWorld = false;
        } else
            setIngameFocus();
    }

    private void checkGLError() {
        final int err = glGetError();
        if (err == 0)
            return;

        throw new IllegalStateException("GL ERROR: " + glGetError());
    }

    public void destroy() {
        try {
            System.out.println("Stopping!");
            changeWorld1(null);
            GLAllocation.deleteTexturesAndDisplayLists();
            sndManager.shutdown();
        } catch (Exception ignored) {
        } finally {
            glfwDestroyWindow(window);
            glfwTerminate();
        }

        System.gc();
        System.exit(0);
    }

    @Override
    public void run() {
        System.out.println(
                "Running on thread " + Thread.currentThread().threadId() + " / " + Thread.currentThread().getName());
        running = true;

        try {
            init();
        } catch (Exception exception) {
            exception.printStackTrace();
            destroy();
        }

        try {
            long begin = System.currentTimeMillis();
            while (running) {
                AABB.clearBoundingBoxPool();
                if (glfwWindowShouldClose(window))
                    stop();

                if (isGamePaused) {
                    final float renderPartialTicks = timer.renderPartialTicks;
                    timer.updateTimer();
                    timer.renderPartialTicks = renderPartialTicks;
                } else
                    timer.updateTimer();

                for (int j = 0; j < Math.min(10, timer.elapsedTicks); ++j) {
                    ++ticksRan;
                    this.tick();
                }
                checkGLError();
                if (isGamePaused)
                    timer.renderPartialTicks = 1.0f;

                sndManager.setListener(player, timer.renderPartialTicks);
                glEnable(GL_TEXTURE_2D);
                if (world != null)
                    while (world.updatingLighting())
                        ;

                if (!skipRenderWorld) {
                    playerController.setPartialTime(timer.renderPartialTicks);

                    // TODO: This method blocks the thread when loading the game
                    entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
                }

                prevFrameTime = System.nanoTime();

                // Other threads can execute
                Thread.yield();

                mouse.poll();

                glfwSwapBuffers(window);
                glfwPollEvents();

                checkGLError();
                ++fps;
                isGamePaused = isSingleplayer() && currentScreen != null && currentScreen.doesGuiPauseGame();

                long now;
                while ((now = System.currentTimeMillis()) >= begin + 1000L) {
                    fpsString = fps + " FPS, " + WorldRenderer.chunksUpdated + " chunk updates";

                    // Reset variables
                    WorldRenderer.chunksUpdated = 0;
                    begin = now;
                    fps = 0;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        destroy();
    }

    private void displayDebugInfo() {
        if (prevFrameTime == -1L)
            prevFrameTime = System.nanoTime();
        final long nanoTime = System.nanoTime();

        OpenCraft.tickTimes[OpenCraft.numRecordedFrameTimes++ & OpenCraft.tickTimes.length - 1] = nanoTime
                - prevFrameTime;
        prevFrameTime = nanoTime;
        glClear(GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, width, height, 0.0, 1000.0, 3000.0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glTranslatef(0.0f, 0.0f, -2000.0f);
        glLineWidth(1.0f);
        glDisable(GL_TEXTURE_2D);
        final Tessellator t = Tessellator.instance;
        t.beginQuads();
        {
            t.color(0x20_200000);

            t.vertex(0.0, height - 100, 0.0);
            t.vertex(0.0, height, 0.0);
            t.vertex(OpenCraft.tickTimes.length, height, 0.0);
            t.vertex(OpenCraft.tickTimes.length, height - 100, 0.0);
        }
        t.render();
        long n = 0L;
        for (int i = 0; i < OpenCraft.tickTimes.length; ++i)
            n += OpenCraft.tickTimes[i];
        int i = (int) (n / 200000L / OpenCraft.tickTimes.length);
        t.beginQuads();
        t.color(0x20_400000);
        t.vertex(0.0, height - i, 0.0);
        t.vertex(0.0, height, 0.0);
        t.vertex(OpenCraft.tickTimes.length, height, 0.0);
        t.vertex(OpenCraft.tickTimes.length, height - i, 0.0);
        t.render();
        t.begin(GL_LINES);
        for (int j = 0; j < OpenCraft.tickTimes.length; ++j) {
            final int n2 = (j - OpenCraft.numRecordedFrameTimes & OpenCraft.tickTimes.length - 1) * 255
                    / OpenCraft.tickTimes.length;
            int n3 = n2 * n2 / 255;
            n3 = n3 * n3 / 255;
            int n4 = n3 * n3 / 255;
            n4 = n4 * n4 / 255;
            t.color(-16777216 + n4 + n3 * 256 + n2 * 65536);
            t.vertex(j + 0.5f, height - OpenCraft.tickTimes[j] / 200000L + 0.5f, 0.0);
            t.vertex(j + 0.5f, height + 0.5f, 0.0);
        }
        t.render();
        glEnable(GL_TEXTURE_2D);
    }

    public void stop() {
        running = false;
    }

    public void setIngameFocus() {
        if (inGameHasFocus)
            return;
        inGameHasFocus = true;
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        displayGuiScreen(null);
        mouseTicksRan = ticksRan + 10000;
    }

    public void setIngameNotInFocus() {
        if (!inGameHasFocus)
            return;
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        inGameHasFocus = false;
    }

    public void displayInGameMenu() {
        if (currentScreen != null)
            return;

        displayGuiScreen(new GuiIngameMenu());
    }

    private void func_6254_a(final int integer, final boolean boolean2) {
        if (playerController.field_1064_b || integer == 0 && leftClickCounter > 0)
            return;
        if (boolean2 && hitResult != null && hitResult.typeOfHit == 0 && integer == 0) {
            final int blockX = hitResult.blockX;
            final int blockY = hitResult.blockY;
            final int blockZ = hitResult.blockZ;
            playerController.sendBlockRemoving(blockX, blockY, blockZ, hitResult.sideHit);
            effectRenderer.addBlockHitEffects(blockX, blockY, blockZ, hitResult.sideHit);
        } else
            playerController.resetBlockRemoving();
    }

    private void clickMouse(final int integer) {
        if (integer == 0 && leftClickCounter > 0)
            return;
        if (integer == 0)
            entityRenderer.itemRenderer.resetEquippedProgress();
        if (hitResult == null) {
            if (integer == 0)
                leftClickCounter = 10;
        } else if (hitResult.typeOfHit == 1) {
            if (integer == 0)
                player.a(hitResult.entityHit);
            if (integer == 1)
                player.c(hitResult.entityHit);
        } else if (hitResult.typeOfHit == 0) {
            final int blockX = hitResult.blockX;
            final int n = hitResult.blockY;
            final int blockZ = hitResult.blockZ;
            final int sideHit = hitResult.sideHit;
            final Block block = Block.BLOCKS[world.getBlockId(blockX, n, blockZ)];
            if (integer == 0) {
                world.onBlockHit(blockX, n, blockZ, hitResult.sideHit);
                if (block != Block.bedrock || player.unusedByte >= 100)
                    playerController.clickBlock(blockX, n, blockZ);
            } else {
                final ItemStack currentItem = player.inventory.getCurrentItem();
                final int blockId = world.getBlockId(blockX, n, blockZ);
                if (blockId > 0 && Block.BLOCKS[blockId].blockActivated(world, blockX, n, blockZ, player)
                        || currentItem == null)
                    return;
                final int stackSize = currentItem.stackSize;
                if (currentItem.useItem(player, world, blockX, n, blockZ, sideHit))
                    entityRenderer.itemRenderer.resetEquippedProgress();
                if (currentItem.stackSize == 0)
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                else if (currentItem.stackSize != stackSize)
                    entityRenderer.itemRenderer.b();
            }
        }
        if (integer == 1) {
            final ItemStack currentItem2 = player.inventory.getCurrentItem();
            if (currentItem2 != null) {
                final int n = currentItem2.stackSize;
                final ItemStack useItemRightClick = currentItem2.useItemRightClick(world, player);
                if (useItemRightClick != currentItem2
                        || useItemRightClick != null && useItemRightClick.stackSize != n) {
                    player.inventory.mainInventory[player.inventory.currentItem] = useItemRightClick;
                    entityRenderer.itemRenderer.d();
                    if (useItemRightClick.stackSize == 0)
                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                }
            }
        }
    }

    private void clickMiddleMouseButton() {
        if (hitResult == null)
            return;

        int blockId = world.getBlockId(hitResult.blockX, hitResult.blockY, hitResult.blockZ);
        if (blockId == Block.grass.id)
            blockId = Block.dirt.id;
        else if (blockId == Block.slabDouble.id)
            blockId = Block.slabSingle.id;
        else if (blockId == Block.bedrock.id)
            blockId = Block.stone.id;
        player.inventory.setCurrentItem(blockId);
    }

    public void tick() {
        ingameGUI.updateTick();
        if (!isGamePaused && world != null)
            playerController.updateController();
        renderer.bindTexture("/assets/terrain.png");
        if (!isGamePaused)
            renderer.updateDynamicTextures();
        if (currentScreen == null && player != null && player.health <= 0)
            displayGuiScreen(null);

        if (leftClickCounter > 0)
            --leftClickCounter;

        if (currentScreen == null || currentScreen.allowUserInput) {
            for (MouseHandler.ButtonEvent event : mouse.buttons.events) {
                if (System.currentTimeMillis() - systemTime > 200L)
                    continue;

                if (currentScreen == null) {
                    if (!inGameHasFocus && event.isPressed())
                        setIngameFocus();
                    else if (event.isPressed()) {
                        clickMouse(event.buttonNumber());
                        mouseTicksRan = ticksRan;
                    }
                } else {
                    if (currentScreen == null)
                        continue;
                    currentScreen.handleMouseEvent(event);
                }
            }

            if (currentScreen == null)
                for (Integer key : keyboard.pressedKeys) {
                    if (key == GLFW_KEY_ESCAPE)
                        displayInGameMenu();

                    if (key == GLFW_KEY_F5) {
                        options.thirdPersonView = !options.thirdPersonView;
                        isRaining = !isRaining;
                    }

                    if (key == options.keyBindings.get(GameSettings.PlayerInput.INVENTORY))
                        displayGuiScreen(new GuiInventory(player.inventory));

                    if (key == options.keyBindings.get(GameSettings.PlayerInput.DROP))
                        player.dropPlayerItemWithRandomChoice(
                                player.inventory.decrStackSize(player.inventory.currentItem, 1), false);

                    if (key >= GLFW_KEY_1 && key <= GLFW_KEY_9)
                        player.inventory.currentItem = key - GLFW_KEY_1;
                }

            if (currentScreen == null) {
                if (mouse.isButtonPressed(1) && ticksRan - mouseTicksRan >= timer.tps / 4.0f && inGameHasFocus) {
                    clickMouse(0);
                    mouseTicksRan = ticksRan;
                }
                if (mouse.isButtonPressed(2) && ticksRan - mouseTicksRan >= timer.tps / 4.0f && inGameHasFocus) {
                    clickMouse(1);
                    mouseTicksRan = ticksRan;
                }
            }
            func_6254_a(0, currentScreen == null && mouse.isButtonPressed(1) && inGameHasFocus);
        }
        if (currentScreen != null)
            mouseTicksRan = ticksRan + 10000;
        if (currentScreen != null) {
            currentScreen.handleInputEvents();
            if (currentScreen != null)
                currentScreen.updateScreen();
        }
        if (world != null) {
            world.difficultySetting = options.difficulty;
            if (!isGamePaused) {
                entityRenderer.updateRenderer();
                renderGlobal.updateClouds();
                world.updateEntities();
                if (isSingleplayer())
                    world.tick();

                world.randomDisplayUpdates(Mth.floor_double(player.x), Mth.floor_double(player.y),
                        Mth.floor_double(player.z));

                effectRenderer.updateEffects();
            }
        }
        systemTime = System.currentTimeMillis();
    }

    public boolean isSingleplayer() {
        return true;
    }

    public void startWorld(final String string) {
        changeWorld1(null);
        System.gc();
        final World world = new World(new File(getGameDir(), "saves"), string);
        if (world.newWorld)
            changeWorld(world, "Generating level");
        else
            changeWorld(world, "Loading level");
    }

    public void changeWorld1(final World fe) {
        changeWorld(fe, "");
    }

    public void changeWorld(final World world, String string) {
        if (this.world != null)
            this.world.saveWorldIndirectly(loadingScreen);
        if ((this.world = world) != null) {
            playerController.func_717_a(world);
            world.h = font;
            if (isSingleplayer()) {
                player = (EntityPlayerSP) world.func_4085_a(EntityPlayerSP.class);
                world.player = player;
            } else if (player != null) {
                player.preparePlayerToSpawn();
                if (world != null) {
                    world.player = player;
                    world.onEntityJoin(player);
                }
            }
            func_6255_d(string);
            if (player == null) {
                (player = new EntityPlayerSP(oc, world, sessionData)).preparePlayerToSpawn();
                playerController.flipPlayer(player);
            }
            player.movementInput = new MovementInput(options, keyboard);
            if (renderGlobal != null)
                renderGlobal.changeWorld(world);
            if (effectRenderer != null)
                effectRenderer.clearEffects(world);
            playerController.func_6473_b(player);
            world.player = player;
            world.spawnPlayerWithLoadedChunks();
            if (world.newWorld)
                world.saveWorldIndirectly(loadingScreen);
        }
        System.gc();
        systemTime = 0L;
        sndManager.currentMusicTheme = "ingame";
        sndManager.ticksBeforeMusic = 0;
        sndManager.stopSound("BgMusic");
    }

    private void func_6255_d(final String string) {
        loadingScreen.printText(string);
        loadingScreen.setLoadingMessage("Building terrain");
        final int n = 128;
        int n2 = 0;
        int n3 = n * 2 / 16 + 1;
        n3 *= n3;
        for (int i = -n; i <= n; i += 16) {
            int x = world.x;
            int z = world.z;
            if (world.player != null) {
                x = (int) world.player.x;
                z = (int) world.player.z;
            }
            for (int j = -n; j <= n; j += 16) {
                loadingScreen.setProgress(n2++ * 100 / n3);
                world.getBlockId(x + i, 64, z + j);
                while (world.updatingLighting());
            }
        }
        loadingScreen.setLoadingMessage("Simulating world for a bit");
        n3 = 2000;
        SandBlock.fallInstantly = true;
        for (int i = 0; i < n3; ++i)
            world.tickUpdates(true);
        world.func_656_j();
        SandBlock.fallInstantly = false;
    }

    public String debugInfoRenders() {
        return renderGlobal.getDebugInfoRenders();
    }

    public String entityRenderingInfo() {
        return renderGlobal.getDebugInfoEntities();
    }

    public String debugInfoEntities() {
        return "P: " + effectRenderer.getStatistics() + ". T: " + world.func_687_d();
    }

    public void respawn() {
        if (player != null && world != null)
            world.setEntityDead(player);
        world.a();
        (player = new EntityPlayerSP(oc, world, sessionData)).preparePlayerToSpawn();
        playerController.flipPlayer(player);
        if (world != null) {
            world.player = player;
            world.spawnPlayerWithLoadedChunks();
        }
        player.movementInput = new MovementInput(options, keyboard);
        playerController.func_6473_b(player);
        func_6255_d("Respawning");
    }

    public static File getGameDir() {
        if (gameDir == null)
            gameDir = new File(PROJECT_NAME_LOWERCASE);
        return gameDir;
    }

    public float getTickDelta() {
        return timer.tickDelta;
    }

    @Override
    public void invoke(long window, int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        if (currentScreen == null)
            return;

        final ScaledResolution scaledResolution = new ScaledResolution(width, height);
        currentScreen.setWorldAndResolution(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
    }

}
