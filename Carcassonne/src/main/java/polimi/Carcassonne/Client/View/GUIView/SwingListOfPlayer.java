package polimi.Carcassonne.Client.View.GUIView;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

import polimi.Carcassonne.Client.IModelView.IEventListOfPlayer;
import polimi.Carcassonne.Client.Model.ClientPlayer;
/**
 * Represents a list of players
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class SwingListOfPlayer extends JPanel implements IEventListOfPlayer{
	
	private static final long serialVersionUID = 960485182614385059L;
	private ArrayList<ClientPlayer> players;
	/**
	 * Create list of player
	 * @param players
	 */
	public SwingListOfPlayer(ArrayList<ClientPlayer> players){
		this.players=players;
		this.setLayout(new GridLayout(this.players.size(),1));
		for(int i=0;i<this.players.size();i++){
			this.add(new SwingPlayer(this.players.get(i)));
		}
	}
	public void refreshListOfPlayer(ArrayList<ClientPlayer> players) {
		this.removeAll();
		this.setLayout(new GridLayout(this.players.size(),1));
		for(int i=0;i<this.players.size();i++){
			this.add(new SwingPlayer(this.players.get(i)));
		}
	}
}
