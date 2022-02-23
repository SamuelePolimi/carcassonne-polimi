package polimi.Carcassonne.Client.IModelController;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and control
 * 
 * implemented by ClientPlayer
 */
public interface IModelControllerPlayer {
	/**
	 * sets the player's score
	 * @param n: number of points
	 */
	public void setPoints(int n);
	/**
	 * Sets the number of marker
	 * @param n: number of markers
	 */
	public void setMarkerNum(int n);
	/**
	 * Sets if this player is current or not
	 * @param current: if he is the current player
	 */
	public void setCurrent(boolean current);
}
