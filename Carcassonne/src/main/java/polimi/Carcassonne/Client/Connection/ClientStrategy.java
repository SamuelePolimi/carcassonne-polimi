package polimi.Carcassonne.Client.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.Controller.ProtocolController;
import polimi.Carcassonne.Client.IModelController.IModelControllerGameState;
import polimi.Carcassonne.Client.Model.ClientGameState;
import polimi.Carcassonne.Server.Connection.IRMI;
import polimi.Carcassonne.Server.Connection.IRMIEntryPoint;
/**
 * This class give the possibility to choose beween Socket, Offline or RMI
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class ClientStrategy {
	private Socket mySocket;
	/**
	 * Take a socket connection
	 * @param cgs: Game state of client
	 * @return SocketWriter
	 * @throws UnknownHostException 
	 * @throws IOException
	 */
	public IViewConnection getClientSocket(ClientGameState cgs) throws UnknownHostException, IOException{
			mySocket = new Socket("127.0.0.1", 1111);
			Scanner in = new Scanner(mySocket.getInputStream());
			PrintWriter out = new PrintWriter(mySocket.getOutputStream());
			
			ProtocolController pc = new ProtocolController(cgs);
			SocketReader sr = new SocketReader(in,pc);
			Thread t = new Thread(sr);
			t.start();
			//sr.run();
			return new SocketWriter(out);
	
	}
	/**
	 * Take an offline connection
	 * @param gs: controller of game state
	 * @return Offline
	 */
	public IViewConnection getOfflineConnection(IModelControllerGameState gs){
		return new Offline(gs);
	}
	/**
	 * returns RMI connection
	 * @param gs
	 * @return
	 */
	public IViewConnection getRMIConnection(IModelControllerGameState gs){
		Registry reg;
		IRMI conn=null;
		try {
			reg = LocateRegistry.getRegistry();
			IModelControllerGameState remoteGS = (IModelControllerGameState)UnicastRemoteObject.exportObject(gs,0);
			IRMIEntryPoint server=(IRMIEntryPoint)reg.lookup("RmiEntryPoint");
			conn = server.connect(remoteGS);
		} catch (RemoteException e) {
			e.printStackTrace();
			Printer.print("CLI<ClientSocketConnection>: RemoteException!");
		} catch (NotBoundException e) {
			e.printStackTrace();
			Printer.print("CLI<ClientSocketConnection>: RemoteException!");
		}
		return new RMIWriter(conn);
	}
}
