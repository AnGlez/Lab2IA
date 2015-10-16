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
	
	public BNode getParentByName(String name) {
		if(!this.hasParents())
			return null;
		name = name.replaceAll("[+-]", "");
		for(int i = 0; i < this.parents.size(); i++) { 
			if(this.parents.get(i).getName().equals(name)){
				return this.parents.get(i);
			}
		}
		return null;
	}

	public void setParents(ArrayList<BNode> parents) {
		this.parents = parents;
	}

	public float getProbability(String key){
		String keyProb;
		if(!hasParents()){
			if(key.charAt(0)=='-'){
				key = key.replaceFirst("-", "+");
				return round(1-this.probability.get(key),2).floatValue();
			}
			return this.probability.get(key);
		}else{
			String[] query = key.split("\\|");                 //Split accepts a string in regex format (that's why we use double back slash for the character |)
			String[] evidenceNodes;
			String auxEvidence;
			BNode parentNode;
			float probSum = 0;
			
			
			Set<String> keySet = this.probability.keySet();     //Here I am retrieving probability keys, EX:{+Dry,-Sick},{-Dry,-Sick}
			Iterator<String> keySetIterator = keySet.iterator();
			
			while (keySetIterator.hasNext()) {
			   keyProb = keySetIterator.next();   
			   
			   if(query.length > 1 && keyProb.contains(query[1])){                  //First we validate if probability applies to request, ex:  {+Dry,-Sick} contains +Dry
				   //System.out.println("entre con llave: "+query[1]);
				   if(keyProb.equals(key)){                     //In case probability searched is exactly alike when + "POSITIVE"
					   return this.probability.get(key);
				   }else if(keyProb.equals(key.replaceFirst("-", "+"))){    //In case probability searched is exactly alike when - "NEGATIVE"
					   key = key.replaceFirst("-", "+");
					   return round(1-this.probability.get(key),4).floatValue();
				   }
				   												//We continue running this probability in case request exactly matches what we are looking for in the probability Table
				   evidenceNodes = query[1].split(",");
				   probSum += this.probability.get(keyProb);
				   
				   for(int i=0; i<evidenceNodes.length; i++){
					   parentNode = this.getParentByName(evidenceNodes[i]);
					   probSum *= parentNode.getProbability(evidenceNodes[i]);
				   }
				   
			   }else if(query.length == 1){ 					//Probability requested is for a node but it has many parents                                          
				   /*If we got to this point is because they requested the full probability
				    * of a Node that has parents but without giving any evidence for 
				    * example P(+Loses)
				    */
				   //System.out.println("QUERY 0" + query[0]);
				   //System.out.println("Query: "+ keyProb);
				   probSum += this.probability.get(keyProb) * getIndividualProbOfKey(keyProb);      //P(+Loses)
				   
				   
			   }
			   //System.out.println(keyProb + "=" + this.probability.get(keyProb));
			}
			float finalRes = round(probSum,4).floatValue();
			     
			return query[0].charAt(0)=='+'? finalRes: 1-finalRes;   //Just to verify if they asked for P(+Loses) or P(-Loses)
		}
	}
	
	public float getIndividualProbOfKey(String key){
		float probMult = 1;
		BNode parentNode;
		String probabilityNodes[] = key.split("\\|");                 //We first take away the name of the current Node, and we look for its parents {"+Loses","+Sick,+Dry"}
		String evidenceNodes[] = probabilityNodes[1].split(",");    //Now we only take the nodes that matter to us {"+Sick","+Dry"}   
		   
	   for(int i=0; i<evidenceNodes.length; i++){
		   parentNode = this.getParentByName(evidenceNodes[i]);
		   //System.out.println("TESTING: "+evidenceNodes[i]);
		   probMult *= parentNode.getProbability(evidenceNodes[i]);
	   }
	   return probMult;
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
