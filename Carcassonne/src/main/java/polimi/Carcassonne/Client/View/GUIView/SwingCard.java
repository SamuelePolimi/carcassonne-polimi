package polimi.Carcassonne.Client.View.GUIView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.*;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import polimi.Carcassonne.Client.IModelView.IEventClientCard;
import polimi.Carcassonne.Client.IModelView.IModelViewCard;
import polimi.Carcassonne.Client.Model.ClientCard;
import resource.GetURLFile;

/**
 * Represent a card
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class SwingCard extends JLabel implements IEventClientCard,MouseListener {
	private static final long serialVersionUID = 994890312815591585L;
	//represent times we have to rotate card in view graphics
	private int nRotate;
	//represent image we have to show
	private Image img;
	//represent orientation of the card. if not null the marker contained
	private Image north;
	private Image south;
	private Image east;
	private Image west;
	//represent our model-view card
	private IModelViewCard card;
	//represent the normal dimension of our control
	private static final int height=150;
	private static final int width=150;
	//represent scale
	private float scale=(float) 0.6;
	private IEventInput event;
	/**
	 * Constructor of the swing card
	 * @param c
	 */
	public SwingCard(IModelViewCard c){
		super();
		this.addMouseListener(this);
		getIcon(c);
		this.setPreferredSize(new Dimension(Math.round(width*scale),Math.round(height*scale)));
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
	public void changed() {
		;
	}
	/**
	 * Override of paint component
	 * @param g: graphics
	 */
	@Override
    protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		//sets border depending the state of the card
		if(!card.isEmpty()){
			this.setBorder(BorderFactory.createEmptyBorder());
			this.repaint();
		}else{
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.repaint();
		}
		//rotate the card of angle we want
			Graphics2D g2d=(Graphics2D)g; 
			g2d.rotate(-Math.PI/2*nRotate,this.getWidth()/(float)2,this.getHeight()/(float)2);
			g2d.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
			g2d.rotate(Math.PI/2*nRotate,this.getWidth()/(float)2,this.getHeight()/(float)2);
			//sets markers
			if(north!=null){
				g2d.drawImage(north, this.getWidth()/3, 0, this.getWidth()/5, this.getHeight()/5,null);
			}
			if(south!=null){
				g2d.drawImage(south, this.getWidth()/3, (this.getHeight()*2)/3, this.getWidth()/5, this.getHeight()/5,null);
			}
			if(east!=null){
				g2d.drawImage(east, (this.getWidth()*2)/3, this.getHeight()/3, this.getWidth()/5, this.getHeight()/5,null);
			}
			if(west!=null){
				g2d.drawImage(west, 0, this.getHeight()/3, this.getWidth()/5, this.getHeight()/5,null);
			}
    }
	/**
	 * Sets image of the card given, and eventual marker
	 * @param c: modelViewCard
	 */
	private  void getIcon(IModelViewCard c){
		if(!c.isEmpty()){
			this.setText("");
			IModelViewCard copy = c.getCopy();
			URL url=null;
			nRotate=0;
			boolean flag=true;
			while(flag){
			url = GetURLFile.getMyUrlFile().getURLCard(copy);
				if(url!=null){
					flag=false;
				} else{
					copy.rotate();
					nRotate++;
				}
			}
			this.setMarker(c);
			img =  Toolkit.getDefaultToolkit().createImage(url);
			this.setBorder(BorderFactory.createEmptyBorder());
			this.repaint();
		}else{
			this.setText(c.getCoordinate().toString());
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.repaint();
		}
		card=c;
		card.setEventReciver(this);
	}
	/**
	 * Sets marker if we found it
	 * @param card
	 */
	private void setMarker(IModelViewCard c){
		if(c.getNorth().getPlayer()!=null){
			north=imgByColor(c.getNorth().getPlayer());
		} else{
			north=null;
		}
		if(c.getSouth().getPlayer()!=null){
			south=imgByColor(c.getSouth().getPlayer());
		}else{
			south=null;
		}
		if(c.getEast().getPlayer()!=null){
			east=imgByColor(c.getEast().getPlayer());
		}else{
			east=null;
		}
		if(c.getWest().getPlayer()!=null){
			west=imgByColor(c.getWest().getPlayer());
		} else{
			west=null;
		}
	}
	/**
	 * Return the correct marker by the color
	 * @param c color given
	 * @return image of corresponding marker
	 */
	protected Image imgByColor(Color c){
		if(c.equals(Color.BLACK)){
			return Toolkit.getDefaultToolkit().createImage(GetURLFile.getMyUrlFile().getBlack());
		}else if(c.equals(Color.BLUE)){
			return Toolkit.getDefaultToolkit().createImage(GetURLFile.getMyUrlFile().getBlue());
		}else if(c.equals(Color.GREEN)){
			return Toolkit.getDefaultToolkit().createImage(GetURLFile.getMyUrlFile().getGreen());
		}else if(c.equals(Color.RED)){
			return Toolkit.getDefaultToolkit().createImage(GetURLFile.getMyUrlFile().getRed());
		}else {
			return Toolkit.getDefaultToolkit().createImage(GetURLFile.getMyUrlFile().getYellow());
		}
	}
	/**
	 * Resets the card
	 * @param card
	 */
	protected void setNew(ClientCard card){
		getIcon(card);
		//this.repaint();
	}
	/**
	 * Event of click
	 */
	public void mouseClicked(MouseEvent arg0) {
		if(event!=null && card.isEmpty()){
			event.selected(card.getCoordinate().getX(), card.getCoordinate().getY());
		}
		
	}
	public void mouseEntered(MouseEvent arg0) {
		;
	}
	public void mouseExited(MouseEvent arg0) {
		;		
	}
	public void mousePressed(MouseEvent arg0) {
		;
	}
	public void mouseReleased(MouseEvent arg0) {
		;
	}
	/**
	 * Set event
	 * @param e
	 */
	public void setEvent(IEventInput e){
		this.event=e;
	}
}
