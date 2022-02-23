package polimi.Carcassonne.Server.Model.Exception;
/**
 * If there is just an explorer on this connection
 * @author Omar Scotti - Samuele Tosatto
 */
public class AlreadyExplorerException extends Exception {
	private static final long serialVersionUID = 6634863860978429624L;
	public AlreadyExplorerException() {
		super("Already explorer on this connection");
	}	
}
