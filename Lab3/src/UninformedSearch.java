import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class UninformedSearch {
	
	public static Graph myGraph;
	
	static class PQsort implements Comparator<Node> {
		public int compare(Node x, Node y) {
		      return x.getCost() - y.getCost();
		}
	}
 
	public Node breadthFirst(int s, int d){
		
		Node start = myGraph.findNode(s);
		Node end = myGraph.findNode(d);
		LinkedList<Node> frontier = new LinkedList<Node>(); //list of unvisited nodes
		String visitedNodes = "";
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];
		int cost = 0;
		
		System.out.println("BREADTH FIRST SEARCH:");
		frontier.add(start); //enqueueing start node to frontier
		cost = 0; //initial cost is 0

		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node shallow = frontier.poll(); //dequeue unvisited node
			visitedNodes += shallow.getId()+", ";
			shallow.setVisited(true); //marking node as visited
			
			if (shallow.equals(end)){ //goal test
				System.out.println(shallow.getCost());
				System.out.println(visitedNodes);
				return shallow;
			}
			
			for (Node n : shallow.getNeighbors().keySet()){ //check each node's neighbor

				if (!n.isVisited() && !inFrontier[n.getId()]){ //enqueue node if hasn't been visited and there is a path to get there
					cost = shallow.getNeighbors().get(n) + shallow.getCost();
					frontier.addLast(n);
					inFrontier[n.getId()] = true;
					n.setCost(cost);
				}	
			}
		}
		System.out.println("Not found");
		return null;
	}

<<<<<<< HEAD
	public Node depthFirst(int sta, int dest, LinkedList<Node> frontier, String visitedNodes){
=======
	public Node depthFirst(int sta, int dest, LinkedList<Node> frontier, String visitedNodes,boolean [] inFrontier){
>>>>>>> origin/master
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		HashMap<Node, Integer> neighbors = null;
		Node nodeResult = null;
<<<<<<< HEAD
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];
=======
>>>>>>> origin/master
		int cost = 0;
		
		frontier.push(start); //pushing (stack) start node to frontier
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node top = frontier.peek(); //check the top of the stack
			neighbors = top.getNeighbors();

			visitedNodes += top.getId()+", ";
<<<<<<< HEAD
			//System.out.println(neighbors.toString());
=======
			top.setVisited(true); //marking node as visited to avoid loops
			
>>>>>>> origin/master
			for (Node n : neighbors.keySet()){ //check each node's neighbor

				if (!n.isVisited()&& !inFrontier[n.getId()]){ //enqueue node if hasn't been visited and there is a path to get there
<<<<<<< HEAD
					cost = top.getCost() + neighbors.get(n);
					//System.out.println("Setting cost "+ n.getId() + ":" + cost);
					n.setCost(cost);
					nodeResult = depthFirst(n.getId(), dest, frontier, visitedNodes);
					if(nodeResult != null)
						return nodeResult; //This is done to avoid going through nodes missing in stack
=======
					cost = n.getCost() + neighbors.get(n);
					n.setCost(cost);
					nodeResult = depthFirst(n.getId(),dest,frontier,visitedNodes,inFrontier);
					inFrontier[n.getId()] = true;
>>>>>>> origin/master
				}
			}
			try{
				top = frontier.pop();
				if (top.equals(end)){ //goal test
					System.out.println(top.getCost());
					System.out.println(visitedNodes);
					return top;
				}else{
					return null;
				}
			}catch(NoSuchElementException e){
				System.out.println("Stack is empty!");
				return null;
			}
		}
		System.out.println("Not found");
		return null;
	}
	
	public Node uniformCost(int sta, int dest){
		PQsort pqs = new PQsort();
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(pqs); //This constructor calls the comparer class which defines property to compare 
		
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		int pathCost = 0;
		HashMap<Node, Integer> neighbors = null;
		//boolean inFrontier[] = new boolean[myGraph.getNodes().size()];
		String visitedNodes = "";
		
		frontier.add(start);

		while (!frontier.isEmpty()){ //while there are still nodes to visit

			Node top = frontier.poll();
			top.setVisited(true); //marking node as visited to avoid loops
			visitedNodes += top.getId()+", ";
			
			if (top.equals(end)){ //goal test
				System.out.println(top.getCost());
				System.out.println(visitedNodes);
				return top;
			}	
			
			neighbors = top.getNeighbors();
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited()){ //There is a path to get to node
						pathCost = neighbors.get(n) + top.getCost();
						if(!frontier.contains(n)){
							n.setCost(pathCost);
							frontier.offer(n);
						}else if(n.getCost()> pathCost){
							frontier.remove(n);
							n.setCost(pathCost);
							frontier.offer(n);
						}
				}
				
			}
		}
		return null;
	}
	
	public static void main(String args[]){
		long start,endTime;
		
		LinkedList<Node> frontier = new LinkedList<Node>(); 
		myGraph = new Graph();
<<<<<<< HEAD
		//String filepath = args[0];
		String filepath = "graph.txt";

		myGraph.load(filepath);
		//String from = args[1];
		String from = "1";

		//String to = args[2];
		String to = "3";
		//myGraph.print();
=======
		String filepath = args[0];
		myGraph.load(filepath);
		
		String from = args[1];
		String to = args[2];
>>>>>>> origin/master
		
		UninformedSearch us = new UninformedSearch();
		start = System.currentTimeMillis();
		Node end = us.breadthFirst(Integer.parseInt(from),Integer.parseInt(to));
		endTime = System.currentTimeMillis();
		System.out.println("time (milliseconds)" + (endTime - start));
		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("DEPTH FIRST SEARCH:");
<<<<<<< HEAD
		Node depthResult = us.depthFirst(Integer.parseInt(from), Integer.parseInt(to), frontier, "");
=======
		String visitedNodes = "";
		
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];
		start = System.currentTimeMillis();
		Node depthResult = us.depthFirst(Integer.parseInt(from), Integer.parseInt(to), frontier,visitedNodes,inFrontier);
		endTime = System.currentTimeMillis();

>>>>>>> origin/master
		if(depthResult==null)
			System.out.println("Destination not found! :(");
		System.out.println("time (milliseconds)" + (endTime - start));

		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("UNIFORM COST SEARCH:");
		start = System.currentTimeMillis();
		us.uniformCost(Integer.parseInt(from),Integer.parseInt(to));
		endTime = System.currentTimeMillis();
		
	}
}


