package polimi.Carcassonne.Server.Model;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
import polimi.Carcassonne.Server.IModelView.IEventLogicGame;
import polimi.Carcassonne.Server.Model.Algorithm.Construction;
import polimi.Carcassonne.Server.Model.Algorithm.Marker;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCardException;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCoveredAreaException;
import polimi.Carcassonne.Server.Model.Exception.CardBlokedException;
import polimi.Carcassonne.Server.Model.Exception.ConnectionTypeMistakeException;
import polimi.Carcassonne.Server.Model.Exception.EmptyDeckException;
import polimi.Carcassonne.Server.Model.Exception.GameRuleException;
import polimi.Carcassonne.Server.Model.Exception.NothingTypeException;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Connection;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class extends table and 
 * has to manage all the game on the server (insert card, put marker, refresh game, lock, unlock...)
 */
public class GameLogic extends Table implements IModelControllerLogicGame{
	private int indexCurrentPlayer;
	private ArrayList<Player> playersList;
	private boolean passInsertCard=false;
	private Deck deck;
	private Card nextCard;
	private Coordinate lastPosition;
	private IEventLogicGame event;
	private String name;
	public GameLogic(){
		;
	}
	/**
	 * Initialize all 
	 * @param playersList
	 */
	public GameLogic(int nPlayer, String gameName, IEventLogicGame event){
		name=gameName;
		Color colori[] = new Color[]{Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.BLACK};
		playersList=new ArrayList<Player>();
		for(int i=0;i<nPlayer;i++){
			this.playersList.add(new Player(colori[i]));
		}
		indexCurrentPlayer=0;
		passInsertCard=true;
		deck=new Deck();
		try {
			super.insertCard(this.getBox(0, 0),new Card( deck.getCard(0)));
			this.event=event;
		}catch (AlreadyCardException e) {
			Printer.print("SRV<GameLogic>: ERROR to insertion 0,0!");
		} catch (ConnectionTypeMistakeException e) {
			Printer.print("SRV<GameLogic>: ERROR to insertion 0,0!");
		} catch (GameRuleException e) {
			Printer.print("SRV<GameLogic>: ERROR to insertion 0,0!");
		}
	}
	/**
	 * Override of insertCard in class table
	 * @param box where i want to insert the card
	 * @param card to insert
	 * @throws ConnectionTypeMistakeException 
	 * @throws AlreadyCardException 
	 */
	@Override
	protected void insertCard(Box box,Card card) throws GameRuleException, AlreadyCardException, ConnectionTypeMistakeException {
		if(passInsertCard){
				super.insertCard(box, card);
				lastPosition=box.getCoordinate();
				passInsertCard=false;
		}else{
			throw new GameRuleException();
		}
		passInsertCard=false;
	}
	/**
	 * Inserts a marker on a connection
	 * @param connection
	 * @throws GameRuleException if the order of calls isn't correct
	 * @throws NothingTypeException 
	 * @throws AlreadyCoveredAreaException 
	 */
	private void putMarker(Connection connection) throws GameRuleException, AlreadyCoveredAreaException, NothingTypeException {
		if(!passInsertCard){
		if(!getCurrentPlayer().isMarkersEmpty()){
			super.putMarker(connection, getCurrentPlayer().removeMarkers(),getBox(lastPosition.getX(),lastPosition.getY()));
		}
			event.updateCard(this.getBox(lastPosition.getX(), lastPosition.getY()));
			pass();
		}else{
			throw new GameRuleException();
		}
	}
	/**
	 * Pass is used if client doesn't want to insert a marker
	 * @throws GameRuleException if the order of calls isn't correct
	 */
	protected void pass() throws GameRuleException{
		if(!passInsertCard){
			indexCurrentPlayer=(indexCurrentPlayer+1)%playersList.size();
			passInsertCard=true;
			this.refreshStateGame();
			event.updateCard(this.getBox(lastPosition.getX(), lastPosition.getY()));
			event.score(new ArrayList<Player>(playersList));
			event.turnChanged(playersList.get(indexCurrentPlayer).getColor());
			peekCard();
		}else{
			throw new GameRuleException();
		}
	}
	/**
	 * 
	 * @return current player
	 */
	protected Player getCurrentPlayer(){
		return playersList.get(indexCurrentPlayer);
	}
	/**
	 * Refresh the state of the game
	 */
	private void refreshStateGame(){
		ArrayList<Construction> closeConstructions=explorerManager.clearClosedConstructionBuffer();
		this.refreshStateGame(closeConstructions);
	}
	private void refreshStateGame(ArrayList<Construction> construction){
		for(Construction closeConstruction : construction){
			Integer max=0;
			HashMap<Player,Integer> markerNum = new HashMap<Player,Integer>(); 
			for(Marker marker : closeConstruction.getMarkers()){
				 if (!markerNum.containsKey(marker.getPlayer())){
					 markerNum.put(marker.getPlayer(), 0);
				 }else{
					 Integer num = markerNum.get(marker.getPlayer());
					 num = num.intValue() + 1;
					 if(max.intValue()<num.intValue()){
						 max=num.intValue();
					 }
					 markerNum.put(marker.getPlayer(), num);
				 }
				 marker.getPlayer().addMarker(marker);
				 marker.getConnection().removeMarker();
				 marker.removeConnection();
				 event.updateCard(marker.getCard());		 
			}
			for(Entry<Player,Integer> e: markerNum.entrySet() ){
				if(e.getValue().equals(max.intValue())){
					e.getKey().addPoint(closeConstruction.getPoint());
				}
			}
		}
	}
	/**
	 * @return true when player have only to put his Card on the table
	 * false when he have to put signals or pass
	 */
	protected boolean getPlayerState(){
		return this.passInsertCard;
	}
	/**
	 * @return a list of the players
	 */
	protected ArrayList<Player> getPlayersList(){
		return playersList;
	}
	/**
	 * Takes a card from deck
	 */
	public void peekCard() {
		try {
			nextCard = deck.getCard();
			while(!this.chechIfPossibleToInsert(nextCard)){
				nextCard = deck.getCard();
			}
			event.nextTile(nextCard);
		} catch (EmptyDeckException e) {
			this.refreshStateGame(this.getOpenedConstruction());
			event.endGame(this.playersList);
		}
	}
	/**
	 * Rotates the selected card
	 */
	public void rotateNextCard() {
		try {
			nextCard.rotateClockwise();
			event.nextTile(nextCard);
		} catch (CardBlokedException e) {
			;
		}
	}
	/**
	 * Put a marker on a selected side
	 * @param side where i want to put the marker: 
	 * 0 for north, 1 for east, 2 for south, 3 for west
	 * 
	 */
	public void putMarker(int orientation){
		try{
			if(orientation==0){		//north case
				this.putMarker(this.getBox(lastPosition.getX(), lastPosition.getY()).getNorth());
				//event.updateCard(this.getBox(lastPosition.getX(), lastPosition.getY()));
			}else if(orientation==1){		//east case
				this.putMarker(this.getBox(lastPosition.getX(), lastPosition.getY()).getEast());
				//event.updateCard(this.getBox(lastPosition.getX(), lastPosition.getY()));
			}else if(orientation==2){		//south case
				this.putMarker(this.getBox(lastPosition.getX(), lastPosition.getY()).getSouth());
				//event.updateCard(this.getBox(lastPosition.getX(), lastPosition.getY()));
			}else if(orientation==3){		//west case
				this.putMarker(this.getBox(lastPosition.getX(), lastPosition.getY()).getWest());
				//event.updateCard(this.getBox(lastPosition.getX(), lastPosition.getY()));
			}else {
				pass();
			}
		} catch (GameRuleException e) {
			event.moveNotValid();
		} catch (AlreadyCoveredAreaException e) {
			event.moveNotValid();
		} catch (NothingTypeException e) {
			event.moveNotValid();
		}
	}
	/**
	 * Insert card into a position on the table
	 * @param x
	 * @param y
	 */
	public void insertPosition(int x, int y) {
			try {
				this.insertCard(this.getBox(x, y), nextCard);
				event.updateCard(this.getBox(x, y));
			} catch (AlreadyCardException e) {
				event.moveNotValid();
			} catch (ConnectionTypeMistakeException e) {
				event.moveNotValid();
			} catch (GameRuleException e) {
				event.moveNotValid();
			}
	}
	/**
	 * Lock the game
	 */
	public void lock() {
		event.lock();
	}
	/**
	 * Unlock the game
	 */
	public void unlock() {
		event.unlock();
	}
	/**
	 * Starts the game
	 */
	public void start() {
		event.start(playersList.size(), this.name, playersList.get(this.indexCurrentPlayer).getColor(), this.getBox(0, 0));
		event.turnChanged(playersList.get(this.indexCurrentPlayer).getColor());
		peekCard();
	}
	/**
	 * 
	 * @param pass: true if you can insert a card
	 */
	public void setPassInsertCard(boolean pass){
		this.passInsertCard=pass;
	}

	/**
	 * Control if it is possible to insert a card
	 * @param card: card to insert
	 * @return true if you can insert the card
	 */
	private boolean chechIfPossibleToInsert(Card card){
		int rotate=0;
		for(Box box:this.getFreeBoxes()){
			for(int i=0;i<4;i++){
				if(this.canPutCardBox(box, card)){
					while(rotate!=0){
						rotate=(rotate+1)%4;
						try {
							card.rotateClockwise();
						} catch (CardBlokedException e) {
							;
						}
					}
					return true;
				}
				rotate=(rotate+1)%4;
				try {
					card.rotateClockwise();
				} catch (CardBlokedException e) {
					;
				}
			}
		}
		return false;
	}
	/**
	 * @param card
	 */
	public void setNextCard(Card card){
		nextCard=card;
	}
	/**
	 * @return nextCard
	 */
	public Card getNextCard(){
		return nextCard;
	}
	/**
	 * @return passInsertCard
	 */
	public boolean getPassInsertCard(){
		return passInsertCard;
	}
	public void removePlayer(Color color) throws RemoteException {
		for(int i=0;i<this.playersList.size();i++){
			if(this.playersList.get(i).getColor().equals(color)){
				this.playersList.remove(i);
				break;
			}
		}
		this.event.leave(color);
	}
}