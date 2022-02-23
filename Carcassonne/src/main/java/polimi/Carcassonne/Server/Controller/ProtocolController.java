package polimi.Carcassonne.Server.Controller;
import java.rmi.RemoteException;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.Controller.CommandController;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
/**
 * @author Omar Scotti - Samuele Tosatto
 * Controller of the protocol, this class receives command and decides what has to do
 */
public class ProtocolController {
	private IModelControllerLogicGame game;
	/**
	 * Constructor that set the interface to use
	 * @param game
	 */
	public ProtocolController(IModelControllerLogicGame game){
		this.game=game;
	}
	/**
	 * Command receiver
	 * @param command: Command controller with the information about the command required
	 * @throws RemoteException 
	 */
	public void reciveCommand(CommandController command) throws RemoteException{
		if(command.getName().equals("rotate")){
				game.rotateNextCard();
		}else if(command.getName().equals("place")){
			try {
				game.insertPosition(Integer.parseInt(command.getValue(0)), Integer.parseInt(command.getValue(1)));
			} catch (NumberFormatException e) {
				Printer.print("SRV<ProtocolController> NumberFormatException");
			}
		}else if(command.getName().equals("pass")){
				game.putMarker(4);
		}else if(command.getName().equals("tile")){
			if(command.getValue(0).equals("north")){
					game.putMarker(0);
			}else if(command.getValue(0).equals("east")){
					game.putMarker(1);
			}else if(command.getValue(0).equals("south")){
					game.putMarker(2);
				}
			else if(command.getValue(0).equals("west")){
					game.putMarker(3);
			}
		}
	}
}
