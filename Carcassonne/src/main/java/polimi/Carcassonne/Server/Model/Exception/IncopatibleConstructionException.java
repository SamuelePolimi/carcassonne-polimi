package polimi.Carcassonne.Server.Model.Exception;
/**
 * If you have try to merge a city with a street
 * @author Omar  Scotti - Samuele Tosatto
 */
public class IncopatibleConstructionException extends Exception {
	private static final long serialVersionUID = -5064064413956948166L;
	public IncopatibleConstructionException(){
		super("You can't merge City with Street.");
	}
}
