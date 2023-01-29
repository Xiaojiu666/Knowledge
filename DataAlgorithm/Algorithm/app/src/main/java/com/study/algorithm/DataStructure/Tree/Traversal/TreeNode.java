package com.study.algorithm.DataStructure.Tree.Traversal;

public class TreeNode<T> {

    public  TreeNode(){
    }

    public  TreeNode(T data){
        setData(data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(TreeNode<T> leftNode) {
        this.leftNode = leftNode;
    }

    public TreeNode<T> getRightNode() {
        return rightNode;
    }

    public void setRightNode(TreeNode<T> rightNode) {
        this.rightNode = rightNode;
    }

    private T data;
    private TreeNode<T> leftNode;
    private TreeNode<T> rightNode;




}
