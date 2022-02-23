package polimi.Carcassonne.Client.IModelController;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.awt.Color;

/**
 * @author Omar Scotti - Samuele Tosatto interface between model and control
 * 
 *         implemented by ClientGameState
 */
public interface IModelControllerGameState extends Remote {
	/**
	 * this method update the card no the table
	 * 
	 * @param x
	 *            : set x-coordinate
	 * @param y
	 *            : set y-coordinate
	 * @param iModelControlCard
	 * @throws RemoteException
	 */
	public void updateCard(int x, int y, IModelControllerCard iModelControlCard)
			throws RemoteException;

	/**
	 * Insert a number of players
	 * 
	 * @param n
	 *            : number of players
	 * @throws RemoteException
	 */
	public void insertNPlayer(int n) throws RemoteException;

	/**
	 * Set what is my player
	 * 
	 * @param color
	 *            : color of this player
	 * @throws RemoteException
	 */
	public void setMyPlayer(Color color) throws RemoteException;

	/**
	 * Set the current player
	 * 
	 * @param color
	 *            : color of current player
	 * @throws RemoteException
	 */
	public void setCurrentPlayer(Color color) throws RemoteException;

	/**
	 * Set the next card of a client
	 * 
	 * @param generateCard
	 * @throws RemoteException
	 */
	public void setNextCard(IModelControllerCard generateCard)
			throws RemoteException;

	/**
	 * Set a rotated card
	 * 
	 * @param generateCard
	 * @throws RemoteException
	 */
	public void setRotatedCard(IModelControllerCard generateCard)
			throws RemoteException;

	/**
	 * 
	 */
	public void moveNotValid() throws RemoteException;

	/**
	 * Set the client with his color
	 * 
	 * @param color
	 *            : color of the client
	 * @throws RemoteException
	 */
	public void setClient(Color color) throws RemoteException;

	/**
	 * Set score of a single player
	 * 
	 * @param color
	 * @param point
	 * @throws RemoteException
	 */
	public void setScore(Color color, int point) throws RemoteException;

	/**
	 * Set score of all players
	 * 
	 * @param color
	 * @param point
	 * @throws RemoteException
	 */
	public void setScore(Color[] color, int[] point) throws RemoteException;

	/**
	 * Ends the game
	 * 
	 * @throws RemoteException
	 */
	public void endGame() throws RemoteException;

	/**
	 * State of the insertion
	 */
	public void setInsertionCardState() throws RemoteException;

	/**
	 * sets the name of the game
	 * 
	 * @param name
	 *            of the game
	 */
	public void setName(String name) throws RemoteException;
	/**
	 * remove the player
	 * @param color
	 */
	public void removePlayer(Color colo) throws RemoteException;
}
