package polimi.Carcassonne.Client.View.TextView;
/**
 * @author Omar Scotti - Samuele Tosatto
 * 
 */
public class HorizontalStringTextWindow extends TextWindow {
	private String s;
	/**
	 * Constructor of Horizontal string text window
	 * @param s: string to print in horizontal
	 * @param r
	 * @param x
	 * @param y
	 */
	public HorizontalStringTextWindow(String s,TextRenderize r, int x, int y) {
		super(r, x, y, s.length(), 1);
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
	 * reSet
	 */
	@Override
	public void reSet(Object o) {
		if(o instanceof String){
			s=(String)o;
			this.setWidth(s.length());
			this.setHeight(1);
			super.cleanBuffer();
			super.putChar(s);
		}	
		toRefresh();
	}
}
