package net.opencraft.client.config.option;

public abstract class NumberOption<E> extends Option<E> {

	/**
	 * @return -1 if number is less than zero. 0 if the number is zero, 1 if the
	 *         number is greater than zero
	 */
	public abstract int signum();

}
