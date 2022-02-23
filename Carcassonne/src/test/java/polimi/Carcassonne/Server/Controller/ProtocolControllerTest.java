package polimi.Carcassonne.Server.Controller;

import static org.junit.Assert.*;
import java.rmi.RemoteException;
import org.junit.Before;
import org.junit.Test;
import polimi.Carcassonne.Client.Controller.CommandController;
import polimi.Carcassonne.Server.Model.GameLogic;
import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class ProtocolControllerTest {
	CommandController commandController;
	ProtocolController protocolController;
	GameLogic gameLogic;
	Game game;
	Card card;
	
	@Before
	public void setUp(){
		game=new Game("Game0");
		gameLogic=new GameLogic(4,"game",game);
		protocolController=new ProtocolController(gameLogic);
	}
	
	
	@Test
	public void recivePlaceCommandTest(){
		card=new Card(new BasicCard(Type.CITY,Type.CITY,Type.CITY,Type.CITY,true,true,true,true,true,true));
		gameLogic.setNextCard(card);
		
		commandController=new CommandController("place:0,-1");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertTrue(gameLogic.getBox(0,-1).getNorth().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getSouth().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getEast().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getWest().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getNorth().getOpposite().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getNorth().getClockwise().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getNorth().getCounterclockwise().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getSouth().getOpposite().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getSouth().getClockwise().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getSouth().getCounterclockwise().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getEast().getOpposite().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getEast().getClockwise().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getEast().getCounterclockwise().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getWest().getOpposite().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getWest().getClockwise().getType().equals(Type.CITY));
		assertTrue(gameLogic.getBox(0,-1).getWest().getCounterclockwise().getType().equals(Type.CITY));	
	}
	
	@Test
	public void reciveRotateCommandTest(){
		Card card=new Card(new BasicCard(Type.CITY,Type.STREET,Type.STREET,Type.CITY,true,true,true,true,true,true));
		gameLogic.setNextCard(card);
		commandController=new CommandController("rotate");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertTrue(card.equals(gameLogic.getNextCard()));
	}
	
	@Test
	public void recivePassCommandTest(){
		card=new Card(new BasicCard(Type.CITY,Type.CITY,Type.CITY,Type.CITY,true,true,true,true,true,true));
		gameLogic.setNextCard(card);
		
		commandController=new CommandController("place:0,-1");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertFalse(gameLogic.getPassInsertCard());
		commandController=new CommandController("pass");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertNull(gameLogic.getBox(0, -1).getNorth().getMarker());
		assertNull(gameLogic.getBox(0, -1).getWest().getMarker());
		assertNull(gameLogic.getBox(0, -1).getSouth().getMarker());
		assertNull(gameLogic.getBox(0, -1).getEast().getMarker());
		assertTrue(gameLogic.getPassInsertCard());
	}
	
	@Test
	public void reciveTileWestCommandTest(){
		card=new Card(new BasicCard(Type.CITY,Type.CITY,Type.CITY,Type.CITY,true,true,true,true,true,true));
		gameLogic.setNextCard(card);
		
		commandController=new CommandController("place:0,-1");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		commandController=new CommandController("tile: west");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertTrue(gameLogic.getBox(0, -1).getWest().getMarker()!=null);
		assertTrue(gameLogic.getBox(0, -1).getSouth().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getNorth().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getEast().getMarker()==null);
	}
	
	@Test
	public void reciveTileEastCommandTest(){
		card=new Card(new BasicCard(Type.CITY,Type.CITY,Type.CITY,Type.CITY,true,true,true,true,true,true));
		gameLogic.setNextCard(card);
		
		commandController=new CommandController("place:0,-1");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		commandController=new CommandController("tile: east");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertTrue(gameLogic.getBox(0, -1).getEast().getMarker()!=null);
		assertTrue(gameLogic.getBox(0, -1).getSouth().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getNorth().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getWest().getMarker()==null);
	}
	
	@Test
	public void reciveTileSouthCommandTest(){
		card=new Card(new BasicCard(Type.CITY,Type.CITY,Type.CITY,Type.CITY,true,true,true,true,true,true));
		gameLogic.setNextCard(card);
		
		commandController=new CommandController("place:0,-1");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		commandController=new CommandController("tile: south");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertTrue(gameLogic.getBox(0, -1).getSouth().getMarker()!=null);
		assertTrue(gameLogic.getBox(0, -1).getNorth().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getEast().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getWest().getMarker()==null);
	}
	
	@Test
	public void reciveTileNorthCommandTest(){
		card=new Card(new BasicCard(Type.CITY,Type.CITY,Type.CITY,Type.CITY,true,true,true,true,true,true));
		gameLogic.setNextCard(card);
		
		commandController=new CommandController("place:0,-1");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		commandController=new CommandController("tile: north");
		try {
			protocolController.reciveCommand(commandController);
		} catch (RemoteException e) {
			;
		}
		assertTrue(gameLogic.getBox(0, -1).getNorth().getMarker()!=null);
		assertTrue(gameLogic.getBox(0, -1).getSouth().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getEast().getMarker()==null);
		assertTrue(gameLogic.getBox(0, -1).getWest().getMarker()==null);
	}
}
