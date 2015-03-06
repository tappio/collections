package com.test.stack;

public class MyArrayStackTest extends MyAbstractStackTest {

    @Override
    protected SimpleStack<String> initStack(int maxSize) {
        return new MyArrayStack<>(maxSize);
    }

}