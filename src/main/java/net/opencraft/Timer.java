package net.opencraft;

public class Timer {

    /**
     * How many full ticks have turned over since the last call to updateTimer(), capped at 10.
     */
    public int elapsedTicks;
    public float renderPartialTicks;
    public float tickDelta;
    public float tps;

    /**
     * The time reported by the system clock at the last sync, in milliseconds
     */
    private long lastSyncSysClock;
    private float msPerTick;

    public Timer(float tps)
    {
        this.msPerTick = 1000.0F / tps;
        this.tps = tps;
        this.lastSyncSysClock = OpenCraft.getSystemTime();
    }

    /**
     * Updates all fields of the Timer using the current time
     */
    public void updateTimer()
    {
        long i = OpenCraft.getSystemTime();
        this.tickDelta = (float)(i - this.lastSyncSysClock) / this.msPerTick;
        this.lastSyncSysClock = i;
        this.renderPartialTicks += this.tickDelta;
        this.elapsedTicks = (int)this.renderPartialTicks;
        this.renderPartialTicks -= (float)this.elapsedTicks;
    }
}
