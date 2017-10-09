package comp4004.library;

import java.util.ArrayList;
import java.util.List;

import comp4004.library.User;
import comp4004.library.FeeTable;
import comp4004.library.LoanTable;

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
	
	public int lookup(String string) {
		int userid=-1;
		for(int i=0;i<userList.size();i++){
			if(userList.get(i).getUsername().equalsIgnoreCase(string)){
				userid=i;
			}
		}
		return userid;
	}
	
	public Object delete(int i) {
		String result="";
		int flag=0;
		int index=0;
		for(int j=0;j<userList.size();j++){
			if(userList.get(j).getUserid()==i){
				index=j;
				flag=flag+1;
			}else{
				flag=flag+0;
			}
		}
		
		if(flag==0){
			result="The User Does Not Exist";
		}else{
			boolean loan=LoanTable.getInstance().checkUser(i);
			boolean fee=FeeTable.getInstance().lookup(i);
			String string=userList.get(index).getUsername();
			String string2=userList.get(index).getPassword();
			if(fee && loan){
				userList.get(index).setUserid(i);
				userList.get(index).setPassword("N/A");
				userList.get(index).setUsername("N/A");
				result="success";
			}else if(fee==false){
				result="Outstanding Fee Exists";
			}else if(loan==false){
				result="Active Loan Exists";
			}
		}
    
		return result;

	}
	
	public List<User> getUserTable() {
		return userList;
	}
}
