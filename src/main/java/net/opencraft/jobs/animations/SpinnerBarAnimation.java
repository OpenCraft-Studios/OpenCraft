package net.opencraft.jobs.animations;

public class SpinnerBarAnimation {

	public static void animate(String msg, int interval) {
		String bars = "|/-\\";
		int index = (int) (System.currentTimeMillis() / interval % 4);
		System.out.print(msg + " " + bars.charAt((int) index) + "\r");
	}
	
	private SpinnerBarAnimation() {
	}
	
}
