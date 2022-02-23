package polimi.Carcassonne.Server.Model.Exception;
/**
 * If you have try to insert a marker on "Nothing"
 * @author Omar  Scotti - Samuele Tosatto
 */
public class NothingTypeException extends Exception {
	private static final long serialVersionUID = -7630826288218454692L;
	public NothingTypeException(){
		super("You can't insert marker on things that are not cities or streets.");
	}
}
