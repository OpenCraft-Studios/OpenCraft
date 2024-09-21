
package net.opencraft.world.chunk;

public class NibbleArray {

    public final byte[] data;

    public NibbleArray(final int integer) {
        this.data = new byte[integer >> 1];
    }

    public NibbleArray(final byte[] arr) {
        this.data = arr;
    }

    public int getNibble(final int integer1, final int integer2, final int integer3) {
        final int n = integer1 << 11 | integer3 << 7 | integer2;
        final int n2 = n >> 1;
        if ((n & 0x1) == 0x0) {
            return this.data[n2] & 0xF;
        }
        return this.data[n2] >> 4 & 0xF;
    }

    public void setNibble(final int integer1, final int integer2, final int integer3, final int integer4) {
        final int n = integer1 << 11 | integer3 << 7 | integer2;
        final int n2 = n >> 1;
        if ((n & 0x1) == 0x0) {
            this.data[n2] = (byte) ((this.data[n2] & 0xF0) | (integer4 & 0xF));
        } else {
            this.data[n2] = (byte) ((this.data[n2] & 0xF) | (integer4 & 0xF) << 4);
        }
    }

    public boolean isValid() {
        return this.data != null;
    }
}
