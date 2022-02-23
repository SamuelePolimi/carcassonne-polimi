package polimi.Carcassonne.Client.IModelView;
import java.awt.Color;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and view
 * 
 * implemented ClientPlayer
 */
public interface IModelViewPlayer {
	/**
	 * @return points
	 */
	public int getPoints();
	/**
	 * @return color: color of the player
	 */
	public Color getColor();
	/**
	 * @return n: number of marker
	 */
	public int getMarkerNum();
	/**
	 * @param event
	 */
	public void setEvent(IEventClientPlayer e);
	/**
	 * @return true if you are the current player
	 */
	public boolean isCurrent();
}
