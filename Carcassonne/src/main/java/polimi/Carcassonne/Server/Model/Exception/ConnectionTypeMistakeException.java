package polimi.Carcassonne.Server.Model.Exception;
/**
 * If you can't insert the card with a particoular orientation
 * @author Omar  Scotti - Samuele Tosatto
 */
public class ConnectionTypeMistakeException extends Exception {
	private static final long serialVersionUID = 1461005457857755673L;
	public ConnectionTypeMistakeException(){
		super("You can't insert the card with this orientation");
	}
}
