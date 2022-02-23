package polimi.Carcassonne.Client.View.TextView;
import polimi.Carcassonne.Client.IModelView.IEventClientPlayer;
import polimi.Carcassonne.Client.IModelView.IModelViewPlayer;
/**
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class PlayerTextWindow extends TextWindow implements IEventClientPlayer {
	private HorizontalStringTextWindow topBorder;
	private HorizontalStringTextWindow bottomBorder;
	private VerticalStringTextWindow leftBorder;
	private VerticalStringTextWindow rightBorder;
	private HorizontalStringTextWindow point;
	private HorizontalStringTextWindow marker;
	private HorizontalStringTextWindow name;
	private IModelViewPlayer player;
	/**
	 * 
	 * @param player
	 * @param r
	 * @param x
	 * @param y
	 */
	public PlayerTextWindow(IModelViewPlayer player,TextRenderize r, int x, int y) {
		super(r, x, y,0,0);
		reSet(player);
	}
	/**
	 * 
	 */
	@Override
	public void refresh() {
		super.killAllChilds();
		super.insertToBottom(topBorder);
		super.insertToBottom(name);
		super.insertToBottom(point);
		super.insertToBottom(marker);
		super.insertToRight(rightBorder);
		super.insertToBottom(bottomBorder);
		super.insertTextWindow(leftBorder);
		refreshed();
	}
	/**
	 * 
	 */
	@Override
	public void reSet(Object o) {
		if(o instanceof IModelViewPlayer){
			IModelViewPlayer player=(IModelViewPlayer)o;
			this.player=player;
			this.player.setEvent(this);
			name = new HorizontalStringTextWindow(" Color: "+player.toString(),super.getMyTextRenderize(),1,0);
			point = new HorizontalStringTextWindow(" Point: "+player.getPoints(),super.getMyTextRenderize(),1,0);
			marker = new HorizontalStringTextWindow(" Markers: "+player.getMarkerNum(),super.getMyTextRenderize(),1,0);
			if(player.isCurrent()){
				beingCurrentPlayer();
			}else{
				beingNotCurrentPlayer();
			}
		}
		refresh();
		toRefresh();
	}
	/**
	 * 
	 */
	public void pointsChanged() {
		point = new HorizontalStringTextWindow(" Point: "+player.getPoints(),super.getMyTextRenderize(),1,0);
		
		toRefresh();
	}
	/**
	 * 
	 */
	public void markerChanged() {
		marker = new HorizontalStringTextWindow(" Markers: "+player.getMarkerNum(),super.getMyTextRenderize(),1,0);
		toRefresh();
	}
	/**
	 * 
	 */
	public void beingCurrentPlayer() {
		rightBorder = new VerticalStringTextWindow("+###",super.getMyTextRenderize(),0,0);
		leftBorder = new VerticalStringTextWindow("+###",super.getMyTextRenderize(),0,0);
		bottomBorder = new HorizontalStringTextWindow("+#############################################+",super.getMyTextRenderize(),0,0);
		topBorder = new HorizontalStringTextWindow("+#############################################",super.getMyTextRenderize(),0,0);
		toRefresh();
	}
	/**
	 * 
	 */
	public void beingNotCurrentPlayer() {
		rightBorder = new VerticalStringTextWindow("+...",super.getMyTextRenderize(),0,0);
		leftBorder = new VerticalStringTextWindow("+...",super.getMyTextRenderize(),0,0);
		bottomBorder = new HorizontalStringTextWindow("+---------------------------------------------+",super.getMyTextRenderize(),0,0);
		topBorder = new HorizontalStringTextWindow("+---------------------------------------------",super.getMyTextRenderize(),0,0);
		toRefresh();
	}
}
