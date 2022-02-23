package polimi.Carcassonne.Client.Connection;
import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * This interface has the commands to use for the game
 * @author Omar Scotti - Samuele Tosatto
 * 
 * implemented by FakeViewConnection
 * implemented by OffLine
 * implemented by SocketWriter
 */
public interface IViewConnection extends Remote{
	/**
	 * To connect
	 * @throws RemoteException 
	 */
	public void connection() throws RemoteException;
	/**
	 * Only for offline implementation 
	 * @param n: number of players
	 * @throws RemoteException 
	 */
	public void connection(int n);
	/**
	 * For insert a card
	 * @param x
	 * @param y
	 * @throws RemoteException 
	 */
	public void insert(int x, int y);
	/**
	 * @throws RemoteException 
	 */
	public void rotate();
	/**
	 * For insert a marker
	 * if orientation==0 means north, 1 east, 2 south, 3 west
	 * @param orientation
	 * @throws RemoteException 
	 */
	public void marker(int orientation);
	/**
	 * For reconnect
	 * @param color: player to reconnect
	 */
	public void reconnect(Color color);
}
