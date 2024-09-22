
package net.opencraft.util;

public class EnumOSMappingHelper {

    public static final /* synthetic */ int[] enumOSMappingArray;

    static {
        enumOSMappingArray = new int[Platform.values().length];
        try {
            EnumOSMappingHelper.enumOSMappingArray[Platform.LINUX.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOSMappingHelper.enumOSMappingArray[Platform.SOLARIS.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOSMappingHelper.enumOSMappingArray[Platform.WINDOWS.ordinal()] = 3;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOSMappingHelper.enumOSMappingArray[Platform.MACOS.ordinal()] = 4;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
