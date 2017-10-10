package comp4004.library.testcases;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import comp4004.library.*;

public class UseCases {
	SInHandler sinhandler = new SInHandler();
	SOutput so;
	
	
	@After
	public void setup() {

	}
	
	@Test
	public void MonitorSystem() {
		//Clerk Logs In
		so = sinhandler.processInput(UtilConfig.CLERK_PASSWORD, SInHandler.CLERKLOGIN);
		assertTrue(so.getState() == SInHandler.CLERK);
		//Clerk Requests to monitor system
		so = sinhandler.processInput("monitor", SInHandler.MONITOR);
		//Clerk returns to menu state
		assertTrue(so.getState() == SInHandler.CLERK);
	}
	
	@Test
	public void CreateUser() {
		//Clerk logs in
		so = sinhandler.processInput(UtilConfig.CLERK_PASSWORD, SInHandler.CLERKLOGIN);
		assertTrue(so.getState() == SInHandler.CLERK);
		//Clerk requests to create a new user
		so = sinhandler.processInput("create user", SInHandler.CLERK);
		assertTrue(so.getState() == SInHandler.CREATEUSER);
		//Clerk enters username,password for the new user
		so = sinhandler.processInput("newguy@carleton.ca,newpass", SInHandler.CREATEUSER);
		//on success return to clerk state
		assertTrue(UserTable.getInstance().lookup(UserTable.getInstance().lookup("newguy@carleton.ca")));
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the user already exists return to clerk
		so = sinhandler.processInput("newguy@carleton.ca,newpasstwo", SInHandler.CREATEUSER);
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the username,password entry was wrong format return to createuser state to attempt again
		so = sinhandler.processInput("newuser,newpass", SInHandler.CREATEUSER);
		assertTrue(so.getState() == SInHandler.CREATEUSER);
	}
	
	@Test
	public void CreateTitle() {
		//Clerk logs in
		so = sinhandler.processInput(UtilConfig.CLERK_PASSWORD, SInHandler.CLERKLOGIN);
		assertTrue(so.getState() == SInHandler.CLERK);
		//Clerk requests to create a new title
		so = sinhandler.processInput("create title", SInHandler.CLERK);
		assertTrue(so.getState() == SInHandler.CREATETITLE);
		//Clerk enters ISBN,booktitle for the new book
		so = sinhandler.processInput("9876543213210,New Test Book", SInHandler.CREATETITLE);
		//on success return to clerk state
		assertTrue(TitleTable.getInstance().lookup("9876543213210"));
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the title already exists return to clerk
		so = sinhandler.processInput("9876543213210,New Test Book Vol.2", SInHandler.CREATETITLE);
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the ISBN,booktitle entry was wrong format return to createtitle state to attempt again
		so = sinhandler.processInput("newuser,newpass", SInHandler.CREATETITLE);
		assertTrue(so.getState() == SInHandler.CREATETITLE);
	}
	
	@Test
	public void CreateItem() {
		//Clerk logs in
		so = sinhandler.processInput(UtilConfig.CLERK_PASSWORD, SInHandler.CLERKLOGIN);
		assertTrue(so.getState() == SInHandler.CLERK);
		//Clerk requests to create a new item
		so = sinhandler.processInput("create item", SInHandler.CLERK);
		assertTrue(so.getState() == SInHandler.CREATEITEM);
		//Clerk enters ISBN for a new copy of the book
		so = sinhandler.processInput("9996543213210,ItemBook Vol.1", SInHandler.CREATETITLE); //creating to ensure another copy can be created
		so = sinhandler.processInput("9996543213210", SInHandler.CREATEITEM);
		//on success return to clerk state
		assertTrue(ItemTable.getInstance().lookup("9996543213210","1"));
		assertTrue(so.getState() == SInHandler.CLERK);
		//if there is no existing title return to clerk
		so = sinhandler.processInput("9886543213210", SInHandler.CREATEITEM);
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the ISBN entry was wrong format return to createitem state to attempt again
		so = sinhandler.processInput("hello the book", SInHandler.CREATEITEM);
		assertTrue(so.getState() == SInHandler.CREATEITEM);
	}
	
	@Test
	public void DeleteUser() {
		//Clerk logs in
		so = sinhandler.processInput(UtilConfig.CLERK_PASSWORD, SInHandler.CLERKLOGIN);
		assertTrue(so.getState() == SInHandler.CLERK);
		//Clerk requests to delete a user
		so = sinhandler.processInput("delete user", SInHandler.CLERK);
		assertTrue(so.getState() == SInHandler.DELETEUSER);
		//Clerk enters username of the user to be deleted
		so = sinhandler.processInput("tbdelete@carleton.ca,password", SInHandler.CREATEUSER); //creating user to be deleted
		so = sinhandler.processInput("tbdelete@carleton.ca", SInHandler.DELETEUSER);
		//on success return to clerk state
		assertTrue(UserTable.getInstance().lookup("tbdelete@carleton.ca") == -1); //doesnt exist
		assertTrue(so.getState() == SInHandler.CLERK);
		//if user doesn't exist ask to delete a different user
		so = sinhandler.processInput("amireal@carleton.ca", SInHandler.DELETEUSER);
		assertTrue(so.getState() == SInHandler.DELETEUSER);
		//if the user has outstanding loans or fines do not delete
		so = sinhandler.processInput("Michelle@carleton.ca", SInHandler.DELETEUSER); //has loan
		assertTrue(UserTable.getInstance().lookup("Michelle@carleton.ca") == 2); //userid is 2
		assertTrue(so.getState() == SInHandler.CLERK);
		so = sinhandler.processInput("Kevin@carleton.ca", SInHandler.DELETEUSER); //has fee
		assertTrue(UserTable.getInstance().lookup("Kevin@carleton.ca") == 3); //userid is 3
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the username entry was wrong format return to deleteuser state to attempt again
		so = sinhandler.processInput("hello", SInHandler.DELETEUSER);
		assertTrue(so.getState() == SInHandler.DELETEUSER);
	}
	
	@Test
	public void DeleteTitle() {
		//Clerk logs in
		so = sinhandler.processInput(UtilConfig.CLERK_PASSWORD, SInHandler.CLERKLOGIN);
		assertTrue(so.getState() == SInHandler.CLERK);
		//Clerk requests to delete a title
		so = sinhandler.processInput("delete title", SInHandler.CLERK);
		assertTrue(so.getState() == SInHandler.DELETETITLE);
		//Clerk enters ISBN of the title to be deleted
		so = sinhandler.processInput("9776543213210,DelTitle Vol.1", SInHandler.CREATETITLE); //creating title to be deleted
		so = sinhandler.processInput("9776543213210", SInHandler.DELETETITLE);
		//on success return to clerk state
		assertFalse(TitleTable.getInstance().lookup("9776543213210")); //doesnt exist
		assertTrue(so.getState() == SInHandler.CLERK);
		//if title doesn't exist return to clerk
		so = sinhandler.processInput("9876543210000", SInHandler.DELETETITLE);
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the title has been loaned do not delete
		so = sinhandler.processInput("9441442668584", SInHandler.DELETETITLE); //is loaned
		assertTrue(TitleTable.getInstance().lookup("9441442668584"));
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the title entry was wrong format return to deletetitle state to attempt again
		so = sinhandler.processInput("hello", SInHandler.DELETETITLE);
		assertTrue(so.getState() == SInHandler.DELETETITLE);
	}
}
