package polimi.Carcassonne.Server.Model.Algorithm;
import java.util.ArrayList;
import java.util.HashMap;

import polimi.Carcassonne.Server.Model.Exception.AlreadyCoveredAreaException;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;;
/**
 * This class represent a collection of pieces of City or Streets of the same kind, connected in a closed or opened shape
 * have information about:
 * 1) OpenedSides (for understanding when it's closed-shape)
 * 2) Number of Boxes (for calculating points)
 * 3) Id (for can do matching between costruction using method equals)
 * 4) Markers (for keep in memory which player are owner of this Costruction)
 * 
 * @author Omar Scotti - Samuele Tosatto
 */
public abstract class Construction {
	private int id;
	private int numOfOpenedSides;
	private int numOfBoxes;
	private ArrayList<Marker> markers;
	private HashMap<Coordinate,Coordinate> coordinate;
	/**
	 * @param number of the construction
	 */
	public Construction(int iD){
		this.id=iD;
		//every construction has at least one open side
		this.numOfOpenedSides=1;
		this.numOfBoxes=0;
		this.markers=new ArrayList<Marker>();
		coordinate= new HashMap<Coordinate,Coordinate>();
	}
	/**
	 * @param a construction
	 */
	@Override
	public boolean equals(Object j){
		if(j instanceof Construction){
			Construction h =(Construction)j;
			return h.id==this.id;
		}
		return false;
	}
	/**
	 * this method do the Override of hashCode
	 */
	@Override
	public int hashCode(){
		return this.id;
	}
	protected void openSide(){
		this.numOfOpenedSides++;
	}
	protected void closeSide(){
		this.numOfOpenedSides--;
	}
	protected void newBox(Coordinate c){
		if(!coordinate.containsKey(c)){
			coordinate.put(c,c);
			this.numOfBoxes++;
		}
	}
	/**
	 * newC is a union between mergeC and the construction on which I invoke the method 
	 * @param mergeC is the construction that we want to join
	 * @param newC is the new construction
	 */
	protected void mergeConstruction(Construction mergeC,Construction newC){
		newC.numOfOpenedSides = mergeC.numOfOpenedSides+this.numOfOpenedSides;
		newC.coordinate=new HashMap<Coordinate,Coordinate>();
		newC.coordinate.putAll(mergeC.coordinate);
		newC.coordinate.putAll(this.coordinate);
		newC.numOfBoxes=newC.coordinate.size();
		newC.markers.addAll(mergeC.markers);
		newC.markers.addAll(this.markers);

	}
	/** 
	 * @return number of open sides
	 */
	public boolean isClosed(){
		return numOfOpenedSides==0;
	}
	
	public abstract int getPoint();
	/**
	 * @return number of boxes of the construction
	 */
	public int getNumOfBoxes(){
		return this.numOfBoxes;
	}
	/**
	 * @param new marker
	 * @throws AlreadyCoveredAreaException if the area has already a marker
	 */
	public void addMarker(Marker s) throws AlreadyCoveredAreaException{
		if(markers.isEmpty()){
			markers.add(s);
		}else{
			throw new AlreadyCoveredAreaException();
		}
	}
	/**
	 * @return markers
	 */
	public ArrayList<Marker> getMarkers(){
		return markers;
	}
	/**
	 * @return the ID of the construction
	 */
	public int getID(){
		return this.id;
	}
	
}
