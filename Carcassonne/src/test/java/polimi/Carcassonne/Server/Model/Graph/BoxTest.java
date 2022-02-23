package polimi.Carcassonne.Server.Model.Graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Server.Model.Exception.AlreadyCardException;
import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class BoxTest{
	Card card;
	Box box;
	
	@Before
	public void setUp() throws AlreadyCardException{
		box = new Box(new Coordinate(0,0));
		card= new Card(new BasicCard(Type.CITY,Type.STREET,Type.CITY,Type.STREET, false, true,false,false,true,false));
	}
	
	@Test
	public void testSetCard() throws Exception {
		assertTrue(box.getNorth().getType()==Type.NOTHING);
		assertTrue(box.getSouth().getType()==Type.NOTHING);
		assertTrue(box.getWest().getType()==Type.NOTHING);
		assertTrue(box.getEast().getType()==Type.NOTHING);
		assertTrue(box.isEmpty());
		box.setCard(card);
		assertTrue(box.getNorth().getType()!=Type.NOTHING);
		assertTrue(box.getSouth().getType()!=Type.NOTHING);
		assertTrue(box.getEast().getType()!=Type.NOTHING);
		assertTrue(box.getWest().getType()!=Type.NOTHING);
		assertTrue(!box.isEmpty());
	}
}
