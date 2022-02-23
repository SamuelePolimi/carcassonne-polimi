package polimi.Carcassonne.Server.Model.Graph;
/**
 * This class represent only the internal connection in a Card (Not in a box)
 *  1) It has normally a Type
 *  2) It has internalconnection betwen his sides and other if BasicCard in input has right booleans connections
 *  
 *  @author Omar Scotti - Samuele Tosatto
 */
public class InternalConnection {
	private InternalConnection clockwise=null;
	private InternalConnection counterclockwise=null;
	private InternalConnection opposite=null;
	private Connection connectionOver; 	
	private Type type;
	/**
	 * Constructor of an internal connection
	 * @param type of internal connection
	 */
	public InternalConnection(Type type){
		setType(type);
	}
	/**
	 * set up the connection that is over the internal connection
	 * @param connection over
	 */
	protected void setConnection(Connection connection){
		this.connectionOver=connection;
	}
	/**
	 * @return the connection that is over the internal connection
	 */
	protected Connection getConnection(){
		return this.connectionOver;
	}
	/**
	 * @return the clockwise internal connection 
	 * (to the right of that which you call the method)
	 */
	public InternalConnection getClockwise() {
		return clockwise;
	}
	/**
	 * @return the counterclockwise internal connection
	 * (to the left of that which you call the method)
	 */
	public InternalConnection getCounterclockwise() {
		return counterclockwise;
	}
	/**
	 * @return the opposite connection of that which you call the method
	 */
	public InternalConnection getOpposite() {
		return opposite;
	}
	/**
	 * set the counterclockwise internal connection 
	 * @param counterclockwise internal connection
	 */
	// Set delle connessioni interne collegate,  controllo grazie al metodo controllo e a
	protected void setCounterclockwise(InternalConnection counterclockwise) {
				this.counterclockwise = counterclockwise;
	}
	/**
	 * set the opposite internal connection 
	 * @param opposite internal connection
	 */
	protected void setOpposite(InternalConnection opposite) {
					this.opposite = opposite;
	}
	/**
	 * set the clockwise internal connection 
	 * @param clockwise internal connection
	 */
	protected void setClockwise(InternalConnection clockwise) {
				this.clockwise = clockwise;
	}
	/**
	 * @return type of this internal connection
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type to set in this internal connection
	 */
	protected void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * whit this method the internal connection will be unchangeable
	 */
	protected void setFinalInternalConnection() {
	}
	@Override
	public String toString(){
		String ret="";
		if(type==Type.STREET){
			ret+="S";
		}else if(type==Type.CITY){
			ret+="C";
		}else if(type==Type.NOTHING){
			ret+="N";
		}
		return ret;
	}
	/**
	 * Override of equals to see if two internal connections are equals
	 * @param o: InternalConnection
	 * @return true if are equals
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof InternalConnection){
			if(((InternalConnection) o).getType().equals(this.getType())){
				if(((InternalConnection) o).getOpposite().getType().equals(this.getOpposite().getType()) || (((InternalConnection) o).getOpposite()==null && this.getOpposite()==null)){
					if(((InternalConnection) o).getClockwise().getType().equals(this.getClockwise().getType()) || (((InternalConnection) o).getClockwise()==null && this.getClockwise()==null)){
						if(((InternalConnection) o).getCounterclockwise().getType().equals(this.getCounterclockwise().getType()) || (((InternalConnection) o).getCounterclockwise()==null && this.getCounterclockwise()==null)){
							return true;
						}
					}
				}
			}
			return false;
		}
		return false;
	}
	/**
	 * Override hashCode
	 */
	@Override
	public int hashCode(){
		return 0;
	}
}
