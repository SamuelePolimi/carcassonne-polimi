package polimi.Carcassonne.Server.Model;
import java.awt.Color;
import java.util.ArrayList;
import polimi.Carcassonne.Server.Model.Algorithm.Marker;
/**
 * This class represents a player
 * This class has color, points and markers of a player
 * @author Omar Scotti - Samuele Tosatto
 */
public class Player {
	private final Color color;
	private int point=0;
	private ArrayList<Marker> markers= new ArrayList<Marker>();
	/**
	 * Constructor of player
	 * @param name
	 * @param color
	 */
	public Player( Color color){
			this.color = color;
			for(int i=0;i<7;i++) {
				markers.add(new Marker(this));
			}
		}
	/**
	 * Remove one marker from markers of player
	 * @return marker
	 */
	public Marker removeMarkers(){
		return markers.remove(0);
	}
	/**
	 * @return true if player hasn't markers else false
	 */
	public boolean isMarkersEmpty(){
		if(markers.isEmpty()) {
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * @return points of the player
	 */
	public int getPoint() {
		return point;
	}
	/**
	 * @return color of the player
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @return number of remaining counters
	 */
	public int getNumberOfMarkers(){
		return markers.size();
	}
	/**
	 * Add marker to player's markers
	 * @param marker to add
	 */
	public void addMarker(Marker marker){
		if(markers.size()<7){
			markers.add(marker);
		}
	}
	/**
	 * Add points to player
	 * @param points
	 */
	public void addPoint(int point){
		this.point=this.point+point;
	}
	/**
	 * override hashCode
	 * @return int
	 */
	@Override
	public int hashCode(){
		return this.color.hashCode();
	}
	/**
	 * override equals
	 * @param player
	 * @return true or false
	 */
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Player){
			return this.color.equals(((Player)obj).getColor());
		}else{
			return false;
		}
		
	}
	/**
	 * @return string of the player's color
	 */
	@Override
	public String toString(){
		String ret="";
		if(this.color==Color.RED){
			return "R";
		}
		else if (this.color==Color.BLUE) {
			return "B";
		}
		else if (this.color==Color.YELLOW){
			return "Y";
		}
		else if (this.color==Color.GREEN){
			return "G";
		}
		else if (this.color==Color.BLACK) {
			return "K";
		}
		return ret;
	}
}
