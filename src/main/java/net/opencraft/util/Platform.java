
package net.opencraft.util;

public enum Platform {
	LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN;

	private static Platform os = null;
	
	public static Platform getOs() {
		if (Platform.os == null)
			return os = getOs0();
		
		return os;
	}
	
	private static Platform getOs0() {
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
