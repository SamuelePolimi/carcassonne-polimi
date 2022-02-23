package polimi.Carcassonne.Server.Controller;
/**
 * @author Omar Scotti - Samuele Tosatto
 * Event used with the timer
 * implemented by ClientConnection
 * implemented by Game
 */
public interface IEventTimer {
	/**
	 * Event to call when the timer is finished
	 */
	public void tick();
}
