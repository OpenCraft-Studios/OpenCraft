package net.opencraft;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;

public class GZTools {

	private GZTools() {
	}

	public static NBTTagCompound loadGZTagFile(File f) throws IOException {
		return loadGZTagOS(new FileInputStream(f));
	}
	
	public static NBTTagCompound loadGZTagOS(InputStream inputStream) throws IOException {
		try(DataInputStream dataInput = new DataInputStream(new GZIPInputStream(inputStream))) {
			return read(dataInput);
		}
	}

	public static void writeGZTagOS(final NBTTagCompound ae, final OutputStream outputStream) throws IOException {
		try(DataOutputStream dataOutput = new DataOutputStream(new GZIPOutputStream(outputStream))) {
			write(ae, dataOutput);
		}
	}

	public static NBTTagCompound read(final DataInput dataInput) throws IOException {
		final NBTBase tag = NBTBase.readTag(dataInput);
		if (tag instanceof NBTTagCompound) {
			return (NBTTagCompound) tag;
		}
		throw new IOException("Root tag must be a named compound tag");
	}

	public static void write(final NBTTagCompound ae, final DataOutput dataOutput) {
		NBTBase.writeTag(ae, dataOutput);
	}

}
