package com.test.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class MyTreeSetTest {

    private Set<String> set;

    @Before
    public void setUp() throws Exception {
        set = new MyTreeSet<>();
        fillWithDefaultData();
    }

    private void fillWithDefaultData() {
        set.add("s");
        set.add("z");
        set.add("a");
        set.add("b");
        set.add("a");
        set.add("b");
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
        assertEquals(4, set.size());
        set.add("AAA");
        set.add("BBB");
        set.add("BBB");
        assertEquals(6, set.size());
        set.remove("a");
        set.remove("b");
        set.remove("notThere");
        assertEquals(4, set.size());
        set.clear();
        assertEquals(0, set.size());

    }

    @Test
    public void testContains() throws Exception {
        assertFalse(set.contains("notThere"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("a"));
    }

    @Test
    public void testIterator() throws Exception {

    }

    @Test
    public void testToArray() throws Exception {

    }

    @Test
    public void testToArrayWithParam() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {
        int startSize = set.size();
        set.add("newValue");
        assertTrue(set.contains("newValue"));
        assertTrue(set.size() == startSize + 1);

        set.add("a");
        assertTrue(set.contains("a"));
        assertTrue(set.size() == startSize + 1);

        long startTime = System.currentTimeMillis();
        addElements(set, 10_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 10.000 elements: " + endTime + "ms.");
    }

    @Test
    public void testRemove() throws Exception {

    }

    @Test
    public void testContainsAll() throws Exception {

    }

    @Test
    public void testAddAll() throws Exception {

    }

    @Test
    public void testRetainAll() throws Exception {

    }

    @Test
    public void testRemoveAll() throws Exception {

    }

    @Test
    public void testClear() throws Exception {

    }

}