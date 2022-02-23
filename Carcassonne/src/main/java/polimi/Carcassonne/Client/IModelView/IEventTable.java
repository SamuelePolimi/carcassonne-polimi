package polimi.Carcassonne.Client.IModelView;
/**
 * @author Omar Scotti - Samuele Tosatto
 * interface between model and view
 * 
 * implemented by TableTextWindow
 */
public interface IEventTable {
	/**
	 * This event is called when a card is being updated 
	 * @param card
	 */
	public void updateCard(IModelViewCard card);
	/**
	 * This event is called when is being insert a new card
	 * @param card
	 */
	public void insertNewCard(IModelViewCard card);
}
