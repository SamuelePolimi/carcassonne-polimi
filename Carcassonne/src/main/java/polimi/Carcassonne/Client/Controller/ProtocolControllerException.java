package polimi.Carcassonne.Client.Controller;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This exception is called when there is a problem of communication
 */
public class ProtocolControllerException extends Exception {
	private static final long serialVersionUID = 8305151107788652150L;
	public ProtocolControllerException(){
		super("Command recived by server is wrong");
	}
}
