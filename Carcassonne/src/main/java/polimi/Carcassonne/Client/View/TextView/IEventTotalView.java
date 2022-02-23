package polimi.Carcassonne.Client.View.TextView;
/**
 * Event of insert card, rotate card, insert marker, pass, wait and refresh
 * implemented by View
 * @author Omar Scotti - Samuele Tosatto
 */
public interface IEventTotalView {
	/**
	 * Insert or rotate
	 */
	public void toInsertOrRotate();
	/**
	 * Insert Marker or pass
	 */
	public void toInsertMarkerOrPass();
	/**
	 * Wait and refresh
	 */
	public void toWaitAndRefresh();
}
