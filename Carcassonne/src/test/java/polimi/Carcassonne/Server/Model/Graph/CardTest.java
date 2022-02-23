package polimi.Carcassonne.Server.Model.Graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class CardTest{
	
private Card card1;
private BasicCard basicCard;
	
	@Before
	public void setUp(){
		basicCard= new BasicCard(Type.CITY,Type.STREET,Type.CITY,Type.STREET, false, true,false,false,true,false);
	}
	
	@Test
	public void testCard(){
		Card card1=new Card(basicCard);
		assertTrue(basicCard.getNorth()==card1.getNorth().getType());
		assertTrue(basicCard.getSouth()==card1.getSouth().getType());
		assertTrue(basicCard.getEast()==card1.getEast().getType());
		assertTrue(basicCard.getWest()==card1.getWest().getType());
		assertTrue(card1.getNorth().getClockwise()==card1.getEast());
		assertTrue(card1.getNorth().getCounterclockwise()==null);
		assertTrue(card1.getNorth().getOpposite()==null);
		assertTrue(card1.getSouth().getClockwise()==card1.getWest());
		assertTrue(card1.getSouth().getCounterclockwise()==null);
		assertTrue(card1.getSouth().getOpposite()==null);
		assertTrue(card1.getEast().getClockwise()==null);
		assertTrue(card1.getEast().getCounterclockwise()==card1.getNorth());
		assertTrue(card1.getEast().getOpposite()==null);
		assertTrue(card1.getWest().getClockwise()==null);
		assertTrue(card1.getWest().getCounterclockwise()==card1.getSouth());
		assertTrue(card1.getWest().getOpposite()==null);
		
		
	}
	
	@Test
	public void testRotateClockwise() throws Exception {
				card1=new Card(basicCard);
				Type t1= card1.getNorth().getType();
				Type t2=card1.getSouth().getType();
				Type t3 =card1.getEast().getType();
				Type t4=card1.getWest().getType();
				
				card1.rotateClockwise();
				card1.rotateClockwise();
				card1.rotateClockwise();
				card1.rotateClockwise();
				assertTrue(t1==card1.getNorth().getType());
				assertTrue(t2==card1.getSouth().getType());
				assertTrue(t3==card1.getEast().getType());
				assertTrue(t4==card1.getWest().getType());
				
				card1.rotateClockwise();
				assertTrue(t1==card1.getEast().getType());
				assertTrue(t2==card1.getWest().getType());
				assertTrue(t3==card1.getSouth().getType());
				assertTrue(t4==card1.getNorth().getType());			
	}
}
