package org.luvx.algorithm.tree;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树
 */
public class BinTree {

    /**
     * 前序遍历:递归方式
     *
     * @param root 根节点
     */
    public static void preSearch(Node root) {
        if (root == null)
            return;
        System.out.println(root.getData());
        preSearch(root.getLeft());
        preSearch(root.getRight());
    }

    /**
     * 中序遍历:递归方式
     *
     * @param root 根节点
     */
    public static void inSearch(Node root) {
        if (root == null)
            return;
        inSearch(root.getLeft());
        System.out.println(root.getData());
        inSearch(root.getRight());
    }

    /**
     * 后序遍历:递归方式
     *
     * @param root 根节点
     */
    public static void postSearch(Node root) {
        if (root == null)
            return;
        postSearch(root.getLeft());
        postSearch(root.getRight());
        System.out.println(root.getData());
    }

    /**
     * 前序遍历:循环方式
     *
     * @param root
     */
    public static void preSearchByLoop(Node root) {
        Stack<Node> stack = new Stack<>();
        while (true) {
            while (root != null) {
                System.out.println(root.getData());
                stack.push(root);
                root = root.getLeft();
            }

            if (stack.isEmpty())
                break;
            root = stack.pop().getRight();
        }
    }


    /**
     * 中序遍历:循环方式
     *
     * @param root 根节点
     */
    public static void inSearchByLoop(Node root) {
        Stack<Node> stack = new Stack<>();
        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }

            if (stack.isEmpty())
                break;
            root = stack.pop();
            System.out.println(root.getData());
            root = root.getRight();
        }
    }

    /**
     * 后序遍历:循环方式
     *
     * @param root 根节点
     */
    public static void postSearchByLoop(Node root) {
        Stack<Node> stack = new Stack<>();
        while (true) {
            if (root != null) {
                stack.push(root);
                root = root.getLeft();
            } else {
                if (stack.isEmpty())
                    return;

                if (stack.lastElement().getRight() == null) {
                    root = stack.pop();
                    System.out.println(root.getData());
                    while (!stack.isEmpty() && root == stack.lastElement().getRight()) {
                        root = stack.pop();
                        System.out.println(root.getData());
                    }
                }
                root = !stack.isEmpty() ? stack.lastElement().getRight() : null;
            }
        }
    }

    /**
     * 层级遍历
     *
     * @param root
     */
    public static void levelSearch(Node root) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        Node temp = null;
        int currentLevel = 1;    //记录当前层需要打印的节点的数量
        int nextLevel = 0;//记录下一层需要打印的节点的数量
        while ((temp = queue.poll()) != null) {
            System.out.print(temp.getData() + " ");

            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
                nextLevel++;

            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
                nextLevel++;
            }

            currentLevel--;
            if (currentLevel == 0) {
                System.out.println();
                currentLevel = nextLevel;
                nextLevel = 0;
            }
        }
    }

    /**
     * 层级遍历
     *
     * @param root 根节点
     */
    public static void levelOrder(Node root) {
        Node temp;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            temp = queue.poll();
            System.out.println(temp.getData());
            if (temp.getLeft() != null)
                queue.offer(temp.getLeft());
            if (temp.getRight() != null) {
                queue.offer(temp.getRight());
            }
        }
    }
}
