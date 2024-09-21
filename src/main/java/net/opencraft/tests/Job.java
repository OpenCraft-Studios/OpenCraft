package net.opencraft.tests;

public interface Job extends Runnable {

	JobMonitor monitor();
	
	void cancel();
	void start();
	void stop();
	
	void exception(Exception ex);
	void run();
	
}
