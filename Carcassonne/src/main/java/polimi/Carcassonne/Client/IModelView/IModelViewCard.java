package polimi.Carcassonne.Client.IModelView;
import java.rmi.Remote;

import polimi.Carcassonne.Client.Model.ClientCardSide;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and view
 * 
 * implemented by ClientCard
 */
public interface IModelViewCard extends Remote{
	/*
	 * theese methods get the type of each side. We don't need any other information about a card
	 * from View point.
	 */
	/**
	 * @return clientCardSide: north side
	 */
	public ClientCardSide getNorth();
	/**
	 * @return clientCardSide: south side
	 */
	public ClientCardSide getSouth();
	/**
	 * @return clientCardSide: east side
	 */
	public ClientCardSide getEast();
	/**
	 * @return clientCardSide: west side
	 */
	public ClientCardSide getWest();
	/**
	 * @return b: northSouth
	 */
	public boolean getNorthSouth();
	/**
	 * @return b: northEast
	 */
	public boolean getNorthEast();
	/**
	 * @return b: northWest
	 */
	public boolean getNorthWest();
	/**
	 * @return b: southEast
	 */
	public boolean getSouthEast();
	/**
	 * @return b: southWest
	 */
	public boolean getSouthWest();
	/**
	 * @return b: eastWest
	 */
	public boolean getEastWest();
	/**
	 * @param event
	 */
	public void setEventReciver(IEventClientCard event);
	/**
	 * True if the card is empty
	 * @return true or false
	 */
	public boolean isEmpty();
	/**
	 * @return coordinate
	 */
	public Coordinate getCoordinate();
	/**
	 * @return copy
	 */
	public IModelViewCard getCopy();
	/**
	 * Signal rotate
	 */
	public void rotate();
}
