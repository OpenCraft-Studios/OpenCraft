
package net.opencraft.tileentity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.opencraft.client.font.FontRenderer;
import net.opencraft.client.renderer.entity.RenderEngine;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityRenderer {

    private Map m;
    public static TileEntityRenderer instance;
    private FontRenderer n;
    public static double b;
    public static double c;
    public static double d;
    public RenderEngine renderEngine;
    public World f;
    public EntityPlayer g;
    public float h;
    public float i;
    public double j;
    public double k;
    public double l;

    private TileEntityRenderer() {
        (this.m = (Map) new HashMap()).put(TileEntitySign.class, new TileEntitySignRenderer());
        this.m.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        final Iterator iterator = this.m.values().iterator();
        while (iterator.hasNext()) {
            ((TileEntitySpecialRenderer) iterator.next()).setTileEntityRenderer(this);
        }
    }

    public TileEntitySpecialRenderer a(final Class class1) {
        TileEntitySpecialRenderer a = (TileEntitySpecialRenderer) this.m.get(class1);
        if (a == null && class1 != TileEntity.class) {
            a = this.a(class1.getSuperclass());
            this.m.put(class1, a);
        }
        return a;
    }

    public boolean a(final TileEntity bk) {
        return this.b(bk) != null;
    }

    public TileEntitySpecialRenderer b(final TileEntity bk) {
        return this.a(bk.getClass());
    }

    public void a(final World fe, final RenderEngine id, final FontRenderer ej, final EntityPlayer gi, final float float5) {
        this.f = fe;
        this.renderEngine = id;
        this.g = gi;
        this.n = ej;
        this.h = gi.prevRotationYaw + (gi.rotationYaw - gi.prevRotationYaw) * float5;
        this.i = gi.prevRotationPitch + (gi.rotationPitch - gi.prevRotationPitch) * float5;
        this.j = gi.lastTickPosX + (gi.posX - gi.lastTickPosX) * float5;
        this.k = gi.lastTickPosY + (gi.posY - gi.lastTickPosY) * float5;
        this.l = gi.lastTickPosZ + (gi.posZ - gi.lastTickPosZ) * float5;
    }

    public void a(final TileEntity bk, final float float2) {
        if (bk.getDistanceFrom(this.j, this.k, this.l) < 4096.0) {
            final float lightBrightness = this.f.getLightBrightness(bk.xCoord, bk.yCoord, bk.zCoord);
            GL11.glColor3f(lightBrightness, lightBrightness, lightBrightness);
            this.renderTileEntityAt(bk, bk.xCoord - TileEntityRenderer.b, bk.yCoord - TileEntityRenderer.c, bk.zCoord - TileEntityRenderer.d, float2);
        }
    }

    public void renderTileEntityAt(final TileEntity bk, final double double2, final double double3, final double double4, final float float5) {
        final TileEntitySpecialRenderer b = this.b(bk);
        if (b != null) {
            b.renderTileEntityMobSpawner(bk, double2, double3, double4, float5);
        }
    }

    public FontRenderer getFontRenderer() {
        return this.n;
    }

    static {
        TileEntityRenderer.instance = new TileEntityRenderer();
    }
}
