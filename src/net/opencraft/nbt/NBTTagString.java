
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

    public String stringValue;

    public NBTTagString() {
    }

    public NBTTagString(final String string) {
        this.stringValue = string;
        if (string == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.stringValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.stringValue = dataInput.readUTF();
    }

    @Override
    public byte getType() {
        return 8;
    }

    public String toString() {
        return "" + this.stringValue;
    }
}
