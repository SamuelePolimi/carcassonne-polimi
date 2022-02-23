package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the deck is empty
 * @author Omar Scotti - Samuele Tosatto
 */
public class EmptyDeckException extends Exception {
	private static final long serialVersionUID = -7104250765831469167L;
	public EmptyDeckException(){
		super("Deck is empty: start new game :-).");
	}
}
