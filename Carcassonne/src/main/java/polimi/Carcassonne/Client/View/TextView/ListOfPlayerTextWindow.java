package polimi.Carcassonne.Client.View.TextView;
import java.util.ArrayList;
import polimi.Carcassonne.Client.IModelView.IModelViewPlayer;
import polimi.Carcassonne.Client.Model.ClientPlayer;
/**
 * List of the player
 * @author Omar Scotti - Samuele Tosatto
 */
public class ListOfPlayerTextWindow extends TextWindow {
	private ArrayList<ClientPlayer> list;
	private ArrayList<PlayerTextWindow> players;
	/**
	 * Constructor of the list players view
	 * @param list: players
	 * @param r
	 * @param x
	 * @param y
	 */
	public ListOfPlayerTextWindow(ArrayList<ClientPlayer> list, TextRenderize r, int x, int y) {
		super(r, x, y, 0, 0);
		reSet(list);
	}
	/**
	 * Refresh the view
	 */
	@Override
	public void refresh() {
		super.killAllChilds();
		for(PlayerTextWindow player: players){
			super.insertToBottom(player);
		}
		refreshed();
	}
	/**
	 * reSet
	 * @param ArrayList<?>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reSet(Object o) {
		if(o instanceof ArrayList<?>){
			this.list = (ArrayList<ClientPlayer>)o;
			this.players=new ArrayList<PlayerTextWindow>();
			for(IModelViewPlayer player: list){
				players.add(new PlayerTextWindow(player, super.getMyTextRenderize(),0,0));
			}
		}
		refresh();
		toRefresh();
	}
}
