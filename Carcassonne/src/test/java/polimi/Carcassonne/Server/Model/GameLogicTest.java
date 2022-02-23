package polimi.Carcassonne.Server.Model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Server.IModelView.IEventLogicGame;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCardException;
import polimi.Carcassonne.Server.Model.Exception.ConnectionTypeMistakeException;
import polimi.Carcassonne.Server.Model.Exception.GameRuleException;
import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class GameLogicTest implements IEventLogicGame {
	GameLogic gameLogic;
	int numberOfPlayer;
	
	
	@Before
	public void setUp(){
		numberOfPlayer=5;
		gameLogic=new GameLogic(numberOfPlayer,"game1",this);
	}
	
	@Test
	public void gameLogicTest(){
		assertTrue(gameLogic.getPlayersList().size()==5);
		assertTrue(!gameLogic.getBox(0,0).isEmpty());
		
	}
	@Test
	public void insertCardTest(){
		assertTrue(gameLogic.getBox(0, 1).isEmpty());
		Card card=new Card(new BasicCard(Type.NOTHING, Type.NOTHING, Type.NOTHING, Type.NOTHING, true, true, true, true, true, true));
		//card= north:NOTHING south:NOTHING east:NOTHING west:NOTHING
		// insert second card in 0,1
		try {
			gameLogic.insertCard(gameLogic.getBox(0, 1), card);
		} catch (GameRuleException e) {
			fail();
		} catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		}
		assertTrue(!gameLogic.getBox(0, 1).isEmpty());
		assertTrue(gameLogic.getBox(0, 1).getSouth().isConnected());
		gameLogic.setPassInsertCard(true);
		
		// Try to insert third card but ConnectionTypeMistakeException
		Card card1=new Card(new BasicCard(Type.CITY, Type.NOTHING, Type.CITY, Type.CITY, true, true, true, true, true, true));
		assertTrue(gameLogic.getBox(1, 1).isEmpty());
		try {
			gameLogic.insertCard(gameLogic.getBox(1, 1), card1);
		} catch (GameRuleException e) {
			fail();
		} catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			assertTrue(true);
		}
		assertTrue(gameLogic.getBox(1, 1).isEmpty());
		// Try to insert third card but AlreadyCardException
		try {
			gameLogic.insertCard(gameLogic.getBox(0, 1), card1);
		} catch (GameRuleException e) {
			fail();
		} catch (AlreadyCardException e) {
			assertTrue(true);
		} catch (ConnectionTypeMistakeException e) {
			fail();
		}
		assertTrue(gameLogic.getBox(0, 1).getNorth().getType().equals(Type.NOTHING));
		gameLogic.setPassInsertCard(false);
		// Try to insert third card but GameRuleException
		try {
			gameLogic.insertCard(gameLogic.getBox(0, 1), card1);
			fail();
		} catch (GameRuleException e) {
			assertTrue(true);
		} catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		}
	}
	
	@Test
	public void putMarkerNorthTest(){
		Card card2=new Card(new BasicCard(Type.CITY, Type.CITY, Type.NOTHING, Type.NOTHING, true, false, false, false, false, false));
		//card2= north:CITY south:CITY east:NOTHING west:NOTHING
		
		// Try to insert marker in north
		try {
			gameLogic.insertCard(gameLogic.getBox(0, -1), card2);
			assertTrue(true);
		} catch (GameRuleException e) {
			fail();
		}
		catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		}
		gameLogic.putMarker(0);
		assertTrue(gameLogic.getBox(0,-1).getNorth().getMarker()!=null);
	}
	
	
	@Test
	public void putMarkerSouthTest(){
		Card card2=new Card(new BasicCard(Type.CITY, Type.CITY, Type.NOTHING, Type.NOTHING, true, false, false, false, false, false));
		// Try to insert marker in south
		try {
			gameLogic.insertCard(gameLogic.getBox(0, -1), card2);
			assertTrue(true);
		} catch (GameRuleException e) {
			fail();
		}
		catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		}
		gameLogic.putMarker(2);
		assertTrue(gameLogic.getBox(0,-1).getSouth().getMarker()!=null);
	}
	
	@Test
	public void putMarkerEastTest(){
		Card card2=new Card(new BasicCard(Type.CITY, Type.CITY, Type.CITY, Type.CITY, true, false, false, false, false, false));
		// Try to insert marker in east
				try {
					gameLogic.insertCard(gameLogic.getBox(0, -1), card2);
					assertTrue(true);
				} catch (GameRuleException e) {
					fail();
				}
				catch (AlreadyCardException e) {
					fail();
				} catch (ConnectionTypeMistakeException e) {
					fail();
				}
		gameLogic.putMarker(1);
		assertTrue(gameLogic.getBox(0,-1).getEast().getMarker()!=null);
	}
	
	@Test
	public void putMarkerWestTest(){
		Card card2=new Card(new BasicCard(Type.CITY, Type.CITY, Type.CITY, Type.CITY, true, false, false, false, false, false));
		// Try to insert marker in west
		try {
			gameLogic.insertCard(gameLogic.getBox(0, -1), card2);
			assertTrue(true);
		} catch (GameRuleException e) {
			fail();
		}
		catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		}
		gameLogic.putMarker(3);
		assertTrue(gameLogic.getBox(0,-1).getWest().getMarker()!=null);
	}
	
	@Test
	public void passTest(){
		Card card2=new Card(new BasicCard(Type.CITY, Type.CITY, Type.NOTHING, Type.NOTHING, true, false, false, false, false, false));
		// Try to pass
		try {
			gameLogic.insertCard(gameLogic.getBox(0, -1), card2);
			assertTrue(true);
		} catch (GameRuleException e) {
			fail();
		}
		catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		}
		gameLogic.putMarker(4);
		assertTrue(gameLogic.getBox(0,-1).getNorth().getMarker()==null);
		assertTrue(gameLogic.getBox(0,-1).getSouth().getMarker()==null);
		assertTrue(gameLogic.getBox(0,-1).getEast().getMarker()==null);
		assertTrue(gameLogic.getBox(0,-1).getWest().getMarker()==null);
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

	public void score(ArrayList<Player> listOfPlayer) {
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

	public void endGame(ArrayList<Player> listOfPlayer) {
		;
	}
}
