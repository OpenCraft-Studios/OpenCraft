
package net.opencraft.entity;

import java.util.ArrayList;
import java.util.List;

import net.opencraft.EnumArt;
import net.opencraft.blocks.material.Material;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.util.Mth;
import net.opencraft.world.World;

public class EntityPainting extends Entity {

    private int tickCounter1;
    public int direction;
    private int xPosition;
    private int yPosition;
    private int zPosition;
    public EnumArt art;

    public EntityPainting(final World world) {
        super(world);
        this.tickCounter1 = 0;
        this.direction = 0;
        this.yOffset = 0.0f;
        this.setSize(0.5f, 0.5f);
    }

    public EntityPainting(World world, int x, int y, int z, int direction) {
        this(world);
        this.xPosition = x;
        this.yPosition = y;
        this.zPosition = z;
        List<EnumArt> list = new ArrayList<>();
        for (final EnumArt art : EnumArt.values()) {
            this.art = art;
            this.setDirection(direction);
            if (this.onValidSurface())
                list.add(art);
        }
        if (list.size() > 0)
            this.art = list.get(this.rand.nextInt(list.size()));
        
        this.setDirection(direction);
    }

    public void setDirection(final int direction) {
        this.direction = direction;
        final float n = (float) (direction * 90);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        float n2 = (float) this.art.sizeX;
        float n3 = (float) this.art.sizeY;
        float n4 = (float) this.art.sizeX;
        if (direction == 0 || direction == 2) {
            n4 = 0.5f;
        } else {
            n2 = 0.5f;
        }
        n2 /= 32.0f;
        n3 /= 32.0f;
        n4 /= 32.0f;
        float n5 = this.xPosition + 0.5f;
        float n6 = this.yPosition + 0.5f;
        float n7 = this.zPosition + 0.5f;
        final float n8 = 0.5625f;
        if (direction == 0) {
            n7 -= n8;
        }
        if (direction == 1) {
            n5 -= n8;
        }
        if (direction == 2) {
            n7 += n8;
        }
        if (direction == 3) {
            n5 += n8;
        }
        if (direction == 0) {
            n5 -= this.getArtSize(this.art.sizeX);
        }
        if (direction == 1) {
            n7 += this.getArtSize(this.art.sizeX);
        }
        if (direction == 2) {
            n5 += this.getArtSize(this.art.sizeX);
        }
        if (direction == 3) {
            n7 -= this.getArtSize(this.art.sizeX);
        }
        n6 += this.getArtSize(this.art.sizeY);
        this.setPosition(n5, n6, n7);
        final float n9 = -0.00625f;
        this.boundingBox.setBounds(n5 - n2 - n9, n6 - n3 - n9, n7 - n4 - n9, n5 + n2 + n9, n6 + n3 + n9, n7 + n4 + n9);
    }

    private float getArtSize(final int integer) {
        if (integer == 32) {
            return 0.5f;
        }
        if (integer == 64) {
            return 0.5f;
        }
        return 0.0f;
    }

    @Override
    public void onUpdate() {
        if (this.tickCounter1++ == 100 && !this.onValidSurface()) {
            this.tickCounter1 = 0;
            this.setEntityDead();
            this.world.entityJoinedWorld(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(Item.painting)));
        }
    }

    public boolean onValidSurface() {
        if (this.world.getCollidingBoundingBoxes(this, this.boundingBox).size() > 0) {
            return false;
        }
        final int n = this.art.sizeX / 16;
        final int n2 = this.art.sizeY / 16;
        int n3 = this.xPosition;
        int n4 = this.yPosition;
        int n5 = this.zPosition;
        if (this.direction == 0) {
            n3 = Mth.floor_double(this.posX - this.art.sizeX / 32.0f);
        }
        if (this.direction == 1) {
            n5 = Mth.floor_double(this.posZ - this.art.sizeX / 32.0f);
        }
        if (this.direction == 2) {
            n3 = Mth.floor_double(this.posX - this.art.sizeX / 32.0f);
        }
        if (this.direction == 3) {
            n5 = Mth.floor_double(this.posZ - this.art.sizeX / 32.0f);
        }
        n4 = Mth.floor_double(this.posY - this.art.sizeY / 32.0f);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                Material material;
                if (this.direction == 0 || this.direction == 2) {
                    material = this.world.getBlockMaterial(n3 + i, n4 + j, this.zPosition);
                } else {
                    material = this.world.getBlockMaterial(this.xPosition, n4 + j, n5 + i);
                }
                if (!material.isSolid()) {
                    return false;
                }
            }
        }
        final List entitiesWithinAABBExcludingEntity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
        for (int j = 0; j < entitiesWithinAABBExcludingEntity.size(); ++j) {
            if (entitiesWithinAABBExcludingEntity.get(j) instanceof EntityPainting) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(final Entity entity, final int nya1) {
        this.setEntityDead();
        this.world.entityJoinedWorld(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(Item.painting)));
        return true;
    }

    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("Dir", (byte) this.direction);
        nbtTagCompound.setString("Motive", this.art.title);
        nbtTagCompound.setInteger("TileX", this.xPosition);
        nbtTagCompound.setInteger("TileY", this.yPosition);
        nbtTagCompound.setInteger("TileZ", this.zPosition);
    }

    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.direction = nbtTagCompound.getByte("Dir");
        this.xPosition = nbtTagCompound.getInteger("TileX");
        this.yPosition = nbtTagCompound.getInteger("TileY");
        this.zPosition = nbtTagCompound.getInteger("TileZ");
        final String string = nbtTagCompound.getString("Motive");
        for (final EnumArt art : EnumArt.values()) {
            if (art.title.equals(string)) {
                this.art = art;
            }
        }
        if (this.art == null) {
            this.art = EnumArt.Kebab;
        }
        this.setDirection(this.direction);
    }
}
