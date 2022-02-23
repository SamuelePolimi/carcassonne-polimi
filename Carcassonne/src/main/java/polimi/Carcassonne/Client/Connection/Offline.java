package polimi.Carcassonne.Client.Connection;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;

import polimi.Carcassonne.ClientServerTranslation;
import polimi.Carcassonne.Client.IModelController.IModelControllerGameState;
import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
import polimi.Carcassonne.Server.IModelView.IEventLogicGame;
import polimi.Carcassonne.Server.Model.GameLogic;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
/**
 * Class that use a direct connection between server an client
 * in particular between LogicGame and GameState
 * This class refresh the GameState of the single client
 * @author Omar Scotti - Samuele Tosatto
 */
public class Offline implements IViewConnection ,IEventLogicGame{
	private IModelControllerLogicGame lg;
	private IModelControllerGameState gs;
	/**
	 * Constructor of Offline
	 * @param gs: controller of GameState
	 */
	public Offline(IModelControllerGameState gs){
		this.gs=gs;
	}
	/**
	 * Not used in this type of connection
	 */
	public void connection() {
		;
	}
	/**
	 * Insertion of a card
	 * @param x
	 * @param y
	 */
	public void insert(int x, int y){
			try {
				lg.insertPosition(x, y);
			} catch (RemoteException e) {
				;
			}
	}
	/**
	 * Rotation of a card
	 */
	public void rotate(){
				try {
					lg.rotateNextCard();
				} catch (RemoteException e) {
					;
				}
	}
	/**
	 * Insertion of a marker
	 * @param orientation: 0 north, 1 east, 2 south, 3 west, 4 pass
	 */
	public void marker(int orientation){
				try {
					lg.putMarker(orientation);
				} catch (RemoteException e) {
					;
				}
	}
	/**
	 * Connection 
	 * @param n: number of players
	 */
	public void connection(int n){
		lg=new GameLogic(n,"Offline modality",this);
			try {
				lg.start();
			} catch (RemoteException e) {
				;
			}
	}
	/**
	 * Set of the next card
	 * @param card: next card
	 * 
	 */
	public void nextTile(Card card){
		ClientCard c =ClientServerTranslation.translate(card);
		c.setEmpty(false);
		try {
			gs.setNextCard(c);
			
		} catch (RemoteException e) {
			;
		}
	}
	/**
	 * Set of next player
	 * @param: color of next player
	 */
	public void turnChanged(Color color){
		try {
			gs.setClient(color);
			gs.setCurrentPlayer(color);
		} catch (RemoteException e) {
			;
		}
	}
	


	/**
	 * Rotation of the card
	 * @param card: card rotated
	 */
	public void rotated(Card card){
		try {
			gs.setNextCard(ClientServerTranslation.translate(card));
		} catch (RemoteException e) {
			;
		}
	}
	/**
	 * Add points to players
	 * @param listOfPlayer
	 */
	public void score(ArrayList<Player> listOfPlayer){
		Color[] colors = new Color[listOfPlayer.size()];
		int[] points = new int[listOfPlayer.size()];
		for(int i=0;i<listOfPlayer.size();i++){
			colors[i]=listOfPlayer.get(i).getColor();
			points[i]=listOfPlayer.get(i).getPoint();
		}
		try {
			gs.setScore(colors, points);
		} catch (RemoteException e) {
			;
		}
	}
	
	/**
	 * Signal a not valid move
	 */
	public void moveNotValid(){
		try {
			gs.moveNotValid();
		} catch (RemoteException e) {
			;
		}
	}
	/**
	 * Lock the game
	 */
	public void lock() {
		
	}
	/**
	 * Unlock the game
	 */
	public void unlock() {
		;
	}
	/**
	 * Signal the client that has leave the game
	 * @param color: color of the player that has leave the game
	 */
	public void leave(Color color) {
		;
	}
	/**
	 * Update a card 
	 * @param card: card to update 
	 */
	public void updateCard(Box card){
		ClientCard cc = ClientServerTranslation.translateBox(card);
		int x = cc.getCoordinate().getX();
		int y= cc.getCoordinate().getY();
		try {
			gs.updateCard(x, y,cc);
		} catch (RemoteException e) {
			;
		}
	}

	/**
	 * Starts the game
	 * @param nPlayer: number of players
	 * @param name: name of the game
	 * @param color: color of this client
	 * @param card: first card
	 */
	public void start(int nPlayer, String name, Color color, Box card){
			try {
				gs.setName("OfflineGame");
				gs.insertNPlayer(nPlayer);
				gs.setClient(color);
				gs.updateCard(0, 0, ClientServerTranslation.translateBox(card));
			} catch (RemoteException e) {
				;
			}
	}
	/**
	 * Reconnect a player
	 * @param color: color of the player to reconnect
	 */
	public void reconnect(Color color) {
		;
	}
	public void endGame(ArrayList<Player> listOfPlayer) {
		try {
			this.score(listOfPlayer);
			gs.endGame();
		} catch (RemoteException e) {
			;
		}
	}
}


	