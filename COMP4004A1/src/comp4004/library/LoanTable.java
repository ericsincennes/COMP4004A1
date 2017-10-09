package comp4004.library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp4004.library.Loan;
import comp4004.library.UtilConfig;
import comp4004.library.FeeTable;
import comp4004.library.ItemTable;
import comp4004.library.LoanTable;
import comp4004.library.TitleTable;
import comp4004.library.UserTable;

public class LoanTable {
	List<Loan> loanList=new ArrayList<Loan>();
    private static class LoanListHolder {
        private static final LoanTable INSTANCE = new LoanTable();
    }
    private LoanTable(){
    	//set up the default list with some instances
    	Loan loan=new Loan(0,"9781442668584","1",new Date(),"0");
    	loanList.add(loan);

    };
    public static final LoanTable getInstance() {
        return LoanListHolder.INSTANCE;
    }
    
	public Object createloan(int i, String string, String string2, Date date) {
		String result="";
		boolean user=UserTable.getInstance().lookup(i);
		boolean isbn=TitleTable.getInstance().lookup(string);
		boolean copynumber=ItemTable.getInstance().lookup(string,string2);
		boolean oloan=LoanTable.getInstance().lookup(i,string,string2);
		boolean limit=LoanTable.getInstance().checkLimit(i);
		boolean fee=FeeTable.getInstance().lookup(i);
		if(user==false){
			result="User Invalid";
		}else if(isbn==false){
			result="ISBN Invalid";
		}else if(copynumber==false){
			result="Copynumber Invalid";
		}else{
			if(oloan){
				if(limit && fee){
				Loan loan=new Loan(i,string,string2,date,"0");
				loanList.add(loan);
				result="success";
				}else if(limit==false){
					result="The Maximun Number of Items is Reached";
				}else if(fee==false){
					result="Outstanding Fee Exists";
				}
			}else{
				result="The Item is Not Available";
			}
		}
    	return result;
	}
	
	public Object renewal(int j, String string, String string2, Date date) {
		String result="";
		int flag=0;
		int index=0;
		boolean limit=LoanTable.getInstance().checkLimit(j);
		boolean fee=FeeTable.getInstance().lookup(j);
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			int userid=(loanList.get(i)).getUserid();
			if((userid==j) && ISBN.equalsIgnoreCase(string) && copynumber.equalsIgnoreCase(string2)){
				flag=flag+1;
				index=i;
			}else{
				flag=flag+0;	
			}
		}
		if(limit && fee){
			if(flag!=0){
				if(loanList.get(index).getRenewstate().equalsIgnoreCase("0")){
					loanList.get(index).setUserid(j);
					loanList.get(index).setIsbn(string);
					loanList.get(index).setCopynumber(string2);
					loanList.get(index).setDate(new Date());
					loanList.get(index).setRenewstate("1");
					result="success";
				}else{
					result="Renewed Item More Than Once for the Same Loan";
					}
			}else{
				result="The loan does not exist";
			}
			
		}else if(limit==false){
			result="The Maximun Number of Items is Reached";
		}else if(fee==false){
			result="Outstanding Fee Exists";
		}
		return result;
	}
	
	public boolean lookup(int j, String string, String string2) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			if(ISBN.equalsIgnoreCase(string) && copynumber.equalsIgnoreCase(string2)){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
    
	private String dateformat(Date date){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datestr=format1.format(date);
		return datestr;
	}
	
	public List<Loan> getLoanTable() {
		return loanList;
	}
	
	public boolean checkUser(int j) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			int userid=(loanList.get(i)).getUserid();
			if(userid==j){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	
	public boolean checkLoan(String string) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			if(ISBN.equalsIgnoreCase(string)){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	
	public boolean checkLoan(String string, String string2) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			if(ISBN.equalsIgnoreCase(string) && copynumber.equalsIgnoreCase(string2)){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	
	public boolean checkLimit(int j) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			int userid=(loanList.get(i)).getUserid();
			if(userid==j){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag>=UtilConfig.MAX_BORROWED_ITEMS){
			result=false;
		}
		return result;
	}
}
