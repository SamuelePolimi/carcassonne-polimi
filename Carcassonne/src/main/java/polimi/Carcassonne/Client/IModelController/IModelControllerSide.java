package polimi.Carcassonne.Client.IModelController;
import java.awt.Color;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and control
 * 
 * implemented by ClientCardSide
 */
public interface IModelControllerSide {
	/**
	 * set the side as connected
	 */
	public void setConnected();
	/**
	 * set a "marker" on the side
	 * @param player: player's marker
	 */
	public void setPlayer(Color player);
}
