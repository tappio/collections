package com.test.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

import com.test.MyAbstractCollection;

public class MyTreeSet<E> extends MyAbstractCollection<E> implements Set<E> {

    private int size;
    private Entry<E> root;

    public List<E> levelOrderTraversal() {
        List<E> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<Entry<E>> entryQueue = new LinkedList<>();
        entryQueue.offer(root);
        while (!entryQueue.isEmpty()) {
            Entry<E> first = entryQueue.poll();
            result.add(first.value);
            if (first.left != null) {
                entryQueue.offer(first.left);
            }
            if (first.right != null) {
                entryQueue.offer(first.right);
            }
        }
        return result;
    }

    // Root (data) -> Left -> Right (DLR)
    public List<E> preOrderTraversal() {
        List<E> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        preOrder(root, result);
        return result;
    }

    // Left -> Root (data) -> Right (LDR)
    public List<E> inOrderTraversal() {
        List<E> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        inOrder(root, result);
        return result;
    }

    // Left -> Right -> Root (data) (LRD)
    public List<E> postOrderTraversal() {
        List<E> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        postOrder(root, result);
        return result;
    }

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
            root = new Entry<>(e, null);
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
            if (comparisonResult < 0) {
                base = base.left;
            } else if (comparisonResult > 0) {
                base = base.right;
            } else {
                base.value = e;
                return true;
            }
        } while (base != null);

        Entry<E> newEntry = new Entry<>(e, parent);
        if (comparisonResult < 0) {
            parent.left = newEntry;
        } else {
            parent.right = newEntry;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            return false;
        }

        Entry<E> entry = searchForEntry(o);
        return deleteEntry(entry);
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
            boolean removed = remove(o);
            if (removed) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }

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
            if (comparisonResult < 0) {
                current = current.left;
            } else if (comparisonResult > 0) {
                current = current.right;
            } else {
                return current;
            }
        } while (current != null);

        return null;
    }

    private boolean deleteEntry(Entry entry) {
        if (entry == null) {
            return false;
        }

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
            if (parent.left == entry) {
                parent.left = null;
            } else {
                parent.right = null;
            }
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
            if (parent.left == entry) {
                parent.left = right;
            } else {
                parent.right = right;
            }
            entry.parent = null;
            entry.right = null;
            right.parent = parent;
            size--;
            return true;
        } else if (right == null) {
            if (parent.left == entry) {
                parent.left = left;
            } else {
                parent.right = left;
            }
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

    @SuppressWarnings("unchecked")
    private static void preOrder(Entry entry, List result) {
        if (entry == null) {
            return;
        }
        result.add(entry.value);
        preOrder(entry.left, result);
        preOrder(entry.right, result);
    }

    @SuppressWarnings("unchecked")
    private static void inOrder(Entry entry, List result) {
        if (entry == null) {
            return;
        }
        inOrder(entry.left, result);
        result.add(entry.value);
        inOrder(entry.right, result);
    }

    @SuppressWarnings("unchecked")
    private static void postOrder(Entry entry, List result) {
        if (entry == null) {
            return;
        }
        postOrder(entry.left, result);
        postOrder(entry.right, result);
        result.add(entry.value);
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

    private static <E> Entry<E> successor(Entry<E> entry) {
        if (entry == null) {
            return null;
        } else if (entry.right != null) {
            Entry<E> current = entry.right;
            while (current.left != null) {
                current = current.left;
            }
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

    private static class Entry<E> {

        Entry<E> left;
        Entry<E> parent;
        Entry<E> right;
        E value;

        public Entry(E value, Entry<E> parent) {
            this.value = value;
            this.parent = parent;
        }

    }

    private class MyTreeSetIterator implements Iterator<E> {

        Entry<E> lastReturned;
        Entry<E> next;

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
            if (entry == null) {
                throw new NoSuchElementException();
            }
            next = successor(entry);
            lastReturned = entry;
            return entry.value;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            if (lastReturned.left != null && lastReturned.right != null) {
                next = lastReturned;
            }
            deleteEntry(lastReturned);
            lastReturned = null;
        }

    }

}
