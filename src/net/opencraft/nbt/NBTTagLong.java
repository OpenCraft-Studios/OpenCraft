
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase {

    public long longValue;

    public NBTTagLong() {
    }

    public NBTTagLong(final long long1) {
        this.longValue = long1;
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.longValue);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.longValue = dataInput.readLong();
    }

    @Override
    public byte getType() {
        return 4;
    }

    public String toString() {
        return new StringBuilder().append("").append(this.longValue).toString();
    }
}
