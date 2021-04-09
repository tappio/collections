package com.test.set;

import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyLinkedHashSetTest extends MyAbstractSetTest {

    @Override
    protected Set<String> initSet() {
        return new MyLinkedHashSet<>();
    }

    @Test
    void testOrder() {
        String[] strings = {"a", "b", "c", "a", "d", null, "", "a", "b"};
        String[] stringsUnique = {"a", "b", "c", "d", null, ""};
        Set<String> set = new MyLinkedHashSet<>();
        for (String s : strings) {
            set.add(s);
        }
        int index = 0;
        for (String s : set) {
            assertEquals(stringsUnique[index], s);
            index++;
        }
    }

}
