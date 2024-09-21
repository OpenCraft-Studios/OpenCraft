
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase {

    public int intValue;

    public NBTTagInt() {
    }

    public NBTTagInt(final int integer) {
        this.intValue = integer;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.intValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.intValue = dataInput.readInt();
    }

    @Override
    public byte getType() {
        return 3;
    }

    public String toString() {
        return new StringBuilder().append("").append(this.intValue).toString();
    }
}
