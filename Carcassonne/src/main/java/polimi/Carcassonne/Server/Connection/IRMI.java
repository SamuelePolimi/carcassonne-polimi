package polimi.Carcassonne.Server.Connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;

/**
 * this is RMI interface exposed for the client using
 * 
 * @author Omar Scotti - Samuele Tosatto
 **/

public interface IRMI extends Remote {
	/**
	 * client will connect, and receives the logicGameserver controller.
	 * 
	 * @return logicGame
	 * @throws RemoteException
	 */
	public IModelControllerLogicGame getServerGame() throws RemoteException;

	public void connect() throws RemoteException;
}
