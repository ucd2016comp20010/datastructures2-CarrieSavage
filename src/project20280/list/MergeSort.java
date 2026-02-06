package project20280.list;

public class MergeSort {
    static class Node {
        int data;
        Node next;
        Node (int data) {
            this.data = data;
        }
    }

    static class LinkedList {
        Node head;

        public void add (int data) {
            if (head == null) {
                head = new Node(data);
                return;
            }
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = new Node(data);
        }

        public LinkedList sort(LinkedList other){
            Node l1 = this.head;
            Node l2 = other.head;

            Node temp = new Node(0);
            Node tail = temp;

            while (l1 != null && l2 != null) {
                if (l1.data <= l2.data) {
                    tail.next = l1;
                    l1 = l1.next;
                }else{
                    tail.next = l2;
                    l2 = l2.next;
                }
                tail = tail.next;
            }

            tail.next = (l1 != null) ? l1 : l2;

            LinkedList result = new LinkedList();
            result.head = temp.next;
            return result;
        }

        public void print() {
            Node cur = head;
            while (cur != null) {
                System.out.print(cur.data + " ");
                cur = cur.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        LinkedList l1 = new LinkedList();
        l1.add(2);
        l1.add(6);
        l1.add(20);
        l1.add(24);

        LinkedList l2 = new LinkedList();
        l2.add(1);
        l2.add(3);
        l2.add(5);
        l2.add(8);
        l2.add(12);
        l2.add(19);
        l2.add(25);

        LinkedList result = l1.sort(l2);

        result.print();
    }
}
