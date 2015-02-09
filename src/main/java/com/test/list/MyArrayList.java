package com.test.list;

import com.test.MyAbstractCollection;

import java.util.*;

public class MyArrayList<E> extends MyAbstractCollection<E> implements List<E> {

    private static final Object[] EMPTY_ARRAY = new Object[0];
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private Object[] data;

    public MyArrayList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.data = new Object[capacity];
    }

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

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
        return new MyArrayListIterator<E>();
    }

    @Override
    public Object[] toArray() {
        if (size == 0) {
            return EMPTY_ARRAY;
        }
        final Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) toArray();
        }
        System.arraycopy(data, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E e) {
        ensureCapacity();
        data[size] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index < 0) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.size() == 0)
            return false;
        ensureCapacity(c.size());
        for (E e : c)
            add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null || c.size() == 0)
            return false;

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacity(numNew);

        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(data, index, data, index + numNew, numMoved);
        }

        System.arraycopy(a, 0, data, index, numNew);
        size += numNew;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.size() == 0)
            return false;

        for (Object o : c) {
            int index = indexOf(o);
            if (index >= 0)
                remove(index);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null || c.size() == 0)
            return false;

        final Object[] newData = new Object[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            Object current = data[i];
            if (c.contains(current)) {
                newData[index] = current;
                index++;
            }
        }
        if (index == 0)
            return false;

        data = newData;
        size = index;
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        checkRange(index);
        return (E) data[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        checkRange(index);
        E oldElement = (E) data[index];
        data[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        checkRange(index);
        ensureCapacity(1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        checkRange(index);
        E oldElement = (E) data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        size--;
        data[size] = null;
        return oldElement;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (data[i] == null) return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(data[i])) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (data[i] == null) return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(data[i])) return i;
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
        System.arraycopy(data, fromIndex, copyData, 0, newSize);
        return (List<E>) Arrays.asList(copyData);
    }

    private void ensureCapacity(int additionalSpace) {
        int freeSpace = data.length - size;
        if (size == data.length || freeSpace <= additionalSpace) {
            int capacity = data.length;
            capacity = capacity + capacity / 2 + 1;
            Object[] newData = new Object[capacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    private void ensureCapacity() {
        ensureCapacity(0);
    }

    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class MyArrayListIterator<E> implements Iterator<E> {

        int cursor;
        int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }

            lastReturned = cursor;
            cursor++;
            return (E) data[lastReturned];
        }

        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            MyArrayList.this.remove(lastReturned);
            cursor = lastReturned;
            lastReturned = -1;
        }
    }

}