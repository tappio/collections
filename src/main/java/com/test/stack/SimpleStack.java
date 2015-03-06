package com.test.stack;

import java.util.Collection;

public interface SimpleStack<E> extends Collection<E> {

    void push(E e);
    E pop();
    boolean offer(E e);
    boolean poll();
    E element();
    E peek();

}