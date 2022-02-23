package polimi.Carcassonne.Server.Controller;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import polimi.Carcassonne.Printer;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class represents a socket connection with the client
 */
public class ClientSocket {
	private Scanner in;
	private Socket socket;
	/** 
	 * Constructor of the connection with socket
	 * @param socket
	 */
	public ClientSocket(Socket socket){
		this.socket=socket;
		try {
			in = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			Printer.print("CLI<ClientSocket>: IOException");
		}
	}
	/**
	 * @return line: input string from client
	 */
	public String[] reciveInput(){
		String input=in.nextLine();
		String[] line=input.toLowerCase().split(":");
		return line;
	}
	/**
	 * @return isConnected: if there is again a connection with the client
	 */
	public boolean isConnected(){
		return socket.isConnected();
	}
	/**
	 * Close socket and input reader
	 */
	public void closeAll(){
		in.close();
		try {
			socket.close();
		} catch (IOException e) {
			Printer.print("CLI<ClientSocket> IOException");
		}
	}
	/**
	 * @return socket
	 */
	public Socket getSocket(){
		return socket;
	}
}
