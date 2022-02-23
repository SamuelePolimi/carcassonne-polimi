package resource;

import java.awt.Color;
import java.net.URL;
import polimi.Carcassonne.Client.IModelView.IModelViewCard;
import polimi.Carcassonne.Server.Model.Graph.Type;

public class GetURLFile {
	private URL path;
	private static GetURLFile urlFile;
	
	/**
	 * PATTERN SINGLETON
	 * Return the object who provides URL-Files
	 * @return
	 */
	public synchronized static GetURLFile getMyUrlFile(){
		if(urlFile==null){
			urlFile=new GetURLFile();
			return urlFile;
		}else{
			return urlFile;
		}
	}
	/**
	 * gets carcassone.txt
	 */
	private GetURLFile(){
		path=this.getClass().getResource("/carcassonne.txt");
	}
	/**
	 * gets black-marker
	 * @return URL
	 */
	public URL getBlack(){
		URL cardPath=this.getClass().getResource("/BLACK.gif");
		return cardPath;
	}
	/**
	 * gets blue marker
	 * @return URL
	 */
	public URL getBlue(){
		URL cardPath=this.getClass().getResource("/BLUE.gif");
		return cardPath;
	}
	/**
	 * gets Red-Marker
	 * @return
	 */
	public URL getRed(){
		URL cardPath=this.getClass().getResource("/RED.gif");
		return cardPath;
	}
	/**
	 * gets Yellow-Marker
	 * @return
	 */
	public URL getYellow(){
		URL cardPath=this.getClass().getResource("/YELLOW.gif");
		return cardPath;
	}
	/**
	 * gets Green-Marker
	 * @return
	 */
	public URL getGreen(){
		URL cardPath=this.getClass().getResource("/GREEN.gif");
		return cardPath;
	}
	/**
	 * gets Carcassone Txt
	 * @return
	 */
	public URL getURLCarcassonneTxt(){
		return path;
	}
	/**
	 * gets a card by IModelViewCard
	 * @param Client-Card
	 * @return URL
	 */
	public URL getURLCard(IModelViewCard c) {
		URL cardPath;
		String url="/";
		//getting type of sides
		url+=getByType(c.getNorth().getType());
		url+=getByType(c.getEast().getType());
		url+=getByType(c.getSouth().getType());
		url+=getByType(c.getWest().getType());
		//getting internal-connection
		url+=getByConnection(c.getNorthSouth());
		url+=getByConnection(c.getNorthEast());
		url+=getByConnection(c.getNorthWest());
		url+=getByConnection(c.getEastWest());
		url+=getByConnection(c.getSouthEast());
		url+=getByConnection(c.getSouthWest());
		url+=".jpg";
		cardPath=this.getClass().getResource(url);
		
		return cardPath;
	}
	/**
	 * Returns char by type
	 * @param t
	 * @return
	 */
	private char getByType(Type t){
		if(t==Type.NOTHING){
			return 'N';
		}else if(t==Type.CITY){
			return 'C';
		}
		return 'S';
	}
	private char getByConnection(boolean c){
		if(c){
			return '1';
		}
		return '0';
	}
	/**
	 * gets the marker by color
	 * @param color
	 * @return URL
	 */
	public URL getMarker(Color color){
		if(color.equals(Color.BLACK)){
			return this.getBlack();
		}
		if(color.equals(Color.BLUE)){
			return this.getBlue();
		}
		if(color.equals(Color.RED)){
			return this.getRed();
		}
		if(color.equals(Color.YELLOW)){
			return this.getYellow();
		}
		return this.getGreen();
	}
}
