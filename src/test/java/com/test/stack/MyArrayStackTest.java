package com.test.stack;

class MyArrayStackTest extends MyAbstractStackTest {

    @Override
    protected SimpleStack<String> initStack(int maxSize) {
        return new MyArrayStack<>(maxSize);
    }

}
