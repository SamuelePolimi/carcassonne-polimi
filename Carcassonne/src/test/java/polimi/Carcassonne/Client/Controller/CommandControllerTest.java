package polimi.Carcassonne.Client.Controller;

import static org.junit.Assert.*;

import org.junit.Test;

import polimi.Carcassonne.Client.Controller.CommandController;

public class CommandControllerTest {
	CommandController commandController;
	
	@Test
	public void commandControllerTest(){
		commandController=new CommandController("tRY:oMar,saMUele,1");
		assertTrue(commandController.getName().equals("try"));
		assertTrue(commandController.getValue(0).equals("omar"));
		assertTrue(commandController.getValue(1).equals("samuele"));
		assertTrue(commandController.getValue(2).equals("1"));
		commandController=new CommandController("update:1,2");
		assertTrue(commandController.getName().equals("update"));
		assertTrue(commandController.getValue(0).equals("1"));
		assertTrue(commandController.getValue(1).equals("2"));
	}
	

}
