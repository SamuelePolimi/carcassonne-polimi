package polimi.Carcassonne.Client.Connection;
import java.util.Scanner;

import polimi.Carcassonne.Client.Controller.ProtocolController;
/**
 * Reader of messages from server (Thread)
 * @author Omar Scotti - Samuele Tosatto
 */
public class SocketReader implements Runnable {
	private Scanner in;
	private ProtocolController pc;
	private boolean stop;
	/**
	 * Constructor of socket reader
	 * @param in: scanner
	 * @param pc: protocol controller
	 */
	public SocketReader(Scanner in, ProtocolController pc){
		this.in =in;
		stop=false;
		this.pc=pc;
	}
	/**
	 * Run of this thread
	 */
	public void run() {
		while(!stop){
			pc.reciveCommand(in.nextLine());
		}
	}
}
