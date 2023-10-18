package main;

import java.util.HashMap;
import java.util.List;

public class Graph {
	private HashMap<Node, EdgesLinkedList> adjacencyMap;
	/**
	 * root of the graph, to know where to start the DFS or BFS
	 */
	private Node root;

	/**
	 * !!!!!! You cannot change this method !!!!!!!
	 */
	public Graph(List<String> edges, List<String> weights) {
		if (edges.isEmpty() || weights.isEmpty()) {
			throw new IllegalArgumentException("edges and weights are empty");
		}
		adjacencyMap = new HashMap<>();
		int i = 0;
		for (String edge : edges) {
			String[] split = edge.split(",");
			Node source = new Node(split[0]);
			Node target = new Node(split[1]);
			Edge edgeObject = new Edge(source, target, Integer.parseInt(weights.get(i)));
			if (!adjacencyMap.containsKey(source)) {
				adjacencyMap.put(source, new EdgesLinkedList());
			}
			// adjacencyMap.get(source).append(edgeObject);
			if (i == 0) {
				root = source;
			}
			i++;
		}
	}

	public boolean isNodeInGraph(Node node) {

		// return true if contains the key
		if (adjacencyMap.containsKey(node)) {
			return true;
		} // else check whether there is target node that is the same
		for (EdgesLinkedList edgesLinkedList : adjacencyMap.values()) {
			Edge edgeInList = edgesLinkedList.get(0);
			while (edgeInList != null) {
				if (node.equals(edgeInList.getTarget())) {
					return true;
				}

				edgeInList = edgeInList.getNext();
			}
		}
		return false;
	}

	public Path computeShortestPath(Node source, Node target) {
		int[] distanceFromSourceNode = new int[adjacencyMap.keySet().size()]; // create array to store distance values
																				// of nodes
		HashMap<Node, Node> previousNodeInShortestPath = new HashMap<>(); // create the hashmap for each node followed
																			// by its previous node in shortest path

		NodesStackAndQueue nodesToBeProcessed = new NodesStackAndQueue(); // NodesStackAndQueue for storing nodes that
																			// needs to be processed
		NodesStackAndQueue nodeVisited = new NodesStackAndQueue(); // NodesStackAndQueue for storing all nodes in
																	// shortest path from source to target
		NodesStackAndQueue allNodesInGraph = new NodesStackAndQueue();// NodesStackAndQueue for storing all nodes in
																		// graph

		Node leadNode = source; // this will be the leadNode value for each relaxation, starting with source
								// node as lead node
		Node nodeVisitedInShortestPath = target;
		Edge edgesOfLeadNode = adjacencyMap.get(leadNode).get(0); // this will be every edge with leadNode as source
																	// node

		int indexOfNodes = 0;
		int indexOfSourceNode = 0;
		int indexOfLeadNode = 0;
		int minimumDistance = Integer.MAX_VALUE;
		int newDistanceValue = 0;
		int totalCost = 0;

		// for each node inside the graph, set the distance values at its corresponding
		// index it is stored in the hash map
		for (Node node : adjacencyMap.keySet()) {
			distanceFromSourceNode[indexOfNodes] = Integer.MAX_VALUE;
			previousNodeInShortestPath.put(node, null); // the previous node is null for each node

			nodesToBeProcessed.push(node); // store all the nodes as nodes to be processed
			allNodesInGraph.push(node);// store all the nodes as nodes to allNodesInGraph which help us to check with
										// index of nodes

			if (node.equals(source)) {
				indexOfSourceNode = indexOfNodes; // get the index of source node inside the distance array
			}

			indexOfNodes++;
		}

		distanceFromSourceNode[indexOfSourceNode] = 0; // set the distance for the source node to be 0

		// repeat until all nodes have been used as the lead node and performed
		// relaxation
		while (!nodesToBeProcessed.isEmpty()) {
			// for each loop to find the node inside the nodesToBeProcessed with minimum
			// distance value and the index of that node
			for (Node node : nodesToBeProcessed.getData()) {
				// only will look for nodes inside the nodesToBeProcessed
				if (distanceFromSourceNode[allNodesInGraph.getData().indexOf(node)] < minimumDistance) {
					minimumDistance = distanceFromSourceNode[allNodesInGraph.getData().indexOf(node)];
					indexOfLeadNode = allNodesInGraph.getData().indexOf(node); // this is the index inside the
																				// stackandqueue that store all nodes of
																				// the graph
				}
			}
			minimumDistance = Integer.MAX_VALUE; // reset it to infinity for next iteration comparison
			leadNode = allNodesInGraph.getData().get(indexOfLeadNode); // lead node will be the node with the shortest
																		// distance in nodesToBeProcessed
			nodesToBeProcessed.getData().remove(nodesToBeProcessed.getData().indexOf(leadNode)); // remove the lead node
																									// from
																									// nodesToBeProcessed
			edgesOfLeadNode = adjacencyMap.get(leadNode).get(0); // this will be every edge of the leadNode

			// repeat until the edge will be equal to null in which case every edge of the
			// leadNode
			while (edgesOfLeadNode != null) {
				Node targetNode = edgesOfLeadNode.getTarget();
				newDistanceValue = distanceFromSourceNode[indexOfLeadNode] + edgesOfLeadNode.getWeight(); // for each
																											// edge that
																											// lead has
																											// get it's
																											// new
																											// distance
																											// value

				// if the new distance is less than the distance of the edge's target node's
				// distance then change it to the new distance and update the previous node
				if (newDistanceValue < distanceFromSourceNode[allNodesInGraph.getData().indexOf(targetNode)]) {
					distanceFromSourceNode[allNodesInGraph.getData().indexOf(targetNode)] = newDistanceValue;
					previousNodeInShortestPath.put(targetNode, leadNode);
				}

				edgesOfLeadNode = edgesOfLeadNode.getNext(); // get next edge with the lead node as source node for that
																// edge
			}
		}

		// add all nodes visited in the shortest path
		while (!nodeVisitedInShortestPath.equals(source)) {
			nodeVisited.append(nodeVisitedInShortestPath);
			nodeVisitedInShortestPath = previousNodeInShortestPath.get(nodeVisitedInShortestPath);
		}

		nodeVisited.append(source);
		totalCost = distanceFromSourceNode[allNodesInGraph.getData().indexOf(target)];
		return new Path(totalCost, nodeVisited.getData());
	}

}