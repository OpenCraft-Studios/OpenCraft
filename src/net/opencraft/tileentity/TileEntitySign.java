
package net.opencraft.tileentity;

import net.opencraft.nbt.NBTTagCompound;

public class TileEntitySign extends TileEntity {

    public String[] signText;
    public int lineBeingEdited;

    public TileEntitySign() {
        this.signText = new String[]{"", "", "", ""};
        this.lineBeingEdited = -1;
    }

    @Override
    public void writeToNBT(final NBTTagCompound ae) {
        super.writeToNBT(ae);
        ae.setString("Text1", this.signText[0]);
        ae.setString("Text2", this.signText[1]);
        ae.setString("Text3", this.signText[2]);
        ae.setString("Text4", this.signText[3]);
    }

    @Override
    public void readFromNBT(final NBTTagCompound ae) {
        super.readFromNBT(ae);
        for (int i = 0; i < 4; ++i) {
            this.signText[i] = ae.getString(new StringBuilder().append("Text").append(i + 1).toString());
            if (this.signText[i].length() > 15) {
                this.signText[i] = this.signText[i].substring(0, 15);
            }
        }
    }
}
