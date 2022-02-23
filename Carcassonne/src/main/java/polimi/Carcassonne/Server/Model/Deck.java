package polimi.Carcassonne.Server.Model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Server.Model.Exception.EmptyDeckException;
import polimi.Carcassonne.Server.Model.Exception.FileFormatWrongException;
import polimi.Carcassonne.Server.Model.Exception.TypeInexistentException;
import polimi.Carcassonne.Server.Model.Graph.BasicCard;
import polimi.Carcassonne.Server.Model.Graph.Card;
import polimi.Carcassonne.Server.Model.Graph.Type;
import resource.GetURLFile;
/**
 * This class represents a Deck
 * With this class we can initialize the card list and take a random card
 * @author Samuele Tosatto - Omar Scotti
 */

public class Deck {
	private Random rnd;
	private ArrayList<BasicCard> cardList;
	private GetURLFile get;
	/**
	 * This method initialize the deck with the file carcassonne.txt
	 */
	public Deck(){
		get= GetURLFile.getMyUrlFile();
		URL path=get.getURLCarcassonneTxt();
		rnd=new Random();
		cardList=new ArrayList<BasicCard>();
		BufferedReader br = null;
		InputStream is = null;
		try {
			String line;
			is=path.openStream();	
			InputStreamReader isr=new InputStreamReader(is);
			br=new BufferedReader(isr);
			StringBuffer buf= new StringBuffer();
			line=br.readLine();
			while(line!=null)
			      {
			    	buf.append(line+"\n");
			        line=br.readLine();
			      }
			      br.close();
			      is.close();
		      this.initializesCardList(buf.toString());
		    }catch(Exception ex){
		    	Printer.print(ex.toString());
		    }finally{
		    	try {
					br.close();
					is.close();
				} catch (IOException e) {
					Printer.print("SRV<Deck>: Error File-Reading!");
				}
		    }
		} 
	/**
	 * This method creates list of CardBase by reading the content
	 * @param content
	 * @throws FileFormatWrongException 
	 */
	private void initializesCardList(String content) throws FileFormatWrongException{
		int lenghtRow=46; 
		for(int pos =0; pos<content.length();pos+=lenghtRow){
			try{
				Type north = getType(content.charAt(pos+2));
				Type south=getType(content.charAt(pos+2+4));
				Type west=getType(content.charAt(pos+2+4*2));
				Type east=getType(content.charAt(pos+2+4*3));
				boolean ns= getConnection(content.charAt(pos+2+4*3+5));
				boolean ne= getConnection(content.charAt(pos+2+4*3+5*2));
				boolean nw= getConnection(content.charAt(pos+2+4*3+5*3));
				boolean we= getConnection(content.charAt(pos+2+4*3+5*4));
				boolean se= getConnection(content.charAt(pos+2+4*3+5*5));
				boolean sw= getConnection(content.charAt(pos+2+4*3+5*6));
				cardList.add(new BasicCard(north, south, east, west, ns, ne, nw, se, sw, we));
			}catch(TypeInexistentException ex){
				throw new FileFormatWrongException();
			}
		}
	}
	/**
	 * From a char returns the type
	 * @param c
	 * @return
	 * @throws TypeInexistentException 
	 */
	private Type getType(char c) throws TypeInexistentException{
		if(c=='S' || c=='s'){
			return Type.STREET;		
		}
		if(c=='N' || c=='n'){
			return Type.NOTHING;		
		}
		if(c=='C' || c=='c'){
			return Type.CITY;		
		}
		throw new TypeInexistentException();
	}
	/**
	 * 
	 * @param char c: 1 or 0 for true or false
	 * @return true or false
	 * @throws TypeInexistentException 
	 */
	private boolean getConnection(char c) throws TypeInexistentException{
		if(c=='0'){
			return false;		
		}
		if(c=='1'){
			return true;		
		}
		throw new TypeInexistentException();
	}
	/**
	 * Removes a card from deck
	 * @return card
	 * @throws EmptyDeckException if the deck is empty
	 */
	public Card getCard() throws EmptyDeckException{
		if(cardList.size()!=0){
		//if(cardList.size()>48){
			int x = rnd.nextInt(cardList.size());
			BasicCard t = cardList.get(x); 
			cardList.remove(x); 
			return new Card( t);
		}else{
			throw new EmptyDeckException();
		}
	}
	/**
	 * @param n number of the card
	 * @return a basic card
	 */
	public BasicCard getCard(int n){
	
		BasicCard card = cardList.get(n);
		cardList.remove(n);
		return card;
	}
}
