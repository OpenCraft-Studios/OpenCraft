
package net.opencraft.util;

public class OsMap {

    public static final int[] field_1193_a;

    static {
        field_1193_a = new int[Platform.values().length];
        try {
            OsMap.field_1193_a[Platform.LINUX.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OsMap.field_1193_a[Platform.SOLARIS.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OsMap.field_1193_a[Platform.WINDOWS.ordinal()] = 3;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OsMap.field_1193_a[Platform.MACOS.ordinal()] = 4;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
