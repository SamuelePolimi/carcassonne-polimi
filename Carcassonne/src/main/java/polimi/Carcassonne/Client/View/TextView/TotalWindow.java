package polimi.Carcassonne.Client.View.TextView;
import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.IModelView.IEventGameState;
import polimi.Carcassonne.Client.IModelView.IModelViewGameState;
import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Client.Model.ClientPlayer;
/**
 * @author Omar Scotti - Samuele Tosatto
 * Prints the total window
 */
public  class TotalWindow extends TextWindow implements IEventGameState {
	private String name;
	private TableTextWindow table;
	private ListOfPlayerTextWindow players;
	private CardTextWindow card;
	private HorizontalStringTextWindow border;
	private HorizontalStringTextWindow turn;
	private HorizontalStringTextWindow whattodo;
	private HorizontalStringTextWindow alert;
	private HorizontalStringTextWindow howtodo;
	private IEventTotalView event;
	private IModelViewGameState gs;
	private boolean onlineModality;
	public TotalWindow(TextRenderize r, int x, int y ) {
		super(r, x, y, 0, 0);
		setOnlineModality(true);
	}	
	/**
	 * @deprecated
	 * Constructor of total window
	 * @param gs interface of game state
	 * @param r textRenderize
	 * @param x
	 * @param y
	 */
	@Deprecated
	public TotalWindow(IModelViewGameState gs,TextRenderize r, int x, int y ) {
		super(r, x, y, 0, 0);
		reSet(gs);
		setOnlineModality(true);
	}
	/**
	 * Set event
	 * @param e
	 */
	public void setEvent(IEventTotalView e){
		event=e;
	}
	/**
	 * Refresh
	 */
	@Override
	public void refresh() {
		super.killAllChilds();
		table.refresh();
		players.reSet(gs.getListOfPlayers());
		super.insertToBottom(table);
		super.insertToRight(players,4);
		StringBuffer bf= new StringBuffer();
		for(int i=0;i<this.getWidth();i++){
			bf.append("-");
		}
		border=new HorizontalStringTextWindow(bf.toString(),this.getMyTextRenderize(),0,0);
		super.insertToBottom(border,1);
		int h = super.insertToBottom(turn,1);
		super.insertToBottom(whattodo);
		super.insertToBottom(alert);
		super.insertToBottom(howtodo);
		card.setX(0);
		card.setY(h-2);
		super.insertTextWindow(card);
		refreshed();
	}
	/**
	 * reSet the object
	 * @param o IModelViewGameState
	 */
	@Override
	public void reSet(Object o) {
		if(o instanceof IModelViewGameState){
			gs = (IModelViewGameState)o;
			gs.setGameStateEvent(this);
			table=new TableTextWindow(gs,this.getMyTextRenderize(),0,0);
			players=new ListOfPlayerTextWindow(gs.getListOfPlayers(),this.getMyTextRenderize(),0,0);
			card= new CardTextWindow(gs.getNextCard(),this.getMyTextRenderize(),0,0);
			if(gs.getCurrentPlayer()!=null){
				turn = new HorizontalStringTextWindow("It's " + gs.getCurrentPlayer().toString() + " turns",this.getMyTextRenderize(),17,0);
			}
			else{
				turn = new HorizontalStringTextWindow("It's " + "your" + " turns",this.getMyTextRenderize(),17,0);
			}
			whattodo = new HorizontalStringTextWindow("loading...",this.getMyTextRenderize(),17,0);
			alert = new HorizontalStringTextWindow(" ",this.getMyTextRenderize(),17,0);
			howtodo = new HorizontalStringTextWindow("wait...",this.getMyTextRenderize(),17,0);
			refresh();
			toRefresh();
		}
	}
	/**
	 * @param player
	 */
	public void changeTurn(ClientPlayer player) {
		turn = new HorizontalStringTextWindow("It's " + player.toString() + " turns",this.getMyTextRenderize(),17,0);
		
		toRefresh();
		if(onlineModality){
			event.toWaitAndRefresh();
		}
	}
	/**
	 * If is your turn
	 */
	public void yourTurn() {
		turn = new HorizontalStringTextWindow("It's your turn!",this.getMyTextRenderize(),17,0);
		toRefresh();
	}
	/**
	 * Print the instruction for the player
	 * @param card
	 */
	public void insertCard(ClientCard card) {
		whattodo=new HorizontalStringTextWindow("Insert a card or rotate",this.getMyTextRenderize(),17,0);
		alert= new HorizontalStringTextWindow(" ",this.getMyTextRenderize(),17,0);
		if(gs.isMyTurn()){
			howtodo=new HorizontalStringTextWindow("For insert write 'x,y' for rotate write 'rotate'",this.getMyTextRenderize(),17,0);
		}else{
			howtodo=new HorizontalStringTextWindow("Wait your turn.'",this.getMyTextRenderize(),17,0);
		}
		this.card.reSet(card);
		toRefresh();
		if(onlineModality){
			if(event!=null){
				event.toInsertOrRotate();
			}
		}else if(gs.isMyTurn()){
			if(event!=null){
				event.toInsertOrRotate();
			}
		}
	}
	/**
	 * Error insert
	 */
	public void errorInsert() {
		alert= new HorizontalStringTextWindow("Move not valid",this.getMyTextRenderize(),17,0);
		toRefresh();
		if(onlineModality){
			if(event!=null){
			event.toInsertOrRotate();
			}
		}else if(gs.isMyTurn()){
			if(event!=null){
			if(gs.hasToInsertMark()){
				event.toInsertMarkerOrPass();
			}else{
				event.toInsertOrRotate();
			}
			}
		}
	}
	/**
	 * Insert marker or pass instruction
	 */
	public void markerOrPass() {
		whattodo=new HorizontalStringTextWindow("Insert a marker or pass",this.getMyTextRenderize(),17,0);
		alert= new HorizontalStringTextWindow(" ",this.getMyTextRenderize(),17,0);
		if(gs.isMyTurn()){
			howtodo=new HorizontalStringTextWindow("Write marker position <north, south, east, west>",this.getMyTextRenderize(),17,0);
		}else{
			howtodo=new HorizontalStringTextWindow("Wait your turn.'",this.getMyTextRenderize(),17,0);
		}
		refresh();
		toRefresh();
		if(onlineModality){
			if(event!=null){
				event.toInsertMarkerOrPass();
			}
		}else if(gs.isMyTurn()){
			if(event!=null){
				event.toInsertMarkerOrPass();
			}
		}
	}
	/**
	 * Update next card
	 * @param card
	 */
	public void updateNext(ClientCard car) {
		this.reSet(car);
		card=new CardTextWindow(car,this.getMyTextRenderize(),0,0);
		toRefresh();
		if(onlineModality){
			if(event!=null){
				event.toWaitAndRefresh();
			}
		}else if(gs.isMyTurn()){
			if(event!=null){
				event.toInsertOrRotate();
			}
		}
	}
	/**
	 * Table dimension is changed
	 */
	public void tableDimensionChanged() {
		table.refresh();
		refresh();
	}
	/**
	 * @return onlineModality: true if we are in online modality
	 */
	public boolean isOnlineModality() {
		return onlineModality;
	}
	/**
	 * @param onlineModality
	 */
	public void setOnlineModality(boolean onlineModality) {
		this.onlineModality = onlineModality;
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
	public void setNome(String name) {
		table.setNome(name);
		this.name = name;
	}
	public void updatePlayers() {
		;
		
	}
	/**
	 * End of the game
	 */
	public void endGame() {
		Printer.print("Game ends");
	}
}
