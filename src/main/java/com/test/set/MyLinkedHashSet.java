package com.test.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.test.MyAbstractCollection;
import com.test.list.MyLinkedList;

public class MyLinkedHashSet<E> extends MyAbstractCollection<E> implements Set<E> {

    private static final int DEFAULT_CAPACITY = 16;
    private final Set<E> set;
    private final List<E> list;

    public MyLinkedHashSet(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity cannot be less than 1.");
        }
        set = new MyHashSet<>(capacity);
        list = new MyLinkedList<>();
    }

    public MyLinkedHashSet() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new MyLinkedHashSetIterator<>();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        boolean added = set.add(e);
        if (added) {
            list.add(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        boolean removed = set.remove(o);
        if (removed) {
            list.remove(o);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }

        boolean changed = false;
        for (E e : c) {
            boolean added = set.add(e);
            if (added) {
                list.add(e);
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        boolean changed = false;
        for (Object o : c) {
            boolean removed = set.remove(o);
            if (removed) {
                list.remove(o);
                changed = true;
            }
        }
        return changed;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null || c.isEmpty())
            return false;

        Object[] result = new Object[set.size()];
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
    public void clear() {
        set.clear();
        list.clear();
    }

    private class MyLinkedHashSetIterator<E> implements Iterator<E> {

        final Iterator<E> iterator;
        E current;

        @SuppressWarnings("unchecked")
        MyLinkedHashSetIterator() {
            iterator = (Iterator<E>) list.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            E next = iterator.next();
            current = next;
            return next;
        }

        @Override
        public void remove() {
            set.remove(current);
            current = null;
            iterator.remove();
        }
    }

}
