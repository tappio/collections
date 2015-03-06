package com.test.set;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MyLinkedHashSetTest extends MyAbstractSetTest {

    @Override
    protected Set<String> initSet() {
        return new MyLinkedHashSet<>();
    }

    @Test
    public void testOrder() throws Exception {
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