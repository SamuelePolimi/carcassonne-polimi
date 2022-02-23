package polimi.Carcassonne.Server.Model.Graph;

import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Connection;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
import polimi.Carcassonne.Server.Model.Graph.Type;
import junit.framework.TestCase;

public class ConnectionTest extends TestCase {
	
	public ConnectionTest(String test){
		super(test);
		
	}
	
	public void  testTryConnectTwoBox() throws Exception{
		//create box (0,0)
		Box box = new Box(new Coordinate(0,0));
		Box box1 = new Box(new Coordinate(0,1));
		box.setNorth(new Connection(Type.CITY));
		box1.setSouth(new Connection(Type.CITY));
		box.getNorth().connect(box1.getSouth());
		box1.getSouth().connect(box.getNorth());
		
		assertTrue(box.getNorth().isConnected());
		assertTrue(box1.getSouth().isConnected());
		assertTrue(box.getNorth().getNeighbor()==box1.getSouth());
	}
}
