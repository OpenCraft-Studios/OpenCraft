
package net.opencraft.client.renderer.entity;

import net.opencraft.OpenCraft;
import net.opencraft.block.Block;
import net.opencraft.block.BlockDoor;
import net.opencraft.block.BlockFluid;
import net.opencraft.block.material.Material;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.util.MathHelper;
import net.opencraft.world.IBlockAccess;
import net.opencraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderBlocks {

    private IBlockAccess a;
    private int b;
    private boolean c;
    private boolean d;
    private OpenCraft mc;

    public RenderBlocks(final IBlockAccess iv) {
        this.b = -1;
        this.c = false;
        this.d = false;
        this.a = iv;
        this.mc = OpenCraft.getOpenCraft();
    }

    public RenderBlocks() {
        this.b = -1;
        this.c = false;
        this.d = false;
        this.a = null;
        this.mc = OpenCraft.getOpenCraft();
    }

    public void a(final Block gs, final int integer2, final int integer3, final int integer4, final int integer5) {
        this.b = integer5;
        this.a(gs, integer2, integer3, integer4);
        this.b = -1;
    }

    public boolean a(final Block gs, final int integer2, final int integer3, final int integer4) {
        final int renderType = gs.getRenderType();
        gs.setBlockBoundsBasedOnState(this.a, integer2, integer3, integer4);
        if (renderType == 0) {
            return this.j(gs, integer2, integer3, integer4);
        }
        if (renderType == 4) {
            return this.i(gs, integer2, integer3, integer4);
        }
        if (renderType == 1) {
            return this.g(gs, integer2, integer3, integer4);
        }
        if (renderType == 6) {
            return this.h(gs, integer2, integer3, integer4);
        }
        if (renderType == 2) {
            return this.b(gs, integer2, integer3, integer4);
        }
        if (renderType == 3) {
            return this.c(gs, integer2, integer3, integer4);
        }
        if (renderType == 5) {
            return this.d(gs, integer2, integer3, integer4);
        }
        if (renderType == 8) {
            return this.f(gs, integer2, integer3, integer4);
        }
        if (renderType == 7) {
            return this.m(gs, integer2, integer3, integer4);
        }
        if (renderType == 9) {
            return this.e(gs, integer2, integer3, integer4);
        }
        if (renderType == 10) {
            return this.l(gs, integer2, integer3, integer4);
        }
        return renderType == 11 && this.k(gs, integer2, integer3, integer4);
    }

    public boolean b(final Block gs, final int integer2, final int integer3, final int integer4) {
        final int blockMetadata = this.a.getBlockMetadata(integer2, integer3, integer4);
        final Tessellator instance = Tessellator.instance;
        float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        if (Block.lightValue[gs.blockID] > 0) {
            blockBrightness = 1.0f;
        }
        instance.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
        final double n = 0.4000000059604645;
        final double n2 = 0.5 - n;
        final double n3 = 0.20000000298023224;
        if (blockMetadata == 1) {
            this.a(gs, integer2 - n2, integer3 + n3, integer4, -n, 0.0);
        } else if (blockMetadata == 2) {
            this.a(gs, integer2 + n2, integer3 + n3, integer4, n, 0.0);
        } else if (blockMetadata == 3) {
            this.a(gs, integer2, integer3 + n3, integer4 - n2, 0.0, -n);
        } else if (blockMetadata == 4) {
            this.a(gs, integer2, integer3 + n3, integer4 + n2, 0.0, n);
        } else {
            this.a(gs, integer2, integer3, integer4, 0.0, 0.0);
        }
        return true;
    }

    public boolean c(final Block gs, final int integer2, int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        int n = gs.getBlockTextureFromSide(0);
        if (this.b >= 0) {
            n = this.b;
        }
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        instance.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
        final int n2 = (n & 0xF) << 4;
        final int n3 = n & 0xF0;
        double n4 = n2 / 256.0f;
        double n5 = (n2 + 15.99f) / 256.0f;
        double n6 = n3 / 256.0f;
        double n7 = (n3 + 15.99f) / 256.0f;
        float n8 = 1.4f;
        if (this.a.isBlockNormalCube(integer2, integer3 - 1, integer4) || Block.fire.canBlockCatchFire(this.a, integer2, integer3 - 1, integer4)) {
            double n9 = integer2 + 0.5 + 0.2;
            double n10 = integer2 + 0.5 - 0.2;
            double n11 = integer4 + 0.5 + 0.2;
            double n12 = integer4 + 0.5 - 0.2;
            double n13 = integer2 + 0.5 - 0.3;
            double n14 = integer2 + 0.5 + 0.3;
            double n15 = integer4 + 0.5 - 0.3;
            double n16 = integer4 + 0.5 + 0.3;
            instance.addVertexWithUV(n13, integer3 + n8, integer4 + 1, n5, n6);
            instance.addVertexWithUV(n9, integer3 + 0, integer4 + 1, n5, n7);
            instance.addVertexWithUV(n9, integer3 + 0, integer4 + 0, n4, n7);
            instance.addVertexWithUV(n13, integer3 + n8, integer4 + 0, n4, n6);
            instance.addVertexWithUV(n14, integer3 + n8, integer4 + 0, n5, n6);
            instance.addVertexWithUV(n10, integer3 + 0, integer4 + 0, n5, n7);
            instance.addVertexWithUV(n10, integer3 + 0, integer4 + 1, n4, n7);
            instance.addVertexWithUV(n14, integer3 + n8, integer4 + 1, n4, n6);
            n4 = n2 / 256.0f;
            n5 = (n2 + 15.99f) / 256.0f;
            n6 = (n3 + 16) / 256.0f;
            n7 = (n3 + 15.99f + 16.0f) / 256.0f;
            instance.addVertexWithUV(integer2 + 1, integer3 + n8, n16, n5, n6);
            instance.addVertexWithUV(integer2 + 1, integer3 + 0, n12, n5, n7);
            instance.addVertexWithUV(integer2 + 0, integer3 + 0, n12, n4, n7);
            instance.addVertexWithUV(integer2 + 0, integer3 + n8, n16, n4, n6);
            instance.addVertexWithUV(integer2 + 0, integer3 + n8, n15, n5, n6);
            instance.addVertexWithUV(integer2 + 0, integer3 + 0, n11, n5, n7);
            instance.addVertexWithUV(integer2 + 1, integer3 + 0, n11, n4, n7);
            instance.addVertexWithUV(integer2 + 1, integer3 + n8, n15, n4, n6);
            n9 = integer2 + 0.5 - 0.5;
            n10 = integer2 + 0.5 + 0.5;
            n11 = integer4 + 0.5 - 0.5;
            n12 = integer4 + 0.5 + 0.5;
            n13 = integer2 + 0.5 - 0.4;
            n14 = integer2 + 0.5 + 0.4;
            n15 = integer4 + 0.5 - 0.4;
            n16 = integer4 + 0.5 + 0.4;
            instance.addVertexWithUV(n13, integer3 + n8, integer4 + 0, n4, n6);
            instance.addVertexWithUV(n9, integer3 + 0, integer4 + 0, n4, n7);
            instance.addVertexWithUV(n9, integer3 + 0, integer4 + 1, n5, n7);
            instance.addVertexWithUV(n13, integer3 + n8, integer4 + 1, n5, n6);
            instance.addVertexWithUV(n14, integer3 + n8, integer4 + 1, n4, n6);
            instance.addVertexWithUV(n10, integer3 + 0, integer4 + 1, n4, n7);
            instance.addVertexWithUV(n10, integer3 + 0, integer4 + 0, n5, n7);
            instance.addVertexWithUV(n14, integer3 + n8, integer4 + 0, n5, n6);
            n4 = n2 / 256.0f;
            n5 = (n2 + 15.99f) / 256.0f;
            n6 = n3 / 256.0f;
            n7 = (n3 + 15.99f) / 256.0f;
            instance.addVertexWithUV(integer2 + 0, integer3 + n8, n16, n4, n6);
            instance.addVertexWithUV(integer2 + 0, integer3 + 0, n12, n4, n7);
            instance.addVertexWithUV(integer2 + 1, integer3 + 0, n12, n5, n7);
            instance.addVertexWithUV(integer2 + 1, integer3 + n8, n16, n5, n6);
            instance.addVertexWithUV(integer2 + 1, integer3 + n8, n15, n4, n6);
            instance.addVertexWithUV(integer2 + 1, integer3 + 0, n11, n4, n7);
            instance.addVertexWithUV(integer2 + 0, integer3 + 0, n11, n5, n7);
            instance.addVertexWithUV(integer2 + 0, integer3 + n8, n15, n5, n6);
        } else {
            final float n17 = 0.2f;
            final float n18 = 0.0625f;
            if ((integer2 + integer3 + integer4 & 0x1) == 0x1) {
                n4 = n2 / 256.0f;
                n5 = (n2 + 15.99f) / 256.0f;
                n6 = (n3 + 16) / 256.0f;
                n7 = (n3 + 15.99f + 16.0f) / 256.0f;
            }
            if ((integer2 / 2 + integer3 / 2 + integer4 / 2 & 0x1) == 0x1) {
                final double n10 = n5;
                n5 = n4;
                n4 = n10;
            }
            if (Block.fire.canBlockCatchFire(this.a, integer2 - 1, integer3, integer4)) {
                instance.addVertexWithUV(integer2 + n17, integer3 + n8 + n18, integer4 + 1, n5, n6);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 1, n5, n7);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 0, n4, n7);
                instance.addVertexWithUV(integer2 + n17, integer3 + n8 + n18, integer4 + 0, n4, n6);
                instance.addVertexWithUV(integer2 + n17, integer3 + n8 + n18, integer4 + 0, n4, n6);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 0, n4, n7);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 1, n5, n7);
                instance.addVertexWithUV(integer2 + n17, integer3 + n8 + n18, integer4 + 1, n5, n6);
            }
            if (Block.fire.canBlockCatchFire(this.a, integer2 + 1, integer3, integer4)) {
                instance.addVertexWithUV(integer2 + 1 - n17, integer3 + n8 + n18, integer4 + 0, n4, n6);
                instance.addVertexWithUV(integer2 + 1 - 0, integer3 + 0 + n18, integer4 + 0, n4, n7);
                instance.addVertexWithUV(integer2 + 1 - 0, integer3 + 0 + n18, integer4 + 1, n5, n7);
                instance.addVertexWithUV(integer2 + 1 - n17, integer3 + n8 + n18, integer4 + 1, n5, n6);
                instance.addVertexWithUV(integer2 + 1 - n17, integer3 + n8 + n18, integer4 + 1, n5, n6);
                instance.addVertexWithUV(integer2 + 1 - 0, integer3 + 0 + n18, integer4 + 1, n5, n7);
                instance.addVertexWithUV(integer2 + 1 - 0, integer3 + 0 + n18, integer4 + 0, n4, n7);
                instance.addVertexWithUV(integer2 + 1 - n17, integer3 + n8 + n18, integer4 + 0, n4, n6);
            }
            if (Block.fire.canBlockCatchFire(this.a, integer2, integer3, integer4 - 1)) {
                instance.addVertexWithUV(integer2 + 0, integer3 + n8 + n18, integer4 + n17, n5, n6);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 0, n5, n7);
                instance.addVertexWithUV(integer2 + 1, integer3 + 0 + n18, integer4 + 0, n4, n7);
                instance.addVertexWithUV(integer2 + 1, integer3 + n8 + n18, integer4 + n17, n4, n6);
                instance.addVertexWithUV(integer2 + 1, integer3 + n8 + n18, integer4 + n17, n4, n6);
                instance.addVertexWithUV(integer2 + 1, integer3 + 0 + n18, integer4 + 0, n4, n7);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 0, n5, n7);
                instance.addVertexWithUV(integer2 + 0, integer3 + n8 + n18, integer4 + n17, n5, n6);
            }
            if (Block.fire.canBlockCatchFire(this.a, integer2, integer3, integer4 + 1)) {
                instance.addVertexWithUV(integer2 + 1, integer3 + n8 + n18, integer4 + 1 - n17, n4, n6);
                instance.addVertexWithUV(integer2 + 1, integer3 + 0 + n18, integer4 + 1 - 0, n4, n7);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 1 - 0, n5, n7);
                instance.addVertexWithUV(integer2 + 0, integer3 + n8 + n18, integer4 + 1 - n17, n5, n6);
                instance.addVertexWithUV(integer2 + 0, integer3 + n8 + n18, integer4 + 1 - n17, n5, n6);
                instance.addVertexWithUV(integer2 + 0, integer3 + 0 + n18, integer4 + 1 - 0, n5, n7);
                instance.addVertexWithUV(integer2 + 1, integer3 + 0 + n18, integer4 + 1 - 0, n4, n7);
                instance.addVertexWithUV(integer2 + 1, integer3 + n8 + n18, integer4 + 1 - n17, n4, n6);
            }
            if (Block.fire.canBlockCatchFire(this.a, integer2, integer3 + 1, integer4)) {
                final double n10 = integer2 + 0.5 + 0.5;
                final double n11 = integer2 + 0.5 - 0.5;
                final double n12 = integer4 + 0.5 + 0.5;
                final double n13 = integer4 + 0.5 - 0.5;
                final double n14 = integer2 + 0.5 - 0.5;
                final double n15 = integer2 + 0.5 + 0.5;
                final double n16 = integer4 + 0.5 - 0.5;
                final double n19 = integer4 + 0.5 + 0.5;
                n4 = n2 / 256.0f;
                n5 = (n2 + 15.99f) / 256.0f;
                n6 = n3 / 256.0f;
                n7 = (n3 + 15.99f) / 256.0f;
                ++integer3;
                n8 = -0.2f;
                if ((integer2 + integer3 + integer4 & 0x1) == 0x0) {
                    instance.addVertexWithUV(n14, integer3 + n8, integer4 + 0, n5, n6);
                    instance.addVertexWithUV(n10, integer3 + 0, integer4 + 0, n5, n7);
                    instance.addVertexWithUV(n10, integer3 + 0, integer4 + 1, n4, n7);
                    instance.addVertexWithUV(n14, integer3 + n8, integer4 + 1, n4, n6);
                    n4 = n2 / 256.0f;
                    n5 = (n2 + 15.99f) / 256.0f;
                    n6 = (n3 + 16) / 256.0f;
                    n7 = (n3 + 15.99f + 16.0f) / 256.0f;
                    instance.addVertexWithUV(n15, integer3 + n8, integer4 + 1, n5, n6);
                    instance.addVertexWithUV(n11, integer3 + 0, integer4 + 1, n5, n7);
                    instance.addVertexWithUV(n11, integer3 + 0, integer4 + 0, n4, n7);
                    instance.addVertexWithUV(n15, integer3 + n8, integer4 + 0, n4, n6);
                } else {
                    instance.addVertexWithUV(integer2 + 0, integer3 + n8, n19, n5, n6);
                    instance.addVertexWithUV(integer2 + 0, integer3 + 0, n13, n5, n7);
                    instance.addVertexWithUV(integer2 + 1, integer3 + 0, n13, n4, n7);
                    instance.addVertexWithUV(integer2 + 1, integer3 + n8, n19, n4, n6);
                    n4 = n2 / 256.0f;
                    n5 = (n2 + 15.99f) / 256.0f;
                    n6 = (n3 + 16) / 256.0f;
                    n7 = (n3 + 15.99f + 16.0f) / 256.0f;
                    instance.addVertexWithUV(integer2 + 1, integer3 + n8, n16, n5, n6);
                    instance.addVertexWithUV(integer2 + 1, integer3 + 0, n12, n5, n7);
                    instance.addVertexWithUV(integer2 + 0, integer3 + 0, n12, n4, n7);
                    instance.addVertexWithUV(integer2 + 0, integer3 + n8, n16, n4, n6);
                }
            }
        }
        return true;
    }

    public boolean d(final Block gs, final int integer2, final int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        int n = gs.getBlockTextureFromSide(0);
        if (this.b >= 0) {
            n = this.b;
        }
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        instance.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
        int n2 = ((n & 0xF) << 4) + 16;
        int n3 = (n & 0xF) << 4;
        final int n4 = n & 0xF0;
        if ((integer2 + integer3 + integer4 & 0x1) == 0x1) {
            n2 = (n & 0xF) << 4;
            n3 = ((n & 0xF) << 4) + 16;
        }
        final double n5 = n2 / 256.0f;
        final double n6 = (n2 + 15.99f) / 256.0f;
        final double n7 = n4 / 256.0f;
        final double n8 = (n4 + 15.99f) / 256.0f;
        final double n9 = n3 / 256.0f;
        final double n10 = (n3 + 15.99f) / 256.0f;
        final double n11 = n4 / 256.0f;
        final double n12 = (n4 + 15.99f) / 256.0f;
        final float n13 = 0.125f;
        final float n14 = 0.05f;
        if (this.a.isBlockNormalCube(integer2 - 1, integer3, integer4)) {
            instance.addVertexWithUV(integer2 + n14, integer3 + 1 + n13, integer4 + 1 + n13, n5, n7);
            instance.addVertexWithUV(integer2 + n14, integer3 + 0 - n13, integer4 + 1 + n13, n5, n8);
            instance.addVertexWithUV(integer2 + n14, integer3 + 0 - n13, integer4 + 0 - n13, n6, n8);
            instance.addVertexWithUV(integer2 + n14, integer3 + 1 + n13, integer4 + 0 - n13, n6, n7);
        }
        if (this.a.isBlockNormalCube(integer2 + 1, integer3, integer4)) {
            instance.addVertexWithUV(integer2 + 1 - n14, integer3 + 0 - n13, integer4 + 1 + n13, n6, n8);
            instance.addVertexWithUV(integer2 + 1 - n14, integer3 + 1 + n13, integer4 + 1 + n13, n6, n7);
            instance.addVertexWithUV(integer2 + 1 - n14, integer3 + 1 + n13, integer4 + 0 - n13, n5, n7);
            instance.addVertexWithUV(integer2 + 1 - n14, integer3 + 0 - n13, integer4 + 0 - n13, n5, n8);
        }
        if (this.a.isBlockNormalCube(integer2, integer3, integer4 - 1)) {
            instance.addVertexWithUV(integer2 + 1 + n13, integer3 + 0 - n13, integer4 + n14, n10, n12);
            instance.addVertexWithUV(integer2 + 1 + n13, integer3 + 1 + n13, integer4 + n14, n10, n11);
            instance.addVertexWithUV(integer2 + 0 - n13, integer3 + 1 + n13, integer4 + n14, n9, n11);
            instance.addVertexWithUV(integer2 + 0 - n13, integer3 + 0 - n13, integer4 + n14, n9, n12);
        }
        if (this.a.isBlockNormalCube(integer2, integer3, integer4 + 1)) {
            instance.addVertexWithUV(integer2 + 1 + n13, integer3 + 1 + n13, integer4 + 1 - n14, n9, n11);
            instance.addVertexWithUV(integer2 + 1 + n13, integer3 + 0 - n13, integer4 + 1 - n14, n9, n12);
            instance.addVertexWithUV(integer2 + 0 - n13, integer3 + 0 - n13, integer4 + 1 - n14, n10, n12);
            instance.addVertexWithUV(integer2 + 0 - n13, integer3 + 1 + n13, integer4 + 1 - n14, n10, n11);
        }
        return true;
    }

    public boolean e(final Block gs, final int integer2, final int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        final int blockMetadata = this.a.getBlockMetadata(integer2, integer3, integer4);
        int n = gs.getBlockTextureFromSideAndMetadata(0, blockMetadata);
        if (this.b >= 0) {
            n = this.b;
        }
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        instance.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
        final int n2 = (n & 0xF) << 4;
        final int n3 = n & 0xF0;
        final double n4 = n2 / 256.0f;
        final double n5 = (n2 + 15.99f) / 256.0f;
        final double n6 = n3 / 256.0f;
        final double n7 = (n3 + 15.99f) / 256.0f;
        final float n8 = 0.0625f;
        float n9 = (float) (integer2 + 1);
        float n10 = (float) (integer2 + 1);
        float n11 = (float) (integer2 + 0);
        float n12 = (float) (integer2 + 0);
        float n13 = (float) (integer4 + 0);
        float n14 = (float) (integer4 + 1);
        float n15 = (float) (integer4 + 1);
        float n16 = (float) (integer4 + 0);
        float n17 = integer3 + n8;
        float n18 = integer3 + n8;
        float n19 = integer3 + n8;
        float n20 = integer3 + n8;
        if (blockMetadata == 1 || blockMetadata == 2 || blockMetadata == 3 || blockMetadata == 7) {
            n12 = (n9 = (float) (integer2 + 1));
            n11 = (n10 = (float) (integer2 + 0));
            n14 = (n13 = (float) (integer4 + 1));
            n16 = (n15 = (float) (integer4 + 0));
        } else if (blockMetadata == 8) {
            n10 = (n9 = (float) (integer2 + 0));
            n12 = (n11 = (float) (integer2 + 1));
            n16 = (n13 = (float) (integer4 + 1));
            n15 = (n14 = (float) (integer4 + 0));
        } else if (blockMetadata == 9) {
            n12 = (n9 = (float) (integer2 + 0));
            n11 = (n10 = (float) (integer2 + 1));
            n14 = (n13 = (float) (integer4 + 0));
            n16 = (n15 = (float) (integer4 + 1));
        }
        if (blockMetadata == 2 || blockMetadata == 4) {
            ++n17;
            ++n20;
        } else if (blockMetadata == 3 || blockMetadata == 5) {
            ++n18;
            ++n19;
        }
        instance.addVertexWithUV(n9, n17, n13, n5, n6);
        instance.addVertexWithUV(n10, n18, n14, n5, n7);
        instance.addVertexWithUV(n11, n19, n15, n4, n7);
        instance.addVertexWithUV(n12, n20, n16, n4, n6);
        instance.addVertexWithUV(n12, n20, n16, n4, n6);
        instance.addVertexWithUV(n11, n19, n15, n4, n7);
        instance.addVertexWithUV(n10, n18, n14, n5, n7);
        instance.addVertexWithUV(n9, n17, n13, n5, n6);
        return true;
    }

    public boolean f(final Block gs, final int integer2, final int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        int n = gs.getBlockTextureFromSide(0);
        if (this.b >= 0) {
            n = this.b;
        }
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        instance.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
        final int n2 = (n & 0xF) << 4;
        final int n3 = n & 0xF0;
        final double n4 = n2 / 256.0f;
        final double n5 = (n2 + 15.99f) / 256.0f;
        final double n6 = n3 / 256.0f;
        final double n7 = (n3 + 15.99f) / 256.0f;
        final int blockMetadata = this.a.getBlockMetadata(integer2, integer3, integer4);
        final float n8 = 0.0f;
        final float n9 = 0.05f;
        if (blockMetadata == 5) {
            instance.addVertexWithUV(integer2 + n9, integer3 + 1 + n8, integer4 + 1 + n8, n4, n6);
            instance.addVertexWithUV(integer2 + n9, integer3 + 0 - n8, integer4 + 1 + n8, n4, n7);
            instance.addVertexWithUV(integer2 + n9, integer3 + 0 - n8, integer4 + 0 - n8, n5, n7);
            instance.addVertexWithUV(integer2 + n9, integer3 + 1 + n8, integer4 + 0 - n8, n5, n6);
        }
        if (blockMetadata == 4) {
            instance.addVertexWithUV(integer2 + 1 - n9, integer3 + 0 - n8, integer4 + 1 + n8, n5, n7);
            instance.addVertexWithUV(integer2 + 1 - n9, integer3 + 1 + n8, integer4 + 1 + n8, n5, n6);
            instance.addVertexWithUV(integer2 + 1 - n9, integer3 + 1 + n8, integer4 + 0 - n8, n4, n6);
            instance.addVertexWithUV(integer2 + 1 - n9, integer3 + 0 - n8, integer4 + 0 - n8, n4, n7);
        }
        if (blockMetadata == 3) {
            instance.addVertexWithUV(integer2 + 1 + n8, integer3 + 0 - n8, integer4 + n9, n5, n7);
            instance.addVertexWithUV(integer2 + 1 + n8, integer3 + 1 + n8, integer4 + n9, n5, n6);
            instance.addVertexWithUV(integer2 + 0 - n8, integer3 + 1 + n8, integer4 + n9, n4, n6);
            instance.addVertexWithUV(integer2 + 0 - n8, integer3 + 0 - n8, integer4 + n9, n4, n7);
        }
        if (blockMetadata == 2) {
            instance.addVertexWithUV(integer2 + 1 + n8, integer3 + 1 + n8, integer4 + 1 - n9, n4, n6);
            instance.addVertexWithUV(integer2 + 1 + n8, integer3 + 0 - n8, integer4 + 1 - n9, n4, n7);
            instance.addVertexWithUV(integer2 + 0 - n8, integer3 + 0 - n8, integer4 + 1 - n9, n5, n7);
            instance.addVertexWithUV(integer2 + 0 - n8, integer3 + 1 + n8, integer4 + 1 - n9, n5, n6);
        }
        return true;
    }

    public boolean g(final Block gs, final int integer2, final int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        instance.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
        this.a(gs, this.a.getBlockMetadata(integer2, integer3, integer4), integer2, integer3, (double) integer4);
        return true;
    }

    public boolean h(final Block gs, final int integer2, final int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        instance.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
        this.b(gs, this.a.getBlockMetadata(integer2, integer3, integer4), integer2, integer3 - 0.0625f, (double) integer4);
        return true;
    }

    public void a(final Block gs, double double2, final double double3, double double4, final double double5, final double double6) {
        final Tessellator instance = Tessellator.instance;
        int n = gs.getBlockTextureFromSide(0);
        if (this.b >= 0) {
            n = this.b;
        }
        final int n2 = (n & 0xF) << 4;
        final int n3 = n & 0xF0;
        final float n4 = n2 / 256.0f;
        final float n5 = (n2 + 15.99f) / 256.0f;
        final float n6 = n3 / 256.0f;
        final float n7 = (n3 + 15.99f) / 256.0f;
        final double n8 = n4 + 0.02734375;
        final double n9 = n6 + 0.0234375;
        final double n10 = n4 + 0.03515625;
        final double n11 = n6 + 0.03125;
        double2 += 0.5;
        double4 += 0.5;
        final double n12 = double2 - 0.5;
        final double n13 = double2 + 0.5;
        final double n14 = double4 - 0.5;
        final double n15 = double4 + 0.5;
        final double n16 = 0.0625;
        final double n17 = 0.625;
        instance.addVertexWithUV(double2 + double5 * (1.0 - n17) - n16, double3 + n17, double4 + double6 * (1.0 - n17) - n16, n8, n9);
        instance.addVertexWithUV(double2 + double5 * (1.0 - n17) - n16, double3 + n17, double4 + double6 * (1.0 - n17) + n16, n8, n11);
        instance.addVertexWithUV(double2 + double5 * (1.0 - n17) + n16, double3 + n17, double4 + double6 * (1.0 - n17) + n16, n10, n11);
        instance.addVertexWithUV(double2 + double5 * (1.0 - n17) + n16, double3 + n17, double4 + double6 * (1.0 - n17) - n16, n10, n9);
        instance.addVertexWithUV(double2 - n16, double3 + 1.0, n14, n4, n6);
        instance.addVertexWithUV(double2 - n16 + double5, double3 + 0.0, n14 + double6, n4, n7);
        instance.addVertexWithUV(double2 - n16 + double5, double3 + 0.0, n15 + double6, n5, n7);
        instance.addVertexWithUV(double2 - n16, double3 + 1.0, n15, n5, n6);
        instance.addVertexWithUV(double2 + n16, double3 + 1.0, n15, n4, n6);
        instance.addVertexWithUV(double2 + double5 + n16, double3 + 0.0, n15 + double6, n4, n7);
        instance.addVertexWithUV(double2 + double5 + n16, double3 + 0.0, n14 + double6, n5, n7);
        instance.addVertexWithUV(double2 + n16, double3 + 1.0, n14, n5, n6);
        instance.addVertexWithUV(n12, double3 + 1.0, double4 + n16, n4, n6);
        instance.addVertexWithUV(n12 + double5, double3 + 0.0, double4 + n16 + double6, n4, n7);
        instance.addVertexWithUV(n13 + double5, double3 + 0.0, double4 + n16 + double6, n5, n7);
        instance.addVertexWithUV(n13, double3 + 1.0, double4 + n16, n5, n6);
        instance.addVertexWithUV(n13, double3 + 1.0, double4 - n16, n4, n6);
        instance.addVertexWithUV(n13 + double5, double3 + 0.0, double4 - n16 + double6, n4, n7);
        instance.addVertexWithUV(n12 + double5, double3 + 0.0, double4 - n16 + double6, n5, n7);
        instance.addVertexWithUV(n12, double3 + 1.0, double4 - n16, n5, n6);
    }

    public void a(final Block gs, final int integer, final double double3, final double double4, final double double5) {
        final Tessellator instance = Tessellator.instance;
        int n = gs.getBlockTextureFromSideAndMetadata(0, integer);
        if (this.b >= 0) {
            n = this.b;
        }
        final int n2 = (n & 0xF) << 4;
        final int n3 = n & 0xF0;
        final double n4 = n2 / 256.0f;
        final double n5 = (n2 + 15.99f) / 256.0f;
        final double n6 = n3 / 256.0f;
        final double n7 = (n3 + 15.99f) / 256.0f;
        final double n8 = double3 + 0.5 - 0.44999998807907104;
        final double n9 = double3 + 0.5 + 0.44999998807907104;
        final double n10 = double5 + 0.5 - 0.44999998807907104;
        final double n11 = double5 + 0.5 + 0.44999998807907104;
        instance.addVertexWithUV(n8, double4 + 1.0, n10, n4, n6);
        instance.addVertexWithUV(n8, double4 + 0.0, n10, n4, n7);
        instance.addVertexWithUV(n9, double4 + 0.0, n11, n5, n7);
        instance.addVertexWithUV(n9, double4 + 1.0, n11, n5, n6);
        instance.addVertexWithUV(n9, double4 + 1.0, n11, n4, n6);
        instance.addVertexWithUV(n9, double4 + 0.0, n11, n4, n7);
        instance.addVertexWithUV(n8, double4 + 0.0, n10, n5, n7);
        instance.addVertexWithUV(n8, double4 + 1.0, n10, n5, n6);
        instance.addVertexWithUV(n8, double4 + 1.0, n11, n4, n6);
        instance.addVertexWithUV(n8, double4 + 0.0, n11, n4, n7);
        instance.addVertexWithUV(n9, double4 + 0.0, n10, n5, n7);
        instance.addVertexWithUV(n9, double4 + 1.0, n10, n5, n6);
        instance.addVertexWithUV(n9, double4 + 1.0, n10, n4, n6);
        instance.addVertexWithUV(n9, double4 + 0.0, n10, n4, n7);
        instance.addVertexWithUV(n8, double4 + 0.0, n11, n5, n7);
        instance.addVertexWithUV(n8, double4 + 1.0, n11, n5, n6);
    }

    public void b(final Block gs, final int integer, final double double3, final double double4, final double double5) {
        final Tessellator instance = Tessellator.instance;
        int n = gs.getBlockTextureFromSideAndMetadata(0, integer);
        if (this.b >= 0) {
            n = this.b;
        }
        final int n2 = (n & 0xF) << 4;
        final int n3 = n & 0xF0;
        final double n4 = n2 / 256.0f;
        final double n5 = (n2 + 15.99f) / 256.0f;
        final double n6 = n3 / 256.0f;
        final double n7 = (n3 + 15.99f) / 256.0f;
        double n8 = double3 + 0.5 - 0.25;
        double n9 = double3 + 0.5 + 0.25;
        double n10 = double5 + 0.5 - 0.5;
        double n11 = double5 + 0.5 + 0.5;
        instance.addVertexWithUV(n8, double4 + 1.0, n10, n4, n6);
        instance.addVertexWithUV(n8, double4 + 0.0, n10, n4, n7);
        instance.addVertexWithUV(n8, double4 + 0.0, n11, n5, n7);
        instance.addVertexWithUV(n8, double4 + 1.0, n11, n5, n6);
        instance.addVertexWithUV(n8, double4 + 1.0, n11, n4, n6);
        instance.addVertexWithUV(n8, double4 + 0.0, n11, n4, n7);
        instance.addVertexWithUV(n8, double4 + 0.0, n10, n5, n7);
        instance.addVertexWithUV(n8, double4 + 1.0, n10, n5, n6);
        instance.addVertexWithUV(n9, double4 + 1.0, n11, n4, n6);
        instance.addVertexWithUV(n9, double4 + 0.0, n11, n4, n7);
        instance.addVertexWithUV(n9, double4 + 0.0, n10, n5, n7);
        instance.addVertexWithUV(n9, double4 + 1.0, n10, n5, n6);
        instance.addVertexWithUV(n9, double4 + 1.0, n10, n4, n6);
        instance.addVertexWithUV(n9, double4 + 0.0, n10, n4, n7);
        instance.addVertexWithUV(n9, double4 + 0.0, n11, n5, n7);
        instance.addVertexWithUV(n9, double4 + 1.0, n11, n5, n6);
        n8 = double3 + 0.5 - 0.5;
        n9 = double3 + 0.5 + 0.5;
        n10 = double5 + 0.5 - 0.25;
        n11 = double5 + 0.5 + 0.25;
        instance.addVertexWithUV(n8, double4 + 1.0, n10, n4, n6);
        instance.addVertexWithUV(n8, double4 + 0.0, n10, n4, n7);
        instance.addVertexWithUV(n9, double4 + 0.0, n10, n5, n7);
        instance.addVertexWithUV(n9, double4 + 1.0, n10, n5, n6);
        instance.addVertexWithUV(n9, double4 + 1.0, n10, n4, n6);
        instance.addVertexWithUV(n9, double4 + 0.0, n10, n4, n7);
        instance.addVertexWithUV(n8, double4 + 0.0, n10, n5, n7);
        instance.addVertexWithUV(n8, double4 + 1.0, n10, n5, n6);
        instance.addVertexWithUV(n9, double4 + 1.0, n11, n4, n6);
        instance.addVertexWithUV(n9, double4 + 0.0, n11, n4, n7);
        instance.addVertexWithUV(n8, double4 + 0.0, n11, n5, n7);
        instance.addVertexWithUV(n8, double4 + 1.0, n11, n5, n6);
        instance.addVertexWithUV(n8, double4 + 1.0, n11, n4, n6);
        instance.addVertexWithUV(n8, double4 + 0.0, n11, n4, n7);
        instance.addVertexWithUV(n9, double4 + 0.0, n11, n5, n7);
        instance.addVertexWithUV(n9, double4 + 1.0, n11, n5, n6);
    }

    public boolean i(final Block gs, final int integer2, final int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        final boolean shouldSideBeRendered = gs.shouldSideBeRendered(this.a, integer2, integer3 + 1, integer4, 1);
        final boolean shouldSideBeRendered2 = gs.shouldSideBeRendered(this.a, integer2, integer3 - 1, integer4, 0);
        final boolean[] array = {gs.shouldSideBeRendered(this.a, integer2, integer3, integer4 - 1, 2), gs.shouldSideBeRendered(this.a, integer2, integer3, integer4 + 1, 3), gs.shouldSideBeRendered(this.a, integer2 - 1, integer3, integer4, 4), gs.shouldSideBeRendered(this.a, integer2 + 1, integer3, integer4, 5)};
        if (!shouldSideBeRendered && !shouldSideBeRendered2 && !array[0] && !array[1] && !array[2] && !array[3]) {
            return false;
        }
        boolean b = false;
        final float n = 0.5f;
        final float n2 = 1.0f;
        final float n3 = 0.8f;
        final float n4 = 0.6f;
        final double minY = 0.0;
        final double maxY = 1.0;
        final Material blockMaterial = gs.blockMaterial;
        final int blockMetadata = this.a.getBlockMetadata(integer2, integer3, integer4);
        final float a = this.a(integer2, integer3, integer4, blockMaterial);
        final float a2 = this.a(integer2, integer3, integer4 + 1, blockMaterial);
        final float a3 = this.a(integer2 + 1, integer3, integer4 + 1, blockMaterial);
        final float a4 = this.a(integer2 + 1, integer3, integer4, blockMaterial);
        if (this.d || shouldSideBeRendered) {
            b = true;
            int i = gs.getBlockTextureFromSideAndMetadata(1, blockMetadata);
            float n5 = (float) BlockFluid.getFlowDirection(this.a, integer2, integer3, integer4, blockMaterial);
            if (n5 > -999.0f) {
                i = gs.getBlockTextureFromSideAndMetadata(2, blockMetadata);
            }
            final int yCoord = (i & 0xF) << 4;
            final int zCoord = i & 0xF0;
            double n6 = (yCoord + 8.0) / 256.0;
            double n7 = (zCoord + 8.0) / 256.0;
            if (n5 < -999.0f) {
                n5 = 0.0f;
            } else {
                n6 = (yCoord + 16) / 256.0f;
                n7 = (zCoord + 16) / 256.0f;
            }
            final float n8 = MathHelper.sin(n5) * 8.0f / 256.0f;
            final float n9 = MathHelper.cos(n5) * 8.0f / 256.0f;
            final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
            instance.setColorOpaque_F(n2 * blockBrightness, n2 * blockBrightness, n2 * blockBrightness);
            instance.addVertexWithUV(integer2 + 0, integer3 + a, integer4 + 0, n6 - n9 - n8, n7 - n9 + n8);
            instance.addVertexWithUV(integer2 + 0, integer3 + a2, integer4 + 1, n6 - n9 + n8, n7 + n9 + n8);
            instance.addVertexWithUV(integer2 + 1, integer3 + a3, integer4 + 1, n6 + n9 + n8, n7 + n9 - n8);
            instance.addVertexWithUV(integer2 + 1, integer3 + a4, integer4 + 0, n6 + n9 - n8, n7 - n9 - n8);
        }
        if (this.d || shouldSideBeRendered2) {
            final float blockBrightness2 = gs.getBlockBrightness(this.a, integer2, integer3 - 1, integer4);
            instance.setColorOpaque_F(n * blockBrightness2, n * blockBrightness2, n * blockBrightness2);
            this.a(gs, (double) integer2, integer3, integer4, gs.getBlockTextureFromSide(0));
            b = true;
        }
        for (int i = 0; i < 4; ++i) {
            int xCoord = integer2;
            final int yCoord = integer3;
            int zCoord = integer4;
            if (i == 0) {
                --zCoord;
            }
            if (i == 1) {
                ++zCoord;
            }
            if (i == 2) {
                --xCoord;
            }
            if (i == 3) {
                ++xCoord;
            }
            final int blockTextureFromSideAndMetadata = gs.getBlockTextureFromSideAndMetadata(i + 2, blockMetadata);
            final int n10 = (blockTextureFromSideAndMetadata & 0xF) << 4;
            final int n11 = blockTextureFromSideAndMetadata & 0xF0;
            if (this.d || array[i]) {
                float n8;
                float n9;
                float blockBrightness;
                float n12;
                float n13;
                float n14;
                if (i == 0) {
                    n12 = a;
                    n8 = a4;
                    n9 = (float) integer2;
                    n13 = (float) (integer2 + 1);
                    blockBrightness = (float) integer4;
                    n14 = (float) integer4;
                } else if (i == 1) {
                    n12 = a3;
                    n8 = a2;
                    n9 = (float) (integer2 + 1);
                    n13 = (float) integer2;
                    blockBrightness = (float) (integer4 + 1);
                    n14 = (float) (integer4 + 1);
                } else if (i == 2) {
                    n12 = a2;
                    n8 = a;
                    n9 = (float) integer2;
                    n13 = (float) integer2;
                    blockBrightness = (float) (integer4 + 1);
                    n14 = (float) integer4;
                } else {
                    n12 = a4;
                    n8 = a3;
                    n9 = (float) (integer2 + 1);
                    n13 = (float) (integer2 + 1);
                    blockBrightness = (float) integer4;
                    n14 = (float) (integer4 + 1);
                }
                b = true;
                final double n15 = (n10 + 0) / 256.0f;
                final double n16 = (n10 + 16 - 0.01) / 256.0;
                final double double5 = (n11 + (1.0f - n12) * 16.0f) / 256.0f;
                final double double6 = (n11 + (1.0f - n8) * 16.0f) / 256.0f;
                final double n17 = (n11 + 16 - 0.01) / 256.0;
                float blockBrightness3 = gs.getBlockBrightness(this.a, xCoord, yCoord, zCoord);
                if (i < 2) {
                    blockBrightness3 *= n3;
                } else {
                    blockBrightness3 *= n4;
                }
                instance.setColorOpaque_F(n2 * blockBrightness3, n2 * blockBrightness3, n2 * blockBrightness3);
                instance.addVertexWithUV(n9, integer3 + n12, blockBrightness, n15, double5);
                instance.addVertexWithUV(n13, integer3 + n8, n14, n16, double6);
                instance.addVertexWithUV(n13, integer3 + 0, n14, n16, n17);
                instance.addVertexWithUV(n9, integer3 + 0, blockBrightness, n15, n17);
            }
        }
        gs.minY = minY;
        gs.maxY = maxY;
        return b;
    }

    private float a(final int integer1, final int integer2, final int integer3, final Material jy) {
        int n = 0;
        float n2 = 0.0f;
        for (int i = 0; i < 4; ++i) {
            final int xCoord = integer1 - (i & 0x1);
            final int zCoord = integer3 - (i >> 1 & 0x1);
            if (this.a.getBlockMaterial(xCoord, integer2 + 1, zCoord) == jy) {
                return 1.0f;
            }
            final Material blockMaterial = this.a.getBlockMaterial(xCoord, integer2, zCoord);
            if (blockMaterial == jy) {
                final int blockMetadata = this.a.getBlockMetadata(xCoord, integer2, zCoord);
                if (blockMetadata >= 8 || blockMetadata == 0) {
                    n2 += BlockFluid.getPercentAir(blockMetadata) * 10.0f;
                    n += 10;
                }
                n2 += BlockFluid.getPercentAir(blockMetadata);
                ++n;
            } else if (!blockMaterial.isSolid()) {
                ++n2;
                ++n;
            }
        }
        return 1.0f - n2 / n;
    }

    public void renderBlockFallingSand(final Block gs, final World fe, final int integer3, final int integer4, final int integer5) {
        final float n = 0.5f;
        final float n2 = 1.0f;
        final float n3 = 0.8f;
        final float n4 = 0.6f;
        final Tessellator instance = Tessellator.instance;
        instance.startDrawingQuads();
        final float blockBrightness = gs.getBlockBrightness(fe, integer3, integer4, integer5);
        float n5 = gs.getBlockBrightness(fe, integer3, integer4 - 1, integer5);
        if (n5 < blockBrightness) {
            n5 = blockBrightness;
        }
        instance.setColorOpaque_F(n * n5, n * n5, n * n5);
        this.a(gs, -0.5, -0.5, -0.5, gs.getBlockTextureFromSide(0));
        n5 = gs.getBlockBrightness(fe, integer3, integer4 + 1, integer5);
        if (n5 < blockBrightness) {
            n5 = blockBrightness;
        }
        instance.setColorOpaque_F(n2 * n5, n2 * n5, n2 * n5);
        this.b(gs, -0.5, -0.5, -0.5, gs.getBlockTextureFromSide(1));
        n5 = gs.getBlockBrightness(fe, integer3, integer4, integer5 - 1);
        if (n5 < blockBrightness) {
            n5 = blockBrightness;
        }
        instance.setColorOpaque_F(n3 * n5, n3 * n5, n3 * n5);
        this.c(gs, -0.5, -0.5, -0.5, gs.getBlockTextureFromSide(2));
        n5 = gs.getBlockBrightness(fe, integer3, integer4, integer5 + 1);
        if (n5 < blockBrightness) {
            n5 = blockBrightness;
        }
        instance.setColorOpaque_F(n3 * n5, n3 * n5, n3 * n5);
        this.d(gs, -0.5, -0.5, -0.5, gs.getBlockTextureFromSide(3));
        n5 = gs.getBlockBrightness(fe, integer3 - 1, integer4, integer5);
        if (n5 < blockBrightness) {
            n5 = blockBrightness;
        }
        instance.setColorOpaque_F(n4 * n5, n4 * n5, n4 * n5);
        this.e(gs, -0.5, -0.5, -0.5, gs.getBlockTextureFromSide(4));
        n5 = gs.getBlockBrightness(fe, integer3 + 1, integer4, integer5);
        if (n5 < blockBrightness) {
            n5 = blockBrightness;
        }
        instance.setColorOpaque_F(n4 * n5, n4 * n5, n4 * n5);
        this.f(gs, -0.5, -0.5, -0.5, gs.getBlockTextureFromSide(5));
        instance.draw();
    }

    public boolean j(final Block gs, final int integer2, final int integer3, final int integer4) {
        final int renderColor = gs.getRenderColor(this.a, integer2, integer3, integer4);
        return this.a(gs, integer2, integer3, integer4, (renderColor >> 16 & 0xFF) / 255.0f, (renderColor >> 8 & 0xFF) / 255.0f, (renderColor & 0xFF) / 255.0f);
    }

    public boolean a(final Block gs, final int integer2, final int integer3, final int integer4, final float float5, final float float6, final float float7) {
        final Tessellator instance = Tessellator.instance;
        boolean b = false;
        final float n = 0.5f;
        final float n2 = 1.0f;
        final float n3 = 0.8f;
        final float n4 = 0.6f;
        final float n5 = n * float5;
        final float n6 = n2 * float5;
        final float n7 = n3 * float5;
        final float n8 = n4 * float5;
        final float n9 = n * float6;
        final float n10 = n2 * float6;
        final float n11 = n3 * float6;
        final float n12 = n4 * float6;
        final float n13 = n * float7;
        final float n14 = n2 * float7;
        final float n15 = n3 * float7;
        final float n16 = n4 * float7;
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        if (this.d || gs.shouldSideBeRendered(this.a, integer2, integer3 - 1, integer4, 0)) {
            float n17 = gs.getBlockBrightness(this.a, integer2, integer3 - 1, integer4);
            if (Block.lightValue[gs.blockID] > 0) {
                n17 = 1.0f;
            }
            instance.setColorOpaque_F(n5 * n17, n9 * n17, n13 * n17);
            this.a(gs, (double) integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 0));
            b = true;
        }
        if (this.d || gs.shouldSideBeRendered(this.a, integer2, integer3 + 1, integer4, 1)) {
            float n17 = gs.getBlockBrightness(this.a, integer2, integer3 + 1, integer4);
            if (gs.maxY != 1.0 && !gs.blockMaterial.getIsLiquid()) {
                n17 = blockBrightness;
            }
            if (Block.lightValue[gs.blockID] > 0) {
                n17 = 1.0f;
            }
            instance.setColorOpaque_F(n6 * n17, n10 * n17, n14 * n17);
            this.b(gs, (double) integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 1));
            b = true;
        }
        if (this.d || gs.shouldSideBeRendered(this.a, integer2, integer3, integer4 - 1, 2)) {
            float n17 = gs.getBlockBrightness(this.a, integer2, integer3, integer4 - 1);
            if (gs.minZ > 0.0) {
                n17 = blockBrightness;
            }
            if (Block.lightValue[gs.blockID] > 0) {
                n17 = 1.0f;
            }
            instance.setColorOpaque_F(n7 * n17, n11 * n17, n15 * n17);
            this.c(gs, integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 2));
            b = true;
        }
        if (this.d || gs.shouldSideBeRendered(this.a, integer2, integer3, integer4 + 1, 3)) {
            float n17 = gs.getBlockBrightness(this.a, integer2, integer3, integer4 + 1);
            if (gs.maxZ < 1.0) {
                n17 = blockBrightness;
            }
            if (Block.lightValue[gs.blockID] > 0) {
                n17 = 1.0f;
            }
            instance.setColorOpaque_F(n7 * n17, n11 * n17, n15 * n17);
            this.d(gs, integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 3));
            b = true;
        }
        if (this.d || gs.shouldSideBeRendered(this.a, integer2 - 1, integer3, integer4, 4)) {
            float n17 = gs.getBlockBrightness(this.a, integer2 - 1, integer3, integer4);
            if (gs.minX > 0.0) {
                n17 = blockBrightness;
            }
            if (Block.lightValue[gs.blockID] > 0) {
                n17 = 1.0f;
            }
            instance.setColorOpaque_F(n8 * n17, n12 * n17, n16 * n17);
            this.e(gs, integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 4));
            b = true;
        }
        if (this.d || gs.shouldSideBeRendered(this.a, integer2 + 1, integer3, integer4, 5)) {
            float n17 = gs.getBlockBrightness(this.a, integer2 + 1, integer3, integer4);
            if (gs.maxX < 1.0) {
                n17 = blockBrightness;
            }
            if (Block.lightValue[gs.blockID] > 0) {
                n17 = 1.0f;
            }
            instance.setColorOpaque_F(n8 * n17, n12 * n17, n16 * n17);
            this.f(gs, integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 5));
            b = true;
        }
        return b;
    }

    public boolean k(final Block gs, final int integer2, final int integer3, final int integer4) {
        final boolean b = false;
        float n = 0.375f;
        float n2 = 0.625f;
        gs.setBlockBounds(n, 0.0f, n, n2, 1.0f, n2);
        this.j(gs, integer2, integer3, integer4);
        int n3 = 0;
        boolean b2 = false;
        if (this.a.getBlockId(integer2 - 1, integer3, integer4) == gs.blockID || this.a.getBlockId(integer2 + 1, integer3, integer4) == gs.blockID) {
            n3 = 1;
        }
        if (this.a.getBlockId(integer2, integer3, integer4 - 1) == gs.blockID || this.a.getBlockId(integer2, integer3, integer4 + 1) == gs.blockID) {
            b2 = true;
        }
        if (n3 == 0 && !b2) {
            n3 = 1;
        }
        n = 0.4375f;
        n2 = 0.5625f;
        float n4 = 0.75f;
        float n5 = 0.9375f;
        if (n3 != 0) {
            gs.setBlockBounds(0.0f, n4, n, 1.0f, n5, n2);
            this.j(gs, integer2, integer3, integer4);
        }
        if (b2) {
            gs.setBlockBounds(n, n4, 0.0f, n2, n5, 1.0f);
            this.j(gs, integer2, integer3, integer4);
        }
        n4 = 0.375f;
        n5 = 0.5625f;
        if (n3 != 0) {
            gs.setBlockBounds(0.0f, n4, n, 1.0f, n5, n2);
            this.j(gs, integer2, integer3, integer4);
        }
        if (b2) {
            gs.setBlockBounds(n, n4, 0.0f, n2, n5, 1.0f);
            this.j(gs, integer2, integer3, integer4);
        }
        gs.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return b;
    }

    public boolean l(final Block gs, final int integer2, final int integer3, final int integer4) {
        final boolean b = false;
        final int blockMetadata = this.a.getBlockMetadata(integer2, integer3, integer4);
        if (blockMetadata == 0) {
            gs.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f);
            this.j(gs, integer2, integer3, integer4);
            gs.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            this.j(gs, integer2, integer3, integer4);
        } else if (blockMetadata == 1) {
            gs.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            this.j(gs, integer2, integer3, integer4);
            gs.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            this.j(gs, integer2, integer3, integer4);
        } else if (blockMetadata == 2) {
            gs.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f);
            this.j(gs, integer2, integer3, integer4);
            gs.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            this.j(gs, integer2, integer3, integer4);
        } else if (blockMetadata == 3) {
            gs.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            this.j(gs, integer2, integer3, integer4);
            gs.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
            this.j(gs, integer2, integer3, integer4);
        }
        gs.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return b;
    }

    public boolean m(final Block gs, final int integer2, final int integer3, final int integer4) {
        final Tessellator instance = Tessellator.instance;
        final BlockDoor blockDoor = (BlockDoor) gs;
        boolean b = false;
        final float n = 0.5f;
        final float n2 = 1.0f;
        final float n3 = 0.8f;
        final float n4 = 0.6f;
        final float blockBrightness = gs.getBlockBrightness(this.a, integer2, integer3, integer4);
        float n5 = gs.getBlockBrightness(this.a, integer2, integer3 - 1, integer4);
        if (blockDoor.minY > 0.0) {
            n5 = blockBrightness;
        }
        if (Block.lightValue[gs.blockID] > 0) {
            n5 = 1.0f;
        }
        instance.setColorOpaque_F(n * n5, n * n5, n * n5);
        this.a(gs, (double) integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 0));
        b = true;
        n5 = gs.getBlockBrightness(this.a, integer2, integer3 + 1, integer4);
        if (blockDoor.maxY < 1.0) {
            n5 = blockBrightness;
        }
        if (Block.lightValue[gs.blockID] > 0) {
            n5 = 1.0f;
        }
        instance.setColorOpaque_F(n2 * n5, n2 * n5, n2 * n5);
        this.b(gs, (double) integer2, integer3, integer4, gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 1));
        b = true;
        n5 = gs.getBlockBrightness(this.a, integer2, integer3, integer4 - 1);
        if (blockDoor.minZ > 0.0) {
            n5 = blockBrightness;
        }
        if (Block.lightValue[gs.blockID] > 0) {
            n5 = 1.0f;
        }
        instance.setColorOpaque_F(n3 * n5, n3 * n5, n3 * n5);
        int n6 = gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 2);
        if (n6 < 0) {
            this.c = true;
            n6 = -n6;
        }
        this.c(gs, integer2, integer3, integer4, n6);
        b = true;
        this.c = false;
        n5 = gs.getBlockBrightness(this.a, integer2, integer3, integer4 + 1);
        if (blockDoor.maxZ < 1.0) {
            n5 = blockBrightness;
        }
        if (Block.lightValue[gs.blockID] > 0) {
            n5 = 1.0f;
        }
        instance.setColorOpaque_F(n3 * n5, n3 * n5, n3 * n5);
        n6 = gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 3);
        if (n6 < 0) {
            this.c = true;
            n6 = -n6;
        }
        this.d(gs, integer2, integer3, integer4, n6);
        b = true;
        this.c = false;
        n5 = gs.getBlockBrightness(this.a, integer2 - 1, integer3, integer4);
        if (blockDoor.minX > 0.0) {
            n5 = blockBrightness;
        }
        if (Block.lightValue[gs.blockID] > 0) {
            n5 = 1.0f;
        }
        instance.setColorOpaque_F(n4 * n5, n4 * n5, n4 * n5);
        n6 = gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 4);
        if (n6 < 0) {
            this.c = true;
            n6 = -n6;
        }
        this.e(gs, integer2, integer3, integer4, n6);
        b = true;
        this.c = false;
        n5 = gs.getBlockBrightness(this.a, integer2 + 1, integer3, integer4);
        if (blockDoor.maxX < 1.0) {
            n5 = blockBrightness;
        }
        if (Block.lightValue[gs.blockID] > 0) {
            n5 = 1.0f;
        }
        instance.setColorOpaque_F(n4 * n5, n4 * n5, n4 * n5);
        n6 = gs.getBlockTextureGeneric(this.a, integer2, integer3, integer4, 5);
        if (n6 < 0) {
            this.c = true;
            n6 = -n6;
        }
        this.f(gs, integer2, integer3, integer4, n6);
        b = true;
        this.c = false;
        return b;
    }

    public void a(final Block gs, final double double2, final double double3, final double double4, int integer) {
        final Tessellator instance = Tessellator.instance;
        if (this.b >= 0) {
            integer = this.b;
        }
        final int n = (integer & 0xF) << 4;
        final int n2 = integer & 0xF0;
        double n3 = (n + gs.minX * 16.0) / 256.0;
        double n4 = (n + gs.maxX * 16.0 - 0.01) / 256.0;
        double n5 = (n2 + gs.minZ * 16.0) / 256.0;
        double n6 = (n2 + gs.maxZ * 16.0 - 0.01) / 256.0;
        if (gs.minX < 0.0 || gs.maxX > 1.0) {
            n3 = (n + 0.0f) / 256.0f;
            n4 = (n + 15.99f) / 256.0f;
        }
        if (gs.minZ < 0.0 || gs.maxZ > 1.0) {
            n5 = (n2 + 0.0f) / 256.0f;
            n6 = (n2 + 15.99f) / 256.0f;
        }
        final double n7 = double2 + gs.minX;
        final double n8 = double2 + gs.maxX;
        final double n9 = double3 + gs.minY;
        final double n10 = double4 + gs.minZ;
        final double n11 = double4 + gs.maxZ;
        instance.addVertexWithUV(n7, n9, n11, n3, n6);
        instance.addVertexWithUV(n7, n9, n10, n3, n5);
        instance.addVertexWithUV(n8, n9, n10, n4, n5);
        instance.addVertexWithUV(n8, n9, n11, n4, n6);
    }

    public void b(final Block gs, final double double2, final double double3, final double double4, int integer) {
        final Tessellator instance = Tessellator.instance;
        if (this.b >= 0) {
            integer = this.b;
        }
        final int n = (integer & 0xF) << 4;
        final int n2 = integer & 0xF0;
        double n3 = (n + gs.minX * 16.0) / 256.0;
        double n4 = (n + gs.maxX * 16.0 - 0.01) / 256.0;
        double n5 = (n2 + gs.minZ * 16.0) / 256.0;
        double n6 = (n2 + gs.maxZ * 16.0 - 0.01) / 256.0;
        if (gs.minX < 0.0 || gs.maxX > 1.0) {
            n3 = (n + 0.0f) / 256.0f;
            n4 = (n + 15.99f) / 256.0f;
        }
        if (gs.minZ < 0.0 || gs.maxZ > 1.0) {
            n5 = (n2 + 0.0f) / 256.0f;
            n6 = (n2 + 15.99f) / 256.0f;
        }
        final double n7 = double2 + gs.minX;
        final double n8 = double2 + gs.maxX;
        final double n9 = double3 + gs.maxY;
        final double n10 = double4 + gs.minZ;
        final double n11 = double4 + gs.maxZ;
        instance.addVertexWithUV(n8, n9, n11, n4, n6);
        instance.addVertexWithUV(n8, n9, n10, n4, n5);
        instance.addVertexWithUV(n7, n9, n10, n3, n5);
        instance.addVertexWithUV(n7, n9, n11, n3, n6);
    }

    public void c(final Block gs, final double double2, final double double3, final double double4, int integer) {
        final Tessellator instance = Tessellator.instance;
        if (this.b >= 0) {
            integer = this.b;
        }
        final int n = (integer & 0xF) << 4;
        final int n2 = integer & 0xF0;
        double n3 = (n + gs.minX * 16.0) / 256.0;
        double n4 = (n + gs.maxX * 16.0 - 0.01) / 256.0;
        double n5 = (n2 + gs.minY * 16.0) / 256.0;
        double n6 = (n2 + gs.maxY * 16.0 - 0.01) / 256.0;
        if (this.c) {
            final double n7 = n3;
            n3 = n4;
            n4 = n7;
        }
        if (gs.minX < 0.0 || gs.maxX > 1.0) {
            n3 = (n + 0.0f) / 256.0f;
            n4 = (n + 15.99f) / 256.0f;
        }
        if (gs.minY < 0.0 || gs.maxY > 1.0) {
            n5 = (n2 + 0.0f) / 256.0f;
            n6 = (n2 + 15.99f) / 256.0f;
        }
        final double n7 = double2 + gs.minX;
        final double n8 = double2 + gs.maxX;
        final double n9 = double3 + gs.minY;
        final double n10 = double3 + gs.maxY;
        final double n11 = double4 + gs.minZ;
        instance.addVertexWithUV(n7, n10, n11, n4, n5);
        instance.addVertexWithUV(n8, n10, n11, n3, n5);
        instance.addVertexWithUV(n8, n9, n11, n3, n6);
        instance.addVertexWithUV(n7, n9, n11, n4, n6);
    }

    public void d(final Block gs, final double double2, final double double3, final double double4, int integer) {
        final Tessellator instance = Tessellator.instance;
        if (this.b >= 0) {
            integer = this.b;
        }
        final int n = (integer & 0xF) << 4;
        final int n2 = integer & 0xF0;
        double n3 = (n + gs.minX * 16.0) / 256.0;
        double n4 = (n + gs.maxX * 16.0 - 0.01) / 256.0;
        double n5 = (n2 + gs.minY * 16.0) / 256.0;
        double n6 = (n2 + gs.maxY * 16.0 - 0.01) / 256.0;
        if (this.c) {
            final double n7 = n3;
            n3 = n4;
            n4 = n7;
        }
        if (gs.minX < 0.0 || gs.maxX > 1.0) {
            n3 = (n + 0.0f) / 256.0f;
            n4 = (n + 15.99f) / 256.0f;
        }
        if (gs.minY < 0.0 || gs.maxY > 1.0) {
            n5 = (n2 + 0.0f) / 256.0f;
            n6 = (n2 + 15.99f) / 256.0f;
        }
        final double n7 = double2 + gs.minX;
        final double n8 = double2 + gs.maxX;
        final double n9 = double3 + gs.minY;
        final double n10 = double3 + gs.maxY;
        final double n11 = double4 + gs.maxZ;
        instance.addVertexWithUV(n7, n10, n11, n3, n5);
        instance.addVertexWithUV(n7, n9, n11, n3, n6);
        instance.addVertexWithUV(n8, n9, n11, n4, n6);
        instance.addVertexWithUV(n8, n10, n11, n4, n5);
    }

    public void e(final Block gs, final double double2, final double double3, final double double4, int integer) {
        final Tessellator instance = Tessellator.instance;
        if (this.b >= 0) {
            integer = this.b;
        }
        final int n = (integer & 0xF) << 4;
        final int n2 = integer & 0xF0;
        double n3 = (n + gs.minZ * 16.0) / 256.0;
        double n4 = (n + gs.maxZ * 16.0 - 0.01) / 256.0;
        double n5 = (n2 + gs.minY * 16.0) / 256.0;
        double n6 = (n2 + gs.maxY * 16.0 - 0.01) / 256.0;
        if (this.c) {
            final double n7 = n3;
            n3 = n4;
            n4 = n7;
        }
        if (gs.minZ < 0.0 || gs.maxZ > 1.0) {
            n3 = (n + 0.0f) / 256.0f;
            n4 = (n + 15.99f) / 256.0f;
        }
        if (gs.minY < 0.0 || gs.maxY > 1.0) {
            n5 = (n2 + 0.0f) / 256.0f;
            n6 = (n2 + 15.99f) / 256.0f;
        }
        final double n7 = double2 + gs.minX;
        final double n8 = double3 + gs.minY;
        final double n9 = double3 + gs.maxY;
        final double n10 = double4 + gs.minZ;
        final double n11 = double4 + gs.maxZ;
        instance.addVertexWithUV(n7, n9, n11, n4, n5);
        instance.addVertexWithUV(n7, n9, n10, n3, n5);
        instance.addVertexWithUV(n7, n8, n10, n3, n6);
        instance.addVertexWithUV(n7, n8, n11, n4, n6);
    }

    public void f(final Block gs, final double double2, final double double3, final double double4, int integer) {
        final Tessellator instance = Tessellator.instance;
        if (this.b >= 0) {
            integer = this.b;
        }
        final int n = (integer & 0xF) << 4;
        final int n2 = integer & 0xF0;
        double n3 = (n + gs.minZ * 16.0) / 256.0;
        double n4 = (n + gs.maxZ * 16.0 - 0.01) / 256.0;
        double n5 = (n2 + gs.minY * 16.0) / 256.0;
        double n6 = (n2 + gs.maxY * 16.0 - 0.01) / 256.0;
        if (this.c) {
            final double n7 = n3;
            n3 = n4;
            n4 = n7;
        }
        if (gs.minZ < 0.0 || gs.maxZ > 1.0) {
            n3 = (n + 0.0f) / 256.0f;
            n4 = (n + 15.99f) / 256.0f;
        }
        if (gs.minY < 0.0 || gs.maxY > 1.0) {
            n5 = (n2 + 0.0f) / 256.0f;
            n6 = (n2 + 15.99f) / 256.0f;
        }
        final double n7 = double2 + gs.maxX;
        final double n8 = double3 + gs.minY;
        final double n9 = double3 + gs.maxY;
        final double n10 = double4 + gs.minZ;
        final double n11 = double4 + gs.maxZ;
        instance.addVertexWithUV(n7, n8, n11, n3, n6);
        instance.addVertexWithUV(n7, n8, n10, n4, n6);
        instance.addVertexWithUV(n7, n9, n10, n4, n5);
        instance.addVertexWithUV(n7, n9, n11, n3, n5);
    }

    public void renderBlockOnInventory(final Block gs) {
        final int n = -1;
        final Tessellator instance = Tessellator.instance;
        final int renderType = gs.getRenderType();
        if (renderType == 0) {
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            instance.startDrawingQuads();
            instance.setNormal(0.0f, -1.0f, 0.0f);
            this.a(gs, 0.0, 0.0, 0.0, gs.getBlockTextureFromSide(0));
            instance.draw();
            instance.startDrawingQuads();
            instance.setNormal(0.0f, 1.0f, 0.0f);
            this.b(gs, 0.0, 0.0, 0.0, gs.getBlockTextureFromSide(1));
            instance.draw();
            instance.startDrawingQuads();
            instance.setNormal(0.0f, 0.0f, -1.0f);
            this.c(gs, 0.0, 0.0, 0.0, gs.getBlockTextureFromSide(2));
            instance.draw();
            instance.startDrawingQuads();
            instance.setNormal(0.0f, 0.0f, 1.0f);
            this.d(gs, 0.0, 0.0, 0.0, gs.getBlockTextureFromSide(3));
            instance.draw();
            instance.startDrawingQuads();
            instance.setNormal(-1.0f, 0.0f, 0.0f);
            this.e(gs, 0.0, 0.0, 0.0, gs.getBlockTextureFromSide(4));
            instance.draw();
            instance.startDrawingQuads();
            instance.setNormal(1.0f, 0.0f, 0.0f);
            this.f(gs, 0.0, 0.0, 0.0, gs.getBlockTextureFromSide(5));
            instance.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        } else if (renderType == 1) {
            instance.startDrawingQuads();
            instance.setNormal(0.0f, -1.0f, 0.0f);
            this.a(gs, n, -0.5, -0.5, -0.5);
            instance.draw();
        } else if (renderType == 6) {
            instance.startDrawingQuads();
            instance.setNormal(0.0f, -1.0f, 0.0f);
            this.b(gs, n, -0.5, -0.5, -0.5);
            instance.draw();
        } else if (renderType == 2) {
            instance.startDrawingQuads();
            instance.setNormal(0.0f, -1.0f, 0.0f);
            this.a(gs, -0.5, -0.5, -0.5, 0.0, 0.0);
            instance.draw();
        } else if (renderType != 3) {
            if (renderType == 5) {
            }
        }
    }
}
