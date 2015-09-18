import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class UninformedSearch {
	
	public static Graph myGraph;

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

	public Node depthFirst(int sta, int dest, LinkedList<Node> frontier){
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		HashMap<Node, Integer> neighbors = null;
		Node nodeResult = null;
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];
		String visitedNodes = "";
		int cost = 0;
		
		frontier.push(start); //pushing (stack) start node to frontier

		while (!frontier.isEmpty()){ //while there are still nodes to visit
			Node top = frontier.peek(); //check the top of the stack
			neighbors = top.getNeighbors();
			top.setVisited(true); //marking node as visited to avoid loops
			visitedNodes += top.getId()+", ";
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited()&& !inFrontier[n.getId()]){ //enqueue node if hasn't been visited and there is a path to get there
					nodeResult = depthFirst(n.getId(), dest, frontier);
					cost = n.getCost() + neighbors.get(n);
					n.setCost(cost);
				}
			}
			try{
				top = frontier.pop();
				if(nodeResult!= null)
					return nodeResult; //This is done to avoid going through nodes missing in stack
				
				if (top.equals(end)){ //goal test
					System.out.println(cost);
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
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(new Comparer()); //This constructor calls the comparer class which defines property to compare 
		Node start = myGraph.findNode(sta);
		Node end = myGraph.findNode(dest);
		int pathCost = 0;
		HashMap<Node, Integer> neighbors = null;
		boolean inFrontier[] = new boolean[myGraph.getNodes().size()];
		String visitedNodes = "";
		
		frontier.add(start);

		while (!frontier.isEmpty()){ //while there are still nodes to visit

			Node top = frontier.poll();
			top.setVisited(true); //marking node as visited to avoid loops
			visitedNodes += top.getId()+", ";
			if (top.equals(end)){ //goal test
				System.out.println(pathCost);
				System.out.println(visitedNodes);
				return top;
			}	
			
			neighbors = top.getNeighbors();
			
			for (Node n : neighbors.keySet()){ //check each node's neighbor
				if (!n.isVisited()&& !inFrontier[n.getId()]){ //There is a path to get to node
						pathCost = neighbors.get(n) + top.getCost();
						if(!inFrontier[n.getId()]){ //If I haven't inserted it, then I add it to queue
							n.setCost(pathCost);
							frontier.add(n);
							
						}else if(pathCost < n.getCost()){ //In case a Node was already in frontier, but we find a better path
							frontier.remove(n); //In case there is a new way to get to a node cheaper, update expensive path
							n.setCost(pathCost);
							frontier.add(n);

						}

				}
			}
		}
		return null;
	}
	
	public static void main(String args[]){
		long start = System.currentTimeMillis();
		long endTime;
		
		LinkedList<Node> frontier = new LinkedList<Node>(); 
		myGraph = new Graph();
		String filepath = args[1];
		myGraph.load(filepath);
		String from = args[2];
		String to = args[3];
		
		UninformedSearch us = new UninformedSearch();
		Node end = us.breadthFirst(Integer.parseInt(from),Integer.parseInt(to));
		
		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("DEPTH FIRST SEARCH:");
		Node depthResult = us.depthFirst(Integer.parseInt(from), Integer.parseInt(to), frontier);
		if(depthResult==null)
			System.out.println("Destination not found! :(");
		
		System.out.print("\n\n\n-----------------------------------------------------\n");
		myGraph.resetGraphState();
		System.out.println("UNIFORM COST SEARCH:");
		us.uniformCost(Integer.parseInt(from),Integer.parseInt(to));
		
	}
}


