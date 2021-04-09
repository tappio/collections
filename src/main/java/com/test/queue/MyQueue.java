package com.test.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import com.test.MyAbstractCollection;

public class MyQueue<E> extends MyAbstractCollection<E> implements Queue<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;
    private int maxSize = -1;

    public MyQueue() {
    }

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
    public boolean contains(Object o) {
        return nodeOf(o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyQueueIterator<E>();
    }

    @Override
    public Object[] toArray() {
        final Object[] result = new Object[size];
        int index = 0;
        for (Node<E> current = first; current != null; current = current.next) {
            result[index] = current.item;
            index++;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray();
        }

        int index = 0;
        for (Node<E> current = first; current != null; current = current.next) {
            a[index] = (T) current.item;
            index++;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        checkSize();
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }
        Node<E> node = nodeOf(o);
        if (node != null) {
            unlink(node);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }

        boolean modified = false;
        for (E e : c) {
            modified = offer(e);
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }

        boolean modified = false;
        for (Object o : c) {
            Node<E> node = nodeOf(o);
            if (node != null) {
                unlink(node);
                modified = true;
            }
        }
        return modified;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }

        Node<E> first = null;
        Node<E> last = null;
        int size = 0;
        boolean modified = false;

        for (Object o : c) {
            if (contains(o)) {
                E e = (E) o;
                Node<E> l = last;
                Node<E> node = new Node<>(l, e, null);
                last = node;
                if (l == null) {
                    first = node;
                } else {
                    l.next = node;
                }
                size++;
                modified = true;
            }
        }

        this.first = first;
        this.last = last;
        this.size = size;
        return modified;
    }

    @Override
    public void clear() {
        for (Node<E> current = first; current != null; ) {
            Node<E> next = current.next;
            current.item = null;
            current.next = null;
            current.previous = null;
            current = next;
        }
        first = null;
        last = null;
        size = 0;
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
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return removeFirst();
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        return removeFirst();
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
        return maxSize <= 0 || size != maxSize;
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

    private E unlink(Node<E> node) {
        Node<E> previous = node.previous;
        Node<E> next = node.next;
        E element = node.item;

        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
        }

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }

        node.item = null;
        size--;
        return element;
    }

    private E removeFirst() {
        E item = first.item;
        unlink(first);
        return item;
    }

    private Node<E> nodeOf(Object o) {
        if (o == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    return node;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    return node;
                }
            }
        }
        return null;
    }

    private static class Node<E> {

        E item;
        Node<E> next;
        Node<E> previous;

        private Node(Node<E> previous, E item, Node<E> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }

    }

    private class MyQueueIterator<E> implements Iterator<E> {

        Node<E> current;
        Node lastReturned;

        @SuppressWarnings("unchecked")
        MyQueueIterator() {
            current = (Node<E>) first;
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

        @SuppressWarnings("unchecked")
        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }

            MyQueue.this.unlink(lastReturned);
            lastReturned = null;
        }

    }

}
