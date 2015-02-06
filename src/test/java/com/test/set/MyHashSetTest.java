package com.test.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class MyHashSetTest {

    private Set<String> set;

    @Before
    public void setUp() throws Exception {
        set = getDefaultData();
    }

    private Set<String> getDefaultData() {
        Set<String> set = new MyHashSet<>();
        set.add("a");
        set.add("b");
        set.add("a");
        set.add("d");
        set.add(null);
        set.add("k");
        set.add("");
        set.add("s");
        set.add("z");
        return set;
    }

    @Test
    public void testSize() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {

    }

    @Test
    public void testContains() throws Exception {

    }

    @Test
    public void testIterator() throws Exception {
        /*Object[] objects = set.toArray();
        Iterator<String> iterator = set.iterator();
        for (Object object : objects) {
            assertEquals(true, iterator.hasNext());
            assertEquals(object, iterator.next());
            iterator.remove();
        }
        assertEquals(false, iterator.hasNext());
        assertEquals(0, list.size());*/

        System.out.println(set);
        int index = 1;
        for (String s : set) {
            System.out.println(index + ": " + s);
            index++;
        }
        System.out.println(set);

    }

    @Test
    public void testToArray() throws Exception {

    }

    @Test
    public void testToArray1() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

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
