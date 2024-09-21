
package net.opencraft.util;

public class MathHelper {

    private static float[] SIN_TABLE;

    public static float sin(float value)
    {
        return SIN_TABLE[(int)(value * 10430.378F) & 65535];
    }

    public static final float cos(final float float1) {
        return MathHelper.SIN_TABLE[(int) (float1 * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static final float sqrt_float(final float float1) {
        return (float) Math.sqrt((double) float1);
    }

    public static final float sqrt_double(final double double1) {
        return (float) Math.sqrt(double1);
    }

    public static int floor_float(final float float1) {
        final int n = (int) float1;
        return (float1 < n) ? (n - 1) : n;
    }

    public static int floor_double(final double double1) {
        final int n = (int) double1;
        return (double1 < n) ? (n - 1) : n;
    }

    public static float abs(final float float1) {
        return (float1 >= 0.0f) ? float1 : (-float1);
    }

    public static double abs_max(double double1, double double2) {
        if (double1 < 0.0) {
            double1 = -double1;
        }
        if (double2 < 0.0) {
            double2 = -double2;
        }
        return (double1 > double2) ? double1 : double2;
    }

    public static int a(final int integer1, final int integer2) {
        if (integer1 < 0) {
            return -((-integer1 - 1) / integer2) - 1;
        }
        return integer1 / integer2;
    }

    static {
        MathHelper.SIN_TABLE = new float[65536];
        for (int i = 0; i < 65536; ++i) {
            MathHelper.SIN_TABLE[i] = (float) Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
    }

    public static float roundToDecimalPoints(float value, int decimalPoints) {
        return (float) (Math.round(value * Math.pow(10, decimalPoints)) / Math.pow(10, decimalPoints));
    }

    public static float roundToDecimalPlace(float value, float decimalPlace) {
        return (float) (Math.round(value * decimalPlace) / decimalPlace);
    }

    public static int clamp(int num, int min, int max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }

    public static float clamp(float num, float min, float max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }

    public static double clamp(double num, double min, double max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }

    public static int roundUp(int number, int interval) {
        if (interval == 0) {
            return 0;
        } else if (number == 0) {
            return interval;
        } else {
            if (number < 0) {
                interval *= -1;
            }

            int i = number % interval;
            return i == 0 ? number : number + interval - i;
        }
    }
}
