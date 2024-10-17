package net.opencraft.util;

public class Friction {

	public static final int MULTIPLY = 1, DIVIDE = 2, PERSEC = 3;
	
	public static float applyFriction(int mode, float value, float friction) {
		return switch (mode) {
			case MULTIPLY -> value * friction;
			case DIVIDE   -> value / friction;
			case PERSEC   -> value * (friction / 1000);
			default -> throw new IllegalStateException("invalid friction mode");
		};
	}
	
}
