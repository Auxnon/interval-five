package interval;

import java.util.HashMap;

public class Inventory {

	HashMap<String, Integer> set;
	boolean updated;
	int cap;
	Item selected;
	
	public Inventory(){
		set=new HashMap<String,Integer>();
		selected=Item.Empty;
	}
	public void add(String n,int count){
		Integer ii=set.get(n);
		int d=count;
		if(ii!=null)
			d+=ii;
		else if(cap!=0 && set.size()>=cap)
			return;
		set.put(n, d);
		updated=true;
	}
	public boolean remove(String n,int count){
		Integer ii=set.get(n);
		if(ii!=null)
			if(ii>=count){
				if(ii==count){
					set.remove(n);
					if(selected.name.equals(n))
						selected=Item.Empty;
				}else{
					set.put(n, ii-count);
				}
				updated=true;
				return true;
			}else if(ii<=0)
				set.remove(n);
		return false;
	}
	
	public boolean wasUpdated(){
		boolean up=updated;
		updated=false;
		return up;
	}
	public Item Selection(){
		return selected;
	}
	
	public void set(String s){
		Integer h=set.get(s);
		if(h!=null && h>0)
			selected=Item.set.get(s);
	}
}
