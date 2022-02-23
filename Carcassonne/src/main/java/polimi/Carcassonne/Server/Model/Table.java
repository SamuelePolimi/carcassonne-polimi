package polimi.Carcassonne.Server.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import polimi.Carcassonne.Server.Model.Algorithm.Construction;
import polimi.Carcassonne.Server.Model.Algorithm.ExplorerManager;
import polimi.Carcassonne.Server.Model.Algorithm.Marker;
import polimi.Carcassonne.Server.Model.Exception.AloneBoxException;
import polimi.Carcassonne.Server.Model.Exception.AlreadyBoxException;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCardException;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCoveredAreaException;
import polimi.Carcassonne.Server.Model.Exception.ConnectionAlreadyFixedException;
import polimi.Carcassonne.Server.Model.Exception.ConnectionTypeMistakeException;
import polimi.Carcassonne.Server.Model.Exception.GameRuleException;
import polimi.Carcassonne.Server.Model.Exception.NothingTypeException;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Connection;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;
/**
 * Table is a factory of box
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class Table {
	private Map<Coordinate, Box> boxMap;
	private Map<Coordinate, Box> freeBoxMap;
	private int minX,minY,maxX,maxY;
	protected ExplorerManager explorerManager;
	/**
	 * Creates a table with its hash tables:
	 * -boxMap that date that from a coordinate returns a cell
	 * -freeBoxMap which allows us to understand what are the free cells
	 */
	public Table(){
		Coordinate o = new Coordinate(0,0);
		minX=0; minY=0;
		maxX=0; maxY=0;
		boxMap=new HashMap<Coordinate, Box>();
		freeBoxMap=new HashMap<Coordinate, Box>();
		explorerManager = new ExplorerManager();
		boxMap.put(o, new Box(o));
	}
	/**
	 * Method used from GameLogic to insert a card
	 * @param box where you want to insert the card
	 * @param card that you want to insert
	 * @throws AlreadyCardException if there is already a card
	 * @throws ConnectionTypeMistakeException if the orientation is not correct
	 * @throws GameRuleException if there is an incorrect order of call 
	 */
	protected void insertCard(Box box,Card card) throws AlreadyCardException, ConnectionTypeMistakeException, GameRuleException{
		//if the card isn't in the correct orientation
		if(!canPutCardBox(box,card)){
			throw new ConnectionTypeMistakeException();
		}
		//put the card into a box
		box.setCard(card);
		//we remove the inserted box from the freeBoxMap
		freeBoxMap.remove(box.getCoordinate());
		//try to create nearby boxes ("try" because maybe there already nearby boxes)
		try{
			createBox(box.getCoordinate().getMyEast());
		}catch(Exception ex){
			;
		}
		try{
			createBox(box.getCoordinate().getMyWest());
		}catch(Exception ex){
			;
		}
		try{
			createBox(box.getCoordinate().getMySouth());
		}catch(Exception ex){
			;
		}
		try{
			createBox(box.getCoordinate().getMyNorth());
		}catch(Exception ex){
			;
		}
		explorerManager.manageExplorersBox(box);
	}
	public ArrayList<Construction> getOpenedConstruction(){
		return explorerManager.getOpenedConstruction();
	}
	/**
	 * Method that clears all closed construction and that returns them
	 * @return an array list of all closed construction
	 */
	public ArrayList<Construction> getClosedConstruction(){
		return explorerManager.clearClosedConstructionBuffer();
	}
	/**
	 * Method that says if it is possible to put a card
	 * @param box
	 * @param card
	 * @return true or false
	 */
	public boolean canPutCardBox(Box box, Card card){
		if(box==null){
			return false;
		}
		Coordinate cbox = box.getCoordinate();
		if(this.boxMap.containsKey(cbox.getMyEast())){
			if(!this.boxMap.get(cbox.getMyEast()).isEmpty()){
				if(this.boxMap.get(cbox.getMyEast()).getWest().getType()!=card.getEast().getType()){
					return false;
				}
			}
		}
		if(this.boxMap.containsKey(cbox.getMyWest())){
			if(!this.boxMap.get(cbox.getMyWest()).isEmpty()){
				if(this.boxMap.get(cbox.getMyWest()).getEast().getType()!=card.getWest().getType()){
					return false;
				}
			}
		}
		if(this.boxMap.containsKey(cbox.getMyNorth())){
			if(!this.boxMap.get(cbox.getMyNorth()).isEmpty()){
				if(this.boxMap.get(cbox.getMyNorth()).getSouth().getType()!=card.getNorth().getType()){
					return false;
				}
			}
		}
		if(this.boxMap.containsKey(cbox.getMySouth())){
			if(!this.boxMap.get(cbox.getMySouth()).isEmpty()){
				if(this.boxMap.get(cbox.getMySouth()).getNorth().getType()!=card.getSouth().getType()){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * @param x
	 * @param y
	 * @return the box
	 */
	public Box getBox(int x,int y){
		if(boxMap.containsKey(new Coordinate(x,y))){
			return boxMap.get(new Coordinate(x,y));
		}else{
			return null;
		}
	}
	/**
	 * Method that say if there is already a box in that coordinate (x,y)
	 * @param x
	 * @param y
	 * @return true or false
	 */
	public boolean existBoxIn(int x,int y){
		return boxMap.containsKey(new Coordinate(x,y));
	}
	/**
	 * Create a box if there are necessary requirements
	 * @param coordinate 
	 * @throws AlreadyBoxException if there is already a box
	 * @throws AloneBoxException if the box is alone
	 */
	private void createBox(Coordinate c) throws AlreadyBoxException, AloneBoxException{
		//if the box isn't already create
		if(!boxMap.containsKey(c)){
			//if there is at least one nearby box
			if(boxMap.containsKey(c.getMyEast()) ||
					boxMap.containsKey(c.getMyWest()) ||
					boxMap.containsKey(c.getMyNorth()) ||
					boxMap.containsKey(c.getMySouth())){
						//we create the empty box
						Box box = new Box(c);
						boxMap.put(c, box);
						freeBoxMap.put(c,box);
						//imposed minimum and maximum of the table
						if(c.getX()<minX){
							minX=c.getX();
						}
						if(c.getY()<minY){
							minY=c.getY();
						}
						if(c.getX()>maxX){
							maxX=c.getX();
						}
						if(c.getY()>maxY){
							maxY=c.getY();
						}
						//create nearby connections
						createNearConnection(box);
			}else{
				throw new AlreadyBoxException();
			}
		}else{
			throw new AloneBoxException();
		}
	}
	/**
	 * Set connections of a box with connections of nearby boxes
	 * @param box to set
	 */
	private void createNearConnection(Box c){
		if(boxMap.containsKey(c.getCoordinate().getMyEast())){
			//we establish a bilateral connection
			Box myEast= boxMap.get(c.getCoordinate().getMyEast());
			createConnection(c.getEast(), myEast.getWest());
		}
		if(boxMap.containsKey(c.getCoordinate().getMyWest())){
			//we establish a bilateral connection
			Box myWest= boxMap.get(c.getCoordinate().getMyWest());
			createConnection(c.getWest(), myWest.getEast());
		}
		if(boxMap.containsKey(c.getCoordinate().getMySouth())){
			//we establish a bilateral connection
			Box mySouth= boxMap.get(c.getCoordinate().getMySouth());
			createConnection(c.getSouth(), mySouth.getNorth());
		}
		if(boxMap.containsKey(c.getCoordinate().getMyNorth())){
			//we establish a bilateral connection
			Box myNorth= boxMap.get(c.getCoordinate().getMyNorth());
			createConnection(c.getNorth(), myNorth.getSouth());
		}
	}
	/**
	 * create connection between conn1 and conn2
	 * @param conn1
	 * @param conn2
	 */
	private void createConnection(Connection conn1, Connection conn2){
		try {
			conn1.connect(conn2);
			conn2.connect(conn1);
		} catch (ConnectionAlreadyFixedException e) {
			;
		}
	}
	/**
	 * 
	 * @return arrayList of free box on the map
	 */
	public ArrayList<Box> getFreeBoxes(){
		ArrayList<Box> ret= new ArrayList<Box>();
		ret.addAll( freeBoxMap.values());
		return ret;
	}
	/**
	 * 
	 * @return the x-coordinate of the high-most box on the table
	 */
	public int getMaxY(){
		return maxY;
	}
	/**
	 * 
	 * @return the y-coordinate of the low-most box on the table
	 */
	public int getMinY(){
		return minY;
	}
	/**
	 * 
	 * @return the x-coordinate of the right-most box on the table
	 */
	public int getMaxX(){
		return maxX;
	}
	/**
	 * 
	 * @return the x-coordinate of the left-most box on the table
	 */
	public int getMinX(){
		return minX;
	}
	/**
	 * Method used to insert a marker
	 * @param connection in which you want to put the marker
	 * @param marker 
	 * @throws AlreadyCoveredAreaException if there is already a marker
	 * @throws NothingTypeException if you want to put a marker where there is nothing
	 */
	public void putMarker(Connection connection,Marker marker,Box box) throws AlreadyCoveredAreaException, NothingTypeException{
		connection.putMarker(marker);
		marker.setConnection(connection);
		marker.setCard(box);
	}
}
