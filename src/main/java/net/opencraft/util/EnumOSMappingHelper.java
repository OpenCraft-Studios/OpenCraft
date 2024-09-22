
package net.opencraft.util;

public class EnumOSMappingHelper {

    public static final /* synthetic */ int[] enumOSMappingArray;

    static {
        enumOSMappingArray = new int[EnumOS2.values().length];
        try {
            EnumOSMappingHelper.enumOSMappingArray[EnumOS2.linux.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOSMappingHelper.enumOSMappingArray[EnumOS2.solaris.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOSMappingHelper.enumOSMappingArray[EnumOS2.windows.ordinal()] = 3;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOSMappingHelper.enumOSMappingArray[EnumOS2.macos.ordinal()] = 4;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
