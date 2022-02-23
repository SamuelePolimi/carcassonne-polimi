package polimi.Carcassonne.Server.Model;

import org.junit.Before;
import org.junit.Test;
import polimi.Carcassonne.Server.Model.Table;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCardException;
import polimi.Carcassonne.Server.Model.Exception.ConnectionTypeMistakeException;
import polimi.Carcassonne.Server.Model.Exception.GameRuleException;
import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Type;
import static org.junit.Assert.*;
/**
 * 
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class TableTest {	
	Table table;

	@Before
	public void beforeTest() {
		table=new Table();
		
	}
	
	@Test
	public void insertCardTest(){
		Card card0 = new Card(new BasicCard(Type.CITY,Type.STREET,Type.CITY,Type.STREET, false, true,false,false,true,false));
		Card card1 = new Card(new BasicCard(Type.NOTHING,Type.NOTHING,Type.CITY,Type.CITY,false,false,false,false,false,true));
		Box box = table.getBox(0, 0);
		try {
			table.insertCard(box, card0);
		} catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		} catch (GameRuleException e) {
			fail();
		}
		Box box1 = table.getBox(1, 0);
		try {
			table.insertCard(box1, card1);
		} catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		} catch (GameRuleException e) {
			fail();
		}
		assertTrue(table.getBox(0, 0).getNorth().getType().equals(Type.CITY));
		assertTrue(table.getBox(0, 0).getSouth().getType().equals(Type.STREET));
		assertTrue(table.getBox(0, 0).getWest().getType().equals(Type.STREET));
		assertTrue(table.getBox(0, 0).getEast().getType().equals(Type.CITY));
		assertTrue(table.getBox(1, 0).getNorth().getType().equals(Type.NOTHING));
		assertTrue(table.getBox(1, 0).getSouth().getType().equals(Type.NOTHING));
		assertTrue(table.getBox(1, 0).getWest().getType().equals(Type.CITY));
		assertTrue(table.getBox(1, 0).getEast().getType().equals(Type.CITY));
	}
	
	@Test
	public void canInsertCardTest(){
		Card card1 = new Card(new BasicCard(Type.STREET,Type.STREET,Type.STREET,Type.STREET,false,false,false,false,false,true));
		Box box1 = table.getBox(0, 0);
		try {
			table.insertCard(box1, card1);
		} catch (AlreadyCardException e) {
			fail();
		} catch (ConnectionTypeMistakeException e) {
			fail();
		} catch (GameRuleException e) {
			fail();
		}
		Card card2 = new Card(new BasicCard(Type.CITY,Type.CITY,Type.CITY,Type.CITY,false,false,false,false,false,true));
		Box box2 = table.getBox(1,0);
		assertFalse(table.canPutCardBox(box2, card2));
		assertTrue(table.canPutCardBox(box2, card1));
	}
	
}
