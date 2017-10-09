package comp4004.library.testcases;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import comp4004.library.*;

public class ServerLogicTest {
	SInHandler sinhandler = new SInHandler();
	SOutput so;
	
	
	@Test
	public void ClerkLoginTest() {
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
	
	@Test
	public void CreateUserTest() {
		Object result ="";
		
		//unique username
		result = UserTable.getInstance().createuser("eric@carleton.ca", "hello");
		assertTrue(result.equals(true));
		
		//username already exists 
		result = UserTable.getInstance().createuser("Zhibo@carleton.ca", "Zhibo");
		assertTrue(result.equals(false));
		
		List<User> temp = UserTable.getInstance().getUserTable();
		//check new addition
		int count = 0;
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getUsername() == "eric@carleton.ca" && temp.get(i).getPassword() == "hello") {
				count++;
			} 
			System.out.println(temp.get(i).getUsername() + " " + temp.get(i).getPassword());
		} 
		if (count != 1) {
			fail("user not added to list");
		}
		
		//check duplicate
		count = 0;
		for (int i=0; i<temp.size()-1; i++) {
			if (temp.get(i).getUsername() == "Zhibo@carleton.ca" && temp.get(i).getPassword() == "Zhibo") {
				count++;
			}
		}
		if (count>1) {
			fail("user added to list regardless");
		}
	}
}
