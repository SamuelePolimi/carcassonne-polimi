package polimi.Carcassonne.Server.Model.Exception;
/**
 * If the format of the file is wrong
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class FileFormatWrongException extends Exception {
	private static final long serialVersionUID = 9152846704557113330L;
	public FileFormatWrongException(){
		super("File format wrong");
	}
}
