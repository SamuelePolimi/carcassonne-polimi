package polimi.Carcassonne.Client.IModelView;
import java.awt.Color;
import polimi.Carcassonne.Server.Model.Graph.Type;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and view
 * 
 * implemented by ClientCardSide
 */
public interface IModelViewSide {
	/**
	 * @return type: type of the side
	 */
	public Type getType();
	/**
	 * @return true if is connected
	 */
	public boolean isConnected();
	/**
	 * @return player
	 */
	public Color getPlayer();
}
