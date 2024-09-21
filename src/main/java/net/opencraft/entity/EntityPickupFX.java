
package net.opencraft.entity;

import net.opencraft.util.MathHelper;
import net.opencraft.client.renderer.entity.RenderManager;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityPickupFX extends EntityFX {

    private Entity entityToPickUp;
    private EntityLiving entityPickingUp;
    private int age = 0;
    private int maxAge = 0;
    private float yOffs;

    public EntityPickupFX(World fe2, Entity eq2, EntityLiving ka2, float f) {
        super(fe2, eq2.posX, eq2.posY, eq2.posZ, eq2.motionX, eq2.motionY, eq2.motionZ);
        this.entityToPickUp = eq2;
        this.entityPickingUp = ka2;
        this.maxAge = 3;
        this.yOffs = f;
    }

    public void renderParticle(Tessellator ag2, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = ((float) this.age + f) / (float) this.maxAge;
        f7 *= f7;
        double d = this.entityToPickUp.posX;
        double d2 = this.entityToPickUp.posY;
        double d3 = this.entityToPickUp.posZ;
        double d4 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.posX - this.entityPickingUp.lastTickPosX) * (double) f;
        double d5 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.posY - this.entityPickingUp.lastTickPosY) * (double) f + (double) this.yOffs;
        double d6 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.posZ - this.entityPickingUp.lastTickPosZ) * (double) f;
        double d7 = d + (d4 - d) * (double) f7;
        double d8 = d2 + (d5 - d2) * (double) f7;
        double d9 = d3 + (d6 - d3) * (double) f7;
        int n = MathHelper.floor_double(d7);
        int n2 = MathHelper.floor_double(d8 + (double) (this.yOffset / 2.0f));
        int n3 = MathHelper.floor_double(d9);
        float f8 = this.worldObj.getLightBrightness(n, n2, n3);
        GL11.glColor4f((float) f8, (float) f8, (float) f8, (float) 1.0f);
        RenderManager.instance.renderEntityWithPosYaw(this.entityToPickUp, (float) (d7 -= interpPosX), (float) (d8 -= interpPosY), (float) (d9 -= interpPosZ), this.entityToPickUp.rotationYaw, f);
    }

    public void onUpdate() {
        ++this.age;
        if (this.age == this.maxAge) {
            this.setEntityDead();
        }
    }

    public int getFXLayer() {
        return 2;
    }
}
