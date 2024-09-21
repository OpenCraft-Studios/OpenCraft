package net.opencraft.tests;

public abstract class Job {

	public abstract boolean isCompleted();
	public abstract void markAsCompleted();
	
	public abstract boolean isCancelled();
	public abstract void cancel();
	
	public abstract boolean isFinished();
	public abstract void markAsFinished();
	
	public abstract boolean endedWithErrors();
	
	public abstract void run();
	
}
