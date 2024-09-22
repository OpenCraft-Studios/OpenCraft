
package net.opencraft.entity;

import net.opencraft.world.World;

public class EntityGiant extends EntityMonster {

    public EntityGiant(final World world) {
        super(world);
        this.texture = "/assets/mob/zombie.png";
        this.moveSpeed = 0.5f;
        this.attackStrength = 50;
        this.health *= 10;
        this.yOffset *= 6.0f;
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }

    @Override
    protected float getBlockPathWeight(final int xCoord, final int yCoord, final int zCoord) {
        return this.world.getLightBrightness(xCoord, yCoord, zCoord) - 0.5f;
    }
}
