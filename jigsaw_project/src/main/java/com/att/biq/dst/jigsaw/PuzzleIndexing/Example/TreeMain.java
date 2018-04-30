package com.att.biq.dst.jigsaw.PuzzleIndexing.Example;

public class TreeMain {

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.add(70);
        tree.add(702);
        tree.add(720);
        tree.add(270);
        tree.add(170);
        tree.add(17);
        tree.add(71);
        tree.add(77);
        tree.add(37);
        System.out.println(tree);

        BinaryTree<String> tree2 = new BinaryTree<>();
        tree2.add("Hello");
        tree2.add("world");
        tree2.add("how");
        tree2.add("are");
        tree2.add("you");
        tree2.add("doing");
        tree2.add("today");
        System.out.println(tree2);
    }

}
