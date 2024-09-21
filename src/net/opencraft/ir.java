
package net.opencraft;

public class ir extends Thread {

    public final /* synthetic */ OpenCraft a;

    public ir(final OpenCraft aw, final String string) {
        super(string);
        this.a = aw;
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        while (this.a.running) {
            try {
                Thread.sleep(2147483647L);
            } catch (InterruptedException ex) {
            }
        }
    }
}
