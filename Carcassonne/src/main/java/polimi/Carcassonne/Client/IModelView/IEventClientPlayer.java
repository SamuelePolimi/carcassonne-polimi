package polimi.Carcassonne.Client.IModelView;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and view
 * 
 * implemented by PlayerTextWindow
 */
public interface IEventClientPlayer {
	/**
	 * points of player are changed
	 */
	public void pointsChanged();	
	/**
	 * number of marker changed
	 */
	public void markerChanged();
	/**
	 * player being current player
	 */
	public void beingCurrentPlayer();
	/**
	 * player from current player state being "normal" player
	 */
	public void beingNotCurrentPlayer();
}
