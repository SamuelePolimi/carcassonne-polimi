package polimi.Carcassonne.Server.IModelController;
import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IModelControllerLogicGame extends Remote{
	public void start() throws RemoteException;
	/**this method keep a random card by deck and sets "nextCard"
	 * implemented by GameLogic
	 */
	public void peekCard() throws RemoteException;
	/**
	 * rotate clockwise next card
	 */
	public void rotateNextCard() throws RemoteException;
	/**
	 * put the marker on card placed
	 * @param orientation 0=north,1=east,2=south,3=west,4=PASS
	 */
	public void putMarker(int orientation) throws RemoteException;
	/**
	 * Put nextMarker in position x,y
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void insertPosition(int x, int y) throws RemoteException;
	/**
	 * lock the game
	 */
	public void lock() throws RemoteException;
	/**unlock the game
	 * 
	 */
	public void unlock() throws RemoteException;
	/**
	 * when a client disconnect, we remove player
	 * @throws RemoteException
	 */
	public void removePlayer(Color color) throws RemoteException;
	
	
}
