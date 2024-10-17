
package net.opencraft.renderer.gui;

public interface IProgressListener {

	void setStage(String stage);

	void setLoadingMessage(String msg);

	void setProgress(int progress);

}
