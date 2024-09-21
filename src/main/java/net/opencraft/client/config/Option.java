package net.opencraft.client.config;

import java.util.function.Supplier;

public abstract class Option<E> implements Supplier<E> {

	public abstract void set(E value);
	
}
