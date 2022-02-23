package polimi.Carcassonne;

import java.awt.Color;

import polimi.Carcassonne.Client.Model.ClientCard;
import polimi.Carcassonne.Client.Model.ClientCardSide;
import polimi.Carcassonne.Server.Model.Graph.Box;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Coordinate;

/**this class gives static methods that are using by Client or by Server for make easyer communication
 * between. An example is the trasformation by "Card" (Class implemented by Server) and "ClientCard" (implemented by client):
 * sometimes it useful use this kind of methods, for example Offline and also RMIClient use this transformation!
 * @author Samuele
 *
 */
public class ClientServerTranslation {
	/**
	 * Translation of the card
	 * @param card: card to translate
	 * @return clientCard: translated clientCard
	 */
	public static ClientCard translate(Card card){
		ClientCard ret= new ClientCard(new Coordinate(0,0));
		ret.setNorth(new ClientCardSide(card.getNorth().getType()));
		ret.setSouth(new ClientCardSide(card.getSouth().getType()));
		ret.setEast(new ClientCardSide(card.getEast().getType()));
		ret.setWest(new ClientCardSide(card.getWest().getType()));
		if(card.getNorth().getClockwise()!=null){
			ret.setNorthEast(true);
		}
		if(card.getNorth().getOpposite()!=null){
			ret.setNorthSouth(true);
		}
		if(card.getNorth().getCounterclockwise()!=null){
			ret.setNorthWest(true);
		}
		if(card.getEast().getClockwise()!=null){
			ret.setSouthEast(true);
		}
		if(card.getEast().getOpposite()!=null){
			ret.setEastWest(true);
		}
		if(card.getSouth().getClockwise()!=null){
			ret.setSouthWest(true);
		}
		return ret;
	}
	/**
	 * Translate a card
	 * @param card: card to translate
	 * @return clientCard transleted
	 */
	public static ClientCard translateBox(Box card){
		ClientCard ret= new ClientCard(card.getCoordinate());
		ret.setEmpty(false);
		ret.setNorth(new ClientCardSide(card.getNorth().getType()));
		ret.setSouth(new ClientCardSide(card.getSouth().getType()));
		ret.setEast(new ClientCardSide(card.getEast().getType()));
		ret.setWest(new ClientCardSide(card.getWest().getType()));
		if(card.getNorth().getClockwise()!=null){
			ret.setNorthEast(true);
		}
		if(card.getNorth().getOpposite()!=null){
			ret.setNorthSouth(true);
		}
		if(card.getNorth().getCounterclockwise()!=null){
			ret.setNorthWest(true);
		}
		if(card.getEast().getClockwise()!=null){
			ret.setSouthEast(true);
		}
		if(card.getEast().getOpposite()!=null){
			ret.setEastWest(true);
		}
		if(card.getSouth().getClockwise()!=null){
			ret.setSouthWest(true);
		}
		
		if(card.getNorth().getMarker()!=null){
			ret.getNorth().setPlayer(card.getNorth().getMarker().getPlayer().getColor());
		}
		if(card.getSouth().getMarker()!=null){
			ret.getSouth().setPlayer(card.getSouth().getMarker().getPlayer().getColor());
		}
		if(card.getEast().getMarker()!=null){
			ret.getEast().setPlayer(card.getEast().getMarker().getPlayer().getColor());
		}
		if(card.getWest().getMarker()!=null){
			ret.getWest().setPlayer(card.getWest().getMarker().getPlayer().getColor());
		}
		return ret;
	}
	public static Color getStringByColor(String s){
		return Color.BLACK;
	}
}
