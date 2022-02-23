package polimi.Carcassonne.Server.Model.Graph;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCardException;
/**
 * This Class represent a "Box" on the Table: the space where you can put a card
 * 1) It has a Coordinate absolute
 * 2) It has InternalConnection between the sides
 * 3) It has Connections for each Sides
 * 4) It can be "Empty" or "Full" (it depends by having Card or not)
 * 
 * @ Omar Scotti - Samuele Tosatto
 */
public class Box {
	// Connection on which the box is connected to the card inserted
	private final Coordinate coordinate;
	private Connection north;
	private Connection south;
	private Connection east;
	private Connection west;
	private boolean isEmpty;
	/**
	 * Create a box
	 * @param coordinate
	 */
	public Box(Coordinate coordinate){
		this.coordinate =coordinate;
		this.north=new Connection();
		this.south=new Connection();
		this.west=new Connection();
		this.east=new Connection();
		this.isEmpty=true;
	}
	/**
	 * set the card by adding connections to box
	 * @param card
	 * @throws AlreadyCardException if there is already a card
	 */
	public void setCard(Card card) throws AlreadyCardException{
		//refresh connections
		if(isEmpty){
			card.getNorth().setConnection(this.getNorth());
			card.getSouth().setConnection(this.getSouth());
			card.getEast().setConnection(this.getEast());
			card.getWest().setConnection(this.getWest());
			this.getNorth().setMyInternal(card.getNorth());
			this.getEast().setMyInternal(card.getEast());
			this.getWest().setMyInternal(card.getWest());
			this.getSouth().setMyInternal(card.getSouth());
			this.getNorth().setFinalInternalConnection();
			this.getSouth().setFinalInternalConnection();
			this.getEast().setFinalInternalConnection();
			this.getWest().setFinalInternalConnection();
			isEmpty=false;
		}else{
			throw new AlreadyCardException();
		}
	}
	/**
	 * @return if is empty
	 */
	public boolean isEmpty(){
		return isEmpty;
	}
	/**
	 * @return coordinate of the box
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}
	/**
	 * @return north connection
	 */
	public Connection getNorth() {
		return north;
	}
	/**
	 * @return south connection
	 */
	public Connection getSouth() {
		return south;
	}
	/**
	 * @return east
	 */
	public Connection getEast() {
		return east;
	}
	public Connection getWest() {
		return west;
	}
	/**
	 * @param north connection
	 */
	protected void setNorth(Connection north) {
		this.north = north;
	}
	/**
	 * @param south connection
	 */
	protected void setSouth(Connection south) {
		this.south = south;
	}
	/**
	 * @param east connection
	 */
	protected void setEast(Connection east) {
		this.east = east;
	}
	/**
	 * @param west connection
	 */
	protected void setWest(Connection west) {
		this.west = west;
	}
}
