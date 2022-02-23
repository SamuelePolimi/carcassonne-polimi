package polimi.Carcassonne.Client.View.TextView;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.Connection.IViewConnection;
import polimi.Carcassonne.Client.IModelView.IModelViewGameState;
import polimi.Carcassonne.Client.View.IView;
/**
 * @author Omar Scotti - Samuele Tosatto
 * Manager of the events
 */
public class View implements IEventTotalView,IView{
	private TotalWindow view;
	private IViewConnection conn;
	/**
	 * Constructor of view
	 * @param view
	 */
	public View (TotalWindow view){
		this.view=view;
		view.setEvent(this);
	}
	/**
	 * Constructor of view
	 * @param view
	 * @param conn
	 */
	public View (TotalWindow view,IViewConnection conn){
		this.view=view;
		view.setEvent(this);
		this.conn=conn;
	}
	/**
	 * Insert card or rotate card
	 */
	public void toInsertOrRotate() {
		view.getMyTextRenderize().clear();
		view.refresh();
		view.renderize();
		Thread t = new Thread(new InsertThread(view,conn));
		t.start();
	}
	/**
	 * Insert marker or pass
	 */
	public void toInsertMarkerOrPass() {
		view.getMyTextRenderize().clear();
		view.refresh();
		view.renderize();
		Thread t = new Thread(new MarkerThread(view,conn));
		t.start();
	}
	/**
	 * Wait and refresh
	 */
	public void toWaitAndRefresh() {
		view.getMyTextRenderize().clear();
		view.refresh();
		view.renderize();
		Printer.print(view.getMyTextRenderize().toString());
	}
	/**
	 * Reset 
	 * @param gs: IModelViewGameState
	 */
	public void reSet(IModelViewGameState gs){
		view.reSet(gs);
	}
	/**
	 * @param viewConnection
	 */
	public void setIViewConnection(IViewConnection viewConnection) {
		this.conn=viewConnection;
	}
	/**
	 * @param gs: gameState
	 */
	public void setIModelView(IModelViewGameState gs) {
		if(this.view!=null){
			this.view.reSet(gs);
		}
	}
}
