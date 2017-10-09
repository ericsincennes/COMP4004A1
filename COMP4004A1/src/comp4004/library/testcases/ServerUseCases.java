package comp4004.library.testcases;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import comp4004.library.*;

public class ServerUseCases {
	SInHandler sinhandler = new SInHandler();
	SOutput so;
	Object result="";
	
	@After
	public void tearDown() throws Exception {
	}
	
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
	
	@Test
	public void CreateTitleTest() {
		//unique isbn
		result= TitleTable.getInstance().createtitle("9999999999999", "Test Book Vol.1");
		assertTrue(result.equals(true));
		
		//isbn already exists 
		result = TitleTable.getInstance().createtitle("9781442616899", "Dante's lyric poetry");
		assertTrue(result.equals(false));
		
		List<Title> temp = TitleTable.getInstance().getTitleTable();
		//check new addition
		int count = 0;
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getISBN() == "9999999999999" && temp.get(i).getBooktitle() == "Test Book Vol.1") {
				count++;
			} 
		} 
		if (count != 1) {
			fail("book not added to list");
		}
		
		//check duplicate
		count = 0;
		for (int i=0; i<temp.size()-1; i++) {
			if (temp.get(i).getISBN() == "9781442616899" && temp.get(i).getBooktitle() == "Dante's lyric poetry") {
				count++;
			}
		}
		if (count>1) {
			fail("book added to list regardless");
		}
	}
	
	@Test
	public void CreateItemTest() {
		//create additional copy of existing book
		result = ItemTable.getInstance().createitem("9781442668584");
		assertTrue(result.equals(true));
		
		List<Item> temp = ItemTable.getInstance().getItemTable();
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getISBN() == "9781442668584" && temp.get(i).getCopynumber() != "1") {
				assertTrue(temp.get(i).getCopynumber().equals("2"));
			} else {
				if (!(temp.get(i).getCopynumber() == "N/A")) {
					assertTrue(temp.get(i).getCopynumber().equals("1"));
				}
			}
		}
		//Cannot create 2nd copy of book with out first copy
		result = ItemTable.getInstance().createitem("1112223334444");
		assertFalse(result.equals(true));
	}
	
	@Test
	public void DeleteUserTest() {
		//delete existing user
		List<User> temp = UserTable.getInstance().getUserTable();
		int id = -1;
		
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getUsername() == "Yu@carleton.ca") {
				id = i;
			}
		}
		result = UserTable.getInstance().delete(id);
		assertTrue(result.equals("success"));
		int count = 0;
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getUsername() == "Yu@carleton.ca") {
				count++;
			} 
		} 
		if (count == 1) {
			fail("user not removed from list");
		}
		
		//delete nonexistant user
		id = -1;
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getUsername() == "testuser") {
				id = i;
			} 
		}
		result = UserTable.getInstance().delete(id);
		assertFalse(result.equals("success"));
	}
	
	@Test
	public void DeleteTitleTest() {
		List<Title> temp = TitleTable.getInstance().getTitleTable();
		//delete existing title
		result = TitleTable.getInstance().delete("9781442616899");
		assertTrue(result.equals("success"));
		
		//delete title that is on loan
		result= TitleTable.getInstance().delete("9781442668584");
		assertTrue(result.equals("Active Loan Exists"));
		
		//delete title that doesnt exist
		result = TitleTable.getInstance().delete("1112223334444");
		assertTrue(result.equals("The Title Does Not Exist"));
		
		
		//check deletion
		int count = 0;
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getISBN() == "9781442616899") {
				count++;
			} 
		} 
		if (count != 0) {
			fail("book not deleted");
		}
		
		//check non-deletion of loaned book
		count = 0;
		for (int i=0; i<temp.size()-1; i++) {
			if (temp.get(i).getISBN() == "9781442668584") {
				count++;
			}
		}
		if (count != 1) {
			fail("book deleted regardless");
		}
	}
	
	@Test
	public void DeleteItemTest() {
		List<Item> temp = ItemTable.getInstance().getItemTable();
		//delete existing item
		result = ItemTable.getInstance().delete("9781611687910", "1");
		assertTrue(result.equals("success"));
		
		//delete item that is on loan
		result= ItemTable.getInstance().delete("9781442668584", "1");
		assertTrue(result.equals("Active Loan Exists"));
		
		//delete title that doesnt exist
		result = ItemTable.getInstance().delete("9781442616899", "3");
		assertTrue(result.equals("The Item Does Not Exist"));
		
		
		//check deletion
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getISBN() == "9781611687910") {
				assertTrue(temp.get(i).getCopynumber() == "N/A");
			} 
		}
		
		//check non-deletion of loaned book
		for (int i=0; i<temp.size(); i++) {
			if (temp.get(i).getISBN() == "9781442668584") {
				assertFalse(temp.get(i).getCopynumber() == "N/A");
			} 
		}
	}
	
	@Test
	public void UserLoginTest() {
		//correct username/password and state
		so = sinhandler.processInput("Zhibo@carleton.ca,Zhibo", 41);
		assertTrue(so.getState() == 40);
		//incorrect username/password - correct state
		so = sinhandler.processInput("Yu@carleton.ca,password", 41);
		assertFalse(so.getState() == 40);
		//correct username/password - incorrect state
		so = sinhandler.processInput("Yu@carleton.ca,Yu", 11);
		assertFalse(so.getState() == 40);
	}
	
	@Test
	public void BorrowTest() {
		//borrowing a book
		result = LoanTable.getInstance().createloan(2, "9781317594277", "1", new Date());
		assertTrue(result.equals("success"));
		//incorrect copynumber
		result = LoanTable.getInstance().createloan(2, "9781317594277", "3", new Date());
		assertTrue(result.equals("Copynumber Invalid"));
		//incorrect ISBN
		result = LoanTable.getInstance().createloan(2, "9781317594000", "1", new Date());
		assertTrue(result.equals("ISBN Invalid"));
		//borrowing unavailable book
		result = LoanTable.getInstance().createloan(2, "9781317594277", "1", new Date());
		assertTrue(result.equals("The Item is Not Available"));
		//user has outstanding fee
		result = LoanTable.getInstance().createloan(0, "9781442667181", "1", new Date());
		assertTrue(result.equals("Outstanding Fee Exists"));
	}
	
	@Test
	public void RenewTest() {
		//renewing loan on a book
		result = LoanTable.getInstance().renewal(2, "9781317594277", "1", new Date());
		assertTrue(result.equals("success"));
		//renewing loan more than once
		result = LoanTable.getInstance().renewal(2, "9781317594277", "1", new Date());
		assertTrue(result.equals("Renewed Item More Than Once for the Same Loan"));
		//user has outstanding fee
		result = LoanTable.getInstance().renewal(0, "9781442667181", "1", new Date());
		assertTrue(result.equals("Outstanding Fee Exists"));
		//renewing a loan that doesnt exist
		result = LoanTable.getInstance().renewal(2, "9781442667181", "1", new Date());
		assertTrue(result.equals("The loan does not exist"));
	}
	
	@Test
	public void ReturnTest() {
		//create loan to be returned
		result = LoanTable.getInstance().createloan(2, "9781236565459", "1", new Date());
		//returning a loaned book
		result = LoanTable.getInstance().returnItem(2, "9781236565459", "1", new Date());
		assertTrue(result.equals("success"));
		//returning a book that hasnt been loaned
		result = LoanTable.getInstance().returnItem(2, "9781442667181", "1", new Date());
		assertTrue(result.equals("The Loan Does Not Exist"));
	}
	
	@Test
	public void PayFineTest() {
		//paying fine
		result = FeeTable.getInstance().payfine(3);
		assertTrue(result.equals("success"));
		//paying fine with outstanding loans
		result = FeeTable.getInstance().payfine(0);
		assertTrue(result.equals("Borrowing Items Exist"));
	}
}
