package polimi.Carcassonne.Client.View;
import polimi.Carcassonne.Client.View.GUIView.SwingTotal;
import polimi.Carcassonne.Client.View.TextView.TextRenderize;
import polimi.Carcassonne.Client.View.TextView.TotalWindow;
import polimi.Carcassonne.Client.View.TextView.View;

/**
 * Gets a kind of Graphics
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class GraphicsStrategy {
	/**
	 * get textual interface
	 * @return Graphics
	 */
	public IView getTextGraphics(){
		TotalWindow total = new TotalWindow(new TextRenderize(1000,1000),0,0);
		total.setOnlineModality(false);
		return new View(total);
	}
	/**
	 * get GUI Graphics
	 * @return
	 */
	public IView getGUIGraphics(){
		return new SwingTotal();
	}
}
