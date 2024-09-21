
package net.opencraft;

import java.awt.image.BufferedImage;
import net.opencraft.world.World;

public class IsoImageBuffer {

    public BufferedImage field_1348_a;
    public World worldObj;
    public int chunkX;
    public int chunkZ;
    public boolean field_1352_e;
    public boolean field_1351_f;
    public int field_1350_g;
    public boolean field_1349_h;

    public IsoImageBuffer(final World fe, final int integer2, final int integer3) {
        this.field_1352_e = false;
        this.field_1351_f = false;
        this.field_1350_g = 0;
        this.field_1349_h = false;
        this.worldObj = fe;
        this.setChunkPosition(integer2, integer3);
    }

    public void setChunkPosition(final int integer1, final int integer2) {
        this.field_1352_e = false;
        this.chunkX = integer1;
        this.chunkZ = integer2;
        this.field_1350_g = 0;
        this.field_1349_h = false;
    }

    public void setWorldAndChunkPosition(final World fe, final int integer2, final int integer3) {
        this.worldObj = fe;
        this.setChunkPosition(integer2, integer3);
    }
}
