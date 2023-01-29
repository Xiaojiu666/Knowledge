package com.study.algorithm.DataStructure.Tree.Search;

public class BinarySearchTreeNode {

    public BinarySearchTreeNode(){
    }

    public BinarySearchTreeNode(int data){
        setData(data);
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public BinarySearchTreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(BinarySearchTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public BinarySearchTreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(BinarySearchTreeNode rightNode) {
        this.rightNode = rightNode;
    }

    private int data;
    private BinarySearchTreeNode leftNode;
    private BinarySearchTreeNode rightNode;




}
