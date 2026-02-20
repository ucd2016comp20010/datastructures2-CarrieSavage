package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    int size = 0; // number of nodes in the tree
    public int heightCalls = 0;

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String [] args) {
        LinkedBinaryTree<String> bt1 = new LinkedBinaryTree<>();
        String[] arr1 = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        bt1.createLevelOrder(arr1);

        System.out.println(bt1.toBinaryTreeString());
        System.out.println("External nodes = " + bt1.countExternal());


        Integer[] arr2 = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, null, null, null, 35};

        LinkedBinaryTree<Integer> bt2 = new LinkedBinaryTree<>();
        bt2.createLevelOrder(arr2);
        System.out.println("Diameter = " + bt1.diameter());

        bt2.heightCalls = 0;
        int h = bt2.height();
        System.out.println("Height = " + h);
        System.out.println("Height calls = " + bt2.heightCalls);
        System.out.println("Left external nodes = " + bt1.countLeftExternal());

        Position<String> root = bt1.root();
        System.out.println("Descendants of root = " + bt1.countDescendants(root));

        Position<String> nodeB = bt1.left(root);
        System.out.println("Descendants of B = " + bt1.countDescendants(nodeB));
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (root != null) {
            throw new IllegalStateException("The tree is not empty");
        }
        root = new Node<>(e, null, null, null);
        size = 1;
        return root;
    }

    public void insert(E e) {
        root = addRecursive(root, e);
    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        if (p == null){
            size++;
            return new Node<>(e, null, null, null);
        }
        Comparable<? super E> cmp = (Comparable<? super E>) e;
        if (cmp.compareTo(p.getElement()) < 0) {
            Node<E> child = addRecursive(p.getLeft(), e);
            p.setLeft(child);
            child.setParent(p);
        }else{
            Node<E> child = addRecursive(p.getRight(), e);
            p.setRight(child);
            child.setParent(p);
        }
        return p;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null){
            throw new IllegalArgumentException("The left node is already in the tree");
        }
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null){
            throw new IllegalArgumentException("The right node is already in the tree");
        }
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E old = node.getElement();
        node.setElement(e);
        return old;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (node.getLeft() != null || node.getRight() != null) {
            throw new IllegalArgumentException("The node is already in the tree");
        }

        size += t1.size + t2.size;

        if (!t1.isEmpty()){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }

        if (!t2.isEmpty()){
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);

        if (childrenCount(node) == 2){
            throw new IllegalArgumentException("Cannot remove node with 2 children");
        }
        Node<E> child = (node.getLeft() != null ?  node.getLeft() : node.getRight());

        if (child != null){
            child.setParent(node.getParent());
        }

        if (node == root){
            root = child;
        }else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }

            size--;

            E removed = node.getElement();
            node.setElement(null);
            node.setLeft(null);
            node.setRight(null);
            node.setParent(node);

            return removed;
    }

    private int childrenCount(Node<E> node){
        int count = 0;
        if (node.getLeft() != null){
            count++;
        }

        if (node.getRight() != null){
            count++;
        }

        return count;
    }

    public String toString() {
        return inorder().toString();
    }

    public int height(){
        return height(root);
    }

    private int height(Node<E> node){
        heightCalls++;
        if (node == null){
            return -1;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    public void createLevelOrder(ArrayList<E> l) {
        root = createLevelOrderHelper(l, null, 0);
        size = computeSize(root);
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        if ( i >= l.size() || l.get(i) == null){
            return null;
        }
        Node<E> node = new Node<>(l.get(i), p, null, null);
        node.setLeft(createLevelOrderHelper(l, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(l, node, 2 * i + 2));
        return node;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, null, 0);
        size = computeSize(root);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        if (i >= arr.length || arr[i] == null){
            return null;
        }
        Node<E> node = new Node<>(arr[i], p, null, null);
        node.setLeft(createLevelOrderHelper(arr, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(arr, node, 2 * i + 2));
        return node;
    }

    private int computeSize(Node<E> n){
        if (n == null){
            return 0;
        }
        return 1 + computeSize(n.getLeft()) + computeSize(n.getRight());
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    public int diameter() {
        return diameterHelper(root).diameter;
    }

    private static class HD {
        int height;
        int diameter;
        HD(int height, int diameter) {
            this.height = height;
            this.diameter = diameter;
        }
    }

    private HD diameterHelper(Node<E> node) {
        if (node == null){
            return new HD(-1, 0);
        }

        HD leftHD = diameterHelper(node.getLeft());
        HD rightHD = diameterHelper(node.getRight());

        int height = 1 + Math.max(leftHD.height, rightHD.height);
        int pathThroughRoot = leftHD.height + rightHD.height + 2;
        int diameter = Math.max(Math.max(leftHD.diameter, rightHD.diameter), pathThroughRoot);

        return new HD(height, diameter);
    }

    public int countExternal(){
        return countExternal(root());
    }

    private int countExternal(Position<E> p){
        if (p == null){
            return 0;
        }

        if (left(p) == null && right(p) == null){
            return 1;
        }

        return countExternal(left(p)) + countExternal(right(p));
    }

    public int countLeftExternal(){
        return countLeftExternal(root());
    }

    private int countLeftExternal(Position<E> p){
        if (p == null){
            return 0;
        }

        Position<E> left = left(p);
        Position<E> right = right(p);

        int count = 0;

        if (left != null && left(left) == null && right(left) == null){
            count = 1;
        }

        return count + countLeftExternal(left) + countLeftExternal(right);
    }

    public int countDescendants(Position<E> p){
        Node<E> node = validate(p);
        return countDescendants(node);
    }

    private int countDescendants(Node<E> node){
        if (node == null){
            return 0;
        }

        int leftCount = countDescendants(node.getLeft());
        int rightCount = countDescendants(node.getRight());

        return leftCount + rightCount + (node.getLeft() != null ? 1 : 0) + (node.getRight() != null ? 1 : 0);
    }

    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }

}
