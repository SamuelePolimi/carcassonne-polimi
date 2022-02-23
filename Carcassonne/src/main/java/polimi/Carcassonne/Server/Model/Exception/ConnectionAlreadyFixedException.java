package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the connection has just a neighbor
 * @author Omar Scotti - Samuele Tosatto
 */
public class ConnectionAlreadyFixedException extends Exception {
	private static final long serialVersionUID = 3629988385242414948L;
	public ConnectionAlreadyFixedException(){
		super("Connection has just a neighbor");
	}
}
