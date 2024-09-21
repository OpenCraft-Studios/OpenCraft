
package net.opencraft.client.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.opencraft.IsoImageBuffer;
import net.opencraft.TerrainTextureManager;
import net.opencraft.ThreadRunIsoClient;
import net.opencraft.util.EnumOS1;
import net.opencraft.util.OsMap;
import net.opencraft.world.World;
import net.opencraft.world.chunk.storage.SaveHandler;

public class CanvasIsomPreview extends Canvas implements KeyListener, MouseListener, MouseMotionListener, Runnable {

    private int field_1793_a;
    private int zoomLevel;
    private boolean displayHelpText;
    private World worldObj;
    private File dataFolder;
    public boolean running;
    private List imageBufferList;
    private IsoImageBuffer[][] imageBuffers;
    private int field_1785_i;
    private int field_1784_j;
    private int xPosition;
    private int yPosition;

    public File getMinecraftDir() {
        if (this.dataFolder == null) {
            this.dataFolder = this.getAppDir("minecraft");
        }
        return this.dataFolder;
    }

    public File getAppDir(final String string) {
        final String property = System.getProperty("user.home", ".");
        File file = null;
        switch (OsMap.field_1193_a[getOs().ordinal()]) {
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

    private static EnumOS1 getOs() {
        final String lowerCase = System.getProperty("os.name").toLowerCase();
        if (lowerCase.contains("win")) {
            return EnumOS1.windows;
        }
        if (lowerCase.contains("mac")) {
            return EnumOS1.macos;
        }
        if (lowerCase.contains("solaris")) {
            return EnumOS1.solaris;
        }
        if (lowerCase.contains("sunos")) {
            return EnumOS1.solaris;
        }
        if (lowerCase.contains("linux")) {
            return EnumOS1.linux;
        }
        if (lowerCase.contains("unix")) {
            return EnumOS1.linux;
        }
        return EnumOS1.unknown;
    }

    public CanvasIsomPreview() {
        this.field_1793_a = 0;
        this.zoomLevel = 2;
        this.displayHelpText = true;
        this.running = true;
        this.imageBufferList = Collections.synchronizedList((List) new LinkedList());
        this.imageBuffers = new IsoImageBuffer[64][64];
        this.dataFolder = this.getMinecraftDir();
        for (int i = 0; i < 64; ++i) {
            for (int j = 0; j < 64; ++j) {
                this.imageBuffers[i][j] = new IsoImageBuffer(null, i, j);
            }
        }
        this.addMouseListener((MouseListener) this);
        this.addMouseMotionListener((MouseMotionListener) this);
        this.addKeyListener((KeyListener) this);
        this.setFocusable(true);
        this.requestFocus();
        this.setBackground(Color.red);
    }

    public void loadWorld(final String string) {
        final int n = 0;
        this.field_1784_j = n;
        this.field_1785_i = n;
        this.worldObj = new SaveHandler(this, new File(this.dataFolder, "saves"), string);
        this.worldObj.skylightSubtracted = 0;
        synchronized (this.imageBufferList) {
            this.imageBufferList.clear();
            for (int i = 0; i < 64; ++i) {
                for (int j = 0; j < 64; ++j) {
                    this.imageBuffers[i][j].setWorldAndChunkPosition(this.worldObj, i, j);
                }
            }
        }
    }

    private void setTimeOfDay(final int integer) {
        synchronized (this.imageBufferList) {
            this.worldObj.skylightSubtracted = integer;
            this.imageBufferList.clear();
            for (int i = 0; i < 64; ++i) {
                for (int j = 0; j < 64; ++j) {
                    this.imageBuffers[i][j].setWorldAndChunkPosition(this.worldObj, i, j);
                }
            }
        }
    }

    public void startThreads() {
        new ThreadRunIsoClient(this).start();
        for (int i = 0; i < 1; ++i) {
            new Thread((Runnable) this).start();
        }
    }

    public void exit() {
        this.running = false;
    }

    private IsoImageBuffer getImageBuffer(final int integer1, final int integer2) {
        final IsoImageBuffer isoImageBuffer = this.imageBuffers[integer1 & 0x3F][integer2 & 0x3F];
        if (isoImageBuffer.chunkX == integer1 && isoImageBuffer.chunkZ == integer2) {
            return isoImageBuffer;
        }
        synchronized (this.imageBufferList) {
            this.imageBufferList.remove(isoImageBuffer);
        }
        isoImageBuffer.setChunkPosition(integer1, integer2);
        return isoImageBuffer;
    }

    public void run() {
        final TerrainTextureManager terrainTextureManager = new TerrainTextureManager();
        while (this.running) {
            IsoImageBuffer di = null;
            synchronized (this.imageBufferList) {
                if (this.imageBufferList.size() > 0) {
                    di = (IsoImageBuffer) this.imageBufferList.remove(0);
                }
            }
            if (di != null) {
                if (this.field_1793_a - di.field_1350_g < 2) {
                    terrainTextureManager.func_799_a(di);
                    this.repaint();
                } else {
                    di.field_1349_h = false;
                }
            }
            try {
                Thread.sleep(2L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update(final Graphics graphics) {
    }

    public void paint(final Graphics graphics) {
    }

    public void showNextBuffer() {
        final BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(2);
            return;
        }
        this.drawScreen((Graphics2D) bufferStrategy.getDrawGraphics());
        bufferStrategy.show();
    }

    public void drawScreen(final Graphics2D graphics2D) {
        ++this.field_1793_a;
        final AffineTransform transform = graphics2D.getTransform();
        graphics2D.setClip(0, 0, this.getWidth(), this.getHeight());
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics2D.translate(this.getWidth() / 2, this.getHeight() / 2);
        graphics2D.scale((double) this.zoomLevel, (double) this.zoomLevel);
        graphics2D.translate(this.field_1785_i, this.field_1784_j);
        if (this.worldObj != null) {
            graphics2D.translate(-(this.worldObj.x + this.worldObj.z), -(-this.worldObj.x + this.worldObj.z) + 64);
        }
        final Rectangle clipBounds = graphics2D.getClipBounds();
        graphics2D.setColor(new Color(-15724512));
        graphics2D.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
        final int n = 16;
        final int n2 = 3;
        final int n3 = clipBounds.x / n / 2 - 2 - n2;
        final int n4 = (clipBounds.x + clipBounds.width) / n / 2 + 1 + n2;
        final int n5 = clipBounds.y / n - 1 - n2 * 2;
        for (int n6 = (clipBounds.y + clipBounds.height + 16 + 128) / n + 1 + n2 * 2, i = n5; i <= n6; ++i) {
            for (int j = n3; j <= n4; ++j) {
                final IsoImageBuffer imageBuffer = this.getImageBuffer(j - (i >> 1), j + (i + 1 >> 1));
                imageBuffer.field_1350_g = this.field_1793_a;
                if (!imageBuffer.field_1352_e) {
                    if (!imageBuffer.field_1349_h) {
                        imageBuffer.field_1349_h = true;
                        this.imageBufferList.add(imageBuffer);
                    }
                } else {
                    imageBuffer.field_1349_h = false;
                    if (!imageBuffer.field_1351_f) {
                        graphics2D.drawImage((Image) imageBuffer.field_1348_a, j * n * 2 + (i & 0x1) * n, i * n - 128 - 16, (ImageObserver) null);
                    }
                }
            }
        }
        if (this.displayHelpText) {
            graphics2D.setTransform(transform);
            final int i = this.getHeight() - 32 - 4;
            graphics2D.setColor(new Color(Integer.MIN_VALUE, true));
            graphics2D.fillRect(4, this.getHeight() - 32 - 4, this.getWidth() - 8, 32);
            graphics2D.setColor(Color.WHITE);
            final String s = "F1 - F5: load levels   |   0-9: Set time of day   |   Space: return to spawn   |   Double click: zoom   |   Escape: hide this text";
            graphics2D.drawString(s, this.getWidth() / 2 - graphics2D.getFontMetrics().stringWidth(s) / 2, i + 20);
        }
        graphics2D.dispose();
    }

    public void mouseDragged(final MouseEvent mouseEvent) {
        final int xPosition = mouseEvent.getX() / this.zoomLevel;
        final int yPosition = mouseEvent.getY() / this.zoomLevel;
        this.field_1785_i += xPosition - this.xPosition;
        this.field_1784_j += yPosition - this.yPosition;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.repaint();
    }

    public void mouseMoved(final MouseEvent mouseEvent) {
    }

    public void mouseClicked(final MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            this.zoomLevel = 3 - this.zoomLevel;
            this.repaint();
        }
    }

    public void mouseEntered(final MouseEvent mouseEvent) {
    }

    public void mouseExited(final MouseEvent mouseEvent) {
    }

    public void mousePressed(final MouseEvent mouseEvent) {
        final int xPosition = mouseEvent.getX() / this.zoomLevel;
        final int yPosition = mouseEvent.getY() / this.zoomLevel;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void mouseReleased(final MouseEvent mouseEvent) {
    }

    public void keyPressed(final KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 48) {
            this.setTimeOfDay(11);
        }
        if (keyEvent.getKeyCode() == 49) {
            this.setTimeOfDay(10);
        }
        if (keyEvent.getKeyCode() == 50) {
            this.setTimeOfDay(9);
        }
        if (keyEvent.getKeyCode() == 51) {
            this.setTimeOfDay(7);
        }
        if (keyEvent.getKeyCode() == 52) {
            this.setTimeOfDay(6);
        }
        if (keyEvent.getKeyCode() == 53) {
            this.setTimeOfDay(5);
        }
        if (keyEvent.getKeyCode() == 54) {
            this.setTimeOfDay(3);
        }
        if (keyEvent.getKeyCode() == 55) {
            this.setTimeOfDay(2);
        }
        if (keyEvent.getKeyCode() == 56) {
            this.setTimeOfDay(1);
        }
        if (keyEvent.getKeyCode() == 57) {
            this.setTimeOfDay(0);
        }
        if (keyEvent.getKeyCode() == 112) {
            this.loadWorld("World1");
        }
        if (keyEvent.getKeyCode() == 113) {
            this.loadWorld("World2");
        }
        if (keyEvent.getKeyCode() == 114) {
            this.loadWorld("World3");
        }
        if (keyEvent.getKeyCode() == 115) {
            this.loadWorld("World4");
        }
        if (keyEvent.getKeyCode() == 116) {
            this.loadWorld("World5");
        }
        if (keyEvent.getKeyCode() == 32) {
            final int n = 0;
            this.field_1784_j = n;
            this.field_1785_i = n;
        }
        if (keyEvent.getKeyCode() == 27) {
            this.displayHelpText = !this.displayHelpText;
        }
        this.repaint();
    }

    public void keyReleased(final KeyEvent keyEvent) {
    }

    public void keyTyped(final KeyEvent keyEvent) {
    }
}
