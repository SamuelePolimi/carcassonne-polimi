package polimi.Carcassonne.Client.View.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.Connection.IViewConnection;
/**
 * Implements insertion-card to client
 * @author Omar Scotti - Samuele Tosatto
 *
 */
public class InsertThread implements Runnable {
	private TotalWindow view;
	private IViewConnection conn;
	public InsertThread(TotalWindow view,IViewConnection conn){
		this.view=view;
		this.conn=conn;
	}
	/**
	 * Request the insertion of a card, or rotate, to client by textual view.
	 */
	public void run() {
		Printer.print(view.getMyTextRenderize().toString());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			boolean flag=true;
			while(flag){
			String z=stdin.readLine();
			if(z!=null){
				String s[] = z.replaceAll(" ", "").toLowerCase().split(",");
				if(s.length==2){
					try{
						conn.insert(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
						flag=false;
					}
					catch(Exception e){
						;
					}
				}
				else{
					if(z.toLowerCase().replace(" ", "").equals("rotate")){
						conn.rotate();
						flag=false;
					}
				}
				if(flag){
					Printer.print("Formato stringa non corretto, correggere");
				}
				}
			}
		} catch (IOException e) {
			;
		}
	}
}