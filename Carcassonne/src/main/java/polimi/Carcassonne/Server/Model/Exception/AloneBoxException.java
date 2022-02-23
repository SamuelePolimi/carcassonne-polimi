package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the selected box is alone
 * @author Omar Scotti - Samuele Tosatto
 */
public class AloneBoxException extends Exception {
	private static final long serialVersionUID = -509436176072589479L;
	public AloneBoxException(){
		super("Selected box is alone.");
	}
}
