package project20280.list;

import project20280.interfaces.List;
import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private E element;            // reference to the element stored at this node
        private Node<E> next;         // reference to the subsequent node in the list

        public Node(E e, Node<E> n) {
            this.element = e;
            this.next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            this.next = n;
        }
    }
    //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    @Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        if (position < 0 || position >= size) {
            return null;
        }
        Node<E> node = head;
        for (int i = 0; i < position; i++) {
            node = node.getNext();
        }
        return node.getElement();
    }

    @Override
    public void add(int position, E e) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }

        if (position == 0){
            addFirst(e);
            return;
        }

        Node<E> node = head;
        for (int i = 0; i < position - 1; i++) {
            node = node.getNext();
        }

        Node<E> latest = new Node<>(e, node.getNext());
        node.setNext(latest);
        size++;
    }

    public void reverse() {
        Node<E> prev = null;
        Node<E> curr = head;

        while (curr != null) {
            Node<E> next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }

        head = prev;
    }

    @Override
    public void addFirst(E e) {
        head = new Node<>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        if (isEmpty()) {
            addFirst(e);
            return;
        }
        Node<E> node = head;
        while (node.getNext() != null) {
            node = node.getNext();
        }
        node.setNext(new Node<>(e, null));
        size++;
    }

    @Override
    public E remove(int position) {
        if (position < 0 || position > size) {
            return null;
        }

        if (position == 0){
            return removeFirst();
        }

        Node<E> node = head;
        for (int i = 0; i < position - 1; i++) {
            node = node.getNext();
        }

        Node<E> removed =  node.getNext();
        node.setNext(removed.getNext());
        size--;
        return removed.getElement();
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        E value = head.getElement();
        head = head.getNext();
        size--;
        return value;
    }

    @Override
    public E removeLast() {
        if  (isEmpty()) {
            return null;
        }

        if  (size == 1) {
            return removeFirst();
        }

        Node<E> node = head;
        while (node.getNext().getNext() != null) {
            node = node.getNext();
        }

        E value = node.getNext().getElement();
        node.setNext(null);
        size--;
        return value;
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);

        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

        ll.reverse();
        System.out.println("Reversed : " + ll);

    }
}
