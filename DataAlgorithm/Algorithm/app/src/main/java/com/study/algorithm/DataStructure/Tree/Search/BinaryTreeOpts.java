package com.study.algorithm.DataStructure.Tree.Search;

import com.study.algorithm.DataStructure.Tree.Traversal.TreeNode;

public class BinaryTreeOpts {

    static BinarySearchTreeNode root;


    private static void insert(int data) {
        if (root == null) {
            root = new BinarySearchTreeNode(data);
            return;
        }
        BinarySearchTreeNode node = root;
        while (node != null) {
            if (data < node.getData()) {
                if (node.getLeftNode() == null) {
                    BinarySearchTreeNode binarySearchTreeNode = new BinarySearchTreeNode(data);
                    node.setLeftNode(binarySearchTreeNode);
                    return;
                }
                node = node.getLeftNode();
            } else if (data > node.getData()) {
                if (node.getRightNode() == null) {
                    BinarySearchTreeNode binarySearchTreeNode = new BinarySearchTreeNode(data);
                    node.setRightNode(binarySearchTreeNode);
                    return;
                }
                node = node.getRightNode();
            }
        }
    }


    public static void main(String[] args) {
//        int[] datas = new int[]{100, 80, 200, 54, 90, 150, 210};
        int[] datas = new int[]{60, 40, 80, 30, 50, 70, 100, 20, 35, 45, 55};
        for (int i = 0; i < datas.length; i++) {
            insert(datas[i]);
        }
        mid(root);
//        find(100);
        System.out.println(" ");
       remove(40);
        //        change(20);
        mid(root);
    }



    private static void change(int data){
        BinarySearchTreeNode node = root;
        node.setData(data);
    }

    private static void remove(int data) {
        BinarySearchTreeNode node = root;
        BinarySearchTreeNode parent = null;
        // 1. 优先找到要删除的节点 和 他的父节点
        while (node != null && node.getData() != data) {
            parent = node;
            if (data > node.getData()) {
                node = node.getRightNode();
            } else if (data < node.getData()) {
                node = node.getLeftNode();
            }
        }

        if (node == null) {
            return;
        }

        // 4.  在Node只有两个子节点时
        // 4.1 删除原理：找到node 右节点中最小值进行替换 ，然后删除最小节点
        if (node.getLeftNode() != null && node.getRightNode() != null) {
            BinarySearchTreeNode minNode = node.getRightNode();
            BinarySearchTreeNode minParentNode = node;
            while (minNode.getLeftNode() != null) {
                minParentNode = minNode;
                minNode = minNode.getLeftNode();

            }
            node.setData(minNode.getData()); // 节点数据转换 ， 左右子指针不变
            node = minNode;                  // 将原有要删除的node 更换为minNode
            parent = minParentNode;
        }

        //2.  找到要被删除节点的 的 子左右节点，
        //2.1 在Node只有单子节点时 ， 删除的根本在于，将Node父节点的指针，指向Node的子节点
        BinarySearchTreeNode childNode = null;
        if (node.getLeftNode() != null) {
            childNode = node.getLeftNode();
        } else if (node.getRightNode() != null) {
            childNode = node.getRightNode();
        }
        System.out.println("被删除节点 : " + node.getData());
        System.out.println("被删除节点的父节点 : " + parent.getData());
//        System.out.println("被删除节点的子节点 : " + childNode.getData());

        //3.   重新对父节点赋值
        if (parent == null) {
            root = childNode;
        } else if (parent.getLeftNode() == node) {
            parent.setLeftNode(childNode);
            System.out.println("parent.getLeftNode() : " + parent.getLeftNode());
        } else if (parent.getRightNode() == node) {
            parent.setRightNode(childNode);
            System.out.println("parent.getRightNode() : " + parent.getRightNode());
        }


    }

    private static void find(int data) {
        BinarySearchTreeNode node = root;
        while (node != null) {
            if (node.getData() < data) {
                node = node.getLeftNode();
            } else if (node.getData() > data) {
                node = node.getRightNode();
            } else {
                System.out.println("找到了 ： " + data);
                return;
            }
        }
        System.out.println("没找到 ： " + data);

    }


    private static void mid(BinarySearchTreeNode cur) {
        if (cur == null) {
            return;
        }
        mid(cur.getLeftNode());
        System.out.print(" ," + cur.getData());
        mid(cur.getRightNode());


    }

}
