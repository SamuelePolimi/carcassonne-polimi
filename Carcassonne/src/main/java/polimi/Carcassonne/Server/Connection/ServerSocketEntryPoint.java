package polimi.Carcassonne.Server.Connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.Controller.GameManager;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class attend the connections of clients
 */
public class ServerSocketEntryPoint implements Runnable{
	private GameManager manager ;
	private ServerSocket serverSocket;
	/**
	 * Constructor of ServerEntryPoint
	 */
	public ServerSocketEntryPoint(GameManager manager){
		try{
			this.manager=manager;
			serverSocket= new ServerSocket(1111);
		}catch(IOException e){
			Printer.print("SRV<ServerSocketEntryPoint>: Error input!");
			return;
		}
	}
	/**
	 * Start the server that attends connection
	 */
	public void startServer(){
		while(true){
			try{
				Socket socket=serverSocket.accept();
				ClientSocketConnection client = new ClientSocketConnection(manager,socket);
				Thread tClient = new Thread(client);
				tClient.start();
			}catch(IOException e){
				Printer.print("SRV<ServerSocketEntryPoint> IOException");
			}
		}
	}
	/**
	 * run of the server
	 */
	public void run() {
		startServer();
	}
}
