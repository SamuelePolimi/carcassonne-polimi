package polimi.Carcassonne.Client.IModelView;
import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Client.Model.ClientPlayer;
/**
 * @author Omar  Scotti - Samuele Tosatto
 * interface between model and view
 * 
 * implemented by TotalWindow
 */
public interface IEventGameState {
	/**
	 * This event is called when turn player changed
	 * @param player
	 */
	public void changeTurn(ClientPlayer player);	
	/**
	 * This event is called when is turn of the client's player
	 */
	public void yourTurn();							
	/** 
	 * This event is called when you have to insert a card
	 * @param card
	 */
	public void insertCard(ClientCard card);		
	/**
	 * This event is called when position client choose for its card is wrong
	 */
	public void errorInsert();						
	/**
	 * This event is called when it's time to insert marker o pass
	 */
	public void markerOrPass();
	/**
	 * This event is called when next card is changed or rotate
	 * @param car
	 */
	public void updateNext(ClientCard car);	
	/**
	 * This event is called when the table dimension is changed
	 */
	public void tableDimensionChanged();
	/**
	 * Update players
	 */
	public void updatePlayers();
	/**
	 * Determinize the end of game
	 */
	public void endGame();
}
