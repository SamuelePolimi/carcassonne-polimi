package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the player doesn't exist
 * @author Omar Scotti - Samuele Tosatto
 */
public class PlayerInexistentException extends Exception {
	private static final long serialVersionUID = 7787205267668893873L;
	public PlayerInexistentException(){
		super("Player specify does not exist");
	}
}
