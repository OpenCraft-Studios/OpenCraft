
package net.opencraft.client.renderer.entity;

import java.util.HashMap;
import java.util.Map;

import net.opencraft.client.config.GameSettings;
import net.opencraft.client.entity.models.*;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityArrow;
import net.opencraft.entity.EntityCreeper;
import net.opencraft.entity.EntityFallingSand;
import net.opencraft.entity.EntityGiant;
import net.opencraft.entity.EntityItem;
import net.opencraft.entity.EntityLiving;
import net.opencraft.entity.EntityMinecart;
import net.opencraft.entity.EntityPainting;
import net.opencraft.entity.EntityPig;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.entity.EntitySheep;
import net.opencraft.entity.EntitySkeleton;
import net.opencraft.entity.EntitySpider;
import net.opencraft.entity.EntityTNTPrimed;
import net.opencraft.entity.EntityZombie;
import net.opencraft.client.renderer.font.FontRenderer;
import net.opencraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderManager {

    private Map<Class<? extends Entity>, Render<?>> entityRenderMap = new HashMap();
    public static RenderManager instance = new RenderManager();
    private FontRenderer fontRenderer;
    public static double renderPosX;
    public static double renderPosY;
    public static double renderPosZ;
    public Renderer renderEngine;
    public World worldObj;
    public EntityPlayer livingPlayer;
    public float playerViewY;
    public float playerViewX;
    public GameSettings options;
    public double field_1222_l;
    public double field_1221_m;
    public double field_1220_n;

    private RenderManager() {
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider());
        this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5f), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7f));
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
        this.entityRenderMap.put(EntitySkeleton.class, new RenderLiving(new ModelSkeleton(), 0.5f));
        this.entityRenderMap.put(EntityZombie.class, new RenderLiving(new ModelZombie(), 0.5f));
        this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
        this.entityRenderMap.put(EntityGiant.class, new RenderGiantZombie(new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5f));
        this.entityRenderMap.put(Entity.class, new RenderEntity());
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
        this.entityRenderMap.put(EntityItem.class, new RenderItem());
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
        this.entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
        for (Render bq2 : this.entityRenderMap.values()) {
            bq2.setRenderManager(this);
        }
    }

    public Render getEntityClassRenderObject(Class clazz) {
        Render bq2 = (Render) this.entityRenderMap.get(clazz);
        if (bq2 == null && clazz != Entity.class) {
            bq2 = this.getEntityClassRenderObject(clazz.getSuperclass());
            this.entityRenderMap.put(clazz, bq2);
        }
        return bq2;
    }

    public Render getEntityRenderObject(Entity eq2) {
        return this.getEntityClassRenderObject(eq2.getClass());
    }

    public void cacheActiveRenderInfo(World fe2, Renderer id2, FontRenderer ej2, EntityPlayer gi2, GameSettings ja2, float f2) {
        this.worldObj = fe2;
        this.renderEngine = id2;
        this.options = ja2;
        this.livingPlayer = gi2;
        this.fontRenderer = ej2;
        this.playerViewY = gi2.prevRotationYaw + (gi2.rotationYaw - gi2.prevRotationYaw) * f2;
        this.playerViewX = gi2.prevRotationPitch + (gi2.rotationPitch - gi2.prevRotationPitch) * f2;
        this.field_1222_l = gi2.lastTickPosX + (gi2.posX - gi2.lastTickPosX) * (double) f2;
        this.field_1221_m = gi2.lastTickPosY + (gi2.posY - gi2.lastTickPosY) * (double) f2;
        this.field_1220_n = gi2.lastTickPosZ + (gi2.posZ - gi2.lastTickPosZ) * (double) f2;
    }

    public void renderEntity(Entity eq2, float f2) {
        double d = eq2.lastTickPosX + (eq2.posX - eq2.lastTickPosX) * (double) f2;
        double d2 = eq2.lastTickPosY + (eq2.posY - eq2.lastTickPosY) * (double) f2;
        double d3 = eq2.lastTickPosZ + (eq2.posZ - eq2.lastTickPosZ) * (double) f2;
        float f3 = eq2.prevRotationYaw + (eq2.rotationYaw - eq2.prevRotationYaw) * f2;
        float f4 = eq2.getEntityBrightness(f2);
        GL11.glColor3f((float) f4, (float) f4, (float) f4);
        this.renderEntityWithPosYaw(eq2, d - renderPosX, d2 - renderPosY, d3 - RenderManager.renderPosZ, f3, f2);
    }

    public void renderEntityWithPosYaw(Entity eq2, double d, double d2, double d3, float f2, float f3) {
        Render bq2 = this.getEntityRenderObject(eq2);
        if (bq2 != null) {
            bq2.doRender(eq2, d, d2, d3, f2, f3);
            bq2.doRenderShadowAndFire(eq2, d, d2, d3, f2, f3);
        }
    }

    public void func_852_a(World fe2) {
        this.worldObj = fe2;
    }

    public double func_851_a(double d, double d2, double d3) {
        double d4 = d - this.field_1222_l;
        double d5 = d2 - this.field_1221_m;
        double d6 = d3 - this.field_1220_n;
        return d4 * d4 + d5 * d5 + d6 * d6;
    }
}
