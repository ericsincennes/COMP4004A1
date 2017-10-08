package comp4004.library;

import comp4004.library.SOutput;
import comp4004.library.UtilConfig;

public class SOutHandler {
	//Wait States
	public static final int WAIT = 0;
	public static final int FINISHWAIT = 1;
	//Clerk States
	public static final int CLERK = 10;
	public static final int CLERKLOGIN = 11;
	
	
	public SOutput clerkLogin(String input) {
		SOutput out = new SOutput("", 0);
		
		if (input.equalsIgnoreCase(UtilConfig.CLERK_PASSWORD)) {
			out.setOutput("What can I do for you? Menu: Create User/Title/Item, Delete User/Title/Item.");
			out.setState(CLERK);
		} else {
			out.setOutput("Wrong Password. Please Input the Password");
			out.setState(CLERKLOGIN);
		}
		return out;
	}
}