package polimi.Carcassonne.Client.View.TextView;
import java.awt.Color;
import polimi.Carcassonne.Client.IModelView.IEventClientCard;
import polimi.Carcassonne.Client.IModelView.IModelViewCard;
import polimi.Carcassonne.Server.Model.Graph.Type;
/**
 * @author Omar Scotti - Samuele Tosatto
 * This class represents a card on the text view
 */
public class CardTextWindow extends TextWindow implements IEventClientCard {
	private IModelViewCard card;
	private HorizontalStringTextWindow topBoard;    //0,0
	private HorizontalStringTextWindow bottomBoard;   ///0,0
	private VerticalStringTextWindow leftBoard;
	private VerticalStringTextWindow rightBoard;
	private HorizontalStringTextWindow labelN,labelE,labelS, labelW;
	private HorizontalStringTextWindow centerLabel;
	private boolean empty;
	/*magic number avoidance*/
	private final static int cardLenght=15;
	private final static int cardHeight=7;
	/**
	 * Constructor of card text window
	 * @param card
	 * @param r
	 * @param x
	 * @param y
	 */
	public CardTextWindow(IModelViewCard card, TextRenderize r, int x, int y) {
		super(r, x, y, cardLenght, cardHeight);
		reSet(card);
	}
	/**
	 * Refresh
	 */
	@Override
	public void refresh() {
		super.killAllChilds();
		super.insertTextWindow(topBoard);
		super.insertTextWindow(bottomBoard);
		super.insertTextWindow(leftBoard);
		super.insertTextWindow(rightBoard);
		if(empty){
			super.insertTextWindow(centerLabel);
		}else{
			super.insertTextWindow(labelW);
			super.insertTextWindow(labelS);
			super.insertTextWindow(labelE);
			super.insertTextWindow(labelN);
		}
		refreshed();
	}
	/**
	 * Set connection
	 * @param card: model view of the card
	 */
	private void connectionSets(IModelViewCard card){
		TextRenderize r=this.getMyTextRenderize();

		if(card.getNorth().isConnected()){
			this.topBoard = new HorizontalStringTextWindow("+.............+",r,0,0);
		}else{
			this.topBoard = new HorizontalStringTextWindow("+#############+",r,0,0);
		}
		if(card.getEast().isConnected()){
			rightBoard = new VerticalStringTextWindow("+.....+",r,cardLenght-1,0);
		}else{
			rightBoard= new VerticalStringTextWindow( "+#####+",r,cardLenght-1,0);
		}
		if(card.getSouth().isConnected()){
			bottomBoard = new HorizontalStringTextWindow("+.............+",r,0,cardHeight-1);
		}else{
			bottomBoard = new HorizontalStringTextWindow("+#############+",r,0,cardHeight-1);
		}
		if(card.getWest().isConnected()){
			leftBoard = new VerticalStringTextWindow("+.....+",r,0,0);
		}else{
			leftBoard = new VerticalStringTextWindow("+#####+",r,0,0);
		}
	}
	/**
	 * Set connection
	 * @param card
	 */
	private void setConnectionName(IModelViewCard card){
		//In this method North=0, East=1, South=2; West=3.
		int north=0,east=1,south=2,west=3;
		//conn[] represents connection of each couple of possible orientation
		//determined by this formula: conn[or1 + or2*4] = true or false.
		//please note that conn[or1+or2*4]=conn[or1*4+or2], and when or1==or2 -> conn[5or1]=true
		boolean conn[] = new boolean[16];
		//for example, NorthEast connection has North=0, East=1, so
		for(int i =north;i<=west;i++){
			setConnection(i,i,conn,true);
		}
		setConnection(north,east,conn,card.getNorthEast());
		setConnection(north,south,conn,card.getNorthSouth());
		setConnection(north,west,conn,card.getNorthWest());
		setConnection(east,south,conn,card.getSouthEast());
		setConnection(west,east,conn,card.getEastWest());
		setConnection(south,west,conn,card.getSouthWest());
		char[] marker = new char[4];
		for(int i=north;i<=west;i++)marker[i]=' ';
		if(card.getNorth().getPlayer()!=null){
			marker[0]=colorChar(card.getNorth().getPlayer());
		}
		if(card.getSouth().getPlayer()!=null){
			marker[2]=colorChar(card.getSouth().getPlayer());
		}
		if(card.getEast().getPlayer()!=null){
			marker[1]=colorChar(card.getEast().getPlayer());
		}
		if(card.getWest().getPlayer()!=null){
			marker[3]=colorChar(card.getWest().getPlayer());
		}
		//Set type of orientations
		Type type[] = new Type[4];
		type[north]=card.getNorth().getType();
		type[east]=card.getEast().getType();
		type[south]=card.getSouth().getType();
		type[west]=card.getWest().getType();
		int city=0,street=0;
		int orientation[] = new int[4];
		boolean used[] = new boolean[4];
		for(int i=0;i<4;i++){
			orientation[i]=1;
			used[i]=false;
		}
		//try all combination, if orientation is not just write, we put a new value define the "id" of a city or a street
		//then we "bring" this value on other remaining orientation
		for(int i=north;i<=west;i++){
			if(!used[i]){
				if(type[i]==Type.STREET){
					street++;
					orientation[i]=street;
				}else if(type[i]==Type.CITY){
					city++;
					orientation[i]=city;
				}
				for(int j=i+1;j<=west;j++){
					if(type[i]==Type.CITY && type[i]==type[j]){
						if(conn[i*4+j]){
							orientation[j]=orientation[i];
							used[j]=true;
						}
					}
					if(type[i]==Type.STREET && type[i]==type[j]){
						if(conn[i*4+j]){
							orientation[j]=orientation[i];
							used[j]=true;
						}	
					}
				}
			}
		}
		String name[]= new String[4];
		//initialize the strings
		for(int i=north;i<=west;i++){
			name[i]=(type[i]!=Type.NOTHING)?((type[i]==Type.STREET)?"S":"C")+orientation[i]+((marker[i]!=' ')?"("+marker[i]+")":""):"";
		}
		//puts the StringTextWindows :-)
		labelN=new HorizontalStringTextWindow(name[0],this.getMyTextRenderize(),7-name[0].length()/2,1);
		labelS=new HorizontalStringTextWindow(name[2],this.getMyTextRenderize(),7-name[2].length()/2,5);
		labelW=new HorizontalStringTextWindow(name[3],this.getMyTextRenderize(),1,3);
		labelE=new HorizontalStringTextWindow(name[1],this.getMyTextRenderize(),14-name[1].length(),3);
	}
	/**
	 * Set connection
	 * @param orNumber1
	 * @param orNumber2
	 * @param conn
	 * @param connection
	 */
	private void setConnection(int orNumber1, int orNumber2, boolean conn[], boolean connection){
		conn[orNumber1+orNumber2*4]=conn[orNumber1*4+orNumber2]=connection;
	}
	/**
	 * reSet
	 * @param o: IModelViewCard
	 */
	@Override
	public void reSet(Object o) {
		if(o instanceof IModelViewCard){
			this.card=(IModelViewCard)o;
			card.setEventReciver(this);
			connectionSets(card);
			if(empty = card.isEmpty()){
				String c =card.getCoordinate().toString();
				centerLabel = new HorizontalStringTextWindow(card.getCoordinate().toString(),this.getMyTextRenderize(),7-c.length()/2,3);
			}else{
				setConnectionName(card);
			}
		}
		toRefresh();
	}
	/**
	 * Changed
	 */
	public void changed() {
		reSet(card);
		toRefresh();
	}
	/**
	 * @param c: color
	 * @return s: first char of the color
	 */
	private char colorChar(Color c){
		if(c.equals(Color.RED)){
			return 'r';
		}
		if(c.equals(Color.GREEN)){
			return 'g';
		}
		if(c.equals(Color.YELLOW)){
			return 'y';
		}
		if(c.equals(Color.BLUE)){
			return 'b';
		}
		if(c.equals(Color.BLACK)){
			return 'B';
		}
		return '?';
	}
}
