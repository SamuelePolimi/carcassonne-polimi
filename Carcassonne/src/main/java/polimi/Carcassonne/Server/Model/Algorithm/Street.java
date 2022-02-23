package polimi.Carcassonne.Server.Model.Algorithm;
/**
This class extends Construction and it's the representation of a street on the graph
* @author Omar Scotti - Samuele Tosatto
*/
public class Street extends Construction {
	/**
	 * Constructor of street
	 * @param iD
	 */
	protected Street(int iD) {
		super(iD);
	}
	/** 
	This method returns 1 points for every box of the street
	@return the sum of the points in a street (1 points for box)
	 */
	@Override
	public int getPoint() {
			return this.getNumOfBoxes();	
	}
}
