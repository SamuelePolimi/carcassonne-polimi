package polimi.Carcassonne.Server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.Connection.IRMIEntryPoint;
import polimi.Carcassonne.Server.Connection.ServerRMIEntryPoint;
import polimi.Carcassonne.Server.Connection.ServerSocketEntryPoint;
import polimi.Carcassonne.Server.Controller.GameManager;
public class Main {
	/**
	 * Main of the server
	 * @param args
	 */
	public static void main(String[] args) {
		
		Printer.print("SRV<Main>: Server start!");
		GameManager manager = new GameManager();
		
		/*Initialize two servers*/
		
		ServerSocketEntryPoint socketServer = new ServerSocketEntryPoint(manager);
		ServerRMIEntryPoint rmiServer;
		
		try {
			rmiServer = new ServerRMIEntryPoint(manager);
			IRMIEntryPoint entryPoint=(IRMIEntryPoint) UnicastRemoteObject.exportObject(rmiServer,0);
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			registry.rebind("RmiEntryPoint",entryPoint);
		} catch (RemoteException e1) {
			Printer.print("SRV<Main>: RemoteException");
			e1.printStackTrace();
		}
		
		Thread tServer= new Thread(socketServer);
		tServer.start();
		
	}
}
