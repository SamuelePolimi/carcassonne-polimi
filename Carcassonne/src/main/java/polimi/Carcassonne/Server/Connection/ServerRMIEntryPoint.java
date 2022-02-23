package polimi.Carcassonne.Server.Connection;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import polimi.Carcassonne.Client.IModelController.IModelControllerGameState;
import polimi.Carcassonne.Server.Controller.GameManager;
/**
 * ServerRMIEntryPoint is the point with clients can interface. 
 * Calling connect method each client can be start a game
 * @author Omar Scotti - Samuele Tosatto
 */
public class ServerRMIEntryPoint implements IRMIEntryPoint {
	private GameManager manager;
	/**
	 * Initialize the RMIentryPoint with the game-manager
	 * @param manager
	 * @throws RemoteException
	**/
	public ServerRMIEntryPoint(GameManager manager) throws RemoteException{
		super();
		this.manager=manager;
	}
	/**
	 *connect the clients which the server using a client-request
	 *@param gameState
	 * @throws RemoteException 
	 */
	public IRMI connect(IModelControllerGameState gameState) throws RemoteException {
		ClientRMIConnection general = new ClientRMIConnection(manager,gameState);
		 
		return (IRMI)UnicastRemoteObject.exportObject(general,0);
	}
}
