
package net.opencraft.item;

import java.util.Random;
import net.opencraft.block.Block;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityLiving;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.world.World;

public class Item {

    protected static Random itemRand;
    public static Item[] itemsList;
    public static Item shovelSteel;
    public static Item pickaxeSteel;
    public static Item axeSteel;
    public static Item flintAndSteel;
    public static Item appleRed;
    public static Item bow;
    public static Item arrow;
    public static Item coal;
    public static Item diamond;
    public static Item ingotIron;
    public static Item ingotGold;
    public static Item swordSteel;
    public static Item swordWood;
    public static Item shovelWood;
    public static Item pickaxeWood;
    public static Item axeWood;
    public static Item swordStone;
    public static Item shovelStone;
    public static Item pickaxeStone;
    public static Item axeStone;
    public static Item swordDiamond;
    public static Item shovelDiamond;
    public static Item pickaxeDiamond;
    public static Item axeDiamond;
    public static Item stick;
    public static Item bowlEmpty;
    public static Item bowlSoup;
    public static Item swordGold;
    public static Item shovelGold;
    public static Item pickaxeGold;
    public static Item axeGold;
    public static Item silk;
    public static Item feather;
    public static Item gunpowder;
    public static Item hoeWood;
    public static Item hoeStone;
    public static Item hoeSteel;
    public static Item hoeDiamond;
    public static Item hoeGold;
    public static Item seeds;
    public static Item wheat;
    public static Item bread;
    public static Item helmetLeather;
    public static Item plateLeather;
    public static Item legsLeather;
    public static Item bootsLeather;
    public static Item helmetChain;
    public static Item plateChain;
    public static Item legsChain;
    public static Item bootsChain;
    public static Item helmetSteel;
    public static Item plateSteel;
    public static Item legsSteel;
    public static Item bootsSteel;
    public static Item helmetDiamond;
    public static Item plateDiamonhd;
    public static Item legsDiamond;
    public static Item bootsDiamond;
    public static Item helmetGold;
    public static Item plateGold;
    public static Item legsGold;
    public static Item bootsGold;
    public static Item flint;
    public static Item porkRaw;
    public static Item porkCooked;
    public static Item painting;
    public static Item appleGold;
    public static Item sign;
    public static Item door;
    public static Item bucketEmpty;
    public static Item bucketWater;
    public static Item bucketLava;
    public static Item minecart;
    public static Item saddle;
    public final int shiftedIndex;
    protected int maxStackSize;
    protected int maxDamage;
    protected int iconIndex;

    protected Item(final int itemid) {
        this.maxStackSize = 64;
        this.maxDamage = 32;
        this.shiftedIndex = 256 + itemid;
        if (Item.itemsList[256 + itemid] != null) {
            System.out.println(new StringBuilder().append("CONFLICT @ ").append(itemid).toString());
        }
        Item.itemsList[256 + itemid] = this;
    }

    public Item setIconIndex(final int integer) {
        this.iconIndex = integer;
        return this;
    }

    public int getIconFromDamage(final ItemStack hw) {
        return this.iconIndex;
    }

    public boolean onItemUse(final ItemStack hw, final EntityPlayer gi, final World fe, final int xCoord, final int yCoord, final int zCoord, final int integer7) {
        return false;
    }

    public float getStrVsBlock(final ItemStack hw, final Block gs) {
        return 1.0f;
    }

    public ItemStack onItemRightClick(final ItemStack hw, final World fe, final EntityPlayer gi) {
        return hw;
    }

    public int getItemStackLimit() {
        return this.maxStackSize;
    }

    public int getMaxDamage() {
        return this.maxDamage;
    }

    public void hitEntity(final ItemStack hw, final EntityLiving ka) {
    }

    public void onBlockDestroyed(final ItemStack hw, final int integer2, final int integer3, final int integer4, final int integer5) {
    }

    public int getDamageVsEntity(final Entity eq) {
        return 1;
    }

    public boolean canHarvestBlock(final Block gs) {
        return false;
    }

    public void saddleEntity(final ItemStack hw, final EntityLiving ka) {
    }

    static {
        Item.itemRand = new Random();
        Item.itemsList = new Item[1024];
        Item.shovelSteel = new ItemSpade(0, 2).setIconIndex(82);
        Item.pickaxeSteel = new ItemPickaxe(1, 2).setIconIndex(98);
        Item.axeSteel = new ItemAxe(2, 2).setIconIndex(114);
        Item.flintAndSteel = new ItemFlintAndSteel(3).setIconIndex(5);
        Item.appleRed = new ItemFood(4, 4).setIconIndex(10);
        Item.bow = new ItemBow(5).setIconIndex(21);
        Item.arrow = new Item(6).setIconIndex(37);
        Item.coal = new Item(7).setIconIndex(7);
        Item.diamond = new Item(8).setIconIndex(55);
        Item.ingotIron = new Item(9).setIconIndex(23);
        Item.ingotGold = new Item(10).setIconIndex(39);
        Item.swordSteel = new ItemSword(11, 2).setIconIndex(66);
        Item.swordWood = new ItemSword(12, 0).setIconIndex(64);
        Item.shovelWood = new ItemSpade(13, 0).setIconIndex(80);
        Item.pickaxeWood = new ItemPickaxe(14, 0).setIconIndex(96);
        Item.axeWood = new ItemAxe(15, 0).setIconIndex(112);
        Item.swordStone = new ItemSword(16, 1).setIconIndex(65);
        Item.shovelStone = new ItemSpade(17, 1).setIconIndex(81);
        Item.pickaxeStone = new ItemPickaxe(18, 1).setIconIndex(97);
        Item.axeStone = new ItemAxe(19, 1).setIconIndex(113);
        Item.swordDiamond = new ItemSword(20, 3).setIconIndex(67);
        Item.shovelDiamond = new ItemSpade(21, 3).setIconIndex(83);
        Item.pickaxeDiamond = new ItemPickaxe(22, 3).setIconIndex(99);
        Item.axeDiamond = new ItemAxe(23, 3).setIconIndex(115);
        Item.stick = new Item(24).setIconIndex(53);
        Item.bowlEmpty = new Item(25).setIconIndex(71);
        Item.bowlSoup = new ItemSoup(26, 10).setIconIndex(72);
        Item.swordGold = new ItemSword(27, 0).setIconIndex(68);
        Item.shovelGold = new ItemSpade(28, 0).setIconIndex(84);
        Item.pickaxeGold = new ItemPickaxe(29, 0).setIconIndex(100);
        Item.axeGold = new ItemAxe(30, 0).setIconIndex(116);
        Item.silk = new Item(31).setIconIndex(8);
        Item.feather = new Item(32).setIconIndex(24);
        Item.gunpowder = new Item(33).setIconIndex(40);
        Item.hoeWood = new ItemHoe(34, 0).setIconIndex(128);
        Item.hoeStone = new ItemHoe(35, 1).setIconIndex(129);
        Item.hoeSteel = new ItemHoe(36, 2).setIconIndex(130);
        Item.hoeDiamond = new ItemHoe(37, 3).setIconIndex(131);
        Item.hoeGold = new ItemHoe(38, 4).setIconIndex(132);
        Item.seeds = new ItemSeeds(39, Block.crops.blockID).setIconIndex(9);
        Item.wheat = new Item(40).setIconIndex(25);
        Item.bread = new ItemFood(41, 5).setIconIndex(41);
        Item.helmetLeather = new ItemArmor(42, 0, 0, 0).setIconIndex(0);
        Item.plateLeather = new ItemArmor(43, 0, 0, 1).setIconIndex(16);
        Item.legsLeather = new ItemArmor(44, 0, 0, 2).setIconIndex(32);
        Item.bootsLeather = new ItemArmor(45, 0, 0, 3).setIconIndex(48);
        Item.helmetChain = new ItemArmor(46, 1, 1, 0).setIconIndex(1);
        Item.plateChain = new ItemArmor(47, 1, 1, 1).setIconIndex(17);
        Item.legsChain = new ItemArmor(48, 1, 1, 2).setIconIndex(33);
        Item.bootsChain = new ItemArmor(49, 1, 1, 3).setIconIndex(49);
        Item.helmetSteel = new ItemArmor(50, 2, 2, 0).setIconIndex(2);
        Item.plateSteel = new ItemArmor(51, 2, 2, 1).setIconIndex(18);
        Item.legsSteel = new ItemArmor(52, 2, 2, 2).setIconIndex(34);
        Item.bootsSteel = new ItemArmor(53, 2, 2, 3).setIconIndex(50);
        Item.helmetDiamond = new ItemArmor(54, 3, 3, 0).setIconIndex(3);
        Item.plateDiamonhd = new ItemArmor(55, 3, 3, 1).setIconIndex(19);
        Item.legsDiamond = new ItemArmor(56, 3, 3, 2).setIconIndex(35);
        Item.bootsDiamond = new ItemArmor(57, 3, 3, 3).setIconIndex(51);
        Item.helmetGold = new ItemArmor(58, 1, 4, 0).setIconIndex(4);
        Item.plateGold = new ItemArmor(59, 1, 4, 1).setIconIndex(20);
        Item.legsGold = new ItemArmor(60, 1, 4, 2).setIconIndex(36);
        Item.bootsGold = new ItemArmor(61, 1, 4, 3).setIconIndex(52);
        Item.flint = new Item(62).setIconIndex(6);
        Item.porkRaw = new ItemFood(63, 3).setIconIndex(87);
        Item.porkCooked = new ItemFood(64, 8).setIconIndex(88);
        Item.painting = new ItemPainting(65).setIconIndex(26);
        Item.appleGold = new ItemFood(66, 42).setIconIndex(11);
        Item.sign = new ItemSign(67).setIconIndex(42);
        Item.door = new ItemDoor(68).setIconIndex(43);
        Item.bucketEmpty = new ItemBucket(69, 0).setIconIndex(74);
        Item.bucketWater = new ItemBucket(70, Block.waterMoving.blockID).setIconIndex(75);
        Item.bucketLava = new ItemBucket(71, Block.lavaMoving.blockID).setIconIndex(76);
        Item.minecart = new ItemMinecart(72).setIconIndex(135);
        Item.saddle = new ItemSaddle(73).setIconIndex(104);
    }
}
