package com.att.biq.dst.jigsaw.PuzzleIndexing.Example;

public class BinaryTree<E extends Comparable<E>> {

    TreeNode<E> root;

    public void add(E val) {
        TreeNode<E> newNode = new TreeNode<>(val);
        if(root == null) {
            root = newNode;
        }
        else {
            root.add(newNode);
        }
    }

    @Override
    public String toString() {
        return "[" + (root!=null? root.toString()+"\b\b" :"") + "]";
    }
}
