package comp4004.library;

import java.util.ArrayList;
import java.util.List;

import comp4004.library.User;
import comp4004.library.FeeTable;
import comp4004.library.LoanTable;

import org.apache.log4j.Logger;
import comp4004.library.UtilTrace;

public class UserTable {
	private Logger logger = UtilTrace.getInstance().getLogger("opreation_file");
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
    	logger.info(String.format("Operation:Initialize UserTable;UserTable: %s", userList));
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
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Success", user,pass));
		}else{
			result = false;
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Fail;Reason:The User already existed.", user,pass));
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
			logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Fail;Reason:The User Does Not Exist.", "N/A","N/A"));
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
				logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Success", string,string2));
			}else if(fee==false){
				result="Outstanding Fee Exists";
				logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Fail;Reason:Outstanding Fee Exists.", string,string2));
			}else if(loan==false){
				result="Active Loan Exists";
				logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Fail;Reason:Active Loan Exists.", string,string2));
			}
		}
    
		return result;

	}
	
	public int checkUser(String string, String string2) {
		int result=0;
		int flag=0;
		int index=0;
		for(int i=0;i<userList.size();i++){
			if(userList.get(i).getUsername().equalsIgnoreCase(string)){
				flag=flag+1;
				index=i;
			}else{
				flag=flag+0;
			}
		}
		boolean password=userList.get(index).getPassword().equalsIgnoreCase(string2);
		if(flag!=0 && password){
			result=0;
		}else if(flag==0){
			result=2;
		}else if(password==false){
			result=1;
		}
		return result;
	}
	
	public boolean lookup(int j) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<userList.size();i++){
			int userid=(userList.get(i)).getUserid();
			if(userid==j){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag==0){
			result=false;
		}
		return result;
	}
	
	public List<User> getUserTable() {
		return userList;
	}
}
