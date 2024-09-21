
package net.opencraft;

import net.opencraft.block.material.Material;
import net.opencraft.client.gui.IProgressUpdate;
import net.opencraft.entity.Entity;
import net.opencraft.entity.EntityLiving;
import net.opencraft.util.MathHelper;
import net.opencraft.world.World;
import net.opencraft.world.chunk.ChunkPosition;

public class ck {

    private final int a;
    private final Class b;
    private final Class[] c;

    public ck(final int integer, final Class class2, final Class[] arr) {
        this.a = integer;
        this.b = class2;
        this.c = arr;
    }

    public void a(final World fe) {
        if (fe.countEntities(this.b) < this.a) {
            for (int i = 0; i < 10; ++i) {
                this.a(fe, 1, fe.player, null);
            }
        }
    }

    protected ChunkPosition a(final World fe, final int integer2, final int integer3) {
        return new ChunkPosition(integer2 + fe.rand.nextInt(256) - 128, fe.rand.nextInt(128), integer3 + fe.rand.nextInt(256) - 128);
    }

    private int a(final World fe, final int integer, final Entity eq, final IProgressUpdate jd) {
        int n = 0;
        final int floor_double = MathHelper.floor_double(eq.posX);
        final int floor_double2 = MathHelper.floor_double(eq.posZ);
        final int nextInt = fe.rand.nextInt(this.c.length);
        final ChunkPosition a = this.a(fe, floor_double, floor_double2);
        final int x = a.x;
        final int y = a.y;
        final int z = a.z;
        if (fe.isBlockNormalCube(x, y, z)) {
            return 0;
        }
        if (fe.getBlockMaterial(x, y, z) != Material.AIR) {
            return 0;
        }
        for (int i = 0; i < 3; ++i) {
            int n2 = x;
            int n3 = y;
            int n4 = z;
            final int n5 = 6;
            for (int j = 0; j < 3; ++j) {
                n2 += fe.rand.nextInt(n5) - fe.rand.nextInt(n5);
                n3 += fe.rand.nextInt(1) - fe.rand.nextInt(1);
                n4 += fe.rand.nextInt(n5) - fe.rand.nextInt(n5);
                if (fe.isBlockNormalCube(n2, n3 - 1, n4) && !fe.isBlockNormalCube(n2, n3, n4) && !fe.getBlockMaterial(n2, n3, n4).isLiquid() && !fe.isBlockNormalCube(n2, n3 + 1, n4)) {
                    final float n6 = n2 + 0.5f;
                    final float n7 = n3 + 1.0f;
                    final float n8 = n4 + 0.5f;
                    if (eq != null) {
                        final double n9 = n6 - eq.posX;
                        final double n10 = n7 - eq.posY;
                        final double n11 = n8 - eq.posZ;
                        if (n9 * n9 + n10 * n10 + n11 * n11 < 1024.0) {
                            continue;
                        }
                    } else {
                        final float n12 = n6 - fe.x;
                        final float n13 = n7 - fe.y;
                        final float n14 = n8 - fe.z;
                        if (n12 * n12 + n13 * n13 + n14 * n14 < 1024.0f) {
                            continue;
                        }
                    }
                    EntityLiving entity;
                    try {
                        entity = (EntityLiving) this.c[nextInt].getConstructor(new Class[]{World.class}).newInstance(new Object[]{fe});
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return n;
                    }
                    entity.setPositionAndRotation(n6, n7, n8, fe.rand.nextFloat() * 360.0f, 0.0f);
                    if (entity.getCanSpawnHere(n6, n7, n8)) {
                        ++n;
                        fe.entityJoinedWorld(entity);
                    }
                }
            }
        }
        return n;
    }
}
