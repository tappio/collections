package com.test.list;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.test.MyAbstractCollection;

public class MyLinkedList<E> extends MyAbstractCollection<E> implements List<E> {

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

    @Override
    public int size() {
        return size;
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
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.size() == 0)
            return false;
        for (E e : c)
            linkLast(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null || c.size() == 0)
            return false;

        checkRange(index);
        for (E e : c) {
            add(index, e);
            index++;
        }
        return true;
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

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null || c.size() == 0)
            return false;

        Node<E> first = null;
        Node<E> last = null;
        int size = 0;
        boolean result = false;

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
                result = true;
            }
        }

        this.first = first;
        this.last = last;
        this.size = size;
        return result;
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
        Node<E> node = nodeOf(index);
        return node.item;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = nodeOf(index);
        E old = node.item;
        node.item = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0) {
            linkFirst(element);
            return;
        }

        if (index == size || index == size - 1) {
            linkLast(element);
            return;
        }

        Node<E> current = nodeOf(index);
        Node<E> next = current.next;
        Node<E> newNode = new Node<>(current, element, next);
        current.next = newNode;
        next.previous = newNode;
        size++;
    }

    @Override
    public E remove(int index) {
        Node<E> current = nodeOf(index);
        E item = current.item;
        unlink(current);
        return item;
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

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        if (o == null) {
            for (Node<E> node = last; node != null; node = node.previous) {
                if (node.item == null)
                    return index;
                index--;
            }
        } else {
            for (Node<E> node = last; node != null; node = node.previous) {
                if (o.equals(node.item))
                    return index;
                index--;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        RuntimeException e = new IndexOutOfBoundsException();
        if (fromIndex < 0) throw e;
        if (toIndex > size) throw e;
        if (fromIndex > toIndex) throw e;

        int newSize = toIndex - fromIndex;
        final Object[] copyData = new Object[newSize];
        Node<E> node = nodeOf(fromIndex);
        for (int i = 0; i < newSize; i++) {
            copyData[i] = node.item;
            node = node.next;
        }
        return (List<E>) Arrays.asList(copyData);
    }

    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
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

    private Node<E> nodeOf(int index) {
        checkRange(index);
        int i = 0;
        for (Node<E> current = first; current != null; current = current.next) {
            if (index == i)
                return current;
            i++;
        }
        return null;
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
            lastReturned = null;
        }
    }

}
