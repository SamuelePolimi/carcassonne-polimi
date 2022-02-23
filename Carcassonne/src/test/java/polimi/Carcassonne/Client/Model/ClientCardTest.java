package polimi.Carcassonne.Client.Model;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Client.View.TextView.CardTextWindow;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;

public class ClientCardTest {
	ClientCard clientCard;
	Coordinate c;
	CardTextWindow cardText;
	
	@Before
	public void setUp(){
		c=new Coordinate(0,1);
		clientCard=new ClientCard(c);
		cardText=new CardTextWindow(clientCard, null, 0, 0);
		clientCard.setEventReciver(cardText);
	}
	
	@Test
	public void insertNorthMarkerTest(){
		clientCard.insertNorthMarker(Color.BLUE);
		clientCard.getNorth().getPlayer().equals(Color.BLUE);
	}
	
	@Test
	public void insertSouthMarkerTest(){
		clientCard.insertSouthMarker(Color.RED);
		clientCard.getSouth().getPlayer().equals(Color.RED);
	}
	
	@Test
	public void insertEastMarkerTest(){
		clientCard.insertEastMarker(Color.YELLOW);
		clientCard.getEast().getPlayer().equals(Color.YELLOW);
	}
	
	@Test
	public void insertWestMarkerTest(){
		clientCard.insertWestMarker(Color.BLACK);
		clientCard.getWest().getPlayer().equals(Color.BLACK);
	}

}
