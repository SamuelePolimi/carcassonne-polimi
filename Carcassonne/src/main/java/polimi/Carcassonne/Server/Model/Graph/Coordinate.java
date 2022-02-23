package polimi.Carcassonne.Server.Model.Graph;

import java.io.Serializable;

/**
 * This class represents coordinates of boxes
 * @author Omar Scotti - Samuele Tosatto
 */
public class Coordinate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7628197249668619837L;
	private final int x;
	private final int y;
	/**
	 * Constructor of coordinates
	 * @param x
	 * @param y
	 */
	public Coordinate(int x, int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Override of equals
	 * @param coordinate
	 */
	@Override
	public boolean equals(Object t){
		if(t instanceof Coordinate){
			Coordinate  c  = ((Coordinate)t);
			return this.x==c.x && this.y == c.y;
		}
		return false;
	}
	/**
	 * Override of hashCode
	 * @return an int that represents the coordinate
	 */
	@Override
	public int hashCode(){
		return Math.abs(x+50)+Math.abs(y+50)*100;
	}
	/**
	 * @return x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return the east coordinate
	 */
	public Coordinate getMyEast(){
		return new Coordinate(this.x+1, this.y);
	}
	/**
	 * @return the west coordinate
	 */
	public Coordinate getMyWest(){
		return new Coordinate(this.x-1, this.y);
	}
	/**
	 * @return the north coordinate
	 */
	public Coordinate getMyNorth(){
		return new Coordinate(this.x, this.y+1);
	}
	/**
	 * @return the south coordinate
	 */
	public Coordinate getMySouth(){
		return new Coordinate(this.x, this.y-1);
	}
	/**
	 * Print of coordinate
	 */
	@Override
	public String toString(){
		return "(" + this.x + ","+ this.y + ")";
	}
}
