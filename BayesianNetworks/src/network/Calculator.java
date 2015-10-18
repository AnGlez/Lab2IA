package network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Calculator {
	private HashMap<String, Float> queries;

	public Calculator(){
		queries = new HashMap<String,Float>();
	}
	
	public void putQueryResponse(String key, float probability){
		queries.put(key, probability);
	}
	
	public float getQueryProbability(String key){
		return queries.get(key);
	}
	
	public void printQueries(){
		System.out.println("------------------------------------------------");
	    System.out.println("           QUERIES"+"\n");
		Set<String> keySet = this.queries.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		
		while (keySetIterator.hasNext()){
		   String key = keySetIterator.next();
		   System.out.print("P("+key+")=");
		   System.out.println(this.queries.get(key));
		}
		System.out.println("");
	}
	
}
