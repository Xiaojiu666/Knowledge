package com.study.algorithm.DataStructure.Tree.Search;

import com.study.algorithm.DataStructure.Tree.Traversal.TreeNode;

public class BinaryTreeOpts {


    public static void main(String[] args) {
        int[] datas = new int[]{100, 80, 200, 54, 90, 150, 210};
    }

    static BinarySearchTreeNode root;

    private void insert(int data) {
        if (root == null) {
            root = new BinarySearchTreeNode();
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


//        if (root.getData())


    }

}
