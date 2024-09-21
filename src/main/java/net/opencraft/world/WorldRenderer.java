
package net.opencraft.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.ContainerBlock;
import net.opencraft.entity.Entity;
import net.opencraft.physics.AABB;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.client.renderer.culling.ICamera;
import net.opencraft.client.renderer.entity.Render;
import net.opencraft.client.renderer.entity.RenderBlocks;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.tileentity.TileEntityRenderer;
import net.opencraft.util.Mth;
import net.opencraft.world.chunk.Chunk;
import net.opencraft.world.chunk.ChunkCache;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {

    private static Tessellator tessellator = Tessellator.instance;
    public static int chunksUpdated;
    public World a;
    private int C;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public boolean isInFrustum;
    public boolean[] p;
    public int q;
    public int r;
    public int s;
    public float t;
    public boolean u;
    public AABB v;
    public int w;
    public boolean x;
    public boolean y;
    public int z;
    public boolean A;
    private boolean E;
    public List B;
    private final List F;

    static {
        WorldRenderer.chunksUpdated = 0;
    }

    public WorldRenderer(final World fe, final List list, final int integer3, final int integer4, final int integer5, final int integer6, final int integer7) {
        this.C = -1;
        this.isInFrustum = false;
        this.p = new boolean[2];
        this.x = true;
        this.E = false;
        this.B = new ArrayList();
        this.a = fe;
        this.F = list;
        this.h = integer6;
        this.g = integer6;
        this.f = integer6;
        this.t = Mth.sqrt_float((this.f * this.f + this.g * this.g + this.h * this.h)) / 2.0f;
        this.C = integer7;
        this.c = -999;
        this.a(integer3, integer4, integer5);
        this.u = false;
    }

    public void a(final int integer1, final int integer2, final int integer3) {
        if (integer1 == this.c && integer2 == this.d && integer3 == this.e) {
            return;
        }
        this.b();
        this.c = integer1;
        this.d = integer2;
        this.e = integer3;
        this.q = integer1 + this.f / 2;
        this.r = integer2 + this.g / 2;
        this.s = integer3 + this.h / 2;
        this.l = (integer1 & 0x3FF);
        this.m = integer2;
        this.n = (integer3 & 0x3FF);
        this.i = integer1 - this.l;
        this.j = integer2 - this.m;
        this.k = integer3 - this.n;
        final float n = 2.0f;
        this.v = AABB.getBoundingBox(integer1 - n, integer2 - n, integer3 - n, integer1 + this.f + n, integer2 + this.g + n, integer3 + this.h + n);
        GL11.glNewList(this.C + 2, 4864);
        Render.renderAABB(AABB.getBoundingBoxFromPool(this.l - n, this.m - n, this.n - n, this.l + this.f + n, this.m + this.g + n, this.n + this.h + n));
        GL11.glEndList();
        this.f();
    }

    private void g() {
        GL11.glTranslatef(this.l, this.m, this.n);
    }

    public void a() {
        if (!this.u) {
            return;
        }
        ++WorldRenderer.chunksUpdated;
        final int c = this.c;
        final int d = this.d;
        final int e = this.e;
        final int n = this.c + this.f;
        final int n2 = this.d + this.g;
        final int n3 = this.e + this.h;
        for (int i = 0; i < 2; ++i) {
            this.p[i] = true;
        }
        Chunk.isLit = false;
        final HashSet set = new HashSet();
        set.addAll((Collection) this.B);
        this.B.clear();
        final int n4 = 1;
        final ChunkCache iv = new ChunkCache(this.a, c - n4, d - n4, e - n4, n + n4, n2 + n4, n3 + n4);
        final RenderBlocks renderBlocks = new RenderBlocks(iv);
        for (int j = 0; j < 2; ++j) {
            boolean b = false;
            boolean b2 = false;
            int n5 = 0;
            for (int k = d; k < n2; ++k) {
                for (int l = e; l < n3; ++l) {
                    for (int integer2 = c; integer2 < n; ++integer2) {
                        final int blockId = iv.getBlockId(integer2, k, l);
                        if (blockId > 0) {
                            if (n5 == 0) {
                                n5 = 1;
                                GL11.glNewList(this.C + j, 4864);
                                GL11.glPushMatrix();
                                this.g();
                                final float n6 = 1.000001f;
                                GL11.glTranslatef(-this.h / 2.0f, -this.g / 2.0f, -this.h / 2.0f);
                                GL11.glScalef(n6, n6, n6);
                                GL11.glTranslatef(this.h / 2.0f, this.g / 2.0f, this.h / 2.0f);
                                WorldRenderer.tessellator.beginQuads();
                                WorldRenderer.tessellator.setTranslationD(-this.c, -this.d, -this.e);
                            }
                            if (j == 0 && Block.blocksList[blockId] instanceof ContainerBlock) {
                                final TileEntity blockTileEntity = iv.getBlockTileEntity(integer2, k, l);
                                if (TileEntityRenderer.instance.a(blockTileEntity)) {
                                    this.B.add(blockTileEntity);
                                }
                            }
                            final Block gs = Block.blocksList[blockId];
                            final int renderBlockPass = gs.getRenderBlockPass();
                            if (renderBlockPass != j) {
                                b = true;
                            } else if (renderBlockPass == j) {
                                b2 |= renderBlocks.a(gs, integer2, k, l);
                            }
                        }
                    }
                }
            }
            if (n5 != 0) {
                WorldRenderer.tessellator.draw();
                GL11.glPopMatrix();
                GL11.glEndList();
                WorldRenderer.tessellator.setTranslationD(0.0, 0.0, 0.0);
            } else {
                b2 = false;
            }
            if (b2) {
                this.p[j] = false;
            }
            if (!b) {
                break;
            }
        }
        final HashSet set2 = new HashSet();
        set2.addAll((Collection) this.B);
        set2.removeAll((Collection) set);
        this.F.addAll((Collection) set2);
        set.removeAll((Collection) this.B);
        this.F.removeAll((Collection) set);
        this.A = Chunk.isLit;
        this.E = true;
    }

    public float chunkIndex(final Entity eq) {
        final float n = (float) (eq.posX - this.q);
        final float n2 = (float) (eq.posY - this.r);
        final float n3 = (float) (eq.posZ - this.s);
        return n * n + n2 * n2 + n3 * n3;
    }

    public void b() {
        for (int i = 0; i < 2; ++i) {
            this.p[i] = true;
        }
        this.isInFrustum = false;
        this.E = false;
    }

    public void c() {
        this.b();
        this.a = null;
    }

    public int a(final int integer) {
        if (!this.isInFrustum) {
            return -1;
        }
        if (!this.p[integer]) {
            return this.C + integer;
        }
        return -1;
    }

    public void a(final ICamera jt) {
        this.isInFrustum = jt.isBoundingBoxInFrustum(this.v);
    }

    public void d() {
        GL11.glCallList(this.C + 2);
    }

    public boolean e() {
        return this.E && this.p[0] && this.p[1];
    }

    public void f() {
        this.u = true;
    }

}
