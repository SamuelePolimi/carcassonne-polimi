package polimi.Carcassonne.Server.Model.Algorithm;
import java.util.ArrayList;
import java.util.HashMap;
import polimi.Carcassonne.Server.Model.Exception.AlreadyExplorerException;
import polimi.Carcassonne.Server.Model.Exception.IncopatibleConstructionException;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Connection;
import polimi.Carcassonne.Server.Model.Graph.InternalConnection;
import polimi.Carcassonne.Server.Model.Graph.Type;
/**
 * This class manages Explorers.
 * His goal is understand all the shapes of costructions on the table and understand wich are closed or not
 * 1) it has nextID to can create unique Costruction
 * 2) it can Merge two Costruction when he understand that two costruction are connected
 * 3) it sets Explorer in the right way on Boxes 
 * 
 * @author Omar Scotti - Samuele Tosatto
 */
public class ExplorerManager {
	private int nextID;
	private ArrayList<Construction> closedConstructionBuffer;
	private ArrayList<Construction> openedConstructionBuffer;
	public ExplorerManager(){
		nextID=0;
		closedConstructionBuffer=new ArrayList<Construction>();
		openedConstructionBuffer=new ArrayList<Construction>();
	}
	/**
	 * This method has to manage all explorers
	 * @param box 
	 */
	public void manageExplorersBox(Box box){
		//We import the explorers from adjacent cells
		this.importExplorers(box);
		//we expand the explorers on the links which are connected
		this.expandExplorers(box);
		//we create new explorers (if we need to do it)
		this.createExplorers(box);
		//Upgrade your cell number to each building discoveries
		this.refreshNumOfUncoveredBox(box);
		//Refresh closed connections
		this.refreshClosedConstructionBuffer(box);
	}
	/**
	 * This method clear all closed construction
	 * @return closed construction
	 */
	public ArrayList<Construction> clearClosedConstructionBuffer(){
		ArrayList<Construction> ret = new ArrayList<Construction>();
		ret.addAll(this.closedConstructionBuffer);
		this.closedConstructionBuffer.clear();
		return ret;
	}
	/**
	 * Import the near explorer
	 * @param box
	 */
	private void importExplorers(Box box){
		try {
			importExplorers(box.getNorth());
			importExplorers(box.getSouth());
			importExplorers(box.getEast());
			importExplorers(box.getWest());
		} catch (AlreadyExplorerException e) {
			;
		}
	}
	/**
	 * import the explorer on the connection
	 * @param connection
	 * @throws AlreadyExplorerException 
	 */
	private void importExplorers(Connection connection) throws AlreadyExplorerException{
		//we control that there aren't explorer on the connection
		if(connection.getExplorer()==null){
			if(connection.getNeighbor()!=null){
				if(connection.getNeighbor().getExplorer()!=null){
					connection.setExplorer(connection.getNeighbor().getExplorer());
					//if we import an explorer so the side is been closed
					// we can close the side
					connection.getExplorer().getConstruction().closeSide();
				}
			}
		}else{
			throw new AlreadyExplorerException();
		}
	}
	/**
	 * expand explorers on the near boxes
	 * @param box
	 */
	private void expandExplorers(Box box){
		expandExplorers(box.getEast());
		expandExplorers(box.getNorth());
		expandExplorers(box.getWest());
		expandExplorers(box.getSouth());
	}
	/**
	 * expand explorers on the selected connection
	 * @param conn
	 */
	private void expandExplorers(Connection conn){
		expandExplorers(conn,conn.getOpposite());
		expandExplorers(conn,conn.getCounterclockwise());
		expandExplorers(conn,conn.getClockwise());
	}
	//we expand an explorer from conn1 to conn2
	//whit controls because in some case we have to do merge 
    private void expandExplorers(Connection conn1, InternalConnection conn2){
    	// if there is an explorer in connection 1
		if(conn1.getExplorer()!=null){
			//if conn2 exists
			if(conn2!=null){
				//if there is an explorer in connection 3
				Connection conn3=null;
				if(conn2 instanceof Connection){
				conn3 = (Connection)conn2;}
				if(conn3!=null){
					if(conn3.getExplorer()!=null){
						//if explorers reference to different connections
						if(!conn3.getExplorer().equals(conn1.getExplorer())){
							//do a merge of connections
							try {
								mergeConstruction(conn1.getExplorer(), conn3.getExplorer());
							} catch (IncopatibleConstructionException e) {
								;
							}
						}
						}else{
							//is an open side so:
							conn3.setExplorer(conn1.getExplorer());
							//add a side
							conn1.getExplorer().getConstruction().openSide();
						}
				}
			}
		}
    }
    /**
     * create explorers on every connections
     * @param box
     */
		private void createExplorers (Box box){
			createExplorer(box.getNorth());
			createExplorer(box.getSouth());
			createExplorer(box.getEast());
			createExplorer(box.getWest());
		}
		private void createExplorer(Connection connection){
			if(connection.getType()!=Type.NOTHING && connection.getExplorer()==null){
				if(connection.getType()==Type.CITY){
					connection.setExplorer(new Explorer(createCity()));
				}
				if(connection.getType()==Type.STREET){
					connection.setExplorer(new Explorer(createStreet()));
				}
				expandExplorers(connection);
			}
		}
	/** 
	 * This method control if it is possible to union the construction and if it is possible make the union
	 * @param explorer1,explorer2
	 * @throws IncopatibleConstructionException 
	 */
    private void mergeConstruction(Explorer explorer1,Explorer explorer2) throws IncopatibleConstructionException{
    	Construction c2 = explorer2.getConstruction();
		Construction c1 = explorer1.getConstruction();
    	boolean isCity1=false;
		boolean isCity2=false;
		//Control if there is a city or a street
		if(c1 instanceof City){
			isCity1=true; // city
		}else{
			isCity1=false; // street
		}
		if(c2 instanceof City){
			isCity2=true;
		}else{
			isCity2=false;
		}
		//if constructions are equal we do the merge
		if(isCity1==isCity2){
			if(isCity1){
				City city = createCity(); 
				c1.mergeConstruction(c2,city);
				explorer1.setConstruction(city);
				explorer2.setConstruction(city);
			}else{
				Street street = createStreet(); 
				c1.mergeConstruction(c2,street);
				explorer1.setConstruction(street);
				explorer2.setConstruction(street);
			}
		}else{
			throw new IncopatibleConstructionException();
		}
    }
    /** 
     * this method refresh the number of uncovered boxes that we discover in the construction 
     * @param box 
     */
    //we use a local table for boxes already counted
    private void refreshNumOfUncoveredBox(Box box){
    	HashMap<Integer,Construction> t = new HashMap<Integer, Construction>();
    	refreshNumOfUncoveredBox(box,box.getNorth(),t);
    	refreshNumOfUncoveredBox(box,box.getSouth(),t);
    	refreshNumOfUncoveredBox(box,box.getEast(),t);
    	refreshNumOfUncoveredBox(box,box.getWest(),t);
    }
    private void refreshNumOfUncoveredBox(Box box, Connection conn, HashMap<Integer,Construction> t ){
    	if(conn.getExplorer()!=null){
    		int id = conn.getExplorer().getConstruction().getID();
    		if(!t.containsKey(id)){
    			conn.getExplorer().getConstruction().newBox(box.getCoordinate());
    			t.put(id, conn.getExplorer().getConstruction());
    		}
    	}
    }
    /**Added to buffer the buildings closed
     * @param box
     */
    private void refreshClosedConstructionBuffer(Box box){
    	HashMap<Integer,Construction> t = new HashMap<Integer, Construction>();
    	refreshClosedConstructionBuffer(box.getNorth(),t);
    	refreshClosedConstructionBuffer(box.getSouth(),t);
    	refreshClosedConstructionBuffer(box.getEast(),t);
    	refreshClosedConstructionBuffer(box.getWest(),t);
    	for(Construction c : t.values()){
    		if(c.isClosed()){
    			closedConstructionBuffer.add(c);
    			for(int i=0;i<openedConstructionBuffer.size();i++){
    				if(openedConstructionBuffer.get(i).equals(c)){
    					openedConstructionBuffer.remove(i);
    					break;
    				}
    			}
    		}
    	}
    }
    private void refreshClosedConstructionBuffer(Connection conn,HashMap<Integer,Construction> t){
    	if(conn.getExplorer()!=null){
    		int id = conn.getExplorer().getConstruction().getID();
    		if(!t.containsKey(id)){
    			t.put(id, conn.getExplorer().getConstruction());
    		}
    	}
    }
    /**method to create a city
     * @return the created city
     */
    private City createCity(){
    	City ret = new City(nextID);
    	openedConstructionBuffer.add(ret);
    	nextID++;
    	return ret;
    }
    /**method to create a street
     * @return the created Street
     */
    private Street createStreet(){
    	Street ret = new Street(nextID);
    	openedConstructionBuffer.add(ret);
    	nextID++;
    	return ret;
    }
    public ArrayList<Construction> getOpenedConstruction(){
    	return this.openedConstructionBuffer;
    }
}
