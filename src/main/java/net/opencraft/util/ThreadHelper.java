package net.opencraft.util;

public final class ThreadHelper {

	@SuppressWarnings("removal")
	public static void stopThread(Thread thread) {
		try {
			thread.stop();
		} catch (Throwable e1) {
			try {
				thread.interrupt();
			} catch (Exception e2) {
				e1.printStackTrace();
				e2.printStackTrace();
			}
		}
	}
	
}
