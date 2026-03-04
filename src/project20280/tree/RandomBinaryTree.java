package project20280.tree;

import java.util.Random;

public class RandomBinaryTree {
    static class Node {
        Node left, right;
        Node() {}
    }

    static Node buildRandomTree(int n, Random rng){
        double[] keys = new double[n];
        for (int i = 0; i < n; i++) keys[i] = rng.nextDouble();

        for (int i = n -1; i > 0; i--){
            int j = rng.nextInt(i + 1);
            double tmp = keys[j];
            keys[j] = keys[i];
            keys[i] = tmp;
        }
        Node root = null;
        for (double k : keys) root = insert(root,k);
        return root;
    }

    static Node insert(Node node, double key){
        if (node == null) return new Node();
        return node;
    }

    static class BSTNode {
        double key;
        BSTNode left, right;
        BSTNode(double k){
            key = k;
        }
    }

    static BSTNode BSTInsert(BSTNode node, double key){
        if (node == null) return new BSTNode(key);
        if (key < node.key) {
            node.left = BSTInsert(node.left, key);
        } else {
            node.right = BSTInsert(node.right, key);
        }
        return node;
    }

    static int height(BSTNode node){
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static BSTNode buildRandomBST(int n, Random r){
        double[] keys = new double[n];
        for (int i = 0; i < n; i++) keys[i] = r.nextDouble();

        for  (int i = n -1; i > 0; i--){
            int j = r.nextInt(i + 1);
            double tmp = keys[i];
            keys[i] = keys[j];
            keys[j] = tmp;
        }
        BSTNode root = null;
        for (double k : keys) root = BSTInsert(root,k);
        return root;
    }

    public static void main(String[] args){
        Random r = new Random(42);
        int trials = 100;

        System.out.println("n,avgHeight");

       for (int n = 50; n <= 5000; n += 50){
           double totalHeight = 0;
           for (int t=0; t < trials; t++){
               BSTNode tree = buildRandomBST(n, r);
               totalHeight += height(tree);
           }


           double avgHeight = totalHeight / trials;
           double logN = Math.log(n) / Math.log(2);
           double ratio = avgHeight / logN;
           System.out.printf("%d,%.4f\n", n, avgHeight);
       }
       }
    }
