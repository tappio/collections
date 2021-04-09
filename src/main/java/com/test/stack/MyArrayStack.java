package com.test.stack;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.test.MyAbstractCollection;

public class MyArrayStack<E> extends MyAbstractCollection<E> implements SimpleStack<E> {

    private static final int DEFAULT_MAX_SIZE = 10;
    private static final Object[] EMPTY_ARRAY = new Object[0];

    private int size;
    private final Object[] data;
    private final int maxSize;

    public MyArrayStack(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Size cannot be less than 1.");
        }
        this.maxSize = maxSize;
        data = new Object[maxSize];
    }

    public MyArrayStack() {
        this(DEFAULT_MAX_SIZE);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) > 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayStackIterator<>();
    }

    @Override
    public Object[] toArray() {
        if (size == 0)
            return EMPTY_ARRAY;
        final Object[] result = new Object[size];
        for (int i = size - 1, j = 0; i >= 0; i--, j++) {
            result[j] = data[i];
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray();
        }
        for (int i = size - 1, j = 0; i >= 0; i--, j++) {
            a[j] = (T) data[i];
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.size() == 0)
            return false;
        if (spaceAvailable(c.size())) {
            for (E e : c)
                add(e);
            return true;
        }
        return false;
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
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public void push(E e) {
        checkCapacityAndThrowException();
        addLast(e);
    }

    @Override
    public E pop() {
        checkIsEmptyAndThrowException();
        return removeLast();
    }

    @Override
    public boolean offer(E e) {
        if (spaceAvailable()) {
            addLast(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean poll() {
        if (isEmpty())
            return false;
        removeLast();
        return true;
    }

    @Override
    public E element() {
        checkIsEmptyAndThrowException();
        return removeLast();
    }

    @Override
    public E peek() {
        E e = null;
        if (!isEmpty())
            e = removeLast();
        return e;
    }

    private int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (data[i] == null) return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(data[i])) return i;
        }
        return -1;
    }

    private void addLast(E e) {
        data[size] = e;
        size++;
    }

    @SuppressWarnings("unchecked")
    private E removeLast() {
        size--;
        E e = (E) data[size];
        data[size] = null;
        return e;
    }

    private boolean spaceAvailable(int additional) {
        return maxSize - size >= additional;
    }

    private boolean spaceAvailable() {
        return spaceAvailable(1);
    }

    private void checkCapacityAndThrowException() {
        if (!spaceAvailable()) {
            throw new IllegalStateException("No space available. Max size: " + maxSize);
        }
    }

    private void checkIsEmptyAndThrowException() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private class MyArrayStackIterator<E> implements Iterator<E> {

        int cursor = size - 1;

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if (cursor < 0) {
                throw new NoSuchElementException();
            }
            E e = (E) data[cursor];
            cursor--;
            return e;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
