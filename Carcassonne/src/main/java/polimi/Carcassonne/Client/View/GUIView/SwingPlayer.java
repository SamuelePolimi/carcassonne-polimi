package polimi.Carcassonne.Client.View.GUIView;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import polimi.Carcassonne.Client.IModelView.IEventClientPlayer;
import polimi.Carcassonne.Client.IModelView.IModelViewPlayer;

/**
 * Represent a player
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class SwingPlayer extends JLayeredPane implements IEventClientPlayer {
	
	private static final long serialVersionUID = -548615125609575216L;

	//model-view player
	private IModelViewPlayer player;
	//label represents the points
	private JLabel points;
	//label represents the markers
	private JLabel markers;
	//label represents the color
	private JLabel color;
	/**
	 * Constructor of a single player that we'll put in the SwingListOfPlayer
	 * @param player
	 */
	public SwingPlayer(IModelViewPlayer player){
		super();
		this.player=player;
		player.setEvent(this);
		this.setLayout(new GridLayout(7,0));
		color=  new JLabel("   "+player.toString().toUpperCase()+"   ");
		points= new JLabel("   Points:  0   ");
		markers=new JLabel("   Markers: 7   ");
		this.add(new JLabel("               "));
		this.add(color);
		this.add(new JLabel("               "));
		this.add(points);
		this.add(new JLabel("               "));
		this.add(markers);
		this.add(new JLabel("               "));
		this.beingNotCurrentPlayer();
	}
	/**
	 * If points change (refresh)
	 */
	public void pointsChanged() {
		points.setText("   Points:  "+player.getPoints()+"   ");
	}
	/**
	 * If marker changes (refresh)
	 */
	public void markerChanged() {
		markers.setText("   Markers: " + player.getMarkerNum()+"   ");
	}
	/**
	 * If you are the current player
	 */
	public void beingCurrentPlayer() {
		this.setBorder(BorderFactory.createLineBorder(player.getColor(),6));
	}
	/**
	 * If you aren't the current player
	 */
	public void beingNotCurrentPlayer() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	}

}
