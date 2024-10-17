
package net.opencraft.renderer.entity;

import java.util.Comparator;
import net.opencraft.entity.Player;
import net.opencraft.world.WorldRenderer;

public class RenderSorter implements Comparator<WorldRenderer> {

	private Player entity;

	public RenderSorter(final Player gi) {
		this.entity = gi;
	}

	public int compare(final WorldRenderer dl1, final WorldRenderer dl2) {
		final boolean isInFrustum = dl1.isInFrustum;
		final boolean isInFrustum2 = dl2.isInFrustum;
		if (isInFrustum && !isInFrustum2) {
			return 1;
		}
		if (isInFrustum2 && !isInFrustum) {
			return -1;
		}
		if (dl1.chunkIndex(this.entity) == dl2.chunkIndex(this.entity))
			return 0;
		return (dl1.chunkIndex(this.entity) < dl2.chunkIndex(this.entity)) ? 1 : -1;
	}

}
