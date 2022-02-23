package polimi.Carcassonne.Client.View.TextView;
import java.util.HashMap;
import java.util.Map;

import polimi.Carcassonne.Client.IModelView.IEventTable;
import polimi.Carcassonne.Client.IModelView.IModelViewCard;
import polimi.Carcassonne.Client.IModelView.IModelViewGameState;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class represents the table on the text view
 */
public class TableTextWindow extends TextWindow implements IEventTable {
	private String name;
	private IModelViewGameState gs;
	private Map<Coordinate, CardTextWindow> table;
	/**
	 * Constructor of the table view
	 * @param gs: ImodelViewGameState
	 * @param r
	 * @param x
	 * @param y
	 */
	public TableTextWindow(IModelViewGameState gs, TextRenderize r, int x, int y) {
		super(r, x, y, 0, 0);
		reSet(gs);
	}
	/**
	 * Refresh
	 */
	@Override
	public void refresh() {
		super.killAllChilds();
		for(Coordinate c:table.keySet()){
			CardTextWindow card = table.get(c);
			card.setX((c.getX()-gs.getXMin())*14 + 2);
			card.setY((gs.getYMax()-c.getY())*6 + 2);
			super.insertTextWindow(card);
		}
		refreshed();
	}
	/**
	 * Reset
	 * @param ImodelViewGameState
	 */
	@Override
	public void reSet(Object o) {
		if(o instanceof IModelViewGameState){
			this.gs=(IModelViewGameState)o;
			gs.setTableEvent(this);
			table=new HashMap<Coordinate,CardTextWindow>();
			for(IModelViewCard c:gs.getMapOfCards()){
				table.put(c.getCoordinate(),new CardTextWindow(c,this.getMyTextRenderize(),0,0));
			}
		}
		refresh();
		toRefresh();
	}
	/**
	 * Update the card
	 * @param card
	 */
	public void updateCard(IModelViewCard card) {
		CardTextWindow ctw = new CardTextWindow(card,this.getMyTextRenderize(),0,0);
		table.put(card.getCoordinate(), ctw);
		refresh();
		toRefresh();
	}
	/**
	 * Insert a new card
	 * @param card
	 */
	public void insertNewCard(IModelViewCard card) {
		CardTextWindow ctw = new CardTextWindow(card,this.getMyTextRenderize(),0 ,0);
		table.put(card.getCoordinate(), ctw);
		toRefresh();
	}
	/**
	 * @return name
	 */
	public String getNome() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setNome(String name) {
		this.name = name;
	}
}
