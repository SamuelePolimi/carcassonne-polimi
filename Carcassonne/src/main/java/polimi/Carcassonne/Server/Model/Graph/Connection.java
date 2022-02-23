package polimi.Carcassonne.Server.Model.Graph;
import polimi.Carcassonne.Server.Model.Algorithm.Explorer;
import polimi.Carcassonne.Server.Model.Algorithm.Marker;
import polimi.Carcassonne.Server.Model.Exception.AlreadyCoveredAreaException;
import polimi.Carcassonne.Server.Model.Exception.ConnectionAlreadyFixedException;
import polimi.Carcassonne.Server.Model.Exception.NothingTypeException;
/**
 * This class represents a connection in a Box (not in a card)
 * 1) Connection can have type (it depends if box who contain this connection is Empty or not)
 * 2) Connection can be connected or not (it depends if it has a neighbor)
 * 3) Connection can have Explorer if it has a type and this tipe is "Street" or "City"
 * 4) Connection can have Marker if player put it on
 * 5) Connection have other things have just InternalConnection
 * 
 * @author Omar Scotti - Samuele Tosatto
 */
public class Connection extends InternalConnection{
	private Connection neighbor;
	private Explorer explorer;
	private Marker marker;
	/**
	 * Create connection with type
	 * @param type
	 */
	protected Connection(Type type) {
		super(type);
	}
	/**
	 * Create connection without type
	 */
	protected Connection(){
		super(Type.NOTHING);
	}
	/**
	 * Connect with neighbor
	 * @param nearConnection
	 * @throws ConnectionAlreadyFixedException if there is already the connection
	 */
	public void connect(Connection nearConnection) throws ConnectionAlreadyFixedException{
		if(neighbor==null){
			neighbor=nearConnection;
		}else{
			throw new ConnectionAlreadyFixedException();
		}
	}
	/** 
	 * Method to verify if there is a near connection
	 * @return if is connected
	 */
	public boolean isConnected(){
		return neighbor!=null;
	}
	/**
	 * set the internal connection
	 * @param internal connection
	 */
	public void setMyInternal(InternalConnection ic){
		if(ic.getOpposite()!=null){
			this.setOpposite(ic.getOpposite().getConnection());
		}
		if(ic.getClockwise()!=null){
			this.setClockwise(ic.getClockwise().getConnection());
		}
		if(ic.getCounterclockwise()!=null){
			this.setCounterclockwise(ic.getCounterclockwise().getConnection());
		}
		this.setType(ic.getType());
	}
	/**
	 * @return explorer
	 */
	public Explorer getExplorer() {
		return explorer;
	}
	/**
	 * @return neighbor
	 */
	public Connection getNeighbor(){
		return neighbor;
	}
	/**
	 * @param explorer
	 */
	public void setExplorer(Explorer explorer) {
		this.explorer = explorer;
	}
	/**
	 * @return marker
	 */
	public Marker getMarker() {
		return marker;
	}
	/**
	 * remove marker from connection
	 */
	public void removeMarker(){
		this.marker=null;
	}
	/**
	 * put a marker on a construction
	 * @param marker
	 * @throws AlreadyCoveredAreaException if there is already a marker
	 * @throws NothingTypeException if you are trying to put the marker on type "nothing"
	 */
	public void putMarker(Marker marker) throws AlreadyCoveredAreaException, NothingTypeException {
		if(this.getType()==Type.NOTHING) {
			throw new NothingTypeException();
		}
		this.explorer.getConstruction().addMarker(marker);
		this.marker = marker;
	}
	/**
	 * Print of a marker in a connection
	 */
	@Override
	public String toString(){
		String ret=super.toString();
		if(marker!=null){
			ret+="+"+marker.toString();
		}
		return ret;
	}
}
