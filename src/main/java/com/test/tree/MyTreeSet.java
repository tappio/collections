package com.test.tree;

import com.test.MyAbstractCollection;

import java.util.*;

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
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        Entry minEntry = findMinEntry();
        return new MyTreeSetIterator(minEntry);
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
    // TODO: Improve. Adding time is very poor.
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
        return deleteEntry(entry);
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
        if (c == null || c.isEmpty())
            return false;

        boolean modified = false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            E e = iterator.next();
            if (!c.contains(e)) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
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
        if (entry == null)
            return false;

        Entry parent = entry.parent;
        Entry left = entry.left;
        Entry right = entry.right;

        // no children case
        if (left == null && right == null) {
            // only root has left
            if (parent == null) {
                clear();
                return true;
            }
            if (parent.left == entry)
                parent.left = null;
            else
                parent.right = null;
            entry.parent = null;
            size--;
            return true;
        }

        // remove root with one child
        if (parent == null && (left == null || right == null)) {
            if (left == null) {
                replaceRight(entry);
                return true;
            } else {
                replaceLeft(entry);
                return true;
            }
        }

        // one child case
        if (left == null) {
            if (parent.left == entry)
                parent.left = right;
            else
                parent.right = right;
            entry.parent = null;
            entry.right = null;
            right.parent = parent;
            size--;
            return true;
        } else if (right == null) {
            if (parent.left == entry)
                parent.left = left;
            else
                parent.right = left;
            entry.parent = null;
            entry.left = null;
            left.parent = parent;
            size--;
            return true;
        }

        // 2 children case
        replaceRight(entry);
        return true;
    }

    private void replaceRight(Entry entry) {
        Entry minEntry = findMinEntry(entry.right);
        Object minValue = minEntry.value;
        deleteEntry(minEntry);
        entry.value = minValue;
    }

    private void replaceLeft(Entry entry) {
        Entry maxEntry = findMaxEntry(entry.left);
        Object maxValue = maxEntry.value;
        deleteEntry(maxEntry);
        entry.value = maxValue;
    }

    private static Entry findMinEntry(Entry root) {
        Entry entry = root;
        if (entry != null) {
            while (entry.left != null) {
                entry = entry.left;
            }
        }
        return entry;
    }

    private Entry findMaxEntry(Entry root) {
        Entry entry = root;
        if (entry != null) {
            while (entry.right != null) {
                entry = entry.right;
            }
        }
        return entry;
    }

    private Entry findMinEntry() {
        return findMinEntry(root);
    }

    private Entry findMaxEntry() {
        return findMaxEntry(root);
    }

    private static <E> Entry<E> successor(Entry<E> entry) {
        if (entry == null)
            return null;
        else if (entry.right != null) {
            Entry<E> current = entry.right;
            while (current.left != null)
                current = current.left;
            return current;
        } else {
            Entry<E> parent = entry.parent;
            Entry<E> current = entry;
            while (parent != null && current == parent.right) {
                current = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    private class MyTreeSetIterator implements Iterator<E> {

        Entry<E> next;
        Entry<E> lastReturned;

        MyTreeSetIterator(Entry<E> first) {
            next = first;
            lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            Entry<E> entry = next;
            if (entry == null)
                throw new NoSuchElementException();
            next = successor(entry);
            lastReturned = entry;
            return entry.value;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            if (lastReturned.left != null && lastReturned.right != null)
                next = lastReturned;
            deleteEntry(lastReturned);
            lastReturned = null;
        }
    }

}