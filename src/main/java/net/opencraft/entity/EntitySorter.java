
package net.opencraft.entity;

import java.util.Comparator;
import net.opencraft.world.WorldRenderer;

public class EntitySorter implements Comparator<WorldRenderer> {

	private Entity a;

	public EntitySorter(final Entity eq) {
		this.a = eq;
	}

	@Override
	public int compare(final WorldRenderer dl1, final WorldRenderer dl2) {
	    float chunkIndex1 = dl1.chunkIndex(this.a);
	    float chunkIndex2 = dl2.chunkIndex(this.a);

	    return Float.compare(chunkIndex1, chunkIndex2);
	}


}
