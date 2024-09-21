
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {

    public double doubleValue;

    public NBTTagDouble() {
    }

    public NBTTagDouble(final double double1) {
        this.doubleValue = double1;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.doubleValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.doubleValue = dataInput.readDouble();
    }

    @Override
    public byte getType() {
        return 6;
    }

    public String toString() {
        return new StringBuilder().append("").append(this.doubleValue).toString();
    }
}
