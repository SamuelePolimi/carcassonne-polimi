package polimi.Carcassonne.Server.Model;

import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import polimi.Carcassonne.Server.Model.Player;
/**
 * 
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class PlayerTest{
	Player player;
	/**
	 * 
	 */
	@Before
	public void setUp(){
		player=new Player(Color.blue);
	}
	/**
	 * 
	 */
	@Test
	public void controlMarkerRemove(){
	for(int i=0;i<7;i++) player.removeMarkers();
	assertTrue(player.isMarkersEmpty());
	}
}
