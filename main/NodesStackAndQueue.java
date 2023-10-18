package main;

import java.util.ArrayList;

public class NodesStackAndQueue {
    private ArrayList<Node> data;

    public NodesStackAndQueue() {
        data = new ArrayList<>();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Push operation refers to inserting an element on the Top of the stack.
     *
     * @param node
     */
    public void push(Node node) {
        data.add(node);
    }

    /**
     * pop an element from the top of the stack (removes and returns the tope
     * element)
     *
     * @return
     */
    public Node pop() {
        if (data.isEmpty()) {
            throw new EmptyStackException();
        }
        Node lastNodeInStack = data.get(data.size() - 1); // store the last node in a temp variable
        data.remove(data.size() - 1); // remove the last node
        return lastNodeInStack;
    }

    /**
     * get the element from the top of the stack without removing it
     *
     * @return
     */
    public Node peek() {
    	if (data.isEmpty()) {
    		throw new EmptyStackException();
    	}
        return data.get(data.size()-1);
    }

    /**
     * append an element at the end of the stack
     *
     * @param node
     */
    public void append(Node node) {
    	data.add(0, node);//shifts all element to the right by one and add one to the index 0 position
    }
    
    public ArrayList<Node> getData() {
    	return data;
    }

}
