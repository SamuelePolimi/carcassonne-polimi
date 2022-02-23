package polimi.Carcassonne.Client.View;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Client.Model.ClientPlayer;
import polimi.Carcassonne.Client.View.TextView.PlayerTextWindow;
import polimi.Carcassonne.Client.View.TextView.TextRenderize;

public class PlayerTextWindowTest {
	ClientPlayer player;
	PlayerTextWindow ptw;
	TextRenderize tr;

	@Before
	public void setPlayerTextWindow(){
		player= new ClientPlayer(Color.BLACK);
		tr=new TextRenderize(100,10);
		player.setPoints(120);
		player.setPoints(4);
		ptw= new PlayerTextWindow(player,tr,0,0);
	}
	
	@Test
	public void test(){
		ptw.refresh();
		ptw.renderize();
		assertTrue(ptw.getHeight()==5);
	}
}
