package net.opencraft.tests;

public interface JobMonitor {

	boolean isFinished();
	boolean isCancelled();
	boolean endedWithErrors();
	
}
