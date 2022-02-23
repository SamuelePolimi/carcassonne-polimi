package polimi.Carcassonne.Client.View.GUIView;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * Represent the jPanel which we can insert a marker or pass
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class SwingInsertMarker extends JPanel implements MouseListener{

	private static final long serialVersionUID = 3073228432022651605L;
	private IEventMarker event;
	private JButton north;
	private JButton south;
	private JButton east;
	private JButton west;
	private JButton pass;
	private boolean canUse;
	/**
	 * Create swing insert marker
	 * @param event
	 */
	public SwingInsertMarker(IEventMarker event){
		this.event =event;
		this.setLayout(new FlowLayout());
		north=new JButton("North");
		north.addMouseListener(this);
		south=new JButton("South");
		south.addMouseListener(this);
		east=new JButton("East");
		east.addMouseListener(this);
		west=new JButton("West");
		west.addMouseListener(this);
		pass=new JButton("Pass");
		pass.addMouseListener(this);
		this.add(north);
		this.add(east);
		this.add(south);
		this.add(west);
		this.add(pass);
		canUse=true;
	}
	/**
	 * Click if the component is able, launch the event of "insert-marker", or "pass",
	 * depending what client clicked.
	 * @param event
	 */
	public void mouseClicked(MouseEvent arg0) {
		if(canUse){
			if(arg0.getSource().equals(north)){
				event.insertMarker(0);
			}else if(arg0.getSource().equals(south)){
				event.insertMarker(2);
			}else if(arg0.getSource().equals(east)){
				event.insertMarker(1);
			}else if(arg0.getSource().equals(west)){
				event.insertMarker(3);
			}else if(arg0.getSource().equals(pass)){
				event.pass();
			}
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
	 * Disable the components
	 */
	public void disable(){
		canUse=false;
		north.setEnabled(false);
		south.setEnabled(false);
		east.setEnabled(false);
		west.setEnabled(false);
		pass.setEnabled(false);
	}
	/**
	 * Re-able the components
	 */
	public void reAble(){
		canUse=true;
		north.setEnabled(true);
		south.setEnabled(true);
		east.setEnabled(true);
		west.setEnabled(true);
		pass.setEnabled(true);
	}
}
