
package net.opencraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTTagCompound extends NBTBase {

    private Map tagMap;

    public NBTTagCompound() {
        this.tagMap = (Map) new HashMap();
    }

    @Override
    public void writeTagContents(final DataOutput dataOutput) throws IOException {
        final Iterator iterator = this.tagMap.values().iterator();
        while (iterator.hasNext()) {
            NBTBase.writeTag((NBTBase) iterator.next(), dataOutput);
        }
        dataOutput.writeByte(0);
    }

    @Override
    public void readTagContents(final DataInput dataInput) throws IOException {
        this.tagMap.clear();
        NBTBase tag;
        while ((tag = NBTBase.readTag(dataInput)).getType() != 0) {
            this.tagMap.put(tag.getKey(), tag);
        }
    }

    @Override
    public byte getType() {
        return 10;
    }

    public void setTag(final String string, final NBTBase hm) {
        this.tagMap.put(string, hm.setKey(string));
    }

    public void setByte(final String string, final byte byte2) {
        this.tagMap.put(string, new NBTTagByte(byte2).setKey(string));
    }

    public void setShort(final String string, final short short2) {
        this.tagMap.put(string, new NBTTagShort(short2).setKey(string));
    }

    public void setInteger(final String string, final int integer) {
        this.tagMap.put(string, new NBTTagInt(integer).setKey(string));
    }

    public void setLong(final String string, final long long2) {
        this.tagMap.put(string, new NBTTagLong(long2).setKey(string));
    }

    public void setFloat(final String string, final float float2) {
        this.tagMap.put(string, new NBTTagFloat(float2).setKey(string));
    }

    public void setString(final String string1, final String string2) {
        this.tagMap.put(string1, new NBTTagString(string2).setKey(string1));
    }

    public void setByteArray(final String string, final byte[] arr) {
        this.tagMap.put(string, new NBTTagByteArray(arr).setKey(string));
    }

    public void setCompoundTag(final String string, final NBTTagCompound ae) {
        this.tagMap.put(string, ae.setKey(string));
    }

    public void setBoolean(final String string, final boolean boolean2) {
        this.setByte(string, (byte) (boolean2 ? 1 : 0));
    }

    public boolean hasKey(final String string) {
        return this.tagMap.containsKey(string);
    }

    public byte getByte(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0;
        }
        return ((NBTTagByte) this.tagMap.get(string)).byteValue;
    }

    public short getShort(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0;
        }
        return ((NBTTagShort) this.tagMap.get(string)).shortValue;
    }

    public int getInteger(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0;
        }
        return ((NBTTagInt) this.tagMap.get(string)).intValue;
    }

    public long getLong(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0L;
        }
        return ((NBTTagLong) this.tagMap.get(string)).longValue;
    }

    public float getFloat(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0.0f;
        }
        return ((NBTTagFloat) this.tagMap.get(string)).floatValue;
    }

    public String getString(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return "";
        }
        return ((NBTTagString) this.tagMap.get(string)).stringValue;
    }

    public byte[] getByteArray(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new byte[0];
        }
        return ((NBTTagByteArray) this.tagMap.get(string)).byteArray;
    }

    public NBTTagCompound getCompoundTag(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new NBTTagCompound();
        }
        return (NBTTagCompound) this.tagMap.get(string);
    }

    public NBTTagList getTagList(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new NBTTagList();
        }
        return (NBTTagList) this.tagMap.get(string);
    }

    public boolean getBoolean(final String string) {
        return this.getByte(string) != 0;
    }

    public String toString() {
        return new StringBuilder().append("").append(this.tagMap.size()).append(" entries").toString();
    }
}
