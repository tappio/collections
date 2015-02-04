package com.test.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyQueue<E> implements Queue<E> {

    private static class Node<E> {
        Node<E> previous;
        E item;
        Node<E> next;

        private Node(Node<E> previous, E item, Node<E> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    private int size;
    private Node<E> first;
    private Node<E> last;
    private int maxSize = -1;

    public MyQueue() {}

    public MyQueue(int maxSize) {
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
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.item == null)
                    return true;
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        checkSize();
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean offer(E e) {
        if (isSpaceAvailable()) {
            linkLast(e);
            return true;
        }
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return first.item;
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return first.item;
    }

    private void checkSize() {
        if (maxSize > 0 && size == maxSize) {
            throw new IllegalStateException("No space available. Max size: " + maxSize);
        }
    }

    private boolean isSpaceAvailable() {
        if (maxSize > 0 && size == maxSize) {
            return false;
        }
        return true;
    }

    private void linkFirst(E item) {
        Node<E> f = first;
        Node<E> node = new Node<>(null, item, f);
        first = node;
        if (f == null) {
            last = node;
        } else {
            f.previous = node;
        }
        size++;
    }

    private void linkLast(E item) {
        Node<E> l = last;
        Node<E> node = new Node<>(l, item, null);
        last = node;
        if (l == null) {
            first = node;
        } else {
            l.next = node;
        }
        size++;
    }

}