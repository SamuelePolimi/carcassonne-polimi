package polimi.Carcassonne.Client.View;
import polimi.Carcassonne.Client.Connection.IViewConnection;
import polimi.Carcassonne.Client.IModelView.IModelViewGameState;
/**IView represent the graphics. All main-graphics have to implements  this methods
 * @author Omar Scotti - Samuele Tosatto
 **/
public interface IView {
	/**
	 * Set View connection is useful for setting connection who interface dialog
	 * @param viewConnection
	 */
	public void setIViewConnection(IViewConnection viewConnection);
	/**
	 * Set ViewModel (The interface that represent game state)
	 * @param gs
	 */
	public void setIModelView(IModelViewGameState gs);
}
