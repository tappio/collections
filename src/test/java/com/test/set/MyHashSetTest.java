package com.test.set;

import java.util.Set;

public class MyHashSetTest extends MyAbstractSetTest {

    @Override
    protected Set<String> initSet() {
        return new MyHashSet<>();
    }

}