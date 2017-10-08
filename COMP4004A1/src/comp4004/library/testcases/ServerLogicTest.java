package comp4004.library.testcases;

import static org.junit.Assert.*;
import org.junit.Test;
import comp4004.library.*;

public class ServerLogicTest {
	
	@Test
	public void ClerkLoginTest() {
		SInHandler sinhandler = new SInHandler();
		SOutput so;
		//correct password and state
		so = sinhandler.processInput("admin", 11);
		assertTrue(so.getState() == 10);
		//incorrect password - correct state
		so = sinhandler.processInput("password", 11);
		assertFalse(so.getState() == 10);
		//correct password - incorrect state
		so = sinhandler.processInput("admin", 10);
		assertFalse(so.getState() == 20);
	}
	
	
}
