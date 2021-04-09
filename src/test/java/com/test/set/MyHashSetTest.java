package com.test.set;

import java.util.Set;

class MyHashSetTest extends MyAbstractSetTest {

    @Override
    protected Set<String> initSet() {
        return new MyHashSet<>();
    }

}
