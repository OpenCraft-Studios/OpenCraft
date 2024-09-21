
package net.opencraft;

import java.util.Random;

public class an extends dh {

    private int[] d;
    public double a;
    public double b;
    public double c;

    public an() {
        this(new Random());
    }

    public an(final Random random) {
        this.d = new int[512];
        this.a = random.nextDouble() * 256.0;
        this.b = random.nextDouble() * 256.0;
        this.c = random.nextDouble() * 256.0;
        for (int i = 0; i < 256; ++i) {
            this.d[i] = i;
        }
        for (int i = 0; i < 256; ++i) {
            final int n = random.nextInt(256 - i) + i;
            final int n2 = this.d[i];
            this.d[i] = this.d[n];
            this.d[n] = n2;
            this.d[i + 256] = this.d[i];
        }
    }

    public double a(final double double1, final double double2, final double double3) {
        double n = double1 + this.a;
        double n2 = double2 + this.b;
        double n3 = double3 + this.c;
        int n4 = (int) n;
        int n5 = (int) n2;
        int n6 = (int) n3;
        if (n < n4) {
            --n4;
        }
        if (n2 < n5) {
            --n5;
        }
        if (n3 < n6) {
            --n6;
        }
        final int n7 = n4 & 0xFF;
        final int n8 = n5 & 0xFF;
        final int n9 = n6 & 0xFF;
        n -= n4;
        n2 -= n5;
        n3 -= n6;
        final double n10 = n * n * n * (n * (n * 6.0 - 15.0) + 10.0);
        final double n11 = n2 * n2 * n2 * (n2 * (n2 * 6.0 - 15.0) + 10.0);
        final double double4 = n3 * n3 * n3 * (n3 * (n3 * 6.0 - 15.0) + 10.0);
        final int n12 = this.d[n7] + n8;
        final int n13 = this.d[n12] + n9;
        final int n14 = this.d[n12 + 1] + n9;
        final int n15 = this.d[n7 + 1] + n8;
        final int n16 = this.d[n15] + n9;
        final int n17 = this.d[n15 + 1] + n9;
        return this.b(double4, this.b(n11, this.b(n10, this.a(this.d[n13], n, n2, n3), this.a(this.d[n16], n - 1.0, n2, n3)), this.b(n10, this.a(this.d[n14], n, n2 - 1.0, n3), this.a(this.d[n17], n - 1.0, n2 - 1.0, n3))), this.b(n11, this.b(n10, this.a(this.d[n13 + 1], n, n2, n3 - 1.0), this.a(this.d[n16 + 1], n - 1.0, n2, n3 - 1.0)), this.b(n10, this.a(this.d[n14 + 1], n, n2 - 1.0, n3 - 1.0), this.a(this.d[n17 + 1], n - 1.0, n2 - 1.0, n3 - 1.0))));
    }

    public double b(final double double1, final double double2, final double double3) {
        return double2 + double1 * (double3 - double2);
    }

    public double a(final int integer, final double double2, final double double3, final double double4) {
        final int n = integer & 0xF;
        final double n2 = (n < 8) ? double2 : double3;
        final double n3 = (n < 4) ? double3 : ((n == 12 || n == 14) ? double2 : double4);
        return (((n & 0x1) == 0x0) ? n2 : (-n2)) + (((n & 0x2) == 0x0) ? n3 : (-n3));
    }

    public double a(final double double1, final double double2) {
        return this.a(double1, double2, 0.0);
    }

    public double c(final double double1, final double double2, final double double3) {
        return this.a(double1, double2, double3);
    }

    public void a(final double[] arr, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6, final int integer7, final double double8, final double double9, final double double10, final double double11) {
        int n = 0;
        final double n2 = 1.0 / double11;
        int n3 = -1;
        double b = 0.0;
        double b2 = 0.0;
        double b3 = 0.0;
        double b4 = 0.0;
        for (int i = 0; i < integer5; ++i) {
            double n4 = (integer2 + i) * double8 + this.a;
            int n5 = (int) n4;
            if (n4 < n5) {
                --n5;
            }
            final int n6 = n5 & 0xFF;
            n4 -= n5;
            final double n7 = n4 * n4 * n4 * (n4 * (n4 * 6.0 - 15.0) + 10.0);
            for (int j = 0; j < integer7; ++j) {
                double n8 = (integer4 + j) * double10 + this.c;
                int n9 = (int) n8;
                if (n8 < n9) {
                    --n9;
                }
                final int n10 = n9 & 0xFF;
                n8 -= n9;
                final double double12 = n8 * n8 * n8 * (n8 * (n8 * 6.0 - 15.0) + 10.0);
                for (int k = 0; k < integer6; ++k) {
                    double n11 = (integer3 + k) * double9 + this.b;
                    int n12 = (int) n11;
                    if (n11 < n12) {
                        --n12;
                    }
                    final int n13 = n12 & 0xFF;
                    n11 -= n12;
                    final double n14 = n11 * n11 * n11 * (n11 * (n11 * 6.0 - 15.0) + 10.0);
                    if (k == 0 || n13 != n3) {
                        n3 = n13;
                        final int n15 = this.d[n6] + n13;
                        final int n16 = this.d[n15] + n10;
                        final int n17 = this.d[n15 + 1] + n10;
                        final int n18 = this.d[n6 + 1] + n13;
                        final int n19 = this.d[n18] + n10;
                        final int n20 = this.d[n18 + 1] + n10;
                        b = this.b(n7, this.a(this.d[n16], n4, n11, n8), this.a(this.d[n19], n4 - 1.0, n11, n8));
                        b2 = this.b(n7, this.a(this.d[n17], n4, n11 - 1.0, n8), this.a(this.d[n20], n4 - 1.0, n11 - 1.0, n8));
                        b3 = this.b(n7, this.a(this.d[n16 + 1], n4, n11, n8 - 1.0), this.a(this.d[n19 + 1], n4 - 1.0, n11, n8 - 1.0));
                        b4 = this.b(n7, this.a(this.d[n17 + 1], n4, n11 - 1.0, n8 - 1.0), this.a(this.d[n20 + 1], n4 - 1.0, n11 - 1.0, n8 - 1.0));
                    }
                    final double b5 = this.b(double12, this.b(n14, b, b2), this.b(n14, b3, b4));
                    final int n21 = n++;
                    arr[n21] += b5 * n2;
                }
            }
        }
    }
}
