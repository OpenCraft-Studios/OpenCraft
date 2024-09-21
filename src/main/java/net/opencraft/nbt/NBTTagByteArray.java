
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByteArray extends NBTBase {

    public byte[] byteArray;

    public NBTTagByteArray() {
    }

    public NBTTagByteArray(final byte[] arr) {
        this.byteArray = arr;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.byteArray.length);
        dataOutput.write(this.byteArray);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        dataInput.readFully(this.byteArray = new byte[dataInput.readInt()]);
    }

    @Override
    public byte getType() {
        return 7;
    }

    public String toString() {
        return new StringBuilder().append("[").append(this.byteArray.length).append(" bytes]").toString();
    }
}
