package polimi.Carcassonne.Server.Model.Algorithm;
/**
 This class extends Construction and it's the representation of a city on the graph
 * @author Omar Scotti - Samuele Tosatto
 */
public class City extends Construction {
	protected City(int ID) {
		super(ID);
	}
	/** 
	return points of a city
	@return returns 2 points for every box of the city
	if the construction is closed else it returns 1 point for every box
	 */
	@Override
	public int getPoint() {
		if(this.isClosed()){
			return 2*this.getNumOfBoxes();
		}else{
			return this.getNumOfBoxes();
		}
	}
}
