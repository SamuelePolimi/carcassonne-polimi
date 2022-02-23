package polimi.Carcassonne.Client.View.TextView;
import java.util.ArrayList;
/**
 * @author Omar Scotti - Samuele Tosatto
 * Prints the window
 */
public abstract class TextWindow {
	private TextRenderize textRenderize;
	private ArrayList<TextWindow> mySubWindows;
	//Absolute Position
	private int xPosAbs;
	private int yPosAbs;
	//Relative Position
	private int xPosRel;
	private int yPosRel;
	private int width;
	private int height;
	private StringBuffer buffer;
	private boolean toRefresh;
	/**
	 * This is base-constructor
	 * @param r is the text-renderize
	 * @param x is the absolute x position respect renderize
	 * @param y is the absolute y position respect renderize
	 * @param width is the number of char on x
	 * @param height is the number of char on y
	 */
	public TextWindow(TextRenderize r, int x, int y, int width, int height){
		textRenderize=r;
		xPosAbs=x;
		xPosRel=x;
		yPosAbs=y;
		yPosRel=y;
		this.width=width;
		this.height=height;
		buffer=new StringBuffer();
		mySubWindows=new ArrayList<TextWindow>();
		toRefresh=false;
	}
	/**
	 * Refresh has to populate subelement
	 * 1) killchilds
	 * 2) insert all textWindows as necessary
	 */
	public abstract void refresh();
	/**
	 * has to build the control
	 */
	public abstract void reSet(Object o);
	/**
	 * this method permit to add a sub-element
	 * @param w is text-window to insert as a child
	 */
	public void insertTextWindow(TextWindow w){
		w.xPosAbs=this.xPosAbs+w.xPosRel;
		w.yPosAbs=this.yPosAbs+w.yPosRel;
		mySubWindows.add(w);
		if(height<w.yPosRel+w.height) {
			height=w.yPosRel+w.height;
		}
		if(width<w.xPosRel+w.width){
			width=w.xPosRel+w.width;
		}
		w.toRefresh();
	}
	/**
	 * insert text window
	 * @param w text window
	 * @return x-position relative
	 */
	public int insertToRight(TextWindow w){
		w.xPosRel=width;
		insertTextWindow(w);
		return width;
	}
	/**
	 * insert text window with offset
	 * @param w
	 * @param offset
	 * @return
	 */
	public int insertToRight(TextWindow w, int offset){
		w.xPosRel=width+offset;
		insertTextWindow(w);
		return width;
	}
	/**
	 * insert text window on the bottom
	 * @param w is the text-window
	 * @return y-position relative
	 */
	public int insertToBottom(TextWindow w){
		w.yPosRel=height;
		insertTextWindow(w);
		return height;
	}
	/**
	 * insert text window on the bottom with offset
	 * @param w
	 * @param offset
	 * @return
	 */
	public int insertToBottom(TextWindow w, int offset){
		w.yPosRel=height+offset;
		insertTextWindow(w);
		return height;
	}
	//this overload method permit to add a string or a char to buffer.
	//this method is normally called by refresh
	/**
	 * puts a char in the window
	 * @param c
	 */
	protected void putChar(char c){
		buffer.append(c);
	}
	/**
	 * puts a string in the window
	 * @param s
	 */
	protected void putChar(String s){
		buffer.append(s);
	}
	/**
	 * clean the buffer to renderize
	 */
	protected void cleanBuffer(){
		buffer=new StringBuffer();
	}
	/**
	 * kill all childs (sub-windows)
	 */
	protected void killAllChilds(){
		mySubWindows=new ArrayList<TextWindow>();
		this.height=0;
		this.width=0;
	}
	/**
	 * Renderize
	 */
	public void renderize(){
		if(toRefresh){
			this.refresh();
			toRefresh();
		}
		for(TextWindow w: mySubWindows){
			w.renderize();
		}
		if(toRefresh){
			this.refresh();
		}
		String s = buffer.toString();
		for(int i=yPosAbs;i<yPosAbs+height;i++){
			for(int j=xPosAbs;j<xPosAbs+width;j++){
				if(s.length()>j-xPosAbs+((i-yPosAbs)*width)){
					textRenderize.setCharAt(j, i, s.charAt(j-xPosAbs+(i-yPosAbs)*width));
				}
			}
		}
	}
	/**
	 * @return text renderize
	 */
	public TextRenderize getMyTextRenderize(){
		return textRenderize;
	}
	/**
	 * @return width
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * @return height
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * sets width
	 * @param width
	 */
	public void setWidth(int width){
		this.width=width;
	}
	/**
	 * sets height
	 * @param height
	 */
	public void setHeight(int height){
		this.height=height;
	}
	/**
	 * Refresh
	 */
	public void toRefresh(){
		toRefresh=true;
	}
	/**
	 * refresh
	 */
	public void refreshed(){
		toRefresh=false;
	}
	/**
	 * Set x
	 * @param x
	 */
	public void setX(int x){
		this.xPosRel=x;
	}
	/**
	 * Set y
	 * @param y
	 */
	public void setY(int y){
		this.yPosRel=y;
	}
}
