
package net.opencraft.world.gen;

import java.util.Random;
import net.opencraft.an;
import net.opencraft.dh;

public class NoiseGeneratorOctaves extends dh {

    private an[] a;
    private int b;

    public NoiseGeneratorOctaves(final Random random, final int integer) {
        this.b = integer;
        this.a = new an[integer];
        for (int i = 0; i < integer; ++i) {
            this.a[i] = new an(random);
        }
    }

    public double a(final double double1, final double double2) {
        double n = 0.0;
        double n2 = 1.0;
        for (int i = 0; i < this.b; ++i) {
            n += this.a[i].a(double1 * n2, double2 * n2) / n2;
            n2 /= 2.0;
        }
        return n;
    }

    public double a(final double double1, final double double2, final double double3) {
        double n = 0.0;
        double n2 = 1.0;
        for (int i = 0; i < this.b; ++i) {
            n += this.a[i].c(double1 * n2, double2 * n2, double3 * n2) / n2;
            n2 /= 2.0;
        }
        return n;
    }

    public double[] a(double[] arr, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6, final int integer7, final double double8, final double double9, final double double10) {
        if (arr == null) {
            arr = new double[integer5 * integer6 * integer7];
        } else {
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = 0.0;
            }
        }
        double double11 = 1.0;
        for (int j = 0; j < this.b; ++j) {
            this.a[j].a(arr, integer2, integer3, integer4, integer5, integer6, integer7, double8 * double11, double9 * double11, double10 * double11, double11);
            double11 /= 2.0;
        }
        return arr;
    }
}
