package polimi.Carcassonne.Client.Model;
import java.awt.Color;
import java.io.Serializable;

import polimi.Carcassonne.Client.IModelController.IModelControllerSide;
import polimi.Carcassonne.Client.IModelView.IModelViewSide;
import polimi.Carcassonne.Server.Model.Graph.Type;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class represent a side of a card
 */
public class ClientCardSide implements IModelViewSide,Serializable, IModelControllerSide {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3559594150631391902L;

	private Color marker;
	private Type type;
	private boolean connected;
	/**
	 * Constructor of client card side
	 * @param type: type to set
	 */
	public ClientCardSide(Type type){
		this.type=type;
		connected=true;
		marker=null;
	}
	/**
	 * @return type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @return connected: true if is connected
	 */
	public boolean isConnected() {
		return connected;
	}
	/**
	 * @return marker
	 */
	public Color getPlayer() {
		return marker;
	}
	/**
	 * Set connected
	 */
	public void setConnected() {
		connected=true;
	}
	/**
	 * Set disconnected
	 */
	public void setNotConnected() {
		connected=false;
	}
	/**
	 * Set marker of the player
	 * @param player: color of the player
	 */
	public void setPlayer(Color player) {
		this.marker=player;
	}


}
