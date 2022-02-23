package polimi.Carcassonne.Client.View.TextView;
/**
 * This class prints the title of the game
 * @author Omar Scotti - Samuele Tosatto
 */
public class TextTitleWindow extends TextWindow {
	private HorizontalStringTextWindow line1;
	private HorizontalStringTextWindow line2;
	private HorizontalStringTextWindow line3;
	private HorizontalStringTextWindow line4;
	public TextTitleWindow(TextRenderize r,int x, int y){
		super(r,x,y,0,0);
		reSet(null);
	}
	/**
	 * Refresh
	 */
	@Override
	public void refresh() {
		this.killAllChilds();
		this.insertToBottom(line1);
		this.insertToBottom(line2);
		this.insertToBottom(line3);
		this.insertToBottom(line4);
		refreshed();
	}
	/**
	 * Reset
	 */
	@Override
	public void reSet(Object o) {
		
		String l1="                                                         __    __    ___    __    __    ___   ___    __   _  _   _  _   ___ ";
		String l2="                                                        / _)  (  )  (  ,)  / _)  (  )  / __) / __)  /  \\ ( \\( ) ( \\( ) (  _)";
		String l3="                                                       ( (_   /__\\   )  \\ ( (_   /__\\  \\__ \\ \\__ \\ ( () ) )  (   )  (   ) _)";
		String l4="                                                        \\__) (_)(_) (_)\\_) \\__) (_)(_) (___/ (___/  \\__/ (_)\\_) (_)\\_) (___)";
		line1=new HorizontalStringTextWindow(l1, this.getMyTextRenderize(), 0, 0);
		line2=new HorizontalStringTextWindow(l2, this.getMyTextRenderize(), 0, 0);
		line3=new HorizontalStringTextWindow(l3, this.getMyTextRenderize(), 0, 0);
		line4=new HorizontalStringTextWindow(l4, this.getMyTextRenderize(), 0, 0);
		toRefresh();
	}
}
