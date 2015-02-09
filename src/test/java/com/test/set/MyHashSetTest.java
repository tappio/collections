package com.test.set;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MyHashSetTest {

    private Set<String> set;

    @Before
    public void setUp() throws Exception {
        set = getDefaultSet();
    }

    private Set<String> getDefaultSet() {
        Set<String> result = new MyHashSet<>();
        fillWithDefaultData(result);
        return result;
    }

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
    public void testSize() throws Exception {
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
    public void testIsEmpty() throws Exception {
        assertEquals(false, set.isEmpty());
        set.clear();
        assertEquals(true, set.isEmpty());
        set.add("a");
        assertEquals(false, set.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        assertEquals(false, set.contains("notThere"));
        assertEquals(true, set.contains(""));
        assertEquals(true, set.contains(null));
        assertEquals(true, set.contains("a"));
    }

    @Test
    public void testIterator() throws Exception {
        Object[] objects = set.toArray();
        Iterator<String> iterator = set.iterator();
        for (Object object : objects) {
            assertEquals(true, iterator.hasNext());
            assertEquals(object, iterator.next());
            iterator.remove();
        }
        assertEquals(false, iterator.hasNext());
        assertEquals(0, set.size());
    }

    @Test
    public void testToArray() throws Exception {
        final Object[] result = new Object[set.size()];
        int i = 0;
        for (String s : set) {
            result[i] = s;
            i++;
        }
        assertArrayEquals(result, set.toArray());
    }

    @Test
    public void testToArrayWithParam() throws Exception {
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
    public void testAdd() throws Exception {
        int startSize = set.size();
        set.add("newValue");
        assertEquals(true, set.contains("newValue"));
        assertEquals(true, set.size() == startSize + 1);

        set.add(null);
        assertEquals(true, set.contains(null));
        assertEquals(true, set.size() == startSize + 1);

        long startTime = System.currentTimeMillis();
        addElements(set, 1_000_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 1.000.000 elements: " + endTime + "ms.");
    }

    @Test
    public void testRemove() throws Exception {
        int startSize = set.size();
        set.remove("a");
        assertEquals(true, set.size() == startSize - 1);

        set.remove("b");
        assertEquals(true, set.size() == startSize - 2);

        set.remove("notThere");
        assertEquals(true, set.size() == startSize - 2);

        set.remove(null);
        assertEquals(true, set.size() == startSize - 3);

        int elNum = 1_000_000;
        addElements(set, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(set, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 1.000.000 elements from head: " + endTime + "ms.");
    }

    @Test
    public void testContainsAll() throws Exception {
        assertEquals(true, set.containsAll(Arrays.asList("a", "b", "k", "s")));
        assertEquals(false, set.containsAll(Arrays.asList("a", "b", "k", "s", "notThere")));
    }

    @Test
    public void testAddAll() throws Exception {
        Set<String> copy = getDefaultSet();
        copy.add("a");
        copy.add("b");
        copy.add("c");
        set.addAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, set);
    }

    @Test
    public void testRetainAll() throws Exception {
        Set<String> newSet = new HashSet<>();
        newSet.add("k");
        newSet.add("s");
        newSet.add("z");
        set.retainAll(newSet);
        assertEquals(newSet, set);
    }

    @Test
    public void testRemoveAll() throws Exception {
        Set<String> copy = getDefaultSet();
        copy.remove("a");
        copy.remove("b");
        copy.remove("c");
        set.removeAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, set);
    }

    @Test
    public void testClear() throws Exception {
        set.clear();
        assertEquals(0, set.size());
        set.add("a");
        assertEquals(1, set.size());
    }

}