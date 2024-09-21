
package net.opencraft.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityList;
import net.opencraft.renderer.entity.RenderManager;

import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {

    private Map<String, Entity> entityHashMap;

    public TileEntityMobSpawnerRenderer() {
        this.entityHashMap = new HashMap<>();
    }

    public void renderTileEntityMobSpawner(final TileEntityMobSpawner cs, final double double2, final double double3, final double double4, final float float5) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) double2 + 0.5f, (float) double3, (float) double4 + 0.5f);
        Entity entityInWorld = (Entity) this.entityHashMap.get(cs.getMobID);
        if (entityInWorld == null) {
            entityInWorld = EntityList.createEntityInWorld(cs.getMobID, null);
            this.entityHashMap.put(cs.getMobID, entityInWorld);
        }
        if (entityInWorld != null) {
            entityInWorld.setWorld(cs.worldObj);
            final float n = 0.4375f;
            GL11.glTranslatef(0.0f, 0.4f, 0.0f);
            GL11.glRotatef((float) (cs.yaw2 + (cs.yaw - cs.yaw2) * float5) * 10.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.4f, 0.0f);
            GL11.glScalef(n, n, n);
            RenderManager.instance.renderEntityWithPosYaw(entityInWorld, 0.0, 0.0, 0.0, 0.0f, float5);
        }
        GL11.glPopMatrix();
    }
}
