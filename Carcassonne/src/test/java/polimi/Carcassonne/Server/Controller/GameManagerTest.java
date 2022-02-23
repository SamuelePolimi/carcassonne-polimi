package polimi.Carcassonne.Server.Controller;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import polimi.Carcassonne.Server.Connection.IClientConnection;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;


public class GameManagerTest implements IClientConnection{
	GameManager gameManager;
	@Before
	public void setUp(){
		gameManager=new GameManager();
	}
	@Test
	public void clientRequestTest(){
		gameManager.clientRequest(this);
		assertTrue(gameManager.getListOfGame().size()==1);
		gameManager.clientRequest(this);
		assertTrue(gameManager.getListOfGame().size()==1);
		assertTrue(gameManager.getListOfGame().get(0).getClientsManager().size()==2);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			;
		}
		assertTrue(gameManager.getListOfGame().get(0).isStarted());
		gameManager.clientRequest(this);
		assertTrue(gameManager.getListOfGame().size()==2);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			;
		}
		assertFalse(gameManager.getListOfGame().get(1).isStarted());
	}

	public void start(int nPlayer, String name, Color color, Box card) {
		;
	}

	public void nextTile(Card card) {
		;
	}

	public void turnChanged(Color color) {
		;
	}

	public void updateCard(Box card) {
		;
	}

	public void rotated(Card card) {
		;
	}

	public void score(ArrayList<Player> player) {
		;
	}

	public void endGame() {
		;
	}

	public void moveNotValid() {
		;
	}

	public void lock() {
		;
	}

	public void unlock() {
		;
	}

	public void leave(Color color) {
		;
	}

	public void setGame(IModelControllerLogicGame gs) {
		;
	}

	public void endGame(ArrayList<Player> player) {
		;
	}

	public void setMyColor(Color color) {
		// TODO Auto-generated method stub
		
	}
}
