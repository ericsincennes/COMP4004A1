package comp4004.library.testcases;

import static org.junit.Assert.*;

import org.junit.Test;
import comp4004.library.*;

public class UseCases {
	SInHandler sinhandler = new SInHandler();
	SOutput so = sinhandler.processInput("testuser@carleton.ca,password", SInHandler.CREATEUSER);
	
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
	
	@Test
	public void DeleteItem() {
		//Clerk logs in
		so = sinhandler.processInput(UtilConfig.CLERK_PASSWORD, SInHandler.CLERKLOGIN);
		assertTrue(so.getState() == SInHandler.CLERK);
		//Clerk requests to delete an item
		so = sinhandler.processInput("delete item", SInHandler.CLERK);
		assertTrue(so.getState() == SInHandler.DELETEITEM);
		//Clerk enters ISBN,copynumber of the title to be deleted
		so = sinhandler.processInput("9777743213210,DelItem Vol.1", SInHandler.CREATETITLE); //creating item to be deleted
		so = sinhandler.processInput("9777743213210", SInHandler.CREATEITEM);
		so = sinhandler.processInput("9777743213210,1", SInHandler.DELETEITEM);
		//on success return to clerk state
		assertFalse(ItemTable.getInstance().lookup("9777743213210", "1")); //doesnt exist
		assertTrue(so.getState() == SInHandler.CLERK);
		//if item doesn't exist return to clerk
		so = sinhandler.processInput("9777743213210,5", SInHandler.DELETEITEM);
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the item has been loaned do not delete
		so = sinhandler.processInput("9441442668584,1", SInHandler.DELETEITEM); //is loaned
		assertTrue(ItemTable.getInstance().lookup("9441442668584", "1"));
		assertTrue(so.getState() == SInHandler.CLERK);
		//if the title entry was wrong format return to deleteitem state to attempt again
		so = sinhandler.processInput("hello,hello", SInHandler.DELETEITEM);
		assertTrue(so.getState() == SInHandler.DELETEITEM);
	}
	
	@Test
	public void Borrow() {
		//User logs in
		so = sinhandler.processInput("testuser@carleton.ca,password", SInHandler.USERLOGIN);
		assertTrue(so.getState() == SInHandler.USER);
		//User requests to borrow a book
		so = sinhandler.processInput("borrow", SInHandler.USER);
		assertTrue(so.getState() == SInHandler.BORROW);
		//User enters username,ISBN,copynumber of the title to be borrowed
		so = sinhandler.processInput("testuser@carleton.ca,9781236565459,1", SInHandler.BORROW);
		//on success return to user state
		assertFalse(LoanTable.getInstance().lookup(UserTable.getInstance().lookup("testuser@carleton.ca"),"9781236565459", "1"));
		assertTrue(so.getState() == SInHandler.USER);
		//if item is unavailable return to user
		so = sinhandler.processInput("testuser@carleton.ca,9441442668584,1", SInHandler.BORROW);
		assertTrue(so.getState() == SInHandler.USER);
		//if the user has outstanding fines or at maximum borrow limit do not loan
		so = sinhandler.processInput("Zhibo@carleton.ca,9781442616899,1", SInHandler.BORROW); //has fines
		assertTrue(ItemTable.getInstance().lookup("9441442668584", "1"));
		assertTrue(so.getState() == SInHandler.USER);
		//if any entered info was wrong format return to borrow state to attempt again
		so = sinhandler.processInput("hello,hello,5", SInHandler.BORROW);
		assertTrue(so.getState() == SInHandler.BORROW);
	}
	
	@Test
	public void Return() {
		//User logs in
		so = sinhandler.processInput("testuser@carleton.ca,password", SInHandler.USERLOGIN);
		assertTrue(so.getState() == SInHandler.USER);
		//User requests to return a book
		so = sinhandler.processInput("return", SInHandler.USER);
		assertTrue(so.getState() == SInHandler.RETURN);
		//User enters username,ISBN,copynumber of the title to be returned
		so = sinhandler.processInput("testuser@carleton.ca,9781317594277,1", SInHandler.BORROW); //borrowing item to return
		so = sinhandler.processInput("testuser@carleton.ca,9781317594277,1", SInHandler.RETURN);
		//on success return to user state
		assertTrue(LoanTable.getInstance().lookup(UserTable.getInstance().lookup("testuser@carleton.ca"),"9781317594277", "1"));
		assertTrue(so.getState() == SInHandler.USER);
		//if the user returns a book that is not loaned
		so = sinhandler.processInput("testuser@carleton.ca,9781317594277,1", SInHandler.RETURN);
		assertTrue(so.getState() == SInHandler.USER);
		//if any entered info was wrong format return to borrow state to attempt again
		so = sinhandler.processInput("hello,hello,5", SInHandler.RETURN);
		assertTrue(so.getState() == SInHandler.RETURN);
	}
	
	@Test
	public void Renew() {
		//User logs in
		so = sinhandler.processInput("testuser@carleton.ca,password", SInHandler.USERLOGIN);
		assertTrue(so.getState() == SInHandler.USER);
		//User requests to renew a loaned book
		so = sinhandler.processInput("renew", SInHandler.USER);
		assertTrue(so.getState() == SInHandler.RENEW);
		//User enters username,ISBN,copynumber of the title to be renewed
		so = sinhandler.processInput("testuser@carleton.ca,9781317594277,1", SInHandler.BORROW); //borrowing item to renew
		so = sinhandler.processInput("testuser@carleton.ca,9781317594277,1", SInHandler.RENEW);
		//on success return to user state
		assertFalse(LoanTable.getInstance().lookup(UserTable.getInstance().lookup("testuser@carleton.ca"),"9781317594277", "1"));
		assertTrue(so.getState() == SInHandler.USER);
		//if the user renews a book that has already been renewed
		so = sinhandler.processInput("testuser@carleton.ca,9781317594277,1", SInHandler.RENEW);
		assertTrue(so.getState() == SInHandler.USER);
		//if the user renews a book that is not loaned
		so = sinhandler.processInput("testuser@carleton.ca,9781611687910,1", SInHandler.RENEW);
		assertTrue(so.getState() == SInHandler.USER);
		//if the user renews a book while they have a fine
		so = sinhandler.processInput("Zhibo@carleton.ca,9781442668584,1", SInHandler.RENEW);
		assertTrue(so.getState() == SInHandler.USER);
		//if any entered info was wrong format return to borrow state to attempt again
		so = sinhandler.processInput("hello,hello,5", SInHandler.RENEW);
		assertTrue(so.getState() == SInHandler.RENEW);
	}
	
	@Test
	public void PayFine() {
		//User logs in
		so = sinhandler.processInput("testuser@carleton.ca,password", SInHandler.USERLOGIN);
		assertTrue(so.getState() == SInHandler.USER);
		//User requests to pay a fine
		so = sinhandler.processInput("pay fine", SInHandler.USER);
		assertTrue(so.getState() == SInHandler.PAYFINE);
		//User enters username of the user with a fine
		so = sinhandler.processInput("Sun@carleton.ca", SInHandler.PAYFINE); //userid = 4
		//on success return to user state
		assertTrue(FeeTable.getInstance().lookup(4));
		assertTrue(so.getState() == SInHandler.USER);
		//if the user attempts to pay fine while having borrowed items
		so = sinhandler.processInput("Zhibo@carleton.ca", SInHandler.PAYFINE); //userid = 0
		assertFalse(FeeTable.getInstance().lookup(0));
		assertTrue(so.getState() == SInHandler.USER);
		//if any entered info was wrong format return to borrow state to attempt again
		so = sinhandler.processInput("hello,hello,5", SInHandler.PAYFINE);
		assertTrue(so.getState() == SInHandler.PAYFINE);
	}
}
