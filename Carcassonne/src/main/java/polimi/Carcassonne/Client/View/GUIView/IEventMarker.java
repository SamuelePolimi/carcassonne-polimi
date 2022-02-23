package polimi.Carcassonne.Client.View.GUIView;
/**
 * Event used for pass or insert marker
 * @author Omar Scotti - Samuele Tosatto
 */
public interface IEventMarker {
	/**
	 * Events represent insertion of marker
	 * @param orientation
	 */
	public void insertMarker(int orientation);
	/**
	 * Events represent pass
	 */
	public void pass();
}
