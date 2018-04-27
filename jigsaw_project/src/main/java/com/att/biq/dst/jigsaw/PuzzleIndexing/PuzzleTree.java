package com.att.biq.dst.jigsaw.PuzzleIndexing;

import java.util.Iterator;

/**
 * TODO question - can we generate the whole tree structure and after rotating the tree and implementing the rotated pieced
 */

public class PuzzleTree implements Iterable<Node> {


    private Node node;




// prepare the tree structure, it's does not change (always 4 levels of 1,0,1)


    public void addNodesToTree(Edges treeLevel, int nodeValue) {
        Node newNode = new Node(treeLevel, nodeValue);
        if (node == null) {
            node = newNode;
        } else {
            Node current = node;
        }

        node.setNext(newNode);
        }












    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Node> iterator() {
        return null;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node curr = node;
        while(curr != null) {
            s.append("You are on Node: ");
            s.append("Node value = "+curr.getNodeValue()+" node treeLevel:  "+curr.getTreeLevel());
            s.append(' ');
            curr = curr.getNext();
        }
        return s.toString();
    }



}
