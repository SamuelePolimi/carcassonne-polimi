package polimi.Carcassonne.Server.Model.Algorithm;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Algorithm.City;
import polimi.Carcassonne.Server.Model.Algorithm.Construction;
import polimi.Carcassonne.Server.Model.Algorithm.Marker;
import polimi.Carcassonne.Server.Model.Algorithm.Street;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCoveredAreaException;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;

public class ConstructionTest {
	
	Construction city1;
	Construction city2;
	Construction street1;
	Construction street2;
	Construction city3;
	Construction street3;
	@Before
	public void setUp(){
		this.city1=new City(1);
		this.city2=new City(2);
		this.street1=new Street(3);
		this.street2=new Street(4);
		this.city3=new City(5);
		this.street3=new City(6);
	}
	@Test
	public void equalsTest(){
		assertTrue("Different city",city1.equals(city1));
		assertFalse("Street equals to city??",city1.equals(street1));
	}
	@Test
	public void newBoxTest(){
		assertTrue("No zero boxes in city",city1.getNumOfBoxes()==0);
		assertTrue("No zero boxes in street",street1.getNumOfBoxes()==0);
		for(int i=0;i<4;i++){ 
			city1.newBox(new Coordinate(0,0));
			street1.newBox(new Coordinate(1,0));
		}
		assertTrue("No four boxes in city",city1.getNumOfBoxes()==1);
		assertTrue("No four boxes in street",street1.getNumOfBoxes()==1);
	}
	@Test
	public void getPointTest(){
		assertTrue("Points not correct in city",city1.getPoint()==2*city1.getNumOfBoxes());
		assertTrue("Points not correct in street",street1.getPoint()==street1.getNumOfBoxes());
	}
	@Test
	public void mergeConstructionTest() throws AlreadyCoveredAreaException{
		city1.addMarker(new Marker(new Player(Color.BLUE)));
		int boxesCity=city1.getNumOfBoxes()+city2.getNumOfBoxes();
		int markersCity=city1.getMarkers().size()+city2.getMarkers().size();
		int boxesStreet=street1.getNumOfBoxes()+street2.getNumOfBoxes();
		int markersStreet=street1.getMarkers().size()+street2.getMarkers().size();
		city1.mergeConstruction(city2, city3);
		assertTrue("Different number of boxes in city3",city3.getNumOfBoxes()==boxesCity);
		assertTrue("Different number of markers in city3",city3.getMarkers().size()==markersCity);
		assertTrue("Different number of boxes in street3",street3.getNumOfBoxes()==boxesStreet);
		assertTrue("Different number of markers in street3",street3.getMarkers().size()==markersStreet);
	}
	@Test
	public void addMarkerTest(){
		city3=new City(7);
		try{
			city3.addMarker(new Marker(new Player(Color.BLUE)));
			assert(true);
			city3.addMarker(new Marker(new Player(Color.BLUE)));
			assert(false);
		}
		catch(AlreadyCoveredAreaException e){
			assertTrue(true);
		}
		assertTrue(city3.getMarkers().size()==1);
	}
}
