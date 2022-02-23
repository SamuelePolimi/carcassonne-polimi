package polimi.Carcassonne.Server.Model.Exception;
/**
 * If you can't rotate the card because is just on the table
 * @author Omar Scotti - Samuele Tosatto
 */
public class CardBlokedException extends Exception {
	private static final long serialVersionUID = 3360033592929393379L;
	public CardBlokedException(){
		super("Card is on the table, you can't rotate");
	}
}
