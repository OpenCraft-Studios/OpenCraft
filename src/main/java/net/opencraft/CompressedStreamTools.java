package net.opencraft;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.opencraft.nbt.NBTBase;
import net.opencraft.nbt.NBTTagCompound;

public class CompressedStreamTools {

    private CompressedStreamTools() {
    }

    public static NBTTagCompound loadGzippedCompoundFromOutputStream(final InputStream inputStream) throws IOException {
        try (DataInputStream dataInput = new DataInputStream(new GZIPInputStream(inputStream))) {
            return read(dataInput);
        }
    }

    public static void writeGzippedCompoundToOutputStream(final NBTTagCompound ae, final OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutput = new DataOutputStream(new GZIPOutputStream(outputStream))) {
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
