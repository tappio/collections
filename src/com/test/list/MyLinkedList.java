package com.test.list;

import java.util.*;

public class MyLinkedList<E> implements List<E>, Deque<E> {

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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerLast(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeFirst() {
        Node<E> firstNode = first;
        if (firstNode == null) return null;
        return unlink(firstNode);
    }

    @Override
    public E removeLast() {
        Node<E> lastNode = last;
        if (lastNode == null) return null;
        return unlink(lastNode);
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.item;
    }

    @Override
    public E getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.item;
    }

    @Override
    public E peekFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E peekLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E element() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E peek() {
        throw new UnsupportedOperationException();
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
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        final Object[] result = new Object[size];
        int index = 0;
        for (Node<E> node = first; node != null; node = node.next) {
            result[index] = node.item;
            index++;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> node = nodeOf(o);
        if (node == null) {
            return false;
        }
        unlink(node);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
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
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    private Node<E> nodeOf(Object o) {
        if (o == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.item == null) return node;
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item)) return node;
            }
        }
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Node<E> node = first; node != null; node = node.next) {
            builder.append(node.item);
            if (node.next != null) builder.append(", ");
        }
        builder.append("}");
        return builder.toString();
    }
}
