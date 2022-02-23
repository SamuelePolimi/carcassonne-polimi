package polimi.Carcassonne.Client.Connection;
import java.awt.Color;
import java.io.PrintWriter;
/**
 * Writer of messages for the server
 * @author Omar Scotti - Samuele Tosatto
 */
public class SocketWriter implements IViewConnection{
	private PrintWriter pw;
	/**
	 * Constructor of socket writer
	 * @param pw: print writer
	 */
	public SocketWriter(PrintWriter pw){
		this.pw=pw;
	}
	/**
	 * Write "connect"
	 */
	public void connection() {
		pw.println("connect");
		pw.flush();
	}
	/**
	 * Only for offline
	 */
	public void connection(int n) {
		;
	}
	/**
	 * Write "insert:x,y"
	 * @param x
	 * @param y 
	 */
	public void insert(int x, int y) {
		pw.println("place: "+x+","+y);
		pw.flush();
	}
	/**
	 * Write "rotate"
	 */
	public void rotate() {
		pw.println("rotate");
		pw.flush();
	}
	/**
	 * Write "tile: north", "tile:east", "tile:west", "tile:south" or "pass"+
	 * @param orientation: 0 north, 1 east, 2 south, 3 west, 4 pass
	 */
	public void marker(int orientation) {
		if(orientation==4){
			pw.println("pass");
			pw.flush();
		}else if(orientation==0){
			pw.println("tile: north");
			pw.flush();
		}else if(orientation==1){
			pw.println("tile: east");
			pw.flush();
		}else if(orientation==2){
			pw.println("tile: south");
			pw.flush();
		}else if(orientation==3){
			pw.println("tile: west");
			pw.flush();
		}
	}
	/**
	 * Write "reconnect: color"
	 * @param color: color of the player to reconnect
	 */
	public void reconnect(Color color,String name) {
		//TODO fare
		;
	}
	public void reconnect(Color color) {
		// TODO Auto-generated method stub
		
	}
	
}
