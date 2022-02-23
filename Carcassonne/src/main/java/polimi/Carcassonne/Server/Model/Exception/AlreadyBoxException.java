package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the selected box is already on the table
 * @author Omar Scotti - Samuele Tosatto
 */
public class AlreadyBoxException extends Exception {
	private static final long serialVersionUID = 5938743564718240880L;
	public AlreadyBoxException(){
		super("Selected box already on the table.");
	}
}
