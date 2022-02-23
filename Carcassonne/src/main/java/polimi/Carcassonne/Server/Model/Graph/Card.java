package polimi.Carcassonne.Server.Model.Graph;
import polimi.Carcassonne.Server.Model.Exception.CardBlokedException;
/**
 * This Class represents a Card. It has more definitions then BasicCard, infact:
 * 1) It has InternalConnections who defines connection Between sides
 * 2) It can rotate Clockwise or Counterclockwise (if "canRotate" flag's true)
 *	
 *	@author Omar Scotti - Samuele Tosatto
 */
public class Card {
	private InternalConnection north;
	private InternalConnection south;
	private InternalConnection east;
	private InternalConnection west;
	// canRotate say if it's possible to rotate the card
	private boolean canRotate;
	/**
	 * Create a Card
	 * @param basicCard
	 */
	public Card(BasicCard basicCard){
		north= new Connection(basicCard.getNorth());
		south= new Connection(basicCard.getSouth());
		east= new Connection(basicCard.getEast());
		west= new Connection(basicCard.getWest());
		canRotate=true;
		// Set internal connection of card
		if(basicCard.isNorthSouth()) {
			north.setOpposite(south);
			south.setOpposite(north);
		}
		if(basicCard.isNorthEast()) {
			north.setClockwise(east);
			east.setCounterclockwise(north);
		}
		if(basicCard.isNorthWest()) {
			north.setCounterclockwise(west);
			west.setClockwise(north);
		}
		if(basicCard.isEastWest()) {
			east.setOpposite(west);
			west.setOpposite(east);
		}
		if(basicCard.isSouthEast()) {
			south.setCounterclockwise(east);
			east.setClockwise(south);
		}
		if(basicCard.isSouthWest()) {
			south.setClockwise(west);
			west.setCounterclockwise(south);
		}
	}
	/**
	 * Rotate the card clockwise
	 * @throws CardBlokedException if the card is already on the table
	 */
	public void rotateClockwise() throws CardBlokedException{
		if(canRotate){
			InternalConnection support=north;
			north=west;
			west=south;
			south=east;
			east=support;
		}else{
			throw new CardBlokedException();
		}
	}
	/**
	 * @return internal connection in north
	 */
	public InternalConnection getNorth(){
		return north;
	}
	/**
	 * @return internal connection in south
	 */
	public InternalConnection getSouth(){
		return south;
	}
	/**
	 * @return internal connection in east
	 */
	public InternalConnection getEast(){
		return east;
	}
	/**
	 * @return internal connection in west
	 */
	public InternalConnection getWest(){
		return west;
	}
	/**
	 * Override of equals
	 * @param card
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof Card){
			if(((Card) o).getNorth().equals(this.getNorth())){
				if(((Card) o).getSouth().equals(this.getSouth())){
					if(((Card) o).getWest().equals(this.getWest())){
						if(((Card) o).getEast().equals(this.getEast())){
							return true;
						}
					}
				}
			}
			return false;
		}
		else{
			return false;
		}
	}
	@Override
	public int hashCode(){
		return 0;
	}
}
