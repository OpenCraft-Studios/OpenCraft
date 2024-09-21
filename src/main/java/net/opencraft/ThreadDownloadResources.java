
package net.opencraft;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ThreadDownloadResources extends Thread {

    public File resourcesFolder;
    private final OpenCraft mc;
    private boolean closing;

    public ThreadDownloadResources(final File file, final OpenCraft aw) {
        this.closing = false;
        this.mc = aw;
        this.setName("Resource download thread");
        this.setDaemon(true);
        this.resourcesFolder = new File(file, "resources/");
        if (!this.resourcesFolder.exists() && !this.resourcesFolder.mkdirs()) {
            throw new RuntimeException(new StringBuilder().append("The working directory could not be created: ").append(this.resourcesFolder).toString());
        }
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
            this.loadResource(this.resourcesFolder, "");
            System.err.println("Cannot download resources. Using local resources instead. Error: " + ex);
        }
    }

    private void loadResource(final File file, final String string) {
        final File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; ++i) {
            if (listFiles[i].isDirectory()) {
                this.loadResource(listFiles[i], string + listFiles[i].getName() + "/");
            } else {
                this.mc.installResource(string + listFiles[i].getName(), listFiles[i]);
            }
        }
    }

    private void downloadAndInstallResource(final URL uRL, final String string) {
        try {
            final String[] split = string.split(",");
            final String string2 = split[0];
            final int int1 = Integer.parseInt(split[1]);
            long long1 = Long.parseLong(split[2]);
            long1 /= 2L;
            final File file = new File(this.resourcesFolder, string2);
            if (!file.exists() || file.length() != int1) {
                file.getParentFile().mkdirs();
                this.downloadResource(new URL(uRL, string2.replaceAll(" ", "%20")), file, int1);
                if (this.closing) {
                    return;
                }
            }
            this.mc.installResource(string2, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void downloadResource(final URL uRL, final File file, final int integer) throws IOException {
        final byte[] array = new byte[4096];
        final DataOutputStream dataOutputStream;
        try (DataInputStream dataInputStream = new DataInputStream(uRL.openStream())) {
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            int read;
            while ((read = dataInputStream.read(array)) >= 0) {
                dataOutputStream.write(array, 0, read);
                if (this.closing) {
                    return;
                }
            }
        }
        dataOutputStream.close();
    }

    public void closeMinecraft() {
        this.closing = true;
    }
}
