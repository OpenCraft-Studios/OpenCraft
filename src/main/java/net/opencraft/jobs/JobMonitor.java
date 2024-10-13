package net.opencraft.jobs;

public interface JobMonitor {

	boolean isFinished();

	boolean isCancelled();

	boolean endedWithErrors();

}
