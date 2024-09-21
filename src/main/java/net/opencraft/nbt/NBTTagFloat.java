
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {

    public float floatValue;

    public NBTTagFloat() {
    }

    public NBTTagFloat(final float float1) {
        this.floatValue = float1;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.floatValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.floatValue = dataInput.readFloat();
    }

    @Override
    public byte getType() {
        return 5;
    }

    public String toString() {
        return new StringBuilder().append("").append(this.floatValue).toString();
    }
}
