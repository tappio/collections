package com.test.tree;

import com.test.MyAbstractCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> extends MyAbstractCollection<E> implements Set<E> {

    private static class Entry<E> {
        E value;
        Entry<E> left;
        Entry<E> right;
        Entry<E> parent;

        public Entry(E value, Entry<E> parent) {
            this.value = value;
            this.parent = parent;
        }
    }

    private int size;
    private Entry<E> root;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        return searchForEntry(o) != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyTreeSetIterator<E>();
    }

    @Override
    public Object[] toArray() {
        final Object[] result = new Object[size];
        int i = 0;
        for (Object o : this) {
            result[i] = o;
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
        for (Object o : this) {
            a[i] = (T) o;
            i++;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }

        if (root == null) {
            root = new Entry<E>(e, null);
            size++;
            return true;
        }

        int comparisonResult;
        Entry<E> parent;
        Entry<E> base = root;
        @SuppressWarnings("unchecked")
        Comparable<? super E> k = (Comparable<? super E>) e;
        do {
            parent = base;
            comparisonResult = k.compareTo(base.value);
            if (comparisonResult < 0)
                base = base.left;
            else if (comparisonResult > 0)
                base = base.right;
            else {
                base.value = e;
                return true;
            }
        } while (base != null);


        Entry<E> newEntry = new Entry<>(e, parent);
        if (comparisonResult < 0)
            parent.left = newEntry;
        else
            parent.right = newEntry;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
            throw new NullPointerException();
        if (isEmpty())
            return false;

        Entry<E> entry = searchForEntry(o);
        if (entry != null) {
            if (size == 1)
                clear();
            else
                deleteEntry(entry);

            size--;
            return true;
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty())
            return false;

        boolean modified = false;
        for (E e : c) {
            boolean added = add(e);
            if (added) modified = true;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.isEmpty())
            return false;

        boolean modified = false;
        for (Object o : c) {
            boolean removed = remove(o);
            if (removed) modified = true;
        }
        return modified;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private Entry<E> searchForEntry(Object o) {
        int comparisonResult;
        Entry<E> current = root;
        @SuppressWarnings("unchecked")
        Comparable<? super E> k = (Comparable<? super E>) o;
        do {
            comparisonResult = k.compareTo(current.value);
            if (comparisonResult < 0)
                current = current.left;
            else if (comparisonResult > 0)
                current = current.right;
            else {
                return current;
            }
        } while (current != null);

        return null;
    }

    private boolean deleteEntry(Entry entry) {
        Entry parent = entry.parent;
        Entry left = entry.left;
        Entry right = entry.right;

        // root case
        if (parent == null) {
            return false;
        }

        // no children case
        if (left == null && right == null) {
            if (parent.left == entry)
                parent.left = null;
            else
                parent.right = null;
            return true;
        }

        // one child case
        if (left == null) {
            if (parent.left == entry)
                parent.left = right;
            else
                parent.right = right;
            return true;
        } else if (right == null) {
            if (parent.left == entry)
                parent.left = left;
            else
                parent.right = left;
            return true;
        }

        // 2 children case here
        return false;
    }

    private static class MyTreeSetIterator<E> implements Iterator<E> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }

}