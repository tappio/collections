package com.test.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class MyAbstractSetTest {

    private Set<String> set;

    @BeforeEach
    void setUp() {
        set = getDefaultSet();
    }

    private Set<String> getDefaultSet() {
        Set<String> result = initSet();
        fillWithDefaultData(result);
        return result;
    }

    protected abstract Set<String> initSet();

    private void fillWithDefaultData(Set<String> set) {
        set.add("a");
        set.add("b");
        set.add("a");
        set.add("d");
        set.add("a");
        set.add(null);
        set.add("k");
        set.add("");
        set.add("s");
        set.add("z");
    }

    private void addElements(Set<String> set, int num) {
        for (int i = 0; i < num; i++) {
            set.add(String.valueOf((char)i));
        }
    }

    private void removeElements(Set<String> set, int num) {
        for (int i = 0; i < num; i++) {
            set.remove(String.valueOf((char)i));
        }
    }

    @Test
    void testSize() {
        assertEquals(8, set.size());
        set.add("AAA");
        set.add("BBB");
        set.add("BBB");
        assertEquals(10, set.size());
        set.remove("a");
        set.remove("d");
        set.remove("notThere");
        set.remove(null);
        assertEquals(7, set.size());
        set.clear();
        assertEquals(0, set.size());
    }

    @Test
    void testIsEmpty() {
        assertFalse(set.isEmpty());
        set.clear();
        assertTrue(set.isEmpty());
        set.add("a");
        assertFalse(set.isEmpty());
    }

    @Test
    void testContains() {
        assertFalse(set.contains("notThere"));
        assertTrue(set.contains(""));
        assertTrue(set.contains(null));
        assertTrue(set.contains("a"));
    }

    @Test
    void testIterator() {
        Object[] objects = set.toArray();
        Iterator<String> iterator = set.iterator();
        for (Object object : objects) {
            assertTrue(iterator.hasNext());
            assertEquals(object, iterator.next());
            iterator.remove();
        }
        assertFalse(iterator.hasNext());
        assertEquals(0, set.size());
    }

    @Test
    void testToArray() {
        final Object[] result = new Object[set.size()];
        int i = 0;
        for (String s : set) {
            result[i] = s;
            i++;
        }
        assertArrayEquals(result, set.toArray());
    }

    @Test
    void testToArrayWithParam() {
        String[] result = new String[set.size()];
        set.toArray(result);

        final String[] strings = new String[set.size()];
        int i = 0;
        for (String s : set) {
            strings[i] = s;
            i++;
        }
        assertArrayEquals(strings, result);
    }

    @Test
    void testAdd() {
        int startSize = set.size();
        set.add("newValue");
        assertTrue(set.contains("newValue"));
        assertEquals(set.size(), startSize + 1);

        set.add(null);
        assertTrue(set.contains(null));
        assertEquals(set.size(), startSize + 1);

        long startTime = System.currentTimeMillis();
        addElements(set, 1_000_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 1.000.000 elements: " + endTime + "ms.");
    }

    @Test
    void testRemove() {
        int startSize = set.size();
        set.remove("a");
        assertEquals(set.size(), startSize - 1);

        set.remove("b");
        assertEquals(set.size(), startSize - 2);

        set.remove("notThere");
        assertEquals(set.size(), startSize - 2);

        set.remove(null);
        assertEquals(set.size(), startSize - 3);

        int elNum = 1_000_000;
        addElements(set, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(set, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 1.000.000 elements from head: " + endTime + "ms.");
    }

    @Test
    void testContainsAll() {
        assertTrue(set.containsAll(Arrays.asList("a", "b", "k", "s")));
        assertFalse(set.containsAll(Arrays.asList("a", "b", "k", "s", "notThere")));
    }

    @Test
    void testAddAll() {
        Set<String> copy = getDefaultSet();
        copy.add("a");
        copy.add("b");
        copy.add("c");
        set.addAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, set);
    }

    @Test
    void testRetainAll() {
        Set<String> newSet = new HashSet<>();
        newSet.add("k");
        newSet.add("s");
        newSet.add("z");
        set.retainAll(newSet);
        assertEquals(newSet, set);
    }

    @Test
    void testRemoveAll() {
        Set<String> copy = getDefaultSet();
        copy.remove("a");
        copy.remove("b");
        copy.remove("c");
        set.removeAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, set);
    }

    @Test
    void testClear() {
        set.clear();
        assertEquals(0, set.size());
        set.add("a");
        assertEquals(1, set.size());
    }

}
