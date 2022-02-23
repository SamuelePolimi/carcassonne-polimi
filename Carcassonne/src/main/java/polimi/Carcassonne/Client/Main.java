
package polimi.Carcassonne.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import polimi.Carcassonne.Printer;
import polimi.Carcassonne.Client.Connection.ClientStrategy;
import polimi.Carcassonne.Client.Connection.IViewConnection;
import polimi.Carcassonne.Client.Model.ClientGameState;
import polimi.Carcassonne.Client.View.GraphicsStrategy;
import polimi.Carcassonne.Client.View.IView;
import polimi.Carcassonne.Client.View.TextView.TextRenderize;
import polimi.Carcassonne.Client.View.TextView.TextTitleWindow;

public class Main {
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TextRenderize titleRender = new TextRenderize(200,5);
		TextTitleWindow title=new TextTitleWindow(titleRender,0,0);
		title.renderize();
		Printer.print(titleRender.toString()+"\n");
		String in="";
		int nPlayer=1;
		//SETS THE STRATEGY
		ClientStrategy cs = new ClientStrategy();
		GraphicsStrategy graphic = new GraphicsStrategy();
		//SETS THE GAME
		ClientGameState gs = new ClientGameState();
		IView view = null;
		IViewConnection connection = null;
		/*----------------SWITCH CONNECTION---------*/
		boolean flag=true;
		Printer.print("Switch between: <socket, rmi, offline>");
		while(flag){
			try {
				in = stdin.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(in!=null){
				if(in.trim().toLowerCase().equals("socket")){
					try {
						connection =  cs.getClientSocket(gs);
						flag=false;
					} catch (UnknownHostException e) {
						Printer.print("Server not ready");
					} catch (IOException e) {
						Printer.print("Unknown error");
					}
				}else if(in.trim().toLowerCase().equals("offline")){
					flag=false;
					Printer.print("Insert number of player <2..5>");
					boolean flag1=true;
					while(flag1){
						try {
							in=stdin.readLine();
							try{
								if(in!=null){
									if(Integer.parseInt(in)>1 && Integer.parseInt(in)<6){
										flag1=false;
									}
								}
							}
							catch(Exception e){
								;
							}
						} catch (IOException e) {
							Printer.print("CLI<Main> IOException");
						}
						if(flag1){
							Printer.print("Please insert a valid number!");
						}
					}
					if(in!=null){
						in=in.toLowerCase().trim();
						nPlayer=Integer.parseInt(in);
						connection =  cs.getOfflineConnection(gs);
					}
				}else if(in.trim().toLowerCase().equals("rmi")){
					flag=false;
					
					connection=cs.getRMIConnection(gs);
				}
				if(flag){
					Printer.print("Input wrong - please retry");
				}
			}
		}
		/*----------------SWITCH GRAPHICS-----------*/
		Printer.print("Switch by <txt, gui>");
		flag=true;
		while(flag){
			try {
				in=stdin.readLine();
			} catch (IOException e) {
				Printer.print("CLI<Main> IOException");
			}
			if(in!=null){
				in=in.trim().toLowerCase();
				if(in.equals("txt")){
					view = graphic.getTextGraphics();
					flag=false;
				}else if(in.equals("gui")){
					view = graphic.getGUIGraphics();
					flag=false;
				}
			}
			if(flag){
				Printer.print("Please insert <txt, gui>");
			}
		}
		/*-----------SETTING-----------------*/
		if(gs!=null && connection!=null){
			view.setIModelView(gs);
			view.setIViewConnection(connection);
			if(nPlayer==1){
				try {
					connection.connection();
				} catch (RemoteException e) {
					;
				}
			}else{
				connection.connection(nPlayer);
			}
		}
	}
}
