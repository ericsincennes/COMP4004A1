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
	//Creation States
	public static final int CREATEUSER = 20;
	public static final int CREATETITLE = 21;
	public static final int CREATEITEM = 22;
	//Deletion States
	public static final int DELETEUSER = 30;
	public static final int DELETETITLE = 31;
	public static final int DELETEITEM = 32;
	//User States
	public static final int USER = 40;
	public static final int USERLOGIN = 41;
	//User Option States
	public static final int BORROW = 50;
	public static final int RENEW = 51;
	
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
	            }else if (input.equalsIgnoreCase("user")) {
	            	output="Please Input Username and Password:'username,password'";
	            	state=USERLOGIN;
	                oo.setOutput(output);
		            oo.setState(state);
	            }else{
	            	output = "Who Are you?Clerk or User?";
	            	state = FINISHWAIT;
	            	oo.setOutput(output);
		            oo.setState(state);
	            }
	         }else if(state==CLERKLOGIN){
		        	o=southandler.clerkLogin(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	         }else if(state==USERLOGIN){
		        	o=southandler.userLogin(input);
	        		output=o.getOutput();
	        		state=o.getState();
	        		oo.setOutput(output);
		            oo.setState(state);
	         }else if (state==CLERK){
	        	 	output = "You are now the clerk";
	        	 	oo.setOutput(output);
	        	 	if (input.equalsIgnoreCase("create user")) {
	        	 		output = "Please Input User Info:'username,password'";
	        	 		state=CREATEUSER;
	        	 		oo.setOutput(output);
	        	 		oo.setState(state);
	        	 	}else if (input.equalsIgnoreCase("create title")) {
		            	output = "Please Input Title Info:'ISBN,title'";
		            	state=CREATETITLE;
		            	oo.setOutput(output);
			            oo.setState(state);
	        	 	}else if (input.equalsIgnoreCase("create item")) {
			            output = "Please Input Item Info:'ISBN'";
			            state=CREATEITEM;
			            oo.setOutput(output);
			            oo.setState(state);
	        	 	}else if (input.equalsIgnoreCase("delete user")) {
		            	output = "Please Input User Info: 'user email'";
		            	state=DELETEUSER;
		            	oo.setOutput(output);
			            oo.setState(state);
	        	 	 }else if (input.equalsIgnoreCase("delete title")) {
	 		            output = "Please Input Title Info:'ISBN'";
	 		            state=DELETETITLE;
	 		            oo.setOutput(output);
	 		            oo.setState(state);
	        	 	}else if (input.equalsIgnoreCase("delete item")) {
		            	output = "Please Input Item Info:'ISBN,copynumber'";
		            	state=DELETEITEM;
		            	oo.setOutput(output);
			            oo.setState(state);
	        	 	}else if(input.equalsIgnoreCase("log out")){
	        	 		output = "Successfully Log Out!";
	        	 		state = WAIT;
	        	 		oo.setOutput(output);
	        	 		oo.setState(state);
	        	 	}else if(input.equalsIgnoreCase("main menu")){
	        	 		output = "What can I do for you? Menu: Create User/Title/Item, Delete User/Title/Item.";
	        	 		state = CLERK;
	        	 		oo.setOutput(output);
	        	 		oo.setState(state);
	        	 	}else{
	        	 		output = "Please select from the menu. Menu: Create User/Title/Item, Delete User/Title/Item.";
	        	 		state = CLERK;
	        	 		oo.setOutput(output);
	        	 		oo.setState(state);
	        	 	}
	         }else if(state==CREATEUSER){
		        	if(input.equalsIgnoreCase("log out")){
		            	output = "Successfully Log Out!";
		                state = WAIT;
		                oo.setOutput(output);
			            oo.setState(state);
		        	}else if(input.equalsIgnoreCase("main menu")){
		        		output = "What can I do for you? Menu: Create User/Title/Item, Delete User/Title/Item.";
		                state = CLERK;
		                oo.setOutput(output);
			            oo.setState(state);
		        	}else{
		        		o=southandler.createUser(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }else if(state==CREATETITLE){
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
		        		o=southandler.createTitle(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }else if(state==CREATEITEM){
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
		        		o=southandler.createItem(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }else if(state==DELETEUSER){
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
		        		o=southandler.deleteUser(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }else if(state==DELETETITLE){
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
		        		o=southandler.deleteTitle(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }else if(state==DELETEITEM){
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
		        		o=southandler.deleteItem(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }else if (state==USER){
	        	 	if (input.equalsIgnoreCase("borrow")) {
		            	output = "Please Input User Info:'useremail,ISBN,copynumber'";
		            	state=BORROW;
		            	oo.setOutput(output);
			            oo.setState(state);
	        	 	}else if (input.equalsIgnoreCase("renew")) {
		            	output = "Please Input Title Info:'useremail,ISBN,copynumber'";
		            	state=RENEW;
		            	oo.setOutput(output);
			            oo.setState(state);
	        	 	}else if(input.equalsIgnoreCase("log out")){
		            	output = "Successfully Log Out!";
		                state = WAIT;
		                oo.setOutput(output);
			            oo.setState(state);
		            }else if(input.equalsIgnoreCase("main menu")){
		        		output = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
		                state = USER;
		                oo.setOutput(output);
			            oo.setState(state);
		        	}else{
		            	output = "Please select from the menu.Menu:Borrow,Renew,Return,Pay Fine.";
		                state = USER;
		                oo.setOutput(output);
			            oo.setState(state);
		            }
	         }else if(state==BORROW){
		        	if(input.equalsIgnoreCase("log out")){
		            	output = "Successfully Log Out!";
		                state = WAIT;
		                oo.setOutput(output);
			            oo.setState(state);
		        	}else if(input.equalsIgnoreCase("main menu")){
		        		output = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
		                state = USER;
		                oo.setOutput(output);
			            oo.setState(state);
		        	}else{
		        		o=southandler.borrow(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }else if(state==RENEW){
		        	if(input.equalsIgnoreCase("log out")){
		            	output = "Successfully Log Out!";
		                state = WAIT;
		                oo.setOutput(output);
			            oo.setState(state);
		        	}else if(input.equalsIgnoreCase("main menu")){
		        		output = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
		                state = USER;
		                oo.setOutput(output);
			            oo.setState(state);
		        	}else{
		        		o=southandler.renew(input);
		        		output=o.getOutput();
		        		state=o.getState();
		        		oo.setOutput(output);
			            oo.setState(state);
		        	}
	         }
		 return oo;
	}
}
