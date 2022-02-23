package polimi.Carcassonne.Server.Controller;
import java.util.ArrayList;
import java.util.HashMap;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.Connection.IClientConnection;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class has to create new game and add player to the class "game"
 */
public class GameManager{
	private ArrayList<Game> listOfGame;
	private HashMap<String,Game> mapGame;

	private int numGame;
	/**
	 * Constructor of game manager, set the port 1111
	 */
	public GameManager(){
		numGame=0;
		listOfGame=new ArrayList <Game>();
		mapGame=new HashMap<String,Game>();
		Game game = new Game("Game" +numGame);
		listOfGame.add(game);
		mapGame.put("Game" +numGame, game);
	}
	/**
	 * Class that sets the game and adds players
	 * @param client
	 */
	public synchronized void clientRequest(IClientConnection client){
		if(listOfGame.get(numGame).isStarted()){
			Printer.print("SRV<GameManager>: new Game");
			numGame++;
			Game game = new Game("Game" +numGame);
			listOfGame.add(game);
			mapGame.put("Game" +numGame, game);
			listOfGame.get(numGame).addPlayer(client);
		}else{
			Printer.print("SRV<GameManager>: add Player");
			listOfGame.get(numGame).addPlayer(client);
		}
	}
	/**
	 * @return listOfGame
	 */
	public ArrayList<Game> getListOfGame(){
		return listOfGame;
	}
}
