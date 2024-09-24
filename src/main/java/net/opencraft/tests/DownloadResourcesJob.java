package net.opencraft.tests;

import static net.opencraft.OpenCraft.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.opencraft.client.sound.SoundManager;
import net.opencraft.util.ThreadHelper;

public class DownloadResourcesJob implements Job {

	public static final String SOUNDS_PATH = "assets/opencraft/resources/";

	private Thread thread;
	private boolean cancelled = false;
	private boolean errors = false;

	@Override
	public void run() {
		System.out.print("Loading sounds...");
		int soundCount;
		try {
			soundCount = loadSounds();
			System.out.println("done!" + soundCount + " sounds loaded.");
		} catch(IOException e) {
			errors = true;
			e.printStackTrace();
		}
	}

	public int loadSounds() throws IOException {
		return loadSounds(getClass().getProtectionDomain().getCodeSource().getLocation());
	}

	/**
	 * Loads sounds from a URL
	 */
	public int loadSounds(final URL resourceURL) throws IOException {
		int count = 0;

		if(!resourceURL.getFile().contains("jar")) {
			File file = new File(resourceURL.getFile());
			if(file.isDirectory()) {
				File soundsDir = new File(file, SOUNDS_PATH);
				if(soundsDir.exists()) {
					List<File> filesToCheck = new ArrayList<>();
					filesToCheck.add(soundsDir);
					while(!filesToCheck.isEmpty()) {
						File f = filesToCheck.removeFirst();
						if(f.isDirectory()) {
							Collections.addAll(filesToCheck, f.listFiles());
						} else {
							oc.sndManager.registerSound(f.toURI().toURL());
							count++;
						}
					}
				}
			}
		} else {
			ZipInputStream zip = null;
			zip = new ZipInputStream(resourceURL.openStream());
			while(true) {
				ZipEntry e = zip.getNextEntry();
				if (e == null) {
					break;
				}
				String name = e.getName();
				if(name.startsWith(SOUNDS_PATH) && !e.isDirectory()) {
					oc.sndManager.registerSound(getClass().getClassLoader().getResource(name));
					count++;
				}
			}
			zip.close();
		}

		if(count == 0) {
			throw new IOException("No sounds found in resources!" + resourceURL);
		}
		return count;
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
