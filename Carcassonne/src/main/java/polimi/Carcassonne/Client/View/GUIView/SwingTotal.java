package polimi.Carcassonne.Client.View.GUIView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import polimi.Carcassonne.Client.Connection.IViewConnection;
import polimi.Carcassonne.Client.IModelView.IEventGameState;
import polimi.Carcassonne.Client.IModelView.IModelViewGameState;
import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Client.Model.ClientPlayer;
import polimi.Carcassonne.Client.View.IView;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
import resource.GetURLFile;
/**
 * This class represent the main window. 
 * Implements:
 * 		IEventGameState, for reciving update by the gameState
 * 		IView, for being used as main view
 * 		IEventMarker for reciving update by insert_marker panel's
 * @author Samuele
 *
 */
public class SwingTotal extends JFrame implements IEventGameState, IView,IEventInput,IEventMarker,MouseListener{
	private static final long serialVersionUID = 7370844067187336664L;
	//Represent the table (wich card and so one)
	private SwingTable table;
	//represent the list of the players wich play the game
	private SwingListOfPlayer players;
	//represent the card user could insert
	private SwingCard next;
	//represent the panel with we can insert a marker on a card
	private SwingInsertMarker insMark;

	private JPanel south;
	//represent the insertion-panel, with a set of state-game labels and insert-marker panel
	private JPanel insert;
	//represent the panel wich contain nextCard
	private JPanel nextPanel;
	//Represent the label wich show us if we encourr in a error
	private JLabel error;
	//represent the connection (offline | rmi | socket)
	private IViewConnection conn;
	private JLabel player;
	//represent the gamestate
	private IModelViewGameState gs;
	//represent a flag who indicates if list of players has shown
	private boolean updatePlayers;
	//represents a flag who indicates if we have to insert a card, or a marker
	private boolean canInsertOrRotate;
	//panel represent top
	private JPanel topPanel;
	//represent myPlayer
	private JLabel myPlayer;
	/**
	 * Constructor of the total view
	 */
	public SwingTotal(){
		super();
		error=new JLabel();
		player=new JLabel();
		topPanel=new JPanel();
		topPanel.setLayout(new GridBagLayout());
		myPlayer=new JLabel();
		topPanel.add(myPlayer);
		this.setTitle("Carcassonne - By Omar & Samu");
		this.setMinimumSize(new Dimension(850,720));
		updatePlayers=false;
		this.setLayout(new BorderLayout());
		insert=new JPanel();
		south=new JPanel();
		south.setLayout(new GridLayout(2,1));
		insert.setLayout(new FlowLayout());
		canInsertOrRotate=false;
	}
	/**
	 * Events called when the current player is changed.
	 * Sets the graphics in a state of wait
	 */
	public void changeTurn(ClientPlayer player) {
		URL url = GetURLFile.getMyUrlFile().getMarker(gs.getThisClient().getColor());
		Image img =  Toolkit.getDefaultToolkit().createImage(url);
		img=img.getScaledInstance(50,50,Image.SCALE_DEFAULT);
		myPlayer.setIcon(new ImageIcon(img));
		this.toWait();
		
	}
	/**
	 * Set this client
	 * @param player
	 */
	public void setThisClient(ClientPlayer player){
		Icon icon= new ImageIcon(next.imgByColor(player.getColor()));
		this.player.setIcon(icon);
	}
	/**
	 * Event called when model has added into itself list of players
	 * we show that list into our graphics
	 */
	public void updatePlayers(){
		if(!updatePlayers){
			this.setTitle("Carcassonne - " + gs.getName() + "- By Omar & Samu");
			players=new SwingListOfPlayer(gs.getListOfPlayers());
			gs.setIEventLop(players);
			updatePlayers=true;
			JScrollPane scroll = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
					   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			insMark=new SwingInsertMarker(this);
			next=new SwingCard(new ClientCard(new Coordinate(0,0)));
			next.addMouseListener(this);
			nextPanel = new JPanel();
			nextPanel.setLayout(new GridBagLayout());
			nextPanel.add(next);
			insert.add(new JLabel("Cards: "));
			insert.add(new JLabel("                                     "));
			insert.add(nextPanel);
			insert.add(insMark);
			south.add(insert);
			south.add(error);
			this.add(south, BorderLayout.SOUTH);
			this.add(scroll,BorderLayout.CENTER);
			this.add(players,BorderLayout.EAST);
			this.add(topPanel,BorderLayout.NORTH);
			this.setVisible(true);
		}
	}
	/**
	 * Set your turn
	 */
	public void yourTurn() {
		URL url = GetURLFile.getMyUrlFile().getMarker(gs.getThisClient().getColor());
		Image img =  Toolkit.getDefaultToolkit().createImage(url);
		img=img.getScaledInstance(80,80,Image.SCALE_DEFAULT);
		myPlayer.setIcon(new ImageIcon(img));
	}
	/**
	 * This event show us that we can insert a card or rotate
	 * @param card: next card
	 */
	public void insertCard(ClientCard card) {
		next.setNew(card);
		if(gs.isMyTurn()){
			this.toInsertCardOrRotate();
		}
		errorRemove();
	}

	/**
	 * This event show us that the move the use tryed, was wrong
	 * **/
	public void errorInsert() {
		error.setText("MOVE NOT VALID");
		error.setForeground(Color.RED);
		error.setHorizontalAlignment( JLabel.CENTER );
		if(gs.isMyTurn()){
			if(gs.hasToInsertMark()){
				this.toInsertMarkerOrPass();
			}else{
				this.toInsertCardOrRotate();
			}
		}
	}
	/**
	 * Remove the error we have shown
	 */
	public void errorRemove(){
		error.setText("");
	}
	/**
	 *Game state notify that user can insert a marker or pass 
	 */
	public void markerOrPass() {
		if(gs.isMyTurn()){
			this.toInsertMarkerOrPass();
		}
	}
	/**
	 * GameState notify that next card is changed
	 * @param card represent the changed card
	 */
	public void updateNext(ClientCard card) {
		
		if(gs.isMyTurn()){
			this.toInsertCardOrRotate();
		}
	}
	public void tableDimensionChanged() {
		;
		
	}
	/**
	 * This method permit to set the connection
	 * @param viewConnection
	 **/
	public void setIViewConnection(IViewConnection viewConnection) {
		conn=viewConnection;
	}
	/**
	 * This method permits to set the gameState
	 * @param gameState
	 */
	public void setIModelView(IModelViewGameState gs) {
		this.gs=gs;
		gs.setGameStateEvent(this);
		table = new SwingTable();
		gs.setTableEvent(table);
		table.setIEventInput(this);
	}
	/**
	 * this event is generated by marker-panel
	 * @param orientation
	 */
	public void insertMarker(int orientation) {
		conn.marker(orientation);
	}
	/**
	 * this method is called when a user click on "pass"
	 */
	public void pass() {
		conn.marker(4);
	}
	/**
	 * this method sets the state of the view as "marker or pass"
	 */
	private void toInsertMarkerOrPass(){
		canInsertOrRotate=false;
		insMark.reAble();
	}
	/**
	 * this method able the user to insert a card or rotate it
	 */
	private void toInsertCardOrRotate(){
		canInsertOrRotate=true;
		insMark.disable();
	}
	/**
	 * this method is caled when user is not thecurrent-user, 
	 * so we disable all possible input
	 */
	private void toWait(){
		canInsertOrRotate=false;
		insMark.disable();
	}
	/**
	 * this method is launch by cards on the table when clicked.
	 * its give us the coordinate of the card selected
	 */
	public void selected(int x, int y) {
		if(canInsertOrRotate){
			conn.insert(x, y);
		}
	}
	/**
	 * Event of click
	 */
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		if(canInsertOrRotate){
			conn.rotate();
		}
	}

	public void mouseEntered(java.awt.event.MouseEvent arg0) {
		;
	}

	public void mouseExited(java.awt.event.MouseEvent arg0) {
		;
	}

	public void mousePressed(java.awt.event.MouseEvent arg0) {
		;
	}

	public void mouseReleased(java.awt.event.MouseEvent arg0) {
		;
	}
	/**
	 * End of the game (show a ranking of players)
	 */
	public void endGame() {
		JDialog dialog =  new JDialog();
		dialog.setTitle("Carcassonne by Omar & Samuele");
		dialog.add( new JLabel("End Game"));
		dialog.add( players);
		dialog.setAlwaysOnTop(true);
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setSize(new Dimension(800,600));
		dialog.setVisible(true);
		this.setVisible(false);
	}
}