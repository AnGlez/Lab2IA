package network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class BayesianNet {
	private HashMap<String, BNode> bNodes;
	private Calculator calc;
	
	public BayesianNet(){
		bNodes = new HashMap<String,BNode>();
		calc = new Calculator();
	}

	public Calculator getCalculator(){
		return this.calc;
	}

	public void putBNode(BNode bNode){
		bNodes.put(bNode.getName(), bNode);
	}
	
	public BNode getBNode(String key){
		if(key.charAt(0)=='-'){
			key = key.substring(1);
			return bNodes.get(key);
		}
		return bNodes.get(key);
	}
	
	public HashMap<String, BNode> getNetwork(){
		return bNodes;
	}
	
	public void calculateProb(String request){
		StringTokenizer tk = new StringTokenizer(request, "|");
		String token;
		StringBuilder sBuilder = new StringBuilder();
		while(tk.hasMoreTokens()){
			token = tk.nextToken();
			sBuilder.append(token);
			
			//System.out.println(token.replaceAll("[+-]", ""));
			BNode queryNode = getBNode(token.replaceAll("[+-]", ""));
			//this.calc.putQueryResponse(request.replace("!", "|"), queryNode.getProbability(token));
			
			if(!queryNode.hasParents() && !tk.hasMoreTokens()){//This NODE requested doesn't depend on anything, return value right away or 
				this.calc.putQueryResponse(request, queryNode.getProbability(token));
				break;
			}else if (queryNode.hasParents()){
				this.calc.putQueryResponse(request, queryNode.getProbability(request));
				break;
			}else {
				String[] query = request.split("\\|");
				float likelihood, prior, nLikelihood, marginal;
				//codigo horrible.. perdon :(
				
				if (query[1].contains(",")){ //we have information about the 2 causes
					String qLike = query[1].split("\\,")[0]+"|"+query[1].split("\\,")[1]+","+query[0];
					System.out.println("qlike"+qLike);
					BNode likeNode =this.getBNode(query[1].split("\\,")[0].substring(1)); 
					likelihood = likeNode.getProbability(qLike);
					prior = this.getBNode(query[0].substring(1)).getProbability(query[0]);
					String nLike = query[1].split("\\,")[0]+"|"+query[1].split("\\,")[1]+",";
					if(query[0].charAt(0) == '+'){
						nLike+="-"+query[0].substring(1);
					} else {
						nLike+="+"+query[0].substring(1);
					}
					nLikelihood =this.getBNode(query[1].split("\\,")[0].substring(1)).getProbability(nLike);
					marginal = likelihood * prior + (1-prior)*nLikelihood;	
				} else {
					likelihood = this.getBNode(query[1].substring(1)).getProbability(query[1]+"|" + query[0]);
					prior = this.getBNode(query[0].substring(1)).getProbability(query[0]);
					marginal = 0;
					HashMap probs = this.getBNode(query[1].substring(1)).getProbabilities();
					Iterator i = probs.entrySet().iterator();
					while (i.hasNext()){
						Map.Entry pair = (Map.Entry)i.next();
						Float condProb = Float.parseFloat(pair.getValue().toString());
						String [] causes = pair.getKey().toString().split("\\|")[1].split(",");
						Float c1 = this.getBNode(causes[0].substring(1)).getProbability(causes[0]);
						Float c2 = this.getBNode(causes[1].substring(1)).getProbability(causes[1]);
						marginal+= condProb *c1*c2;
					}
				}
				float res = likelihood * prior/marginal;
				this.calc.putQueryResponse(request,res);
				break;
			}
		}
		
	}
	
	public void printNet(){
		System.out.println("------------------------------------------------");
	    System.out.println("           BAYESIAN NETWORK"+"\n");
		Set<String> keySet = this.bNodes.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		
		while (keySetIterator.hasNext()){
		   String key = keySetIterator.next();
		   this.bNodes.get(key).printNode();
		}
	}
	
	/* Reads text file with nodes */
	public void load(String path) {
		FileReader text;
		//BNode tempNode = null;
		BayesianNet network = this;
		try {
			text = new FileReader(path);
			BNode tempNode = null;

			BufferedReader reader = new BufferedReader(text);
			StringBuilder sBuilder;
			String line,token = "", aux;
			StringTokenizer tk;
			StringTokenizer tkParents;
			boolean probRequests = false;
			int node = 0;
			float prob;
			
			line = reader.readLine();
			while (line.charAt(0) == '#'){
				reader.mark(100000);
				line = reader.readLine();
			}
			
																//First we read all the main Nodes that will exist in our network
			StringTokenizer t = new StringTokenizer(line);
			while (t.hasMoreTokens()){
				network.putBNode(new BNode(t.nextToken()));
			}
		
			while (!probRequests && (line = reader.readLine()) != null){
				tk = new StringTokenizer(line, "|="); 
				
				node = 0;
				sBuilder = new StringBuilder(); //This will be used to hold node names and use them as keys for probabilities
				while (tk.hasMoreTokens()){
					token = tk.nextToken();
					token = token.replace(" ", "");
					
					if(token.charAt(0)=='@'){           //Here we break parsing probabilities and now we go to the probability requests
						probRequests = true;
						break;
					}
					prob = 0;

					if(node==0){                                          //This node read is the node we will modify its probability
						tempNode = network.getBNode(token.replaceAll("[+-]", ""));
						sBuilder.append(token);
						
					}else if(token.contains(".")){                        //This validation makes sure we are reading a float
						prob = Float.parseFloat(token);
						tempNode.putProbability(sBuilder.toString(), prob);
						network.putBNode(tempNode);
						
					}else if(node>0){
						sBuilder.append("|"+token);
						aux = token.replaceAll("[+-]", "");
						if(token.contains(",") && !tempNode.hasParents()){ //We want to make sure we don't add parents twice
							tkParents = new StringTokenizer(aux,",");
							while(tkParents.hasMoreTokens()){
								tempNode.addParent(getBNode(tkParents.nextToken()));
							}
						}else if(!tempNode.hasParents()){
							tempNode.addParent(getBNode(aux));
						}
					}
					node++;
				}
			}
			
			while ((line = reader.readLine()) != null){ //Now we start reading the probability requests
				tk = new StringTokenizer(line);
				while (tk.hasMoreTokens()){
					token = tk.nextToken();
					calc.putQueryResponse(token, 0);
					network.calculateProb(token);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
