package com.test.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import com.test.MyAbstractCollection;

public class MyHashSet<E> extends MyAbstractCollection<E> implements Set<E> {

    private static class Node<E> {
        E value;
        Node<E> next;
        int hashCode;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
            if (value != null)
                this.hashCode = value.hashCode();
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size;
    private Node[] table;
    private int capacity;
    private int threshold;

    public MyHashSet(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        table = new Node[capacity];
        this.threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
    }

    public MyHashSet() {
        this(DEFAULT_CAPACITY);
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
        return new MyHashSetIterator<E>();
    }

    @Override
    public Object[] toArray() {
        final Object[] result = new Object[size];
        int index = 0;
        for (Node node : table) {
            for (Node start = node; start != null; start = start.next) {
                result[index] = start.value;
                index++;
            }
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
        for (Node node : table) {
            for (Node start = node; start != null; start = start.next) {
                a[index] = (T) start.value;
                index++;
            }
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        if (contains(e))
            return false;

        if (size == threshold)
            resize();

        int index = getIndex(e);
        if (table[index] == null) {
            table[index] = new Node<E>(e, null);
        } else {
            @SuppressWarnings("unchecked")
            Node<E> head = table[index];
            Node<E> newNode = new Node<>(e, head);
            table[index] = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        int index = getIndex(o);
        Node head = table[index];
        if (head.next == null) {
            table[index] = null;
            size--;
            return true;
        }

        if (o == null)
            return removeNull();
        else
            return removeElement(o, index);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty())
            return false;

        for (E e : c) {
            add(e);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null || c.isEmpty())
            return false;

        final Object[] result = new Object[size];
        int index = 0;
        for (Object o : c) {
            if (contains(o)) {
                result[index] = o;
                index++;
            }
        }

        clear();
        for (int i = 0; i < index; i++) {
            add((E) result[i]);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.isEmpty())
            return false;

        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        for (Node node : table) {
            for (Node current = node; current != null; ) {
                Node<E> next = current.next;
                current.value = null;
                current.next = null;
                current = next;
            }
        }
        table = new Node[capacity];
        size = 0;
    }

    private int getIndex(Object o) {
        if (o == null)
            return 0;
        int hashCode = o.hashCode();
        return hashCode % capacity;
    }

    @SuppressWarnings("unchecked")
    private Node<E> nodeOf(Object o) {
        int index = getIndex(o);
        if (table[index] == null) {
            return null;
        } else {
            Node head = table[index];
            if (o == null) {
                for (Node start = head; start != null; start = start.next) {
                    if (start.value == null)
                        return start;
                }
            } else {
                int hashCode = o.hashCode();
                for (Node start = head; start != null; start = start.next) {
                    if (start.hashCode == hashCode && o.equals(start.value))
                        return start;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity = capacity * 2;
        threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
        Node[] newTable = new Node[capacity];

        for (Node node : table) {
            for (Node current = node; current != null; current = current.next) {
                E o = (E) current.value;
                int index = getIndex(o);
                if (newTable[index] == null) {
                    newTable[index] = new Node<>(o, null);
                } else {
                    Node newHead = newTable[index];
                    Node<E> newNode = new Node<E>(o, newHead);
                    newTable[index] = newNode;
                }
            }
        }
        table = newTable;
    }

    private boolean removeElement(Object o, int index) {
        int hashCode = o.hashCode();
        Node previous = null;
        for (Node current = table[index]; current != null; current = current.next) {
            if (hashCode == current.hashCode && o.equals(current.value)) {
                if (previous == null) {
                    table[index] = current.next;
                } else if (current.next == null) {
                    previous.next = null;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            } else {
                previous = current;
            }
        }
        return false;
    }

    private boolean removeNull() {
        Node previous = null;
        for (Node current = table[0]; current != null; current = current.next) {
            if (current.value == null) {
                if (previous == null) {
                    table[0] = current.next;
                } else if (current.next == null) {
                    previous.next = null;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            } else {
                previous = current;
            }
        }
        return false;
    }

    private class MyHashSetIterator<E> implements Iterator<E> {

        Node<E> current;
        Node<E> lastReturned;
        int position;

        @SuppressWarnings("unchecked")
        MyHashSetIterator() {
            if (size != 0) {
                current = table[position];
                while (current == null) {
                    position++;
                    if (position < capacity)
                        current = table[position];
                    else
                        break;
                }
            }
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
            while (current == null) {
                position++;
                if (position < capacity)
                    current = table[position];
                else
                    break;
            }
            return lastReturned.value;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }

            MyHashSet.this.remove(lastReturned.value);
            lastReturned = null;
        }
    }

}
