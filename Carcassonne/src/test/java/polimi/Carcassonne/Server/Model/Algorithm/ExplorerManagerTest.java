package polimi.Carcassonne.Server.Model.Algorithm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.Model.Algorithm.ExplorerManager;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCardException;
import polimi.Carcassonne.Server.Model.Exception.ConnectionAlreadyFixedException;
import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class ExplorerManagerTest {
	
	Box box0;
	Box box1;
	Card card0;
	Card card1;
	ExplorerManager exMan;
	
	@Before
	public void setUp(){
		exMan = new ExplorerManager();
		
		box0 = new Box(new Coordinate(0,0));
		box1 = new Box(new Coordinate(1,0));
		
		card0= new Card(new BasicCard(Type.CITY,Type.STREET,Type.CITY,Type.STREET, false, true,false,false,true,false));
		card1= new Card(new BasicCard(Type.NOTHING,Type.NOTHING,Type.CITY,Type.CITY,false,false,false,false,false,true));
		try {
			box0.setCard(card0);
		} catch (AlreadyCardException e) {
			Printer.print("SRV<ExplorerManager>: AlreadyCardException!");
		}
	}
	
	@Test
	public void testManageExplorerBox(){
		
		// One box
		assertTrue("Northern explorer",box0.getNorth().getExplorer()==null);
		assertTrue("Southern explorer",box0.getSouth().getExplorer()==null);
		assertTrue("Eastern explorer",box0.getEast().getExplorer()==null);
		assertTrue("Western explorer",box0.getWest().getExplorer()==null);
		exMan.manageExplorersBox(box0);
		

		assertTrue("No northern explorer",box0.getNorth().getExplorer()!=null);
		assertTrue("No southern explorer",box0.getSouth().getExplorer()!=null);
		assertTrue("No eastern explorer",box0.getEast().getExplorer()!=null);
		assertTrue("No western explorer",box0.getWest().getExplorer()!=null);
		
		assertTrue("Different explorer north-east",box0.getNorth().getExplorer().equals(box0.getEast().getExplorer()));
		assertTrue("Different explorer north-south",!box0.getNorth().getExplorer().equals(box0.getSouth().getExplorer()));
		assertTrue("Different explorer south-west",box0.getSouth().getExplorer().equals(box0.getWest().getExplorer()));
		assertTrue("Different explorer east-south",!box0.getSouth().getExplorer().equals(box0.getEast().getExplorer()));
		
		
		// Two boxes
		try {
			box0.getEast().connect(box1.getWest());
			box1.getWest().connect(box0.getEast());
		} catch (ConnectionAlreadyFixedException e1) {
			Printer.print("SRV<ExplorerManager>: AlreadyFixedException!");
		}
		try {
			box1.setCard(card1);
		} catch (AlreadyCardException e) {
			Printer.print("SRV<ExplorerManager>: AlreadyCardException!");
		}
		
		exMan.manageExplorersBox(box1);
		
		assertTrue("Northern explorer",box1.getNorth().getExplorer()==null);
		assertTrue("Southern explorer",box1.getSouth().getExplorer()==null);
		assertTrue("No Eastern explorer",box1.getEast().getExplorer()!=null);
		assertTrue("No Western explorer",box1.getWest().getExplorer()!=null);
		
		assertTrue("Different explorer west-east",box1.getWest().getExplorer().equals(box0.getEast().getExplorer()));
		assertTrue("Different explorer west-north",box1.getWest().getExplorer().equals(box0.getNorth().getExplorer()));
	}
	
	@Test
	public void testClearClosedConstructionBuffer(){
		assertTrue("Not empty",exMan.clearClosedConstructionBuffer().size()==0);
		try {
			box0.getEast().connect(box1.getWest());
		} catch (ConnectionAlreadyFixedException e) {
			;
		}
		exMan.manageExplorersBox(box0);
		try {
			box1.getWest().connect(box0.getEast());
		} catch (ConnectionAlreadyFixedException e) {
		}
		exMan.manageExplorersBox(box1);
		assertTrue("Empty",exMan.clearClosedConstructionBuffer().size()==0);
	}
	
}
