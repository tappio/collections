package com.test.stack;

public class MyStackTest extends MyAbstractStackTest {

    @Override
    protected SimpleStack<String> initStack(int maxSize) {
        return new MyStack<>(maxSize);
    }

}