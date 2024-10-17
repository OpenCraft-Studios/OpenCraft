
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase {

	public byte byteValue;

	public NBTTagByte() {
	}

	public NBTTagByte(final byte byte1) {
		this.byteValue = byte1;
	}

	@Override
	public void write(final DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(byteValue);
	}

	@Override
	public void read(final DataInput dataInput) throws IOException {
		this.byteValue = dataInput.readByte();
	}

	@Override
	public byte getType() {
		return 1;
	}

	public String toString() {
		return "" + byteValue;
	}

}
