package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {}

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current = tail.getNext();
        for (int k = 0; k < i; k++) {
            current = current.getNext();
        }
        return current.getData();
    }

    @Override
    public void add(int i, E e) {
        if  (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }

        if (i == 0){
            addFirst(e);
        }
        else if (i == size){
            addLast(e);
        }
        else {
            Node<E> prev = tail.getNext();
            for (int k = 0; k < size; k++) {
                prev = prev.getNext();
            }
            prev.setNext(new Node<>(e, prev.getNext()));
            size++;
        }
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        if  (i == 0){
            return removeFirst();
        }

        if  (i == size - 1){
            return removeLast();
        }

        Node<E> prev = tail.getNext();
        for (int k = 0; k < size; k++) {
            prev = prev.getNext();
        }

        E value = prev.getNext().getData();
        prev.setNext(prev.getNext().getNext());
        size--;
        return value;
    }

    public void rotate() {
        if (!isEmpty()) {
            tail = tail.getNext();
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node<E> head = tail.getNext();
        E value = head.getData();

        if (tail == head) {
            tail = null;
        }else{
            tail.setNext(head.getNext());
        }
        size--;
        return value;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }

        if  (size == 1) {
            return removeFirst();
        }

        Node<E> prev = tail.getNext();
        while (prev.getNext() != tail) {
            prev = prev.getNext();
        }

        E value = tail.getData();
        prev.setNext(tail.getNext());
        tail = prev;
        size--;
        return value;
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode;

        if (isEmpty()){
            newNode = new Node<>(e, null);
            newNode.setNext(newNode);
            tail = newNode;
        }else{
            newNode = new Node<>(e, tail.getNext());
            tail.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        addFirst(e);
        tail = tail.getNext();
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}
