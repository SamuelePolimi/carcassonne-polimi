package polimi.Carcassonne.Server.Controller;
import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.Connection.IClientConnection;
import polimi.Carcassonne.Server.IModelView.IEventLogicGame;
import polimi.Carcassonne.Server.Model.GameLogic;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class represents a single game on the server
 */
public class Game implements IEventTimer, IEventLogicGame, Remote{
	private ArrayList<IClientConnection> clientsManager;
	private final String nameGame;
	private Timer timer;
	private boolean isStarted;
	private GameLogic game;
	private int nDisconnect;
	private Color[] colors=new Color[]{Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.BLACK};
	/**
	 * Constructor of the game
	 * @param nameGame
	 */
	public Game(String nameGame){
		nDisconnect=0;
		this.timer=new Timer(5000,this);
		this.nameGame=nameGame;
		this.isStarted=false;
		// Set first client Manager
		this.clientsManager= new ArrayList<IClientConnection>();
		start();
	}
	/**
	 * Starter of the timer
	 */
	public void start(){
		Thread thread = new Thread(timer);
		thread.start();
	}
	/**
	 * Adds a new player at the game
	 * @param player
	 */
	public  void addPlayer(IClientConnection player){	
		Printer.print("SVR<Game>: add player");
		player.setMyColor(colors[clientsManager.size()]);
		clientsManager.add(player);
		timer.reset();
		if(clientsManager.size()==5){
			timer.stop();
			isStarted=true;
			Printer.print("SVR<Game>: startGame()");
			startGame();
		}
	}
	/**
	 * Starter of the game
	 */
	public void startGame(){

		Printer.print("SRV<Game>: Game Start!!");
		game = new GameLogic(clientsManager.size(),this.nameGame,this);
		for(int i=0;i<clientsManager.size();i++){
			clientsManager.get(i).setGame(game);
		}
		game.start();
	}
	/**
	 * @return number of the game
	 */
	public String getNumGame() {
		return nameGame;
	}
	/**
	 * @return clientsManager
	 */
	public ArrayList<IClientConnection> getClientsManager(){
		return clientsManager;
	}
	/**
	 * @return isStarted: true if the game is started
	 */
	public boolean isStarted(){
		return isStarted;
	}
	/**
	 * The timer is ended so the game start
	 */
	public void tick() {
		if(clientsManager.size()>=2){
			isStarted=true;
			timer.stop();
			Printer.print("SVR<Game>: startGame()");
			startGame();
		}
	}
	/**
	 * @return timer
	 */
	public Timer getTimer(){
		return timer;
	}
	/**
	 * set the start of the game
	 * @param nPlayer: number of players
	 * @param name: name of the game
	 * @param color: color of the first player
	 * @param card: first card
	 */
	public void start(int nPlayer, String name, Color color, Box card) {
		Color colori[] = new Color[]{Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.BLACK};
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).start(nPlayer, name, colori[i], card);
		}
	}
	/**
	 * set the next tile to insert
	 * @param card
	 */
	public void nextTile(Card card) {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).nextTile(card);
		}
	}
	/**
	 * set the change of turn
	 * @param color: color of the next player
	 */
	public void turnChanged(Color color) {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).turnChanged(color);
		}
	}
	/**
	 * update a card
	 * @param card
	 */
	public void updateCard(Box card) {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).updateCard(card);
		}
	}
	/**
	 * signals a rotated card
	 * @param card
	 */
	public void rotated(Card card) {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).rotated(card);
		}
	}
	/**
	 * set the score of the specific player
	 */
	public void score(ArrayList<Player> listOfPlayer) {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).score(listOfPlayer);
		}
	}
	
	/**
	 * set a not valid move
	 */
	public void moveNotValid() {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).moveNotValid();
		}
	}
	/**
	 * Lock the game (if there is a disconnection)
	 */
	public void lock() {
		nDisconnect++;
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).lock();
		}
	}
	/**
	 * Unlock the game
	 */
	public void unlock() {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).unlock();
		}
	}
	/**
	 * Signals that a player has leave the game
	 * @param color: color of the player
	 */
	public void leave(Color color) {
		//TODO remove
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).leave(color);
		}
	}
	/**
	 * this method determine the endGame
	 */
	public void endGame(ArrayList<Player> listOfPlayer) {
		for(int i=0;i<this.clientsManager.size();i++){
			clientsManager.get(i).endGame(listOfPlayer);
		}
	}
	/**
	 * reinsert Player in the game
	 */
	public void reinsertPlayer(Color color){
		nDisconnect--;
		if(nDisconnect==0){
			unlock();
		}
	}
	public void removePlayer(Color color){
		try {
			this.game.removePlayer(color);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}