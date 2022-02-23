package polimi.Carcassonne.Client.Model;
import java.awt.Color;
import polimi.Carcassonne.Client.IModelController.IModelControllerPlayer;
import polimi.Carcassonne.Client.IModelView.IEventClientPlayer;
import polimi.Carcassonne.Client.IModelView.IModelViewPlayer;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class represents a player
 */
public class ClientPlayer implements IModelControllerPlayer, IModelViewPlayer {
	private int points;
	private Color color;
	private int signal;
	private boolean current;
	private IEventClientPlayer event;
	/**
	 * Constructor of clientPlayer
	 * @param color
	 */
	public ClientPlayer(Color color){
		this.color=color;
		signal=7;
		points=0;
	}
	/**
	 * @return points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @return signal
	 */
	public int getMarkerNum() {
		return signal;
	}
	/**
	 * @param n: number of points
	 */
	public void setPoints(int n) {
		points=n;
		if(event!=null){
			this.event.pointsChanged();
		}
	}
	/**
	 * @param n: number of markers
	 */
	public void setMarkerNum(int n) {
		signal=n;
		if(event!=null){
			this.event.markerChanged();
		}
	}
	/**
	 * Override of toString
	 * @return color
	 */
	@Override
	public String toString(){
		if(color.equals(Color.BLACK)){
			return "Black";
		} else if(color.equals(Color.GREEN)){
			return "Green";
		}else if(color.equals(Color.RED)){
			return "Red";
		}else if(color.equals(Color.YELLOW)){
			return "Yellow";
		}else if(color.equals(Color.BLUE)){
			return "Blue";
		}
		return "";
	}
	/**
	 * @param current
	 */
	public void setCurrent(boolean current) {
			this.current=current;
			if(event!=null){
				if(current){
					this.event.beingCurrentPlayer();
				}else{
					this.event.beingNotCurrentPlayer();
				}
			}
	}
	/**
	 * @param event
	 */
	public void setEvent(IEventClientPlayer e) {
		event=e;
	}
	/**
	 * @return current: true if you are the current player
	 */
	public boolean isCurrent() {
		return current;
	}
}
