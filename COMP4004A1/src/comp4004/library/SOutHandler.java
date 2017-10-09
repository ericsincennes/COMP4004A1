package comp4004.library;

import comp4004.library.SOutput;
import comp4004.library.UtilConfig;
import comp4004.library.UserTable;

public class SOutHandler {
	//Wait States
	public static final int WAIT = 0;
	public static final int FINISHWAIT = 1;
	//Clerk States
	public static final int CLERK = 10;
	public static final int CLERKLOGIN = 11;
	//Creation States
	public static final int CREATEUSER = 20;
	
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
	
	public SOutput createUser(String input) {
		SOutput out = new SOutput("" , 0);
		String[] strArray = null;   
        strArray = input.split(",");
        boolean email=strArray[0].contains("@");
        Object result="";
        if(strArray.length!=2 || email!=true){
        	out.setOutput("Your input should in this format: 'username,password'");
        	out.setState(CREATEUSER);
        }else{
        	result=UserTable.getInstance().createuser(strArray[0], strArray[1]);
        	if(result.equals(true)){
        		out.setOutput("Success!");
        	}else{
        		out.setOutput("The User Already Exists!");
        	}
        	out.setState(CLERK);
        }
		return out;
	}
}