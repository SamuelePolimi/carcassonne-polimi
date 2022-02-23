package polimi.Carcassonne.Server.Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Server.Model.Deck;
import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Type;

/**
 * 
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class DeckTest{

	private Deck deck;
	
	@Before
	public void setUp() {
		deck=new Deck();
	}

	@Test
	public void testGetCard(){																				
		BasicCard card0 = new BasicCard(Type.NOTHING, Type.CITY, Type.STREET, Type.STREET, false, false, false, false, false, true);
		assertTrue(deck.getCard(0).equals(card0));
		BasicCard card10 = new BasicCard(Type.CITY,Type.STREET, Type.STREET, Type.NOTHING,false,false,false,true,false,false);
		assertTrue(deck.getCard(10).equals(card10));
		assertFalse(deck.getCard(10).equals(card0));
	}

}
