package polimi.Carcassonne.Server.Controller;
import java.rmi.Remote;

import polimi.Carcassonne.Printer;

/**
 * Timer is an object who generate an event called "Tick()" each time.
 * In constructor you can specify how many time you want call tick event
 * In constructor you can specify precision. Lower is precision, higher is later with we call tick event
 * but more is the precision associated to reset method
 * Higher is precision (but non higher then time), higher is precision associated to tick, but worse is work of reset method
 * @author Samuele Tosatto - Omar scotti
 * __________________________________________________________
 * How to use:
 * 1) implements IEventTimer
 * 2) Timer timer = new Timer(t,p,this);
 * 3) do: Thread t_timer = new Thread(t);
 * 4) do: t_timer.start();
 * 5) implements what you want in tick(){....};
 */
public class Timer implements Runnable, Remote{
	private int time;
	private int timepassed=0;
	private boolean stop=false;
	private IEventTimer event;
	private int precision;
	/**
	 * Constructor of timer with precision 100
	 * @param time
	 * @param event
	 */
	public Timer(int time,IEventTimer event){
		this.time=time;
		this.event=event;
		precision=100;
	}
	/**
	 * when precision is low, while into
	 * @param time
	 * @param precision
	 * @param event
	 */
	public Timer(int time,int precision,IEventTimer event){
		this(time,event);
		this.precision=precision;
	}
	/**
	 * Go of the timer
	 */
	public void run() {
		while(!stop){
			if(timepassed>=time){
				timepassed=0;
				event.tick();
			}
			timepassed+=precision;
			try {
				Thread.sleep(precision);
			} catch (InterruptedException e) {
				Printer.print("SRV<Timer> Timer Interrupted");
			}
		}
	}
	/**
	 * Stopped the timer
	 */
	public void stop(){
		stop=true;
	}
	/**
	 * Reset timer
	 */
	public void reset(){
		timepassed=0;
	}
	/**
	 * @return stop: true if the timer isn't running
	 */
	public boolean getStop(){
		return stop;
	}
	/**
	 * @return time passed
	 */
	public int getTimepassed(){
		return timepassed;
	}
}
