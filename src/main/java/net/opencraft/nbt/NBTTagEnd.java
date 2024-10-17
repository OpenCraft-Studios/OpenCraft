
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {

	@Override
	public void read(final DataInput dataInput) throws IOException {
	}

	@Override
	public void write(final DataOutput dataOutput) throws IOException {
	}

	@Override
	public byte getType() {
		return 0;
	}

	public String toString() {
		return "END";
	}

}
