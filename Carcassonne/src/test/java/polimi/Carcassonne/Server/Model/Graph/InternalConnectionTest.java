package polimi.Carcassonne.Server.Model.Graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class InternalConnectionTest{
	InternalConnection internal1;
	InternalConnection internal2;

	@Before
	public void setUp(){
		internal1=new InternalConnection(Type.CITY);
		internal2=new InternalConnection(Type.STREET);
	}
	
	@Test
	public void testEquals(){
		internal1.setClockwise(new InternalConnection(Type.STREET));
		internal1.setCounterclockwise(new InternalConnection(Type.CITY));
		internal1.setOpposite(new InternalConnection(Type.NOTHING));
		assertTrue(internal1.equals(internal1));
		assertFalse(internal1.equals(internal2));
		internal1.setFinalInternalConnection();
		internal1.setClockwise(new InternalConnection(Type.STREET));
		assertTrue(internal1.getClockwise().getType().equals(Type.STREET));
	}
}
