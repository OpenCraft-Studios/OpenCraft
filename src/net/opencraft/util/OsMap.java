
package net.opencraft.util;

public class OsMap {

    public static final int[] field_1193_a;

    static {
        field_1193_a = new int[EnumOS1.values().length];
        try {
            OsMap.field_1193_a[EnumOS1.linux.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OsMap.field_1193_a[EnumOS1.solaris.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OsMap.field_1193_a[EnumOS1.windows.ordinal()] = 3;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OsMap.field_1193_a[EnumOS1.macos.ordinal()] = 4;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
