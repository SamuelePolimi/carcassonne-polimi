package polimi.Carcassonne.Client.Model;
import java.awt.Color;
import java.io.Serializable;

import polimi.Carcassonne.Client.IModelController.IModelControllerCard;
import polimi.Carcassonne.Client.IModelView.IEventClientCard;
import polimi.Carcassonne.Client.IModelView.IModelViewCard;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
import polimi.Carcassonne.Server.Model.Graph.Type;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class represents a card
 */
public class ClientCard implements IModelControllerCard, Serializable, IModelViewCard{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1693457520189628238L;
	private ClientCardSide north;
	private ClientCardSide south;
	private ClientCardSide east;
	private ClientCardSide west;
	private IEventClientCard event;
	private boolean northSouth=false;
	private boolean northEast=false;
	private boolean northWest=false;
	private boolean southEast=false;
	private boolean southWest=false;
	private boolean eastWest=false;
	private final Coordinate coordinate;
	//tells me if this card is empty or not
	private boolean empty;
	/**
	 * Constructor of cards
	 * @param c: coordinate
	 */
	public ClientCard(Coordinate c){
		coordinate=c;
		north=new ClientCardSide(Type.NOTHING);
		south=new ClientCardSide(Type.NOTHING);
		east=new ClientCardSide(Type.NOTHING);
		west=new ClientCardSide(Type.NOTHING);
		empty=true;
	}
	/**
	 * @return ret: a copy of the card
	 */
	public ClientCard getCopy(){
		ClientCard ret=new ClientCard(this.coordinate);
		ret.setEmpty(this.isEmpty());
		ret.setWest(this.west);
		ret.setNorth(this.north);
		ret.setEast(this.east);
		ret.setSouth(this.south);
		ret.setEastWest(this.eastWest);
		ret.setNorthEast(this.northEast);
		ret.setNorthSouth(this.northSouth);
		ret.setNorthWest(this.northWest);
		ret.setSouthEast(this.southEast);
		ret.setSouthWest(this.southWest);
		return ret;
	}
	/**
	 * Rotate a card
	 */
	public void rotate(){
		ClientCardSide support=north;
		north=west;
		west=south;
		south=east;
		east=support;
		boolean opposite = eastWest;
		eastWest=northSouth;
		northSouth=opposite;
		boolean clockwise = northEast;
		northEast=northWest;
		northWest=southWest;
		southWest=southEast;
		southEast=clockwise;
	}
	/**
	 * @return north ClientCardSide
	 */
	public ClientCardSide getNorth() {
		return north;
	}
	/**
	 * @return south ClientCardSide
	 */
	public ClientCardSide getSouth() {
		return south;
	}
	/**
	 * @return east ClientCardSide
	 */
	public ClientCardSide getEast() {
		return east;
	}
	/**
	 * @return west ClientCardSide
	 */
	public ClientCardSide getWest() {
		return west;
	}
	/**
	 * @return northSouth
	 */
	public boolean getNorthSouth() {
		return northSouth;
	}
	/**
	 * @return northEast
	 */
	public boolean getNorthEast() {
		return northEast;
	}
	/**
	 * @return northWest
	 */
	public boolean getNorthWest() {
		return northWest;
	}
	/**
	 * @return southEast
	 */
	public boolean getSouthEast() {
		return southEast;
	}
	/**
	 * @return southWest
	 */
	public boolean getSouthWest() {
		return southWest;
	}
	/**
	 * @return eastWest
	 */
	public boolean getEastWest() {
		return eastWest;
	}
	/**
	 * @param type: type to set into south
	 */
	public void setSouth(ClientCardSide type) {
		south=type;
	}
	/**
	 * @param type: type to set into north
	 */
	public void setNorth(ClientCardSide type) {
		north=type;
	}
	/**
	 * @param type: type to set into east
	 */
	public void setEast(ClientCardSide type) {
		east=type;
	}
	/**
	 * @param type: type to set into west
	 */
	public void setWest(ClientCardSide type) {
		west=type;
	}
	/**
	 * @param b: boolean to set into northSouth
	 */
	public void setNorthSouth(boolean b) {
		northSouth=b;
	}
	/**
	 * @param b: boolean to set into northEast
	 */
	public void setNorthEast(boolean b) {
		northEast=b;
	}
	/**
	 * @param b: boolean to set into northWest
	 */
	public void setNorthWest(boolean b) {
		northWest=b;
	}
	/**
	 * @param b: boolean to set into southWest
	 */
	public void setSouthEast(boolean b) {
		southEast=b;
	}
	/**
	 * @param b: boolean to set into southWest
	 */
	public void setSouthWest(boolean b) {
		southWest=b;
	}
	/**
	 * @param b: boolean to set into eastWest
	 */
	public void setEastWest(boolean b) {
		eastWest=b;
	}
	/**
	 * @return true if the card is empty
	 */
	public boolean isEmpty() {
		return empty;
	}
	/**
	 * Set empty
	 * @param empty
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
		if(event!=null){
			event.changed();
		}
	}
	/**
	 * @return coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}
	/**
	 * Insert north marker
	 * @param player
	 */
	public void insertNorthMarker(Color player){
		this.getNorth().setPlayer(player);
		event.changed();
	}
	/**
	 * Insert south marker
	 * @param player
	 */
	public void insertSouthMarker(Color player){
		this.getSouth().setPlayer(player);
		event.changed();
	}
	/**
	 * Insert east marker
	 * @param player
	 */
	public void insertEastMarker(Color player){
		this.getEast().setPlayer(player);
		event.changed();
	}
	/**
	 * Insert west marker
	 * @param player
	 */
	public void insertWestMarker(Color player){
		this.getWest().setPlayer(player);
		event.changed();
	}
	/**
	 * Set event reciver
	 * @param event 
	 */
	public void setEventReciver(IEventClientCard event){
		this.event=event;
	}
}
