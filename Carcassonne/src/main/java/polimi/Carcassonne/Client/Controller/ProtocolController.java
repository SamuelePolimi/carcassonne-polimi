package polimi.Carcassonne.Client.Controller;
import java.awt.Color;
import java.rmi.RemoteException;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.IModelController.IModelControllerCard;
import polimi.Carcassonne.Client.IModelController.IModelControllerGameState;
import polimi.Carcassonne.Client.Model.*;
import polimi.Carcassonne.Server.Model.Graph.*;
/**
 * This class receives commands from view  and from socket, and "translate" 
 * in "ViewControll" primitives!
 * @author Omar Scotti - Samuele Tosatto
 */
public class ProtocolController {
	private IModelControllerGameState gs;
	/**
	 * Set the gs
	 * @param gs: controller game state 
	 */
	public ProtocolController(IModelControllerGameState gs){
		this.gs=gs;
	}
	/**
	 * Initialize a command controller
	 * @param text: string received
	 */
	public void reciveCommand(String text){
		try {
			reciveCommand(new CommandController(text));
		} catch (ProtocolControllerException e) {
			Printer.print("CLI<ProtocolController> Wrong command recived");
		}
	}
	/**
	 * Apply the command received
	 * @param command: command to apply
	 * @throws ProtocolControllerException 
	 */
	private  void reciveCommand(CommandController command) throws ProtocolControllerException{
		if(command.getName().equals("start")){
			reciveStart(command);
		}else if(command.getName().equals("turn")){
			reciveTurn(command);
		}else if(command.getName().equals("next")){
			reciveNext(command);
		}else if(command.getName().equals("rotated")){
			reciveRotated(command);	
		}else if(command.getName().equals("update")){
			reciveUpdate(command);
		}else if(command.getName().equals("movenotvalid")){
			reciveMoveNotValid();
		}else if(command.getName().equals("score")){
			reciveScores(command);
		}else if(command.getName().equals("end")){
			try {
				reciveScores(command);
				gs.endGame();
			} catch (RemoteException e) {
				Printer.print("SRV<ProtocolController>: RemoteException");
			}
		}else if(command.getName().equals("leave")){
			try {
				gs.removePlayer(getColor(command.getValue(0)));
			} catch (RemoteException e) {
				Printer.print("Remoteexception");
			}
		}
	}
	/**
	 * Apply start
	 * @param command: command controller
	 * @throws ProtocolControllerException 
	 */
	private void reciveStart(CommandController command) throws ProtocolControllerException{
		try {
			gs.setName(command.getValue(1));
			gs.insertNPlayer( Integer.parseInt(command.getValue(3).trim()));
			gs.setMyPlayer(getColor(command.getValue(2)));
			gs.updateCard(0, 0, generateCard(command.getValue(0),0,0));
		} catch (NumberFormatException e) {
			throw new ProtocolControllerException();
		} catch (RemoteException e) {
			throw new ProtocolControllerException();
		}
	}
	/**
	 * @param text: color
	 * @return color
	 * @throws ProtocolControllerException 
	 */
	private Color getColor(String text) throws ProtocolControllerException{
		if(text.equals("green")){
			return Color.GREEN;
		}else if(text.equals("red")){
			return Color.RED;
		}else if(text.equals("yellow")){
			return Color.YELLOW;
		}else if(text.equals("black")){
			return Color.BLACK;
		}else if(text.equals("blue")){
			return Color.BLUE;
		}else if(text.equals("black")){
			return Color.BLACK;
		}else{
			throw new ProtocolControllerException();
		}
	}
	/**
	 * Apply change of turn
	 * @param command: command controller
	 */
	private void reciveTurn(CommandController command){
		try {
			gs.setCurrentPlayer(getColor(command.getValue(0)));
		} catch (RemoteException e) {
			Printer.print("CLI<ProtocolController> RemoteException");
		} catch (ProtocolControllerException e){
			Printer.print("CLI<ProtocolController> Command wrong");
		}
	}
	/**
	 * Set next card
	 * @param command
	 */
	private void reciveNext(CommandController command){
		try {
			gs.setNextCard(generateCard(command.getValue(0),0,0));
		} catch (RemoteException e) {
			Printer.print("CLI<ProtocolController> RemoteException");
		}
	}
	/**
	 * Set rotated card
	 * @param command
	 */
	private void reciveRotated(CommandController command){
		try {
			gs.setRotatedCard(generateCard(command.getValue(0),0,0));
		} catch (RemoteException e) {
			Printer.print("CLI<ProtocolController> RemoteException");
		}
	}
	/**
	 * Update of card
	 * @param command
	 */
	private void reciveUpdate(CommandController command){
		int x=Integer.parseInt(command.getValue(1));
		int y=Integer.parseInt(command.getValue(2));
		try {
			gs.updateCard(x, y, generateCard(command.getValue(0),x,y));
		} catch (RemoteException e) {
			Printer.print("CLI<ProtocolController> RemoteException");
		}
	}
	/**
	 * Set move not valid
	 */
	private void reciveMoveNotValid(){
		try {
			gs.moveNotValid();
		} catch (RemoteException e) {
			Printer.print("CLI<ProtocolController> RemoteException");
		}
	}
	/**
	 * Set all points
	 * @param command
	 */
	private void reciveScores(CommandController command){
		for(int i=0;i<command.numberOfValues();i++){
			String[] s = command.getValue(i).split("=");
			try{
				if(s[0].toLowerCase().replaceAll(" ", "").equals("red")){
					gs.setScore(Color.RED, Integer.parseInt(s[1].toLowerCase().replaceAll(" ", "")));
				}else if(s[0].toLowerCase().replaceAll(" ", "").equals("black")){
					gs.setScore(Color.BLACK, Integer.parseInt(s[1].toLowerCase().replaceAll(" ", "")));
				}else if(s[0].toLowerCase().replaceAll(" ", "").equals("blue")){
					gs.setScore(Color.BLUE, Integer.parseInt(s[1].toLowerCase().replaceAll(" ", "")));
				}else if(s[0].toLowerCase().replaceAll(" ", "").equals("green")){
					gs.setScore(Color.GREEN, Integer.parseInt(s[1].toLowerCase().replaceAll(" ", "")));
				}else if(s[0].toLowerCase().replaceAll(" ", "").equals("yellow")){
					gs.setScore(Color.YELLOW, Integer.parseInt(s[1].toLowerCase().replaceAll(" ", "")));
				}
			}
			catch (RemoteException e) {
				Printer.print("CLI<ProtocolController> RemoteException");
			}
		}
	}
	/**
	 * Generate a card from the text
	 * @param text: card
	 * @param x
	 * @param y
	 * @return card
	 */
	private IModelControllerCard generateCard(String text, int x, int y){
		IModelControllerCard card = new ClientCard(new Coordinate(x,y));
		((ClientCard)card).setEmpty(false);
		String split[] = text.split("=");
		String val[]= new String[split.length-1];
		for(int i=1;i<split.length;i++){
			val[i-1]=split[i].toLowerCase().trim();
		}
		try{
			card.setNorth(new ClientCardSide(getTypeByString(val[0])));
			setCardSide(val[0],((ClientCard)card).getNorth());
			card.setSouth(new ClientCardSide(getTypeByString(val[1])));
			setCardSide(val[1],((ClientCard)card).getSouth());
			card.setEast(new ClientCardSide(getTypeByString(val[3])));
			setCardSide(val[3],((ClientCard)card).getEast());
			card.setWest(new ClientCardSide(getTypeByString(val[2])));
			setCardSide(val[2],((ClientCard)card).getWest());
		}
		catch(ProtocolControllerException e){
			Printer.print("CLI<ProtocolController> Command wrong");
		}
		
		card.setNorthEast(getBooleanByString(val[4]));
		card.setNorthSouth(getBooleanByString(val[5]));
		card.setNorthWest(getBooleanByString(val[6]));
		card.setEastWest(getBooleanByString(val[7]));
		card.setSouthEast(getBooleanByString(val[8]));
		card.setSouthWest(getBooleanByString(val[9]));
		return card;
	}
	/**
	 * 
	 * @param c: type
	 * @return type
	 * @throws ProtocolControllerException 
	 */
	private Type getTypeByString(String c) throws ProtocolControllerException{
		if(c.charAt(0)=='s'){
			return Type.STREET;
		}
		else if(c.charAt(0)=='c'){
			return Type.CITY;
		}
		else if(c.charAt(0)=='n'){
			return Type.NOTHING;
		}
		else{
			throw new ProtocolControllerException();
		}
	}
	/**
	 * 
	 * @param c: 0 or 1
	 * @return true or false
	 */
	private boolean getBooleanByString(String c){
		if(c.charAt(0)=='0'){
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param c: marker (k,r,g,y,b)
	 * @param s: client card side
	 * @return
	 */
	private void setCardSide(String c, ClientCardSide s){
		if(c.length()>2){
		if(c.charAt(2)=='k'){
			s.setPlayer(Color.BLACK);
		}else if(c.charAt(2)=='r'){
			s.setPlayer(Color.RED);
		}else if(c.charAt(2)=='g'){
			s.setPlayer(Color.GREEN);
		}else if(c.charAt(2)=='y'){
			s.setPlayer(Color.YELLOW);
		}else if(c.charAt(2)=='b'){
			s.setPlayer(Color.BLUE);
		}
		}
	}
	
}
