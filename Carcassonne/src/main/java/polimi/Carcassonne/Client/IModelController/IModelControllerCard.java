package polimi.Carcassonne.Client.IModelController;
import java.rmi.Remote;

import polimi.Carcassonne.Client.Model.ClientCardSide;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and control
 * 
 * implemented by ClientCard
 */
public interface IModelControllerCard extends Remote{
	/**
	 * @param type: is used to set South side
	 */
	public void setSouth(ClientCardSide type);
	/**
	 * @param type: is used to set North side
	 */
	public void setNorth(ClientCardSide type);
	/**
	 * @param type: is used to set East side
	 */
	public void setEast(ClientCardSide type);
	/**
	 * @param type: is used to set West side
	 */
	public void setWest(ClientCardSide type);
	/*
	 * theese methods set internal connection
	 */
	/**
	 * @param b: true when theres connection
	 */
	public void setNorthSouth(boolean b);
	/**
	 * @param b: true when theres connection
	 */
	public void setNorthEast(boolean b);
	/**
	 * @param b: true when theres connection
	 */
	public void setNorthWest(boolean b);
	/**
	 * @param b: true when theres connection
	 */
	public void setSouthEast(boolean b);
	/**
	 * @param b: true when theres connection
	 */
	public void setSouthWest(boolean b);
	/**
	 * @param b: true when theres connection
	 */
	public void setEastWest(boolean b);
}
