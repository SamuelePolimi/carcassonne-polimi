package polimi.Carcassonne.Server.IModelView;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
public interface IEventLogicGame {
	/**
	 * Starts the game
	 * @param number of player
	 * @param name
	 * @param color
	 * @param card
	 * 
	 * implemented by Offline
	 */
	public void start( int nPlayer,String name, Color color,Box card);
	/**
	 * this event is called when turn change
	 * @param color is the color of new current player
	 */
	public void nextTile(Card card);
	/**
	 * Reports the change of turn
	 * @param color
	 */
	public void turnChanged(Color color);
	/**
	 * this event is called when someone change a card on the table
	 * @param card is the card changed (it has also the position on the pable inside by coordinate)
	 *
	 */
	public void updateCard(Box card);
	/**
	 * this event is called when card is rotated by someone
	 * @param card
	 */
	public void rotated(Card card);
	/**
	 * This event gives information about player's scores
	 * @param listOfPlayer
	 */
	public void score(ArrayList<Player> listOfPlayer);
	/**
	 * This event signals the end of the game
	 */
	public void endGame(ArrayList<Player> listOfPlayer);
	/**
	 * This event inform that move tried is not valid
	 * @throws RemoteException 
	 */
	public void moveNotValid();
	/**
	 * This event signals that game is blocked
	 */
	public void lock();
	/**
	 * This event inform that game can continue
	 */
	public void unlock();
	/**
	 * This event inform that a player leaved the table
	 * @param color
	 */
	public void leave(Color color);
}
