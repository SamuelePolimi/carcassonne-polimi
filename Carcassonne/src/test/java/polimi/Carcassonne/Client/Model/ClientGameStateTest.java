package polimi.Carcassonne.Client.Model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class ClientGameStateTest{
	ClientGameState clientGame;
	ClientCard clientCard;
	Coordinate c;
	ClientPlayer player;
	
	@Before
	public void setUp(){
		c=new Coordinate(0,1);
		clientGame=new ClientGameState();
		clientCard=new ClientCard(c);
		clientCard.setNorth(new ClientCardSide(Type.CITY));
		clientCard.setEast(new ClientCardSide(Type.CITY));
		clientCard.setWest(new ClientCardSide(Type.STREET));
		clientCard.setSouth(new ClientCardSide(Type.STREET));
		clientCard.setNorthEast(true);
		clientCard.setSouthEast(true);
		player=new ClientPlayer(Color.RED);
	}
	
	@Test
	public void updateCardTest(){
		clientGame.updateCard(0, 1,clientCard);
		assertTrue(clientGame.getClientCard(0, 1).equals(clientCard));
	}
	
	@Test
	public void insertNPlayerTest(){
		clientGame.insertNPlayer(4);
		assertTrue(clientGame.getListOfPlayers().size()==4);
	}
	
	@Test
	public void setCurrentPlayerTest(){
		clientGame.insertNPlayer(4);
		clientGame.setClient(Color.RED);
		
		clientGame.setClient(Color.YELLOW);
		clientGame.setCurrentPlayer(Color.RED);
		assertTrue(clientGame.getCurrentPlayer().getColor().equals(Color.RED));
		assertFalse(clientGame.getCurrentPlayer().getColor().equals(Color.YELLOW));
	}
	
	@Test
	public void setNextCardTest(){
		clientGame.setNextCard(clientCard);
		assertTrue(clientGame.getNextCard().equals(clientCard));
	}
	
	@Test
	public void setRotatedCardTest(){
		clientGame.setRotatedCard(clientCard);
		assertTrue(clientGame.getNextCard().equals(clientCard));
	}
	
	@Test
	public void setClientTest(){
		clientGame.insertNPlayer(4);
		clientGame.setClient(Color.RED);
		clientGame.setClient(Color.BLUE);
		assertTrue(clientGame.getThisClient().getColor().equals(Color.BLUE));
	}
	
	@Test
	public void setScoreTest(){
		clientGame.insertNPlayer(5);
		clientGame.setScore(Color.YELLOW, 5);
		clientGame.setScore(Color.BLACK, 7);
		clientGame.setScore(Color.YELLOW,9);
		assertTrue(clientGame.getListOfPlayers().get(3).getPoints()==9);// Yellow
		assertTrue(clientGame.getListOfPlayers().get(4).getPoints()==7);// Black
	}
	
	@Test
	public void setAllScoreTest(){
		clientGame.insertNPlayer(4);
		Color colors[] = new Color[]{Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW};
		int[] points= new int[]{2,3,4,6};
		clientGame.setScore(colors, points);
		clientGame.setScore(Color.RED,8);
		assertTrue(clientGame.getListOfPlayers().get(0).getPoints()==8);
		assertTrue(clientGame.getListOfPlayers().get(1).getPoints()==3);
		assertTrue(clientGame.getListOfPlayers().get(2).getPoints()==4);
		assertTrue(clientGame.getListOfPlayers().get(3).getPoints()==6);
		// with a not order list of colors:
		colors = new Color[]{Color.YELLOW,Color.GREEN,Color.BLUE,Color.RED};
		clientGame.setScore(colors, points);
		assertTrue(clientGame.getListOfPlayers().get(0).getPoints()==6);
		assertTrue(clientGame.getListOfPlayers().get(1).getPoints()==4);
		assertTrue(clientGame.getListOfPlayers().get(2).getPoints()==3);
		assertTrue(clientGame.getListOfPlayers().get(3).getPoints()==2);
	}
}
