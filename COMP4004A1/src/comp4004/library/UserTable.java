package comp4004.library;

import java.util.ArrayList;
import java.util.List;

import comp4004.library.User;

public class UserTable {
	List<User> userList=new ArrayList<User>();
	
    private static class UserListHolder {
        private static final UserTable INSTANCE = new UserTable();
    }
    
    private UserTable(){
    	//set up the default list with some instances
    	String[] passwordList=new String[]{"Zhibo","Yu","Michelle","Kevin","Sun"};
    	String[] usernameList=new String[]{"Zhibo@carleton.ca","Yu@carleton.ca","Michelle@carleton.ca","Kevin@carleton.ca","Sun@carleton.ca"};
    	for(int i=0;i<usernameList.length;i++){
			User deuser=new User(i,usernameList[i],passwordList[i]);
			userList.add(deuser);
		}
    };
    
    public static final UserTable getInstance() {
        return UserListHolder.INSTANCE;
    }
    
	public Object createuser(String user, String pass) {		
		boolean result=true;
		boolean exists = false;
		for(int i=0;i<userList.size();i++){
			String email=(userList.get(i)).getUsername();
			if(email.equalsIgnoreCase(user)){
				exists = true;
				break;
			}
		}
		if(!exists){
			User newuser=new User(userList.size(),user,pass);
			result=userList.add(newuser);
		}else{
			result = false;
		}
		return result;	
	}
	
	public List<User> getUserTable() {
		return userList;
	}
}
