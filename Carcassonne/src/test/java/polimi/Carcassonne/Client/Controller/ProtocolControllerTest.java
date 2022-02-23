package polimi.Carcassonne.Client.Controller;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Client.Model.ClientGameState;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class ProtocolControllerTest {
	ProtocolController protocolController;
	ClientGameState clientGameState;
	Coordinate c;
	ClientCard clientCard;
	
	@Before
	public void setUp(){
		c=new Coordinate(0,0);
		clientGameState=new ClientGameState();
		protocolController=new ProtocolController(clientGameState);
	}
	
	@Test
	public void reciveStartCommandTest(){
		protocolController.reciveCommand("start: N=S S=C W=N E=S NS=0 NE=0 NW=0 WE=1 SE=0 SW=0, game, red, 4");
		clientCard=clientGameState.getTable().get(c);
		assertTrue(clientCard.getNorth().getType().equals(Type.STREET));
		assertTrue(clientCard.getSouth().getType().equals(Type.CITY));
		assertTrue(clientCard.getEast().getType().equals(Type.STREET));
		assertTrue(clientCard.getWest().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getNorthSouth()==false);
		assertTrue(clientCard.getNorthEast()==false);
		assertTrue(clientCard.getNorthWest()==false);
		assertTrue(clientCard.getEastWest()==true);
		assertTrue(clientCard.getSouthEast()==false);
		assertTrue(clientCard.getSouthWest()==false);
		assertTrue(clientGameState.getListOfPlayers().size()==4);
		assertTrue(clientGameState.getThisClient().getColor().equals(Color.RED));
	}
	
	@Test
	public void reciveTurnTest(){
		protocolController.reciveCommand("start: N=N S=C W=N E=S NS=0 NE=0 NW=0 WE=1 SE=0 SW=0, game, red, 4");
		protocolController.reciveCommand("turn: blue");
		assertTrue(clientGameState.getCurrentPlayer().getColor().equals(Color.BLUE));
	}
	
	@Test
	public void reciveNextTest(){
		protocolController.reciveCommand("start: N=S S=S W=S E=S NS=0 NE=0 NW=0 WE=1 SE=0 SW=0, game, red, 4");
		protocolController.reciveCommand("next: N=S S=C W=N E=S NS=0 NE=0 NW=0 WE=1 SE=1 SW=0");
		clientCard =clientGameState.getNextCard();
		assertTrue(clientCard.getNorth().getType().equals(Type.STREET));
		assertTrue(clientCard.getSouth().getType().equals(Type.CITY));
		assertTrue(clientCard.getEast().getType().equals(Type.STREET));
		assertTrue(clientCard.getWest().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getNorthSouth()==false);
		assertTrue(clientCard.getNorthEast()==false);
		assertTrue(clientCard.getNorthWest()==false);
		assertTrue(clientCard.getEastWest()==true);
		assertTrue(clientCard.getSouthEast()==true);
		assertTrue(clientCard.getSouthWest()==false);
	}
	
	@Test
	public void reciveRotatedTest(){
		protocolController.reciveCommand("start: N=S S=S W=S E=S NS=0 NE=0 NW=0 WE=1 SE=0 SW=0, game, red, 4");
		protocolController.reciveCommand("next: N=N S=N W=N E=N NS=0 NE=0 NW=0 WE=1 SE=1 SW=0");
		clientCard=clientGameState.getNextCard();
		assertTrue(clientCard.getNorth().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getSouth().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getEast().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getWest().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getNorthSouth()==false);
		assertTrue(clientCard.getNorthEast()==false);
		assertTrue(clientCard.getNorthWest()==false);
		assertTrue(clientCard.getEastWest()==true);
		assertTrue(clientCard.getSouthEast()==true);
		assertTrue(clientCard.getSouthWest()==false);
		protocolController.reciveCommand("rotated: N=S S=C W=N E=S NS=0 NE=0 NW=0 WE=1 SE=1 SW=0");
		clientCard=clientGameState.getNextCard();
		assertTrue(clientCard.getNorth().getType().equals(Type.STREET));
		assertTrue(clientCard.getSouth().getType().equals(Type.CITY));
		assertTrue(clientCard.getEast().getType().equals(Type.STREET));
		assertTrue(clientCard.getWest().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getNorthSouth()==false);
		assertTrue(clientCard.getNorthEast()==false);
		assertTrue(clientCard.getNorthWest()==false);
		assertTrue(clientCard.getEastWest()==true);
		assertTrue(clientCard.getSouthEast()==true);
		assertTrue(clientCard.getSouthWest()==false);
	}
	
	@Test
	public void reciveUpdateTest(){
		c=new Coordinate(0,1);
		protocolController.reciveCommand("start: N=S S=S W=S E=S NS=0 NE=0 NW=0 WE=1 SE=0 SW=0, game, red, 4");
		protocolController.reciveCommand("update: N=S S=C W=N E=N NS=1 NE=1 NW=1 WE=1 SE=1 SW=1,0,1");
		clientCard=clientGameState.getTable().get(c);
		assertTrue(clientCard.getNorth().getType().equals(Type.STREET));
		assertTrue(clientCard.getSouth().getType().equals(Type.CITY));
		assertTrue(clientCard.getEast().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getWest().getType().equals(Type.NOTHING));
		assertTrue(clientCard.getNorthSouth()==true);
		assertTrue(clientCard.getNorthEast()==true);
		assertTrue(clientCard.getNorthWest()==true);
		assertTrue(clientCard.getEastWest()==true);
		assertTrue(clientCard.getSouthEast()==true);
		assertTrue(clientCard.getSouthWest()==true);
	}
	
	@Test
	public void reciveScoreTest(){
		protocolController.reciveCommand("start: N=C S=C W=C E=C NS=0 NE=0 NW=0 WE=1 SE=0 SW=0, game, red, 4");
		protocolController.reciveCommand("score: red=4, blue=4,green=6,yellow=5");
		assertTrue(clientGameState.getListOfPlayers().get(0).getPoints()==4);
		assertTrue(clientGameState.getListOfPlayers().get(1).getPoints()==4);
		assertTrue(clientGameState.getListOfPlayers().get(2).getPoints()==6);
		assertTrue(clientGameState.getListOfPlayers().get(3).getPoints()==5);
	}
}
