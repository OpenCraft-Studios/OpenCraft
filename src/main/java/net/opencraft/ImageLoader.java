
package net.opencraft;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Loads an image from a URL.
 */
public class ImageLoader extends Thread {

    public final /* synthetic */ String targetURL;
    public final /* synthetic */ ImageProvider provider;
    public final /* synthetic */ TextureHolder textureHolderThing;

    public ImageLoader(final TextureHolder textureHolderThing, final String targetURL, final ImageProvider provider) {
        this.textureHolderThing = textureHolderThing;
        this.targetURL = targetURL;
        this.provider = provider;
    }

    @Override
    public void run() {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(targetURL).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 404) {
                return;
            }
            if (this.provider == null) {
                this.textureHolderThing.image = ImageIO.read(httpURLConnection.getInputStream());
            } else {
                this.textureHolderThing.image = this.provider.a(ImageIO.read(httpURLConnection.getInputStream()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
