package polimi.Carcassonne.Client.IModelView;
import java.util.ArrayList;
import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Client.Model.ClientPlayer;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and view
 * 
 * implemented by ClientGameState
 */
public interface IModelViewGameState {
	//theese methods get information about game state
	/**
	 * @return currentPlayer
	 */
	public IModelViewPlayer getCurrentPlayer();
	/**
	 * @return listOfPlayers
	 */
	public ArrayList<ClientPlayer> getListOfPlayers();
	//theese other methods give informations about table dimension
	/**
	 * @return xMax
	 */
	public int getXMax();
	/**
	 * @return yMax
	 */
	public int getYMax();
	/**
	 * @return yMin
	 */
	public int getYMin();
	/**
	 * @return xMin
	 */
	public int getXMin();
	/**
	 * @param x
	 * @param y
	 * @return clientCard
	 */
	public ClientCard getClientCard(int x, int y);
	/**
	 * @return mapOfCards
	 */
	public ArrayList<ClientCard> getMapOfCards();
	/**
	 * @return nextCard
	 */
	public ClientCard getNextCard();
	/**
	 * @param event
	 */
	public void setTableEvent(IEventTable e );
	/**
	 * @param event
	 */
	public void setGameStateEvent(IEventGameState e);
	/**
	 * @return true if is your turn
	 */
	public boolean isMyTurn();
	/**
	 * @return true if there is a marker to insert
	 */
	public boolean hasToInsertMark();
	/**
	 * @return this client
	 */
	public ClientPlayer getThisClient();
	
	/**
	 * gets the name of the game
	 * @return name of the game
	 */
	public String getName();
	
	public void setIEventLop(IEventListOfPlayer event);
	
}
