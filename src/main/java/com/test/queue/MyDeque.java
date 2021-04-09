package com.test.queue;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.test.MyAbstractCollection;

public class MyDeque<E> extends MyAbstractCollection<E> implements Deque<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;

    @Override
    public void addFirst(E e) {
        linkFirst(e);
    }

    @Override
    public void addLast(E e) {
        linkLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        linkFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final E item = first.item;
        unlink(first);
        return item;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final E item = last.item;
        unlink(last);
        return item;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        final E item = first.item;
        unlink(first);
        return item;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        final E item = last.item;
        unlink(last);
        return item;
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.item;
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return last.item;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return first.item;
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return last.item;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Node<E> node = firstNodeOf(o);
        if (node == null) {
            return false;
        }
        unlink(node);
        return true;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<E> node = lastNodeOf(o);
        if (node == null) {
            return false;
        }
        unlink(node);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        return firstNodeOf(o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyDequeIterator<E>();
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
        addLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
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
            Node<E> node = firstNodeOf(o);
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

    private void linkLast(E e) {
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

    private Node<E> firstNodeOf(Object o) {
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

    private Node<E> lastNodeOf(Object o) {
        if (o == null) {
            for (Node<E> node = last; node != null; node = node.previous) {
                if (node.item == null) {
                    return node;
                }
            }
        } else {
            for (Node<E> node = last; node != null; node = node.previous) {
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

    private class MyDequeIterator<E> implements Iterator<E> {

        Node<E> current;
        Node lastReturned;

        @SuppressWarnings("unchecked")
        MyDequeIterator() {
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

            MyDeque.this.unlink(lastReturned);
            lastReturned = null;
        }

    }

}
