package polimi.Carcassonne.Client.View.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.Connection.IViewConnection;
/**
 * Implements the request by textual view, of the marker insertion
 * @author Omar Scotti - Samuele Tosatto
 */
public class MarkerThread implements Runnable {
	private TotalWindow view;
	private IViewConnection conn;
	public MarkerThread(TotalWindow view,IViewConnection conn){
		this.view=view;
		this.conn=conn;
	}
	/**
	 * Request to insert a marker or pass to client
	 */
	public void run() {
		Printer.print(view.getMyTextRenderize().toString());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			boolean flag=false;
			while(!flag){
			String z=stdin.readLine();
			if(z!=null){
				String s= z.toLowerCase().replace(" ", "");
				if(s.equals("north")){
					conn.marker(0);
					flag=true;
				}else if(s.equals("east")){
					conn.marker(1);
					flag=true;
				}else if(s.equals("south")){
					conn.marker(2);
					flag=true;
				}else if(s.equals("west")){
					conn.marker(3);
					flag=true;
				}else if(s.equals("pass")){
					conn.marker(4);
					flag=true;
				}
				if(!flag){
					Printer.print("String format not correct, please re-try");
				}
			}
			}
		} catch (IOException e) {
			;
		}
	}
}
