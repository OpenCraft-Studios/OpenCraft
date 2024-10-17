
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTTagList extends NBTBase {

	private final List<NBTBase> tagList = new ArrayList<>();
	private byte tagType;

	@Override
	public void write(DataOutput dos) throws IOException {
		tagType = tagList.isEmpty() ? 1 : tagList.get(0).getType();

		dos.writeByte(tagType);
		dos.writeInt(tagCount());
		for (NBTBase nbt : tagList)
			nbt.write(dos);
	}

	@Override
	public void read(final DataInput dataInput) throws IOException {
		tagType = dataInput.readByte();
		int listLen = dataInput.readInt();
		tagList.clear();
		for (int i = 0; i < listLen; ++i) {
			NBTBase nbt = NBTBase.createTagOfType(this.tagType);
			nbt.read(dataInput);
			tagList.add(nbt);
		}
	}

	@Override
	public byte getType() {
		return 9;
	}

	public String toString() {
		return "%d entries of type %s".formatted(tagCount(), NBTBase.getTagName(this.tagType));
	}

	public void setTag(NBTBase nbt) {
		this.tagType = nbt.getType();
		this.tagList.add(nbt);
	}

	public NBTBase getTag(int index) {
		return tagList.get(index);
	}

	public int tagCount() {
		return this.tagList.size();
	}

}
