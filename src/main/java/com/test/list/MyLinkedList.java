package com.test.list;

import java.util.*;

public class MyLinkedList<E> implements List<E> {

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
        return new MyLinkedListIterator<E>();
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray();
        }

        int index = 0;
        for (Node<E> node = first; node != null; node = node.next) {
            a[index] = (T) node.item;
            index++;
        }
        return a;
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
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.size() == 0)
            return false;
        for (E e : c)
            linkLast(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.size() == 0)
            return false;

        for (Object o : c) {
            Node<E> node = nodeOf(o);
            if (node != null) {
                unlink(node);
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
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
                if (node.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    private Node<E> nodeOf(Object o) {
        if (o == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.item == null)
                    return node;
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.item))
                    return node;
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

    private class MyLinkedListIterator<E> implements Iterator<E> {

        Node<E> current;
        Node lastReturned;

        @SuppressWarnings("unchecked")
        MyLinkedListIterator() {
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

            MyLinkedList.this.unlink(lastReturned);
            current = lastReturned;
            lastReturned = null;
        }
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
