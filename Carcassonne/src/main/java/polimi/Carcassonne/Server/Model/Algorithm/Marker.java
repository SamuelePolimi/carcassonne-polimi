package polimi.Carcassonne.Server.Model.Algorithm;
import polimi.Carcassonne.Server.Model.Player;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Connection;
/**Marker represent a market with information about his owner
	@author Omar Scotti - Samuele Tosatto
*/
public class Marker {
	private Player player;
	private Connection connection;
	private Box box;
	/**
	 * This method creates the marker
	 * @param player
	 */
	public Marker(Player player){
		this.player=player;
	}
	/**
	 * get the player
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * get the connection
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}
	/**
	 * remove the connection from the marker
	 */
	public void removeConnection(){
		this.connection=null;
	}
	/**
	 * set the connection on which there is the marker
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	/**
	 * @return box
	 */
	public Box getCard() {
		return box;
	}
	/**
	 * @param box
	 */
	public void setCard(Box box) {
		this.box = box;
	}
	/**
	 * Print of player's marker
	 */
	@Override
	public String toString(){
		return this.getPlayer().toString();
	}
}
