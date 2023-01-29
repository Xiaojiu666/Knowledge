package com.study.algorithm.DataStructure.Tree.Traversal;

public class BinaryTreeTraversal {

    public static void main(String[] args) {
        TreeNode<String> cur = initData();

        System.out.println("二叉树: 前序遍历");
        pre(cur);
        System.out.println("二叉树: 中序遍历");
        mid(cur);
        System.out.println("二叉树: 后序遍历");
        post(cur);
    }

    private static TreeNode<String> initData() {
        TreeNode<String> a = new TreeNode<>("A");
        TreeNode<String> b = new TreeNode<>("B");
        TreeNode<String> c = new TreeNode<>("C");
        TreeNode<String> d = new TreeNode<>("D");
        TreeNode<String> e = new TreeNode<>("E");
        TreeNode<String> f = new TreeNode<>("F");
        TreeNode<String> g = new TreeNode<>("G");
        TreeNode<String> h = new TreeNode<>("H");
        a.setLeftNode(b);
        a.setRightNode(c);

        b.setLeftNode(d);
        b.setRightNode(e);

        d.setLeftNode(h);

        c.setLeftNode(f);
        c.setRightNode(g);

        return a;

    }


    private static void pre(TreeNode cur) {
        if (cur == null) {
            return;
        }
        System.out.println(cur.getData());
        pre(cur.getLeftNode());
        pre(cur.getRightNode());

    }

    private static void mid(TreeNode cur) {
        if (cur == null) {
            return;
        }

        mid(cur.getLeftNode());
        System.out.println(cur.getData());
        mid(cur.getRightNode());
    }


    private static void post(TreeNode cur) {
        if (cur == null) {
            return;
        }
        post(cur.getLeftNode());
        post(cur.getRightNode());
        System.out.println(cur.getData());

    }


}
