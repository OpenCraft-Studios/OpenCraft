
package net.opencraft.util;

public enum Platform {
	LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN;

	private static Platform os;

	public static Platform getOs() {
		if (os == null)
			os = getOs0();

		return os;
	}

	private static Platform getOs0() {
		final String osname = System.getProperty("os.name").toLowerCase();
		
		if (osname.contains("win"))
			return Platform.WINDOWS;
		if (osname.contains("mac"))
			return Platform.MACOS;
		if (osname.contains("solaris"))
			return Platform.SOLARIS;
		if (osname.contains("sunos"))
			return Platform.SOLARIS;
		if (osname.contains("linux"))
			return Platform.LINUX;
		if (osname.contains("unix"))
			return Platform.LINUX;

		return Platform.UNKNOWN;
	}

}
