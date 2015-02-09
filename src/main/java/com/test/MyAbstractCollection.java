package com.test;

import java.util.Collection;
import java.util.Iterator;

public abstract class MyAbstractCollection<E> implements Collection<E> {

    @Override
    public abstract int size();

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public abstract boolean contains(Object o);

    @Override
    public abstract Iterator<E> iterator();

    @Override
    public abstract Object[] toArray();

    @Override
    public abstract <T> T[] toArray(T[] a);

    @Override
    public abstract boolean add(E e);

    @Override
    public abstract boolean remove(Object o);

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
    public abstract boolean addAll(Collection<? extends E> c);

    @Override
    public abstract boolean removeAll(Collection<?> c);

    @Override
    public abstract boolean retainAll(Collection<?> c);

    @Override
    public abstract void clear();

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Collection))
            return false;

        Iterator<E> e1 = iterator();
        Iterator e2 = ((Collection) o).iterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append('[');
        while (it.hasNext()) {
            builder.append(it.next());
            if (it.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append(']');
        return builder.toString();
    }

}