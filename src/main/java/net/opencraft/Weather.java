package net.opencraft;

public enum Weather {

	CLEAR,
	RAIN;

	public boolean isClear() {
		return this == Weather.CLEAR;
	}

	public boolean isRaining() {
		return this == Weather.RAIN;
	}

}
