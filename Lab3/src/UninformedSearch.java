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


	public Node depthFirst(int sta, int dest, LinkedList<Node> frontier, String visitedNodes, boolean[] inFrontier){
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		HashMap<Node, Integer> neighbors = null;
		Node nodeResult = null;
		int cost = 0;
		
		frontier.push(start); //pushing (stack) start node to frontier
		
		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node top = frontier.peek(); //check the top of the stack
			neighbors = top.getNeighbors();
			top.setVisited(true); //marking node as visited to avoid loops
			visitedNodes += top.getId() + ", ";

			for (Node n : neighbors.keySet()){ //check each node's neighbor

				if (!n.isVisited()&& !inFrontier[n.getId()]){ //enqueue node if hasn't been visited and there is a path to get there
					cost = top.getCost() + neighbors.get(n);
					n.setCost(cost);
					nodeResult = depthFirst(n.getId(), dest, frontier, visitedNodes,inFrontier);
					if(nodeResult != null)
						return nodeResult; //This is done to avoid going through nodes missing in stack
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
	public void printPath(LinkedList<Node> path, Node n){
		String visitedNodes = "";
		for(int i= 0; i< path.size(); i++){
			if(path.get(i) != n.getParent()){
				visitedNodes += path.get(i) .getId()+", ";
			}else{
				visitedNodes += n.getParent() + ", "+ n.getId();
				break;
			}
		}
		System.out.println(visitedNodes);
		
	}
	public Node uniformCost(int sta, int dest){
		PQsort pqs = new PQsort();
		LinkedList<Node> path = new LinkedList<Node>();
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(pqs); //This constructor calls the comparer class which defines property to compare 
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		int pathCost = 0, index = 0;
		HashMap<Node, Integer> neighbors = null;
		frontier.add(start);

		while (!frontier.isEmpty()){ //while there are still nodes to visit
			
			Node top = frontier.poll();
			top.setVisited(true); //marking node as visited to avoid loops
			path.add(top);
			if (top.equals(end)){ //goal test
				System.out.println(top.getCost());
				printPath(path, top);
				return top;
			}	
			
			neighbors = top.getNeighbors();
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited()){ //There is a path to get to node
						pathCost = neighbors.get(n) + top.getCost();
						if(!frontier.contains(n)){
							n.setParent(top);
							n.setCost(pathCost);
							frontier.offer(n);
						}else if(n.getCost()> pathCost){
							frontier.remove(n);
							n.setParent(top);
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
		String filepath = args[0];

		myGraph.load(filepath);
		String from = args[1];
		String to = args[2];
		myGraph.load(filepath);
		
		
		UninformedSearch us = new UninformedSearch();
		start = System.currentTimeMillis();
		Node end = us.breadthFirst(Integer.parseInt(from),Integer.parseInt(to));
		endTime = System.currentTimeMillis();
		System.out.println("time (milliseconds)" + (endTime - start));
		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("DEPTH FIRST SEARCH:");
		String visitedNodes = "";
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];
		start = System.currentTimeMillis();
		Node depthResult = us.depthFirst(Integer.parseInt(from), Integer.parseInt(to), frontier,visitedNodes,inFrontier);
		endTime = System.currentTimeMillis();

		if(depthResult==null)
			System.out.println("Destination not found! :(");
		System.out.println("time (milliseconds)" + (endTime - start));

		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("UNIFORM COST SEARCH:");
		start = System.currentTimeMillis();
		us.uniformCost(Integer.parseInt(from),Integer.parseInt(to));
		endTime = System.currentTimeMillis();
		System.out.println("time (milliseconds)" + (endTime - start));
		
	}
}


