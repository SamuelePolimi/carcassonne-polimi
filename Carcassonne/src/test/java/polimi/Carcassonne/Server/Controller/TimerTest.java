package polimi.Carcassonne.Server.Controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TimerTest implements IEventTimer {
	
	private	Timer timer;
	private Thread ttimer;
	private	boolean stop;
	@Before
	public void setUp(){
		timer=new Timer(1000,1000,this);
		ttimer= new Thread(timer);
		stop=false;
	}
	@Test
	public void startTest() {
		ttimer.start();
		//dopo 500 ms tick non è neancora stato invocato
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			fail();
		}
		assertTrue(!stop);
		//ma dopo 500+600=1100 ms stop invece dovrebbe essere stato invocato
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			fail();
		}
		assertTrue(stop);
		timer.stop();
	}
	@Test
	public void resetTest() {
		timer=new Timer(1000,50,this);
		ttimer= new Thread(timer);
		stop=false;
		//resettiamo il timer
		timer.reset();
		//startiamolo nuovamente
		ttimer.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			fail();
		}
		//resettiamolo ancora
		timer.reset();
		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			fail();
		}
		assertTrue(!timer.getStop());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			fail();
		}
		timer.stop();
		assertTrue(timer.getStop());
	}
	@Test
	public void stopTest() {
		timer=new Timer(1000,50,this);
		ttimer= new Thread(timer);
		stop=false;
		//resettiamo il timer
		timer.reset();
		//startiamolo nuovamente
		ttimer.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			fail();
		}
		//resettiamolo ancora
		timer.stop();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			fail();
		}
		assertTrue(!stop);
		timer.stop();
	}
	public void tick() {
		stop=true;
		timer.stop();
	}
}
