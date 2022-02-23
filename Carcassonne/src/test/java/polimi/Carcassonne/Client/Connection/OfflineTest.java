package polimi.Carcassonne.Client.Connection;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Client.Model.ClientGameState;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;

public class OfflineTest {
	ClientGameState clientGame;
	Offline offline;
	Coordinate c;
	Box box;
	
	@Before
	public void setUp(){
		clientGame=new ClientGameState();
		offline=new Offline(clientGame);
		c=new Coordinate(0,0);
		box=new Box(c);
		//card= north:CITY south:STREET east:STREET west:NOTHING
		//(new BasicCard(Type.CITY,Type.STREET, Type.STREET, Type.NOTHING,false,false,false,true,false,false));
	}
	
	@Test
	public void turnChangedTest(){
		clientGame.insertNPlayer(4);
		clientGame.setClient(Color.RED);
		offline.turnChanged(Color.RED);
		assertTrue(clientGame.getCurrentPlayer().getColor().equals(Color.RED));
		
	}
	
	@Test
	public void startTest(){
		offline.start(4, "game1", Color.RED, box);
		assertTrue(clientGame.getListOfPlayers().size()==4);
		clientGame.getTable().get(c).equals(box);
	}
	
	@Test
	public void updateCardTest(){
		offline.updateCard(box);
		clientGame.getTable().get(c).equals(box);
	}
	
	@Test
	public void scoreTest(){
		ArrayList<Player> players=new ArrayList<Player>();
		clientGame.insertNPlayer(2);
		Player player1=new Player(Color.RED);
		player1.addPoint(20);
		Player player2=new Player(Color.BLUE);
		player2.addPoint(13);
		players.add(player1);
		players.add(player2);
		offline.score(players);
		assertTrue(clientGame.getListOfPlayers().get(0).getPoints()==20);
		assertTrue(clientGame.getListOfPlayers().get(1).getPoints()==13);
		Player player3=new Player(Color.GREEN);
		player3.addPoint(22);
		assertTrue(clientGame.getListOfPlayers().size()==2);
	}
	
}
