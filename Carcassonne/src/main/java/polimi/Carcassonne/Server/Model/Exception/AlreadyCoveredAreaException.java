package polimi.Carcassonne.Server.Model.Exception;
/**
 * If there is another marker on the construction
 * @author Omar Scotti - Samuele Tosatto
 */
public class AlreadyCoveredAreaException extends Exception {
	private static final long serialVersionUID = 6296191487070457349L;
	public AlreadyCoveredAreaException(){
		super("There is another marker on this construction.");
	}
}
