
package net.opencraft.util;

public enum Platform {
	LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN;

	public static Platform getOs() {
		final String lowerCase = System.getProperty("os.name").toLowerCase();
		if (lowerCase.contains("win"))
			return Platform.WINDOWS;
		if (lowerCase.contains("mac"))
			return Platform.MACOS;
		if (lowerCase.contains("solaris"))
			return Platform.SOLARIS;
		if (lowerCase.contains("sunos"))
			return Platform.SOLARIS;
		if (lowerCase.contains("linux"))
			return Platform.LINUX;
		if (lowerCase.contains("unix"))
			return Platform.LINUX;

		return Platform.UNKNOWN;

	}
}
