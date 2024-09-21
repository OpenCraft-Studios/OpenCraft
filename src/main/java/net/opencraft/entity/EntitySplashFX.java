
package net.opencraft.entity;

import net.opencraft.world.World;

public class EntitySplashFX extends EntityRainFX {

    public EntitySplashFX(final World fe, final double double2, final double double3, final double double4, final double double5, final double double6, final double double7) {
        super(fe, double2, double3, double4);
        this.particleGravity = 0.04f;
        ++this.particleTextureIndex;
        if (double6 == 0.0 && (double5 != 0.0 || double7 != 0.0)) {
            this.motionX = double5;
            this.motionY = double6 + 0.1;
            this.motionZ = double7;
        }
    }
}
