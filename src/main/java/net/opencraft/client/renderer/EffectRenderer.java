
package net.opencraft.client.renderer;

import static org.joml.Math.*;

import java.util.*;

import org.lwjgl.opengl.GL11;

import net.opencraft.block.Block;
import net.opencraft.client.renderer.entity.RenderEngine;
import net.opencraft.entity.*;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EffectRenderer {

    protected World worldObj;
    private List<EntityFX>[] fxLayers;
    private RenderEngine renderer;
    private Random rand;

    @SuppressWarnings("unchecked")
	public EffectRenderer(final World fe, final RenderEngine id) {
        this.fxLayers = new List[3];
        this.rand = new Random();
        if (fe != null)
            this.worldObj = fe;
        
        this.renderer = id;
        for (int i = 0; i < 3; ++i)
            this.fxLayers[i] = new ArrayList<>();
    }

    public void addEffect(final EntityFX ix) {
        this.fxLayers[ix.getFXLayer()].add(ix);
    }

    public void updateEffects() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < this.fxLayers[i].size(); ++j) {
                final EntityFX entityFX = (EntityFX) this.fxLayers[i].get(j);
                entityFX.onUpdate();
                if (entityFX.isDead)
                    this.fxLayers[i].remove(j--);
            }
        }
    }

    public void renderParticles(final Entity eq, final float float2) {
        final float cos = cos(toRadians(eq.rotationYaw));
        final float sin = sin(toRadians(eq.rotationYaw));
        final float float3 = -sin * sin(toRadians(eq.rotationPitch));
        final float float4 = cos * sin(toRadians(eq.rotationPitch));
        final float cos2 = cos(toRadians(eq.rotationPitch));
        EntityFX.interpPosX = eq.lastTickPosX + (eq.posX - eq.lastTickPosX) * float2;
        EntityFX.interpPosY = eq.lastTickPosY + (eq.posY - eq.lastTickPosY) * float2;
        EntityFX.interpPosZ = eq.lastTickPosZ + (eq.posZ - eq.lastTickPosZ) * float2;
        for (int i = 0; i < 2; ++i) {
            if (this.fxLayers[i].size() != 0) {
                int n = 0;
                if (i == 0) {
                    n = this.renderer.getTexture("/assets/particles.png");
                }
                if (i == 1) {
                    n = this.renderer.getTexture("/assets/terrain.png");
                }
                GL11.glBindTexture(3553, n);
                final Tessellator instance = Tessellator.instance;
                instance.startDrawingQuads();
                for (int j = 0; j < this.fxLayers[i].size(); ++j) {
                    ((EntityFX) this.fxLayers[i].get(j)).renderParticle(instance, float2, cos, cos2, sin, float3, float4);
                }
                instance.draw();
            }
        }
    }

    public void renderLitParticles(final Entity eq, final float float2) {
        final int n = 2;
        if (this.fxLayers[n].size() == 0) {
            return;
        }
        final Tessellator instance = Tessellator.instance;
        for (int i = 0; i < this.fxLayers[n].size(); ++i) {
            ((EntityFX) this.fxLayers[n].get(i)).renderParticle(instance, float2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
    }

    public void clearEffects(final World fe) {
        this.worldObj = fe;
        for (int i = 0; i < 3; ++i) {
            this.fxLayers[i].clear();
        }
    }

    public void addBlockDestroyEffects(final int integer1, final int integer2, final int integer3) {
        final int blockId = this.worldObj.getBlockId(integer1, integer2, integer3);
        if (blockId == 0) {
            return;
        }
        final Block gs = Block.blocksList[blockId];
        for (int n = 4, i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    final double double2 = integer1 + (i + 0.5) / n;
                    final double double3 = integer2 + (j + 0.5) / n;
                    final double double4 = integer3 + (k + 0.5) / n;
                    this.addEffect(new EntityDiggingFX(this.worldObj, double2, double3, double4, double2 - integer1 - 0.5, double3 - integer2 - 0.5, double4 - integer3 - 0.5, gs));
                }
            }
        }
    }

    public void addBlockHitEffects(final int integer1, final int integer2, final int integer3, final int integer4) {
        final int blockId = this.worldObj.getBlockId(integer1, integer2, integer3);
        if (blockId == 0) {
            return;
        }
        final Block gs = Block.blocksList[blockId];
        final float n = 0.1f;
        double double2 = integer1 + this.rand.nextDouble() * (gs.maxX - gs.minX - n * 2.0f) + n + gs.minX;
        double double3 = integer2 + this.rand.nextDouble() * (gs.maxY - gs.minY - n * 2.0f) + n + gs.minY;
        double double4 = integer3 + this.rand.nextDouble() * (gs.maxZ - gs.minZ - n * 2.0f) + n + gs.minZ;
        if (integer4 == 0) {
            double3 = integer2 + gs.minY - n;
        }
        if (integer4 == 1) {
            double3 = integer2 + gs.maxY + n;
        }
        if (integer4 == 2) {
            double4 = integer3 + gs.minZ - n;
        }
        if (integer4 == 3) {
            double4 = integer3 + gs.maxZ + n;
        }
        if (integer4 == 4) {
            double2 = integer1 + gs.minX - n;
        }
        if (integer4 == 5) {
            double2 = integer1 + gs.maxX + n;
        }
        this.addEffect(new EntityDiggingFX(this.worldObj, double2, double3, double4, 0.0, 0.0, 0.0, gs).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));
    }

    public String getStatistics() {
        return "" + this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size();
    }
}
