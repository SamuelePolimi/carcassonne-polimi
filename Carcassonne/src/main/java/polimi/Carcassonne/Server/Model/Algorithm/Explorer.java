package polimi.Carcassonne.Server.Model.Algorithm;
/**
 * This class represent an explorer. This components works to understands the shapes of constructions
 * @author Omar Scotti - Samuele Tosatto
 */
public class Explorer {
	private Construction construction;
	/**
	 * its work ever for a construction
	 * @param construction where you want to put the explorer
	 */
	protected Explorer(Construction construction){
		this.setConstruction(construction);
	}
	/**
	 * 
	 * @return construction
	 */
	public Construction getConstruction() {
		return construction;
	}
	/**
	 * 
	 * @param construction to set in this explorer
	 */
	protected void setConstruction(Construction construction) {
		this.construction = construction;
	}
	/**
	 * For understand if two Explorer are working for the same Construction
	 * @param Explorer
	 * @return boolean 
	 */
	@Override
	public boolean equals(Object j){
		if(j instanceof Explorer){
			Explorer e = (Explorer)j;
			return e.construction.equals(this.construction);
		}else{
			return false;
		}
	}
	/**
	 * Override of hashCode
	 */
	@Override
	public int hashCode(){
		return 0;
	}
}
