package polimi.Carcassonne.Server.Model.Graph;
/**
 * This class represent a very simple Card of the game:
 * 1) Defines what's the type on North South East and West
 * 2) Defines with booleans the connection between the sides
 * 3) CANNOT turned
 * 4) Don't knows anything about external world
 * 
 * @author Omar Scotti - Samuele Tosatto
 */
public class BasicCard {
	private final Type north;
	private final Type south;
	private final Type east;
	private final Type west;
	private final boolean northSouth;
	private final boolean northEast;
	private final boolean northWest;
	private final boolean southEast;
	private final boolean southWest;
	private final boolean eastWest;
	/**
	 * Initialize basic card
	 * @param north
	 * @param south
	 * @param east
	 * @param west
	 * @param northSouth
	 * @param northEast
	 * @param northWest
	 * @param southEast
	 * @param south_West
	 * @param east_West
	 */
	public BasicCard(Type north, Type south, Type east, Type west, boolean northSouth, boolean northEast, boolean northWest, boolean southEast, boolean southWest, boolean eastWest){
		this.north=north;
		this.south=south;
		this.east=east;
		this.west=west;
		this.northSouth=northSouth;
		this.northWest=northWest;
		this.northEast=northEast;
		this.southEast=southEast;
		this.southWest=southWest;
		this.eastWest=eastWest;
	}
	/**
	 * @return north
	 */
	public Type getNorth() {
		return north;
	}
	/**
	 * @return south
	 */
	public Type getSouth() {
		return south;
	}
	/**
	 * @return east
	 */
	public Type getEast() {
		return east;
	}
	/**
	 * @return west
	 */
	public Type getWest() {
		return west;
	}
	/**
	 * @return if north is connected with south
	 */
	public boolean isNorthSouth() {
		return northSouth;
	}
	/**
	 * @return if north is connected with east
	 */
	public boolean isNorthEast() {
		return northEast;
	}
	/**
	 * @return if north is connected with west
	 */
	public boolean isNorthWest() {
		return northWest;
	}
	/**
	 * @return if east is connected with west
	 */
	public boolean isEastWest() {
		return eastWest;
	}
	/**
	 * @return if south is connected with east
	 */
	public boolean isSouthEast() {
		return southEast;
	}
	/**
	 * @return if south is connected with west
	 */
	public boolean isSouthWest() {
		return southWest;
	}
	/**
	 * override equals for basic cards
	 * @param basicCard
	 * @return boolean
	 */
	@Override
	public boolean equals(Object j){
		BasicCard ret=null;
		if(j instanceof BasicCard){ 
			ret=(BasicCard)j;
			return  south==ret.getSouth() &&
					north==ret.getNorth() &&
					east==ret.getEast() &&
					west==ret.getWest() &&
					northSouth==ret.isNorthSouth() && 
					northEast==ret.isNorthEast() && 
					northWest==ret.isNorthWest() &&
					southEast==ret.isSouthEast() &&
					southWest==ret.isSouthWest() &&
					eastWest ==ret.isEastWest();
		}
		else return false;
	}
	/**
	 * Override hashCode
	 */
	@Override
	public int hashCode(){
		return 0;
	}
}
