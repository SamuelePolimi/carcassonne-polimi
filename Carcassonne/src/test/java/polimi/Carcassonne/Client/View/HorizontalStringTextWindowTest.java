package polimi.Carcassonne.Client.View;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Client.View.TextView.HorizontalStringTextWindow;
import polimi.Carcassonne.Client.View.TextView.TextRenderize;

public class HorizontalStringTextWindowTest {
	private HorizontalStringTextWindow ostw;
	
	@Before
	public void SetUp(){
		ostw=new HorizontalStringTextWindow("prova123",new TextRenderize(50,50),5,5);
		ostw.renderize();
	}
	
	@Test
	public void Control(){
		assertTrue(ostw.getHeight()==1);
		assertTrue(ostw.getWidth()==8);
	}
}
