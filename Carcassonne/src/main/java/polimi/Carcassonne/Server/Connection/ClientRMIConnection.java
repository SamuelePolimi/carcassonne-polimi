package polimi.Carcassonne.Server.Connection;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import polimi.Carcassonne.ClientServerTranslation;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.IModelController.IModelControllerGameState;
import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Server.Controller.GameManager;
import polimi.Carcassonne.Server.IModelController.IModelControllerLogicGame;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
/**
 * This class implements the connection between client and server using RMI
 * @author Omar Scotti - Samuele Tosatto
 */
public class ClientRMIConnection implements Serializable, IClientConnection ,IRMI{
	private static final long serialVersionUID = 940743782255997052L;
	private IModelControllerGameState clientRMI;
	private IModelControllerLogicGame lg;
	private GameManager manager;
	/**
	 * Constructor of Client in RMI
	 * @param manager
	 * @param clientRMI
	 */
	public ClientRMIConnection(GameManager manager,IModelControllerGameState clientRMI){
		this.manager=manager;
		this.clientRMI=clientRMI;
	}
	/**
	 * Start of the game
	 * @param nPlayer: number of players
	 * @param name: name of the game
	 * @param color: color of the first player
	 * @param card: first card
	 */
	public void start(int nPlayer, String name, Color color, Box card) {
		try {
			this.clientRMI.setName(name);
			this.clientRMI.insertNPlayer(nPlayer);
			this.clientRMI.setMyPlayer(color);
			this.clientRMI.updateCard(0, 0,ClientServerTranslation.translateBox(card));
		} catch (RemoteException e) {
			Printer.print("SRV<ClientRMIConnection>: RemoteException!");
		}

	}
	/**
	 * @param card: next tile
	 */
	public void nextTile(Card card) {
		try {
			ClientCard c = ClientServerTranslation.translate(card);
			c.setEmpty(false);
			this.clientRMI.setNextCard(c);
		} catch (RemoteException e) {
			Printer.print("SRV<ClientRMIConnection>: RemoteException!");
		}

	}
	/**
	 * @param color: next player's color
	 */
	public void turnChanged(Color color) {
		try {
			this.clientRMI.setCurrentPlayer(color);
		} catch (RemoteException e) {
			Printer.print("SRV<ClientRMIConnection>: RemoteException!");
		}
	}
	/**
	 * @param card: card to update
	 */
	public void updateCard(Box card) {
		try {
			this.clientRMI.updateCard(card.getCoordinate().getX(), card.getCoordinate().getY(), ClientServerTranslation.translateBox(card));
		} catch (RemoteException e) {
			Printer.print("SRV<ClientRMIConnection>: RemoteException!");
		}
	}
	/**
	 * @param card: card to rotate
	 */
	public void rotated(Card card) {
		try {
			this.clientRMI.setNextCard(ClientServerTranslation.translate(card));
		} catch (RemoteException e) {
			Printer.print("SRV<ClientRMIConnection>: RemoteException!");
		}
	}
	public void score(ArrayList<Player> player) {
		Color[] colors = new Color[player.size()];
		int[] scores = new int[player.size()];
		
		for(int i=0;i<player.size();i++){
			colors[i]=player.get(i).getColor();
			scores[i]=player.get(i).getPoint();
		}
		try {
			this.clientRMI.setScore(colors,scores);
		} catch (RemoteException e) {
			Printer.print("SRV<ClientConnection>: RemoteException");
		} 
	}
	/**
	 * If the move isn't valid
	 */
	public void moveNotValid() {
		try {
			this.clientRMI.moveNotValid();
		} catch (RemoteException e) {
			Printer.print("SRV<ClientRMIConnection>: RemoteException!");
		}
	}
	public void lock() {
		;
	}
	public void unlock() {
		;
	}
	public void leave(Color color) {
		;
	}
	/**
	 * set game
	 */
	public void setGame(IModelControllerLogicGame gl) {
		this.lg=gl;
	}
	/**
	 * @param serverGame
	 * @throws RemoteException 
	 */
	public IModelControllerLogicGame getServerGame() throws RemoteException {
		
		try {
			return (IModelControllerLogicGame) UnicastRemoteObject.exportObject(this.lg,0);
		} catch (RemoteException e) {
			return lg;
		}
		
	}
	/**
	 * connect
	 */
	public void connect() {
		manager.clientRequest(this);
		
	}
	/**
	 * end of the game
	 * @param players
	 */
	public void endGame(ArrayList<Player> player) {
		try {
			System.out.println("entra");
			this.score(player);//TODO
			this.clientRMI.endGame();
		} catch (RemoteException e) {
			Printer.print("SRV<ClientRMIConnection>: RemoteException!");
		}
	}
	public void setMyColor(Color color) {
		// TODO Auto-generated method stub
		
	}
}
