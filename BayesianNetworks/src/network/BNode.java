package network;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BNode {
	private ArrayList<BNode> parents;
	private HashMap<String,Float> probability;
	private String name;
	
	public BNode(String name){
		parents = new ArrayList<BNode>();
		probability = new HashMap<String,Float>();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<BNode> getParents() {
		return parents;
	}

	public void setParents(ArrayList<BNode> parents) {
		this.parents = parents;
	}

	public float getProbability(String key){
		if(key.charAt(0)=='-'){
			key = key.substring(1);
			return round(1-this.probability.get(key),2).floatValue();
		}
		return this.probability.get(key);
	}
	
	public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }
	public void putProbability(String key, Float probability){
		this.probability.put(key, probability);
	}
	
	public void addParent(BNode bNode){
		this.parents.add(bNode);
	}
	
	public boolean hasParents(){
		return this.parents.size()>0;
	}
	
	public void printNode(){
		System.out.print("Node: "+getName() + "  Parents: ");
		for(int i = 0; i < this.parents.size(); i++) {   
		    System.out.print(this.parents.get(i).getName()+" ");
		} 
		System.out.println("");
		Set<String> keySet = this.probability.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		//System.out.println("Probabilities: ");
		while (keySetIterator.hasNext()) {
		   String key = keySetIterator.next();
		   System.out.println(key + "=" + this.probability.get(key));
		}
		System.out.println("");
	}

}
