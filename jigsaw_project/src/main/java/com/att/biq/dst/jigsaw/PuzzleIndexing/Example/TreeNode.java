package com.att.biq.dst.jigsaw.PuzzleIndexing.Example;

public class TreeNode<E extends Comparable<E>> {
    enum SIDE {
        LEFT(0), RIGHT(1);
        private final int index;

        SIDE(int index) {
            this.index = index;
        }
    }

    // left and right
    private TreeNode<E>[] nodes = new TreeNode[2];
    private E value;

    /*package*/
    TreeNode(E val) {
        value = val;
    }

    /*package*/
    void add(TreeNode node) {
        if(node.value.compareTo(value) < 0) {
            go(SIDE.LEFT, node);
        }
        else {
            go(SIDE.RIGHT, node);
        }
    }

    private void go(SIDE side, TreeNode node) {
        if(nodes[side.index] == null) {
            nodes[side.index] = node;
        }
        else {
            nodes[side.index].add(node);
        }
    }

    private static String toString(TreeNode node) {
        if (node != null) {
            return node.toString();
        }
        return "";
    }

    @Override
    public String toString() {
        return toString(nodes[SIDE.LEFT.index]) +
                value + ", " +
                toString(nodes[SIDE.RIGHT.index]);
    }
}
