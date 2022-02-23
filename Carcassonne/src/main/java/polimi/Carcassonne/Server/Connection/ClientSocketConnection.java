package polimi.Carcassonne.Server.Connection;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.Controller.CommandController;
import polimi.Carcassonne.Server.Controller.GameManager;
import polimi.Carcassonne.Server.Controller.IEventTimer;
import polimi.Carcassonne.Server.Controller.ProtocolController;
import polimi.Carcassonne.Server.Controller.Timer;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.InternalConnection;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class has to write and to receive input
 */
public class ClientSocketConnection implements Runnable,IEventTimer, IClientConnection {
	private Scanner in;
	private GameManager manager;
	private IModelControllerLogicGame game;
	private boolean stop;
	private ProtocolController protocol;
	private PrintWriter out;
	private Timer timer;
	private Color myColor;
	/**
	 * Constructor of client connection
	 * @param manager: gameManager
	 * @param socket
	 */
	public ClientSocketConnection(GameManager manager,Socket socket){
		this.manager=manager;
		stop=false;
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());
			stop=false;
		} catch (IOException e) {
			Printer.print("SRV<ClientSocketConnection>: SocketException!");
		}	
	}
	/**
	 * Set the timer
	 */
	public void setTimer(){
		timer=new Timer(3000,3000,this);
		Thread tTimer = new Thread(timer);
		tTimer.start(); 
	}
	/**
	 * Set a game
	 * @param game
	 */
	public void setGame(IModelControllerLogicGame game){
		this.game=game;
		protocol=new ProtocolController(game);
	}
	/**
	 * Attends input
	 */
	public void run(){
		while(!stop){
			String s;
			try{
				s = in.nextLine();
			}
			catch(Exception e){
				s=null;
				//this.setTimer();
				try {
					game.removePlayer(myColor);
				} catch (RemoteException e1) {
					Printer.print("SRV<ClientSocketConnection>: RemoteException");
				}
			}
			if(s!=null){
				if(s.toLowerCase().replaceAll(" ", "").equals("connect")){
					manager.clientRequest(this);
				}else{
					if(game!=null){
						CommandController command = new CommandController(s);
						try {
							protocol.reciveCommand(command);
						} catch (RemoteException e) {
							Printer.print("SRV<ClientSocketConnection>: SocketException!");
						}
					}else{
						stop=false;
					}
				}
			}
		}
	}
	/**
	 * Control if the socket is again connected
	 * @throws RemoteException 
	 */
	public void tick() {
		if(game!=null){
			try {
				game.removePlayer(myColor);
			} catch (RemoteException e) {
				Printer.print("SRV<ClientSocketConnection>: RemoteException");
			}
		}
	}
	/**
	 * Write output to client
	 * @param text
	 * @throws RemoteException 
	 */
	public void write(String text){
		try{
			out.println(text);
			out.flush();
		}catch(Exception ex){
			try {
				game.removePlayer(myColor);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void start(int nPlayer, String name, Color color, Box card) {
		write("start: "+this.getCardText(card)+", "+name+", "+getColorText(color)+", "+nPlayer );
	}
	public void nextTile(Card card) {
		write("next: "+getCardText(card));
		
	}
	public void turnChanged(Color color) {
		write("turn: "+getColorText(color));
	}
	public void updateCard(Box card) {
		write("update: "+getCardText(card)+","+card.getCoordinate().getX()+","+card.getCoordinate().getY());
	}
	public void rotated(Card card) {
		write("rotate: "+getCardText(card));
	}
	public void score(ArrayList<Player> listOfPlayer) {
		write("score: "+getScores(listOfPlayer));
	}
	
	public void moveNotValid() {
		write("move not valid");
	}
	public void lock() {
		write("lock");
	}
	public void unlock() {
		write("unlock");
	}
	public void leave(Color color) {
		write("leave: " + getColorText(color));
	}
	/**
	 * Returns a string with the name of the color
	 * @param color
	 * @return string
	 */
	private String getColorText(Color color){
		if(color.equals(Color.BLACK)){
			return "black";
		}else if(color.equals(Color.BLUE)){
			return "blue";
		}else if(color.equals(Color.YELLOW)){
			return "yellow";
		}else if(color.equals(Color.RED)){
			return "red";
		}else if(color.equals(Color.GREEN)){
			return "green";
		}
		return "";
	}
	/**
	 * Returns a string that represents the card
	 * @param card
	 * @return string
	 */
	private String getCardText(Card card){
		String ret="";
		ret+="N="+card.getNorth();
		ret+="S="+card.getSouth();
		ret+="W="+card.getWest();
		ret+="E="+card.getEast();
		ret+="NE="+getConnectionText(card.getNorth().getClockwise());
		ret+="NS="+getConnectionText(card.getNorth().getOpposite());
		ret+="NW="+getConnectionText(card.getNorth().getCounterclockwise());
		ret+="WE="+getConnectionText(card.getWest().getOpposite());
		ret+="SE="+getConnectionText(card.getSouth().getCounterclockwise());
		ret+="SW="+getConnectionText(card.getSouth().getClockwise());
		return ret;
	}
	/**
	 * Returns a string that represent a box
	 * @param box
	 * @return string
	 */
	private String getCardText(Box card){
		String ret="";
		ret+="N="+card.getNorth().toString();
		ret+="S="+card.getSouth().toString();
		ret+="W="+card.getWest().toString();
		ret+="E="+card.getEast().toString();
		ret+="NE="+getConnectionText(card.getNorth().getClockwise());
		ret+="NS="+getConnectionText(card.getNorth().getOpposite());
		ret+="NW="+getConnectionText(card.getNorth().getCounterclockwise());
		ret+="WE="+getConnectionText(card.getWest().getOpposite());
		ret+="SE="+getConnectionText(card.getSouth().getCounterclockwise());
		ret+="SW="+getConnectionText(card.getSouth().getClockwise());
		return ret;
	}
	/**
	 * Returns a string with scores of all players
	 * @param array: list of players
	 * @return string
	 */
	private String getScores(ArrayList<Player> array){
		String ret="";
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<array.size()-1;i++){
			buffer.append(this.getColorText(array.get(i).getColor()));
			buffer.append("=");
			buffer.append(array.get(i).getPoint());
			buffer.append(",");
		}
		ret=buffer.toString();
		ret+=this.getColorText(array.get(array.size()-1).getColor())+"="+array.get(array.size()-1).getPoint();
		return ret;
	}
	/**
	 * @param conn: internal connection
	 * @return a string with 1 or 0
	 */
	private String getConnectionText(InternalConnection conn){
		if(conn!=null){
			return "1";
		}
		return "0";
	}
	/**
	 * end of the game
	 * @param players
	 */
	public void endGame(ArrayList<Player> player) {
		write("end: " +getScores(player));
	}
	public Color getMyColor() {
		return myColor;
	}
	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}
	
}
