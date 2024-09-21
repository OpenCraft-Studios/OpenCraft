
package net.opencraft;

public class NextTickListEntry implements Comparable<NextTickListEntry> {

    private static long nextTickEntryID;
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public int blockID;
    public long scheduledTime;
    private final long tickEntryID;

    static {
        NextTickListEntry.nextTickEntryID = 0L;
    }

    public NextTickListEntry(final int integer1, final int integer2, final int integer3, final int integer4) {
        this.tickEntryID = NextTickListEntry.nextTickEntryID++;
        this.xCoord = integer1;
        this.yCoord = integer2;
        this.zCoord = integer3;
        this.blockID = integer4;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof NextTickListEntry) {
            final NextTickListEntry nextTickListEntry = (NextTickListEntry) object;
            return this.xCoord == nextTickListEntry.xCoord && this.yCoord == nextTickListEntry.yCoord && this.zCoord == nextTickListEntry.zCoord && this.blockID == nextTickListEntry.blockID;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.xCoord * 128 * 1024 + this.zCoord * 128 + this.yCoord) * 256 + this.blockID;
    }

    public NextTickListEntry setScheduledTime(final long long1) {
        this.scheduledTime = long1;
        return this;
    }

    @Override
    public int compareTo(final NextTickListEntry cz) {
        if (this.scheduledTime < cz.scheduledTime) {
            return -1;
        }
        if (this.scheduledTime > cz.scheduledTime) {
            return 1;
        }
        if (this.tickEntryID < cz.tickEntryID) {
            return -1;
        }
        if (this.tickEntryID > cz.tickEntryID) {
            return 1;
        }
        return 0;
    }

}
