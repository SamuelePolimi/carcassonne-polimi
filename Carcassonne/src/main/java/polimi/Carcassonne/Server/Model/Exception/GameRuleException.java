package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the calls are not made in order
 * @author Omar  Scotti - Samuele Tosatto
 */
public class GameRuleException extends Exception {
	private static final long serialVersionUID = -4522472441138077218L;
	public GameRuleException(){
		super("You can't call InsertCard or InsertMarker or Pass in incorrect order.");
	}
}
