package net.opencraft.tests;

public abstract class Job {

	public abstract boolean isCompleted();
	public abstract void markAsCompleted();
	
	public abstract boolean isCancelled();
	
	
}
