package net.opencraft.tests;

import static net.opencraft.OpenCraft.*;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.opencraft.util.ThreadHelper;

public class DownloadResourcesJob implements Job {

	public static final String SOUNDS_PATH = "assets/opencraft/resources/";

	private Thread thread;
	private boolean cancelled = false;
	private boolean errors = false;

	@Override
	public void run() {
		loadResource(getClass().getProtectionDomain().getCodeSource().getLocation());
	}

	/**
	 * This is a recursive function that is building resourceURL paths, but somehow those paths are important to
	 * the sound registration. TODO: Investigate why this is important.
	 */
	private void loadResource(final URL resourceURL) {
		ZipInputStream zip = null;
		try {
			System.out.println(resourceURL);
			zip = new ZipInputStream(resourceURL.openStream());
			while(true) {
				ZipEntry e = zip.getNextEntry();
				if (e == null) {
					break;
				}
				String name = e.getName();
				if(name.startsWith(SOUNDS_PATH) && !e.isDirectory()) {
					oc.registerSound(name.substring(SOUNDS_PATH.length()), getClass().getClassLoader().getResource(name));
				}
			}
			zip.close();
		} catch(IOException e) {
			e.printStackTrace();
			errors = true;
		}
	}

	@Override
	public JobMonitor monitor() {
		return new JobMonitor() {
			public boolean isFinished() {
				return !thread.isAlive();
			}
			public boolean isCancelled() {
				return cancelled;
			}
			public boolean endedWithErrors() {
				return errors;
			}
		};
	}

	@Override
	public void cancel() {
		stop();
		this.cancelled = true;
	}

	@Override
	public void start() {
		this.thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		ThreadHelper.stopThread(thread);
	}

	@Override
	public void exception(Exception ex) {

	}

}
