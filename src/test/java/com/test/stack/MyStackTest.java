package com.test.stack;

class MyStackTest extends MyAbstractStackTest {

    @Override
    protected SimpleStack<String> initStack(int maxSize) {
        return new MyStack<>(maxSize);
    }

}
