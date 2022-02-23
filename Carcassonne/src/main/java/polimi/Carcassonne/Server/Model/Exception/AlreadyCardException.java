package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the selected box is just used
 * @author Omar Scotti - Samuele Tosatto
 */
public class AlreadyCardException extends Exception {
	private static final long serialVersionUID = 2216799108473576678L;
	public AlreadyCardException (){
		super("Selected box already taken");
	}
}
