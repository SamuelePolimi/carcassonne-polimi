package polimi.Carcassonne.Client.View.TextView;
/**
 * Renderize the text
 * @author Omar Scotti - Samuele Tosatto
 */
public class TextRenderize {
	// a char 2-dimensional matrix represent our screen
	private char matrix[][];
	// dimension of text-renderize
	private int width,height;
	private int xmin,xmax,ymin,ymax;
	/**
	 * the constructor needs..
	 * @param width
	 * @param height
	 */
	public TextRenderize(int width, int height){
		this.width=width;
		this.height=height;
		matrix=new char[height][width];
		xmin=0;
		xmax=0;
		ymin=0;
		ymax=0;
	}
	/**
	 * clean matrix (to use first call  renderize() on the windows)
	 */
	public void clear(){
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				matrix[i][j]=' ';
			}
		}
		xmin=0;
		xmax=0;
		ymin=0;
		ymax=0;
	}
	/**
	 * permit to insert a char on a specific position
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setCharAt(int x, int y, char c){
		//if(y<height && x<width &&y>=0 && x>=0)
		if(y<ymin){
			ymin=y;
		}
		if(y>ymax){
			ymax=y;
		}
		if(x<xmin){
			xmin=x;
		}
		if(x>xmax){
			xmax=x;
		}
		matrix[y][x]=c;
	}
	/**
	 * @return the String Screen
	 */
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer();
		  for (int i = ymin; i <= ymax; i++) {
			  for(int j=xmin;j<=xmax;j++){
					  buf.append(matrix[i][j]);
			  }
			  buf.append('\n');
		  }
		  return buf.substring(0,buf.length()-1);
	}
}
