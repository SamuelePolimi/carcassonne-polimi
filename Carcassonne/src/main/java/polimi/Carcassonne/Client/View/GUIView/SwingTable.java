package polimi.Carcassonne.Client.View.GUIView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import polimi.Carcassonne.Client.IModelView.IEventTable;
import polimi.Carcassonne.Client.IModelView.IModelViewCard;
import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;

/**
 * Represent the table-game
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class SwingTable extends JPanel implements IEventTable {
	private static final long serialVersionUID = 7976363387304558503L;
	//represent the offset of the GridBagLayout
	private static final int offsetX=53;
	private static final int offsetY=53;
	//represent the sets of card on the table
	private Map<Coordinate,SwingCard> cardMap;
	//is the interface of main-view input
	private IEventInput event;
	private LayoutManager layout;
	private GridBagConstraints lim = new GridBagConstraints();
	/**
	 * Constructor of the table
	 */
	public SwingTable(){
		super();
		layout=new GridBagLayout();
		this.setLayout(layout);
		cardMap=new HashMap<Coordinate,SwingCard>();
	}
	/**
	 * This method is generated when card on the table is changed
	 * @param card
	 */
	public void updateCard(IModelViewCard card) {
		if(cardMap.containsKey(card.getCoordinate())){
			 cardMap.get(card.getCoordinate()).setNew((ClientCard)card);
		}else{
			SwingCard sc = new SwingCard(card);
			addCard(sc,card.getCoordinate().getX(),card.getCoordinate().getY());
			cardMap.put(card.getCoordinate(), sc);
		}
		
	}
	/**
	 * This methods permits to insert a card on the table
	 * @param card  to puts
	 * @param x coordinated
	 * @param y coordinate
	 */
	private void addCard(SwingCard card, int x, int y){
		lim.gridx=offsetX+x;
		lim.gridy=offsetY-y;
		card.setEvent(event);
		this.add(card, lim);
	}
	/**
	 * Events permit to insert a new card
	 */
	public void insertNewCard(IModelViewCard card) {
		updateCard( card);
	}
	/**
	 * Sets the events input interface
	 * @param event (main view)
	 */
	public void setIEventInput(IEventInput event){
		this.event=event;
	}

}
