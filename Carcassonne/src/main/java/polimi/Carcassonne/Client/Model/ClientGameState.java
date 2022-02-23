package polimi.Carcassonne.Client.Model;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import polimi.Carcassonne.Client.IModelController.IModelControllerCard;
import polimi.Carcassonne.Client.IModelController.IModelControllerGameState;
import polimi.Carcassonne.Client.IModelView.IEventGameState;
import polimi.Carcassonne.Client.IModelView.IEventListOfPlayer;
import polimi.Carcassonne.Client.IModelView.IEventTable;
import polimi.Carcassonne.Client.IModelView.IModelViewGameState;
import polimi.Carcassonne.Client.IModelView.IModelViewPlayer;
import polimi.Carcassonne.Server.Model.Graph.*;
/**
 * This class represents the table and turns
 * @author Omar Scotti - Samuele Tosatto
 */
public class ClientGameState implements IModelViewGameState, IModelControllerGameState {
	private String name;
	private Map<Coordinate,ClientCard> table;
	private ClientPlayer currentPlayer;
	private ClientPlayer thisClient;
	private ArrayList<ClientPlayer> listOfPlayers;
	private int minX;
	private int minY;
	private int maxY;
	private int maxX;
	private boolean myturn=false;
	private ClientCard next;
	private boolean insertMarker=false;
	private IEventTable tableEvent;
	private IEventGameState gsEvent;
	private IEventListOfPlayer lopEvent;
	/**
	 * Constructor of the client game state
	 */
	public ClientGameState(){
		table=new HashMap<Coordinate, ClientCard>();
		table.put(new Coordinate(0,0), new ClientCard(new Coordinate(0,0)));
		listOfPlayers=new ArrayList<ClientPlayer>();
		minX=0;
		minY=0;
		maxY=0;
		maxX=0;
		updateCard(0, 0, next = new ClientCard(new Coordinate(0,0))); 
		myturn=false;
		next=new ClientCard(new Coordinate(0,0));
	}
	/**
	 * Refresh the neighbor
	 * @param c: coordinate
	 */
	private void refreshNeighbor(Coordinate c){
		Coordinate east= c.getMyEast();
		table.get(c).getEast().setConnected();
		if(table.containsKey(east)){
			table.get(east).getWest().setConnected();
			if(table.get(east).isEmpty() ^ table.get(c).isEmpty()){
				table.get(east).getWest().setNotConnected();
				table.get(c).getEast().setNotConnected();
			}
			if(tableEvent!=null){
				tableEvent.updateCard(table.get(east));
			}
		}
		Coordinate west= c.getMyWest();
		table.get(c).getWest().setConnected();
		if(table.containsKey(west)){
			table.get(west).getEast().setConnected();
			if(table.get(west).isEmpty() ^ table.get(c).isEmpty()){
				table.get(west).getEast().setNotConnected();
				table.get(c).getWest().setNotConnected();
			}
			if(tableEvent!=null){
				tableEvent.updateCard(table.get(west));
			}
		}
		Coordinate north= c.getMyNorth();
		table.get(c).getNorth().setConnected();
		if(table.containsKey(north)){
			table.get(north).getSouth().setConnected();
			if(table.get(north).isEmpty() ^ table.get(c).isEmpty()){
				table.get(north).getSouth().setNotConnected();
				table.get(c).getNorth().setNotConnected();
			}if(tableEvent!=null){
				tableEvent.updateCard(table.get(north));
			}
		}
		Coordinate south= c.getMySouth();
		table.get(c).getSouth().setConnected();
		if(table.containsKey(south)){
			table.get(south).getNorth().setConnected();
			if((table.get(south).isEmpty() ^ table.get(c).isEmpty()) ){
				table.get(south).getNorth().setNotConnected();
				table.get(c).getSouth().setNotConnected();
			}
			if(tableEvent!=null){
				tableEvent.updateCard(table.get(south));
			}
		}
		if(tableEvent!=null){
			tableEvent.updateCard(table.get(c));
		}
	}
	/**
	 * @return currenPlayer
	 */
	public IModelViewPlayer getCurrentPlayer() {
		return currentPlayer;
	}
	/**
	 * @return listOfPlayers
	 */
	public ArrayList<ClientPlayer> getListOfPlayers() {
		return listOfPlayers;
	}
	/**
	 * @return maxX
	 */
	public int getXMax() {
		return maxX;
	}
	/**
	 * @return maxY
	 */
	public int getYMax() {
		return maxY;
	}
	/**
	 * @return minY
	 */
	public int getYMin() {
		return minY;
	}
	/**
	 * @return minX
	 */
	public int getXMin() {
		return minX;
	}
	/**
	 * @return mapOfCards
	 */
	public ArrayList<ClientCard> getMapOfCards(){
		return new ArrayList<ClientCard>(table.values());
	}
	/**
	 * @param x
	 * @param y
	 * @return clientCard
	 */
	public ClientCard getClientCard(int x, int y) {
		if(table.containsKey(new Coordinate(x,y))){
			return table.get(new Coordinate(x,y));
		}else{
			return null;
		}
	}
	/**
	 * Refresh
	 * @param x
	 * @param y
	 */
	private void refresh(int x,int y){
		boolean flag=false;
		if(x>maxX){
			maxX=x;
			flag=true;
		}
		if(y>maxY){
			maxY=y;
			flag=true;
		}
		if(x<minX){
			minX=x;
			flag=true;
		}
		if(y<minY){
			minY=y;
			flag=true;
		}
		if(flag){
			if(gsEvent!=null){
			gsEvent.tableDimensionChanged();
			}
		}
	}
	/**
	 * Update a card
	 * @param x
	 * @param y
	 * @param card
	 */
	public void updateCard(int x, int y, IModelControllerCard card) {
		Coordinate c= new Coordinate(x,y);
		//----This section update number of marker of player
		if(listOfPlayers.size()!=0){
		int[] prew= new int[listOfPlayers.size()];
		for(int i =0;i<prew.length;i++){
			prew[i]=0;
		}
		if(table.containsKey(c)){
			prew = numMarker(table.get(c));
		}
		int[] next = numMarker((ClientCard)card);
		for(int i=0;i<prew.length;i++){
			int delta =listOfPlayers.get(i).getMarkerNum()-( next[i]-prew[i]);
			if(next[i]-prew[i]!=0){
			this.listOfPlayers.get(i).setMarkerNum(delta);
			}
		}
		}
		//----End section
		table.put(c, (ClientCard)card);
		if(!table.containsKey(c.getMyEast())){
			insertCard(new ClientCard(c.getMyEast()));
		}
		if(!table.containsKey(c.getMyWest())){
			insertCard(new ClientCard(c.getMyWest()));
		}
		if(!table.containsKey(c.getMyNorth())){
			insertCard(new ClientCard(c.getMyNorth()));
		}
		if(!table.containsKey(c.getMySouth())){
			insertCard(new ClientCard(c.getMySouth()));
		}
		this.refreshNeighbor(c.getMyEast());
		this.refreshNeighbor(c.getMyWest());
		this.refreshNeighbor(c.getMyNorth());
		this.refreshNeighbor(c.getMySouth());
		refresh(x,y);
		if(tableEvent!=null){
			this.tableEvent.updateCard((ClientCard)card);	
		}
		if(gsEvent!=null){
			if(!insertMarker){
				insertMarker=true;
				this.gsEvent.markerOrPass();
			}
		}
	}
	/**
	 * Insert a card
	 * @param card
	 */
	private void insertCard(ClientCard card){
		Coordinate c = card.getCoordinate();
		table.put(new Coordinate(c.getX(),c.getY()), (ClientCard)card);
		refresh(c.getX(),c.getY());
	}
	/**
	 * Insert the number of players
	 * @param n: number of players
	 */
	public void insertNPlayer(int n) {
		//secondo l’ordine rosso, blu, verde, giallo e nero
		Color colori[] = new Color[]{Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.BLACK};
		for(int i=0;i<n;i++){
			this.listOfPlayers.add(new ClientPlayer(colori[i]));
		}
		if(this.gsEvent!=null){
			gsEvent.updatePlayers();
		}
	}
	/**
	 * @param color: color of the player
	 */
	public void setMyPlayer(Color color) {
		this.thisClient=new ClientPlayer(color);
	}
	/**
	 * @param color: color of the current player
	 */
	public void setCurrentPlayer(Color color) {
		insertMarker=false;
		for(ClientPlayer cl:this.listOfPlayers){
			if(cl.getColor().equals(color)){
				if(currentPlayer!=null){
					currentPlayer.setCurrent(false);
				}
				currentPlayer=cl;
				currentPlayer.setCurrent(true);
			}
		}
		if(currentPlayer!=null){
			if(this.thisClient.getColor().equals(currentPlayer.getColor())){
				this.myturn=true;
				if(gsEvent!=null){
					gsEvent.yourTurn();
					return;
				}
			}
		}
		this.myturn=false;
		if(gsEvent!=null){
			gsEvent.changeTurn(currentPlayer);
		}
	}
	/**
	 * @param next card
	 */
	public void setNextCard(IModelControllerCard generateCard) {
		this.insertMarker=false;
		next=(ClientCard)generateCard;
		if(gsEvent!=null){
				gsEvent.insertCard((ClientCard)generateCard);
			}
	}
	/**
	 * @param rotated card
	 */
	public void setRotatedCard(IModelControllerCard generateCard) {
		if(generateCard instanceof ClientCard){
			next=(ClientCard)generateCard;
			if(gsEvent!=null){
				gsEvent.updateNext((ClientCard)generateCard);
			}
		}
	}
	/**
	 * Move not valid
	 */
	public void moveNotValid() {
		if(gsEvent!=null){
			gsEvent.errorInsert();
		}
	}
	/**
	 * @param event
	 */
	public void setTableEvent(IEventTable e) {
		tableEvent=e;
		
	}
	/**
	 * @param event
	 */
	public void setGameStateEvent(IEventGameState e) {
		gsEvent=e;
	}
	/**
	 * @return nextCard
	 */
	public ClientCard getNextCard() {

		return next;
	}
	/**
	 * @param color: color of the client
	 */
	public void setClient(Color color) {
		for(ClientPlayer p : this.listOfPlayers){
			if(p.getColor().equals(color)){
				thisClient=p;
			}
		}
	}
	/**
	 * @return true if is my turn
	 */
	public boolean isMyTurn() {
		return myturn;
	}
	/**
	 * Set score for a single player
	 * @param color
	 * @param point
	 */
	public void setScore(Color color, int point) {
		for(ClientPlayer p : this.listOfPlayers){
			if(p.getColor().equals(color)){
				p.setPoints(point);
			}
		}
	}
	/**
	 * Set score for all players
	 * @param colors
	 * @param points 
	 */
	public void setScore(Color[] colors, int[] points) {
		int i=0;
		for(Color color:colors){
		for(ClientPlayer p : this.listOfPlayers){
			if(p.getColor().equals(color)){
				p.setPoints(points[i]);
			}
		}
		i++;
		}
	}
	/**
	 * End of the game
	 */
	public void endGame() {
		this.gsEvent.endGame();
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return insertMarker: true if there is a marker to insert
	 */
	public boolean hasToInsertMark(){
		return this.insertMarker;
	}
	/**
	 * @param card
	 * @return numbers of markers
	 */
	private int[] numMarker(ClientCard card){
		int[] ret = new int[this.listOfPlayers.size()];
		for(int i=0;i<ret.length;i++){
			ret[i]=0;
		}
		setIntArray(ret,card.getNorth().getPlayer());
		setIntArray(ret,card.getSouth().getPlayer());
		setIntArray(ret,card.getEast().getPlayer());
		setIntArray(ret,card.getWest().getPlayer());
		return ret;
	}
	/**
	 * @param arr
	 * @param color
	 */
	private void setIntArray(int[] arr,Color color){
		if(color==null){
			return;
		}
		if(color.equals(Color.RED)){
			arr[0]++;
		}else if(color.equals(Color.BLUE)){
			arr[1]++;
		}else if(color.equals(Color.GREEN)){
			arr[2]++;
		}else if(color.equals(Color.YELLOW)){
			arr[3]++;
		}else if(color.equals(Color.BLACK)){
			arr[4]++;
		}
	}
	/**
	 * @return thisClient
	 */
	public ClientPlayer getThisClient(){
		return thisClient;
	}
	/**
	 * @return table
	 */
	public HashMap<Coordinate, ClientCard> getTable(){
		return (HashMap<Coordinate, ClientCard>)table;
	}
	public void setInsertionCardState() {
		this.insertMarker=true;
	}
	public void removePlayer(Color colo) {
		for(int i=0;i<this.listOfPlayers.size();i++){
			if(listOfPlayers.get(i).getColor().equals(colo)){
				listOfPlayers.remove(i);
				break;
			}
		}
		if(lopEvent!=null){
			lopEvent.refreshListOfPlayer(this.listOfPlayers);
		}
	}
	public void setIEventLop(IEventListOfPlayer event) {
		this.lopEvent=event;
	}
}
