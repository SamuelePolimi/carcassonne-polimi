package polimi.Carcassonne.Client.View.GUIView;
/**
 * Input event
 * @author Omar - Scotti - Samuele Tosatto
 */
public interface IEventInput {
	/**
	 * Event that represent the clicking of a empty card
	 * @param x
	 * @param y
	 */
	public void selected(int x, int y);
}
