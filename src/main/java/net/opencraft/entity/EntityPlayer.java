
package net.opencraft.entity;

import java.util.List;
import net.opencraft.block.Block;
import net.opencraft.block.material.Material;
import net.opencraft.inventory.IInventory;
import net.opencraft.inventory.InventoryPlayer;
import net.opencraft.item.Item;
import net.opencraft.item.ItemStack;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.tileentity.TileEntityFurnace;
import net.opencraft.tileentity.TileEntitySign;
import net.opencraft.util.MathHelper;
import net.opencraft.world.World;

public class EntityPlayer extends EntityLiving {

    public InventoryPlayer inventory;
    public byte unusedByte;
    public int score;
    public float prevCameraYaw;
    public float cameraYaw;
    protected String username;
    private int swingProgressInt;

    public EntityPlayer(final World world) {
        super(world);
        this.inventory = new InventoryPlayer(this);
        this.unusedByte = 0;
        this.score = 0;
        this.swingProgressInt = 0;
        this.setPositionAndRotation(world.x + 0.5, world.y, world.z + 0.5, 0.0f, 0.0f);
        this.yOffset = 1.62f;
        this.health = 20;
        this.entityType = "humanoid";
        this.unused180 = 180.0f;
        this.fireResistance = 20;
        this.texture = "/assets/char.png";
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.prevCameraYaw = this.cameraYaw;
        this.cameraYaw = 0.0f;
    }

    public void preparePlayerToSpawn() {
        this.yOffset = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        if (this.worldObj != null) {
            this.worldObj.player = this;
        }
        this.health = 20;
        this.deathTime = 0;
    }

    @Override
    public void onLivingUpdate() {
        this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "calm", 0.0f);
        if (this.worldObj.difficultySetting == 0 && this.health < 20 && this.ticksExisted % 20 * 4 == 0) {
            this.heal(1);
        }
        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float n = (float) Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (sqrt_double > 0.1f) {
            sqrt_double = 0.1f;
        }
        if (!this.onGround || this.health <= 0) {
            sqrt_double = 0.0f;
        }
        if (this.onGround || this.health <= 0) {
            n = 0.0f;
        }
        this.cameraYaw += (sqrt_double - this.cameraYaw) * 0.4f;
        this.cameraPitch += (n - this.cameraPitch) * 0.8f;
        if (this.health > 0) {
            final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0, 0.0, 1.0));
            if (entitiesWithinAABBExcludingEntity != null) {
                for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
                    this.collideWithPlayer((Entity) entitiesWithinAABBExcludingEntity.get(i));
                }
            }
        }
    }

    private void collideWithPlayer(final Entity eq) {
        eq.onCollideWithPlayer(this);
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public void onDeath(final Entity entity) {
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612;
        if (this.username.equals("Notch")) {
            this.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }
        this.inventory.dropAllItems();
        if (entity != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        } else {
            final double n = 0.0;
            this.motionZ = n;
            this.motionX = n;
        }
        this.yOffset = 0.1f;
    }

    @Override
    public void addToPlayerScore(final Entity entity, final int score) {
        this.score += score;
    }

    public void dropPlayerItem(final ItemStack hw) {
        this.dropPlayerItemWithRandomChoice(hw, false);
    }

    public void dropPlayerItemWithRandomChoice(final ItemStack hw, final boolean boolean2) {
        if (hw == null) {
            return;
        }
        final EntityItem entity = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896, this.posZ, hw);
        entity.delayBeforeCanPickup = 40;
        float n = 0.1f;
        if (boolean2) {
            final float n2 = this.rand.nextFloat() * 0.5f;
            final float n3 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            entity.motionX = -MathHelper.sin(n3) * n2;
            entity.motionZ = MathHelper.cos(n3) * n2;
            entity.motionY = 0.20000000298023224;
        } else {
            n = 0.3f;
            entity.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
            entity.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
            entity.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * n + 0.1f;
            n = 0.02f;
            final float n2 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            n *= this.rand.nextFloat();
            final EntityItem entityItem = entity;
            entityItem.motionX += Math.cos((double) n2) * n;
            final EntityItem entityItem2 = entity;
            entityItem2.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            final EntityItem entityItem3 = entity;
            entityItem3.motionZ += Math.sin((double) n2) * n;
        }
        this.worldObj.entityJoinedWorld(entity);
    }

    public float getCurrentPlayerStrVsBlock(final Block gs) {
        float strVsBlock = this.inventory.getStrVsBlock(gs);
        if (this.isInsideOfMaterial(Material.water)) {
            strVsBlock /= 5.0f;
        }
        if (!this.onGround) {
            strVsBlock /= 5.0f;
        }
        return strVsBlock;
    }

    public boolean canHarvestBlock(final Block gs) {
        return this.inventory.canHarvestBlock(gs);
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
    }

    public void displayGUIChest(final IInventory kd) {
    }

    public void displayWorkbenchGUI() {
    }

    public void onItemPickup(final Entity eq) {
    }

    @Override
    protected float getEyeHeight() {
        return 0.12f;
    }

    @Override
    public boolean attackEntityFrom(final Entity entity, int nya1) {
        this.entityAge = 0;
        if (this.health <= 0) {
            return false;
        }
        if (this.heartsLife > this.heartsHalvesLife / 2.0f) {
            return false;
        }
        if (entity instanceof EntityMonster || entity instanceof EntityArrow) {
            if (this.worldObj.difficultySetting == 0) {
                nya1 = 0;
            }
            if (this.worldObj.difficultySetting == 1) {
                nya1 = nya1 / 3 + 1;
            }
            if (this.worldObj.difficultySetting == 3) {
                nya1 = nya1 * 3 / 2;
            }
        }
        final int n = nya1 * (25 - this.inventory.getTotalArmorValue()) + this.swingProgressInt;
        this.inventory.damageArmor(nya1);
        nya1 = n / 25;
        this.swingProgressInt = n % 25;
        return nya1 != 0 && super.attackEntityFrom(entity, nya1);
    }

    public void displayGUIFurnace(final TileEntityFurnace el) {
    }

    public void displayGUIEditSign(final TileEntitySign jn) {
    }

    public void c(final Entity eq) {
    }
}
