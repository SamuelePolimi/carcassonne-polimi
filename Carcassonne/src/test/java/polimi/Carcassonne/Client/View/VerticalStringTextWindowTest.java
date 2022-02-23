package polimi.Carcassonne.Client.View;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import polimi.Carcassonne.Client.View.TextView.TextRenderize;
import polimi.Carcassonne.Client.View.TextView.VerticalStringTextWindow;

public class VerticalStringTextWindowTest {
	private VerticalStringTextWindow ostw;

	@Before
	public void SetUp(){
		ostw=new VerticalStringTextWindow("prova123",new TextRenderize(50,50),5,5);
	}

	@Test
	public void renderizeTest(){
		ostw.renderize();
		assertTrue(ostw.getHeight()==8);
		assertTrue(ostw.getWidth()==1);
	}
}
