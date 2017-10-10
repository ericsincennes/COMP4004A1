package comp4004.library;

import java.util.ArrayList;
import java.util.List;
import comp4004.library.Title;
import comp4004.library.ItemTable;
import comp4004.library.LoanTable;

import org.apache.log4j.Logger;
import comp4004.library.UtilTrace;

public class TitleTable {
	private Logger logger = UtilTrace.getInstance().getLogger("opreation_file");
	List<Title> titleList=new ArrayList<Title>();
    private static class TitleListHolder {
        private static final TitleTable INSTANCE = new TitleTable();
    }
    private TitleTable(){
    	//set up the default list with some instances
    	String[] ISBNList=new String[]{"9781442668584","9781442616899","9781442667181","9781611687910","9781317594277", "9781236565459", "9441442668584"};
    	String[] titlenameList=new String[]{"By the grace of God","Dante's lyric poetry","Courtesy lost","Writing for justice","The act in context", "My First Book", "My First Loan"};
    	for(int i=0;i<ISBNList.length;i++){
    		Title detitle=new Title(ISBNList[i],titlenameList[i]);
    		titleList.add(detitle);
		}
    	logger.info(String.format("Operation:Initialize TitleTable;TitleTable: %s", titleList));
    };
    public static final TitleTable getInstance() {
        return TitleListHolder.INSTANCE;
    }
	public Object createtitle(String isbn, String btitle) {		
		boolean result=true;
		boolean exists = false;
		for(int i=0;i<titleList.size();i++){
			String ISBN=(titleList.get(i)).getISBN();
			if(ISBN.equalsIgnoreCase(isbn)){
				exists = true;
				break;
			}
		}
		if(!exists){
			Title newtitle=new Title(isbn,btitle);
			result=titleList.add(newtitle);
			logger.info(String.format("Operation:Create New Title;Title Info:[%s,%s];State:Success", isbn,btitle));
		}else{
			result=false;
			logger.info(String.format("Operation:Create New Title;Title Info:[%s,%s];State:Fail;Reason:The ISBN already existed.", isbn,btitle));
		}
		return result;	
	}
	
	public boolean lookup(String string) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<titleList.size();i++){
			String ISBN=(titleList.get(i)).getISBN();
			if(ISBN.equalsIgnoreCase(string)){
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
	
	public Object delete(String string) {
		String result="";
		int index=0;
		int flag=0;
		for(int i=0;i<titleList.size();i++){
			if(titleList.get(i).getISBN().equalsIgnoreCase(string)){
				flag=flag+1;
				index=i;
			}else{
				flag=flag+0;
			}
		}
		if(flag!=0){
			boolean loan=LoanTable.getInstance().checkLoan(string);
			if(loan){
				String string2=titleList.get(index).getBooktitle();
				ItemTable.getInstance().deleteAll(string);
				titleList.remove(index);
				result="success";
				logger.info(String.format("Operation:Delete Title;Title Info:[%s,%s];State:Success", string,string2));
			}else{
				result="Active Loan Exists";
				logger.info(String.format("Operation:Delete Title;ISBN Info:[%s];State:Fail;Reason:Active Loan Exists.", string));
			}
		}else{
			result="The Title Does Not Exist";
			logger.info(String.format("Operation:Delete Title;ISBN Info:[%s];State:Fail;Reason:The Title Does Not Exist.", string));
		}
		return result;
	}
	
	public List<Title> getTitleTable() {
		return titleList;
	}
    
	


}
