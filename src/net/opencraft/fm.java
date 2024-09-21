
package net.opencraft;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class fm extends Thread {

    public final /* synthetic */ String a;
    public final /* synthetic */ p b;
    public final /* synthetic */ ei c;

    public fm(final ei ei, final String string, final p p) {
        this.c = ei;
        this.a = string;
        this.b = p;
    }

    @Override
    public void run() {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(this.a).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 404) {
                return;
            }
            if (this.b == null) {
                this.c.a = ImageIO.read(httpURLConnection.getInputStream());
            } else {
                this.c.a = this.b.a(ImageIO.read(httpURLConnection.getInputStream()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
