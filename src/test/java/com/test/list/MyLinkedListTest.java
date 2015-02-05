package com.test.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MyLinkedListTest {

    private List<String> list;

    @Before
    public void setUp() throws Exception {
        list = getDefaultData();
    }

    private List<String> getDefaultData() {
        List<String> list = new MyLinkedList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("d");
        list.add("a");
        list.add(null);
        list.add("k");
        list.add("");
        list.add("s");
        list.add("z");
        return list;
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
        Object[] objects = list.toArray();
        Iterator<String> iterator = list.iterator();
        for (Object object : objects) {
            assertEquals(true, iterator.hasNext());
            assertEquals(object, iterator.next());
        }
        assertEquals(false, iterator.hasNext());
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
    public void testAddAll1() throws Exception {

    }

    @Test
    public void testRemoveAll() throws Exception {

    }

    @Test
    public void testRetainAll() throws Exception {

    }

    @Test
    public void testClear() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testAdd1() throws Exception {

    }

    @Test
    public void testRemove1() throws Exception {

    }

    @Test
    public void testIndexOf() throws Exception {

    }

    @Test
    public void testLastIndexOf() throws Exception {

    }

    @Test
    public void testListIterator() throws Exception {

    }

    @Test
    public void testListIterator1() throws Exception {

    }

    @Test
    public void testSubList() throws Exception {

    }
}