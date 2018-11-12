package com.demo.javabase.collection;


public class LinkedNode<E> {

    transient int size = 0;
    transient LinkedNode.Node<E> first;
    transient LinkedNode.Node<E> last;
    protected transient int modCount = 0;


    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    void linkLast(E e) {
        final LinkedNode.Node<E> l = last;
        final LinkedNode.Node<E> newNode = new LinkedNode.Node<>(l, e, null);
        last = newNode;
        if (l == null){
            first = newNode;
        }else{
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }
    Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++){
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--){
                x = x.prev;
            }
            return x;
        }
    }
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }


    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

    private static class Node<E> {
        E item;
        LinkedNode.Node<E> next;
        LinkedNode.Node<E> prev;

        Node(LinkedNode.Node<E> prev, E element, LinkedNode.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }


    public static void main(String[] args) {
        LinkedNode<String> linkedNode = new LinkedNode();
        linkedNode.add("1");

        String str = linkedNode.get(0);
        System.out.println(str);

        linkedNode.remove(0);
        System.out.println(linkedNode.get(0));
    }
}
