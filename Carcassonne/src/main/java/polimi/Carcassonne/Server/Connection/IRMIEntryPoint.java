package polimi.Carcassonne.Server.Connection;
import java.rmi.Remote;
import java.rmi.RemoteException;
import polimi.Carcassonne.Client.IModelController.IModelControllerGameState;
/**
 * Entry point for rmi server
 * @author Omar Scotti - Samuele Tosatto
 */
public interface IRMIEntryPoint extends Remote{
	/**
	 * this methods connect a clients with the server
	 * @param gameState
	 * @return IRMI interface
	 * @throws RemoteException
	 */
	public IRMI connect(IModelControllerGameState gameState) throws RemoteException;
}
