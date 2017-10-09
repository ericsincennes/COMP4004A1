package comp4004.library;

import java.util.ArrayList;
import java.util.List;
import comp4004.library.Item;


public class ItemTable {
	List<Item> itemList=new ArrayList<Item>();
    private static class ItemListHolder {
        private static final ItemTable INSTANCE = new ItemTable();
    }
    private ItemTable(){
    	//set up the default list with some instances
    	String[] ISBNList=new String[]{"9781442668584","9781442616899","9781442667181","9781611687910"};
    	String[] cnList=new String[]{"1","1","1","1"};
    	for(int i=0;i<ISBNList.length;i++){
			Item deitem=new Item(i,ISBNList[i],cnList[i]);
			itemList.add(deitem);
		}
    };
    
    public static final ItemTable getInstance() {
        return ItemListHolder.INSTANCE;
    }
    
	public Object createitem(String string) {
		boolean result=true;
		result=TitleTable.getInstance().lookup(string);
		if(result){
		int flag=0;
		for(int i=0;i<itemList.size();i++){
			if(itemList.get(i).getISBN().equalsIgnoreCase(string)){
				flag=flag+1;
			}else{
				flag=flag+0;
			}
		}
		Item newitem=new Item(itemList.size(),string,String.valueOf(flag+1));
		itemList.add(newitem);
		}else{
			result=false;
		}
		return result;
	}

	public List<Item> getItemTable() {
		return itemList;
	}
}