package net.opencraft.tests;

import static net.opencraft.OpenCraft.*;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import net.opencraft.util.ThreadHelper;

public class DownloadResourcesJob implements Job {

	private Thread thread;
	private boolean cancelled = false;
	private boolean errors = false;
	private boolean closing = false;
	private File resourcesFolder;

	public DownloadResourcesJob(File file) {
		this.resourcesFolder = new File(file, "resources/");
	}

	@Override
	public void run() {
		try {
			final ArrayList list = new ArrayList();
			final URL url = new URL("https://opencraft.nicolastech.xyz/resources/");
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					list.add(line);
				}
			}
			for (int i = 0; i < list.size(); ++i) {
				this.downloadAndInstallResource(url, (String) list.get(i));
				if (this.closing) {
					return;
				}
			}
		} catch (IOException ex) {
			exception(ex);
		}
	}

	public void exception(Exception ex) {
		this.loadResource(this.resourcesFolder, "");
		System.err.println("Cannot download resources. Using local resources instead. Error: " + ex);
		errors = true;
	}

	private void loadResource(final File file, final String string) {
		final File[] listFiles = file.listFiles();
		for (int i = 0; i < listFiles.length; ++i) {
			if (listFiles[i].isDirectory()) {
				this.loadResource(listFiles[i], string + listFiles[i].getName() + "/");
			} else {
				oc.installResource(string + listFiles[i].getName(), listFiles[i]);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void downloadAndInstallResource(final URL uRL, final String string) {
		try {
			final String[] split = string.split(",");
			final String string2 = split[0];
			final int int1 = Integer.parseInt(split[1]);
			final File file = new File(this.resourcesFolder, string2);
			if (!file.exists() || file.length() != int1) {
				file.getParentFile().mkdirs();
				
				this.downloadResource(new URL(uRL, string2.replaceAll(" ", "%20")), file, int1);
				if (this.closing) 
					return;
			}
			oc.installResource(string2, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void downloadResource(final URL uRL, final File file, final int integer) throws IOException {
		final byte[] array = new byte[4096];
		final DataOutputStream dos;
		try (DataInputStream dataInputStream = new DataInputStream(uRL.openStream())) {
			dos = new DataOutputStream(new FileOutputStream(file));
			int read;
			while ((read = dataInputStream.read(array)) != -1) {
				dos.write(array, 0, read);
			}
		}
		dos.close();
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

}
