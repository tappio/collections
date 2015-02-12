package com.test.queue;

import com.test.MyAbstractCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyStack<E> extends MyAbstractCollection<E> {

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private int size;
    private Node<E> head;
    private int maxSize = -1;

    public MyStack() {}

    public MyStack(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException();
        }
        this.maxSize = maxSize;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        return nodeOf(o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyStackIterator<E>();
    }

    @Override
    public Object[] toArray() {
        final Object[] result = new Object[size];
        int i = 0;
        for (Node node = head; node != null; node = node.next) {
            result[i] = node.item;
            i++;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray();
        }

        int i = 0;
        for (Node node = head; node != null; node = node.next) {
            a[i] = (T) node.item;
            i++;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty())
            return false;

        if (c.size() > (maxSize - size)) {
            throw new IllegalStateException("No space available. Max size: " + maxSize);
        }

        for (E e : c) {
            linkFirst(e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (Node node = head; node != null; ) {
            Node next = node.next;
            node.item = null;
            node.next = null;
            node = next;
        }
        head = null;
        size = 0;
    }

    public void push(E e) {
        checkSize();
        linkFirst(e);
    }

    public E pop() {
        if (isEmpty())
            throw new NoSuchElementException();
        return unlinkFirst();
    }

    public boolean offer(E e) {
        if (isSpaceAvailable()) {
            linkFirst(e);
            return true;
        }
        return false;
    }

    public boolean poll() {
        if (isEmpty())
            return false;
        unlinkFirst();
        return true;
    }

    public E element() {
        if (isEmpty())
            throw new NoSuchElementException();
        E item = head.item;
        unlinkFirst();
        return item;
    }

    public E peek() {
        if (isEmpty())
            return null;
        E item = head.item;
        unlinkFirst();
        return item;
    }

    private void checkSize() {
        if (!isSpaceAvailable()) {
            throw new IllegalStateException("No space available. Max size: " + maxSize);
        }
    }

    private boolean isSpaceAvailable() {
        if (maxSize > 0 && size == maxSize) {
            return false;
        }
        return true;
    }

    private void linkFirst(E e) {
        Node<E> first = head;
        head = new Node<E>(e, first);
        size++;
    }

    private E unlinkFirst() {
        Node<E> first = head;
        Node<E> next = first.next;
        E element = first.item;

        head.item = null;
        head = next;
        size--;
        return element;
    }

    private Node<E> nodeOf(Object o) {
        if (o == null) {
            for (Node<E> node = head; node != null; node = node.next) {
                if (node.item == null)
                    return node;
            }
        } else {
            for (Node<E> node = head; node != null; node = node.next) {
                if (o.equals(node.item))
                    return node;
            }
        }
        return null;
    }

    private class MyStackIterator<E> implements Iterator<E> {

        Node<E> current;
        Node lastReturned;

        @SuppressWarnings("unchecked")
        MyStackIterator() {
            current = (Node<E>) head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            lastReturned = current;
            current = current.next;
            return (E) lastReturned.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}