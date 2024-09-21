
package net.opencraft.tileentity;

import net.opencraft.entity.EntityList;
import net.opencraft.entity.EntityLiving;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.AxisAlignedBB;

public class TileEntityMobSpawner extends TileEntity {

    public int delay;
    public String getMobID;
    public double yaw;
    public double yaw2;

    public TileEntityMobSpawner() {
        this.delay = -1;
        this.yaw2 = 0.0;
        this.getMobID = "Pig";
        this.delay = 20;
    }

    public boolean anyPlayerInRange() {
        return this.worldObj.player.getDistanceSq(this.xCoord, this.yCoord, this.zCoord) <= 256.0;
    }

    @Override
    public void updateEntity() {
        this.yaw2 = this.yaw;
        if (!this.anyPlayerInRange()) {
            return;
        }
        double n = this.xCoord + this.worldObj.rand.nextFloat();
        double n2 = this.yCoord + this.worldObj.rand.nextFloat();
        double n3 = this.zCoord + this.worldObj.rand.nextFloat();
        this.worldObj.spawnParticle("smoke", n, n2, n3, 0.0, 0.0, 0.0);
        this.worldObj.spawnParticle("flame", n, n2, n3, 0.0, 0.0, 0.0);
        this.yaw += 1000.0f / (this.delay + 200.0f);
        while (this.yaw > 360.0) {
            this.yaw -= 360.0;
            this.yaw2 -= 360.0;
        }
        if (this.delay == -1) {
            this.updateDelay();
        }
        if (this.delay > 0) {
            --this.delay;
            return;
        }
        for (int n4 = 4, i = 0; i < n4; ++i) {
            final EntityLiving entity = (EntityLiving) EntityList.createEntityInWorld(this.getMobID, this.worldObj);
            if (entity == null) {
                return;
            }
            if (this.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBoxFromPool(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(8.0, 4.0, 8.0)).size() >= 6) {
                this.updateDelay();
                return;
            }
            if (entity != null) {
                final double n5 = this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0;
                final double n6 = this.yCoord + this.worldObj.rand.nextInt(3) - 1;
                final double n7 = this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0;
                entity.setPositionAndRotation(n5, n6, n7, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
                if (entity.getCanSpawnHere(n5, n6, n7)) {
                    this.worldObj.entityJoinedWorld(entity);
                    for (int j = 0; j < 20; ++j) {
                        n = this.xCoord + 0.5 + (this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                        n2 = this.yCoord + 0.5 + (this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                        n3 = this.zCoord + 0.5 + (this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                        this.worldObj.spawnParticle("smoke", n, n2, n3, 0.0, 0.0, 0.0);
                        this.worldObj.spawnParticle("flame", n, n2, n3, 0.0, 0.0, 0.0);
                    }
                    entity.spawnExplosionParticle();
                    this.updateDelay();
                }
            }
        }
        super.updateEntity();
    }

    private void updateDelay() {
        this.delay = 200 + this.worldObj.rand.nextInt(600);
    }

    @Override
    public void readFromNBT(final NBTTagCompound ae) {
        super.readFromNBT(ae);
        this.getMobID = ae.getString("EntityId");
        this.delay = ae.getShort("Delay");
    }

    @Override
    public void writeToNBT(final NBTTagCompound ae) {
        super.writeToNBT(ae);
        ae.setString("EntityId", this.getMobID);
        ae.setShort("Delay", (short) this.delay);
    }
}
