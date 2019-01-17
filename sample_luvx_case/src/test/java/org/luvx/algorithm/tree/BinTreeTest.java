package org.luvx.algorithm.tree;


import org.junit.Before;
import org.junit.Test;

public class BinTreeTest {

    Node root;

    @Before
    public void beforeTest() {
        //   1
        //   /\
        //  2  3
        //  /\  /\
        // 4  5 6 7
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);
        Node<Integer> node7 = new Node<>(7);
        node1.setLeft(node2);
        node1.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);
        node3.setRight(node7);
        root = node1;
    }

    @Test
    public void searchTest() {
        BinTree.preSearch(root);
        System.out.println("-----------");
        BinTree.inSearch(root);
        System.out.println("-----------");
        BinTree.postSearch(root);
    }

    @Test
    public void searchByLoopTest() {
        BinTree.preSearchByLoop(root);
        System.out.println("-----------");
        BinTree.inSearchByLoop(root);
        System.out.println("-----------");
        BinTree.postSearchByLoop(root);
    }

    @Test
    public void levelTest() {
        BinTree.levelSearch(root);
        System.out.println("-----------");
        BinTree.levelOrder(root);
    }
}