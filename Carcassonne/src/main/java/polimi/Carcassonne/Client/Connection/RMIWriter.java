package polimi.Carcassonne.Client.Connection;
import java.awt.Color;
import java.rmi.RemoteException;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.Connection.IRMI;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
/**
 * This class represent the communication via RMI between client and server
 * client modify game-logic server's
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class RMIWriter implements IViewConnection {
	private IModelControllerLogicGame lg;
	private IRMI conn;
	/**
	 * RMIWriter constructor
	 * @param conn
	 */
	public RMIWriter(IRMI conn){
		this.conn=conn;
	}
	/**
	 * Connection
	 * @throws RemoteException 
	 */
	public void connection() throws RemoteException {
		conn.connect();
	}
	public void connection(int n) {
		;

	}
	/**
	 * insertion of a card in logic game
	 * @param x
	 * @param y
	 */
	public void insert(int x, int y) {
		try {
			if(lg!=null){
				lg.insertPosition(x, y);
			}else{
				lg = conn.getServerGame();
				lg.insertPosition(x, y);
			}
		} catch (RemoteException e) {
			Printer.print("CLI<RMIWriter> RemoteException");
		}

	}
/**
 * Rotate a card
 */
	public void rotate() {
		try {
			if(lg!=null){
				this.lg.rotateNextCard();
			}else{
				lg = conn.getServerGame();
				this.lg.rotateNextCard();
			}
		} catch (RemoteException e) {
			Printer.print("CLI<RMIWriter> RemoteException");
		}
	}
	/**
	 * @param orientation: 0=north, 1=east, 2=south,3=west, 4=pass
	 */
	public void marker(int orientation) {
		try {
			lg.putMarker(orientation);
		} catch (RemoteException e) {
			Printer.print("CLI<RMIWriter> RemoteException");
		}
	}
	public void reconnect(Color color) {
		;
	}

}
