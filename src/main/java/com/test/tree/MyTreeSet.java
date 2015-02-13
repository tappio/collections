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
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        final Object[] result = new Object[size];
        int i = 0;
        Iterator iterator = iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            result[i] = next;
            i++;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
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
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

}