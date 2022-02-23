package polimi.Carcassonne.Server.Connection;
import java.awt.Color;
import java.util.ArrayList;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
/**
 * this interface represent the access point from the server to the client,
 * here Game can call method to updade client-gameState.
 * @author Omar Scotti - Samuele Tosatto
 */
public interface IClientConnection {
	/**
	 * Start the game
	 * @param nPlayer: number of players
	 * @param name: name of the game
	 * @param color: color of the first player
	 * @param card: first card
	 */
	public void start(int nPlayer, String name, Color color, Box card);
	/**
	 * @param card: next card
	 */
	public void nextTile(Card card);
	/**
	 * @param color: color of the next player
	 */
	public void turnChanged(Color color);
	/**
	 * @param card: card to update
	 */
	public void updateCard(Box card);
	/**
	 * @param card: card to rotate
	 */
	public void rotated(Card card);
	/**
	 * @param players
	 */
	public void score(ArrayList<Player> player);
	/**
	 * @param players
	 */
	public void endGame(ArrayList<Player> player);
	public void moveNotValid();
	public void lock();
	public void unlock();
	/**
	 * @param color: player that leave the game
	 */
	public void leave(Color color);
	/**
	 * @param gs
	 */
	public void setGame(IModelControllerLogicGame gs);
	
	public void setMyColor(Color color);
}
