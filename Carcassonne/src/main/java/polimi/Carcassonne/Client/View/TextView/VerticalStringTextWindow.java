package polimi.Carcassonne.Client.View.TextView;
/**
 * @author Omar Scotti - Samuele Tosatto
 * Print a string in vertical
 */
public class VerticalStringTextWindow extends TextWindow{
	private String s;
	/**
	 * Constructor of vertical string text view
	 * @param s: string to stanp
	 * @param r
	 * @param x
	 * @param y
	 */
	public VerticalStringTextWindow(String s,TextRenderize r, int x, int y) {
		super(r, x, y, 1, s.length());
		reSet(s);
	}
	/**
	 * Refresh
	 */
	@Override
	public void refresh() {
		refreshed();
	}

	/**
	 * Reset
	 * @param string
	 */
	@Override
	public void reSet(Object o) {
		if(o instanceof String){
			s=(String)o;
			this.setWidth(1);
			this.setHeight(s.length());
			super.cleanBuffer();
			super.putChar(s);
		}
		toRefresh();
	}
}
