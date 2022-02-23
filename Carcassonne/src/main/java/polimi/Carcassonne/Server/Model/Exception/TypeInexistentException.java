package polimi.Carcassonne.Server.Model.Exception;
/**
 * The type specificated does not exist
 * @author Omar Scotti - Samuele Tosatto
 */
public class TypeInexistentException extends Exception {
	private static final long serialVersionUID = -3084670422466714298L;
	public TypeInexistentException() {
		super("Type does not exist");
	}



}
