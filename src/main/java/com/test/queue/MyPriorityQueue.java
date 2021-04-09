package com.test.queue;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import com.test.MyAbstractCollection;

public class MyPriorityQueue<E> extends MyAbstractCollection<E> implements Queue<E> {

    private final Comparator<? super E> comparator;
    private int size;
    private Node<E> first;
    private Node<E> last;

    public MyPriorityQueue(Comparator<? super E> comparator) {
        if (comparator == null) {
            throw new NullPointerException();
        }
        this.comparator = comparator;
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
        return new MyPriorityQueueIterator();
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
        if (e == null) {
            throw new NullPointerException();
        }

        if (size == 0) {
            linkLast(e);
            return true;
        }

        for (Node<E> current = last; current != null; current = current.previous) {
            E item = current.item;
            if (comparator.compare(e, item) <= 0) {
                linkAfter(current, e);
                return true;
            }
        }

        linkFirst(e);
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
            boolean added = add(e);
            if (added) {
                modified = true;
            }
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
            }
        }

        this.first = first;
        this.last = last;
        this.size = size;
        return true;
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
        return add(e);
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

    private Node<E> nodeOf(Object o) {
        if (o != null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    return node;
                }
            }
        }
        return null;
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

    private void linkAfter(Node<E> after, E item) {
        Node<E> before = after.next;
        if (before == null) {
            linkLast(item);
            return;
        }
        Node<E> node = new Node<>(after, item, before);
        after.next = node;
        before.previous = node;
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

    private void linkFirst(E e) {
        Node<E> f = first;
        Node<E> node = new Node<>(null, e, f);
        first = node;
        if (f == null) {
            last = node;
        } else {
            f.previous = node;
        }
        size++;
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

    private class MyPriorityQueueIterator implements Iterator<E> {

        Node<E> current;
        Node lastReturned;

        MyPriorityQueueIterator() {
            current = first;
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

            MyPriorityQueue.this.unlink(lastReturned);
            lastReturned = null;
        }

    }

}
