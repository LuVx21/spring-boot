package org.luvx.algorithm.tree;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinSearchTreeTest {

    Node root;

    @Before
    public void beforeTest() {
        //   4
        //   /\
        //  2  6
        //  /\  /\
        // 1  3 5 7
        Node<Integer> node1 = new Node<>(4);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(6);
        Node<Integer> node4 = new Node<>(1);
        Node<Integer> node5 = new Node<>(3);
        Node<Integer> node6 = new Node<>(5);
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
    public void run01() {
        BinSearchTree.search(root, 1);

    }

    @Test
    public void run02() {
        int height = BTHeight.comHeight(root);
        Assert.assertEquals(height, 3);
        height = BTHeight.comHeightByLoop(root);
        Assert.assertEquals(height, 3);
    }

    @Test
    public void run03() {
        int nums = BTLeafNum.comLeafNums(root);
        System.out.println(nums);
        // Assert.assertEquals(nums, 3);
        nums = BTLeafNum.comLeafNumsByLoop(root);
        System.out.println(nums);
        // Assert.assertEquals(nums, 3);
    }

}