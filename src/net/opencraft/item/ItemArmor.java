
package net.opencraft.item;

public class ItemArmor extends Item {

    private static final int[] damageReduceAmountArray;
    private static final int[] maxDamageArray;
    public final int armorLevel;
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;

    public ItemArmor(final int itemid, final int integer2, final int renderIndex, final int armorType) {
        super(itemid);
        this.armorLevel = integer2;
        this.armorType = armorType;
        this.renderIndex = renderIndex;
        this.damageReduceAmount = ItemArmor.damageReduceAmountArray[armorType];
        this.maxDamage = ItemArmor.maxDamageArray[armorType] * 3 << integer2;
        this.maxStackSize = 1;
    }

    static {
        damageReduceAmountArray = new int[]{3, 8, 6, 3};
        maxDamageArray = new int[]{11, 16, 15, 13};
    }
}
