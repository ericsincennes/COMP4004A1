package comp4004.library;

import comp4004.library.SOutput;
import comp4004.library.SOutHandler;

public class SInHandler {
	//Wait States
	public static final int WAIT = 0;
	public static final int FINISHWAIT = 1;
	//Clerk States
	public static final int CLERK = 10;
	public static final int CLERKLOGIN = 11;
	
	SOutHandler southandler = new SOutHandler();
	
	public SOutput processInput(String input, int state) {
		 String output = "";
		 SOutput o = new SOutput("",0);
		 SOutput oo = new SOutput(output,o.getState()); 
		 if (state == WAIT) {
	        	output = "Who Are you?Clerk or User?";
	            state = FINISHWAIT;
	            oo.setOutput(output);
	            oo.setState(state);
	         }else if (state == FINISHWAIT) {
	            if (input.equalsIgnoreCase("clerk")) {
	            	output="Please Input The Password:";
	            	state=CLERKLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	            }
	         }else if(state==CLERKLOGIN){
		        	o=southandler.clerkLogin(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	         }else if (state==CLERK){
	        	 output = "You are now the clerk";
	             oo.setOutput(output);
	             if(input.equalsIgnoreCase("log out")){
	            	output = "Successfully Log Out!";
	                state = WAIT;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else if(input.equalsIgnoreCase("main menu")){
	        		output = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	        	}else{
	            	output = "Please select from the menu.Menu:Create User/Title/Item,Delete User/Title/Item.";
	                state = CLERK;
	                oo.setOutput(output);
		            oo.setState(state);
	            }
			}
		 
		 return oo;
	}
}
