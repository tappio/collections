package com.test.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class MyListTest {

    private final String[] startArr = {"a", "b", "a", "d", "a", null, "k", "", "s", "z"};
    private List<String> list;

    @BeforeEach
    void setUp() {
        list = getDefaultList();
    }

    protected abstract List<String> initList();

    private List<String> getDefaultList() {
        List<String> result = initList();
        fillWithDefaultData(result);
        return result;
    }

    private void fillWithDefaultData(List<String> list) {
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
    }

    private void addElements(List<String> list, int num) {
        for (int i = 0; i < num; i++) {
            list.add(String.valueOf((char)i));
        }
    }

    private void removeElements(List<String> list, int num) {
        for (int i = 0; i < num; i++) {
            list.remove(0);
        }
    }

    @Test
    void testSize() {
        assertEquals(10, list.size());
        list.add("AAA");
        list.add("BBB");
        assertEquals(12, list.size());
        list.remove("a");
        list.remove("d");
        list.remove("notThere");
        list.remove(null);
        assertEquals(9, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void testIsEmpty() {
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
        list.add("a");
        assertFalse(list.isEmpty());
    }

    @Test
    void testContains() {
        assertFalse(list.contains("notThere"));
        assertTrue(list.contains(""));
        assertTrue(list.contains(null));
        assertTrue(list.contains("a"));
    }

    @Test
    void testIterator() {
        Object[] objects = list.toArray();
        Iterator<String> iterator = list.iterator();
        for (Object object : objects) {
            assertTrue(iterator.hasNext());
            assertEquals(object, iterator.next());
            iterator.remove();
        }
        assertFalse(iterator.hasNext());
        assertEquals(0, list.size());
    }

    @Test
    void testToArray() {
        Object[] objects = list.toArray();
        assertArrayEquals(objects, startArr);
    }

    @Test
    void testToArrayWithParam() {
        String[] result = new String[startArr.length];
        String[] objects = list.toArray(result);
        assertArrayEquals(objects, startArr);
        assertSame(result, objects);
    }

    @Test
    void testAdd() {
        int startSize = list.size();
        list.add("newValue");
        assertTrue(list.contains("newValue"));
        assertEquals(list.size(), startSize + 1);
        assertEquals("newValue", list.get(list.size() - 1));

        list.add(null);
        assertTrue(list.contains(null));
        assertEquals(list.size(), startSize + 2);
        assertNull(list.get(list.size() - 1));

        long startTime = System.currentTimeMillis();
        addElements(list, 1_000_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 1.000.000 elements: " + endTime + "ms.");
    }

    @Test
    void testRemove() {
        int startSize = list.size();
        list.remove("a");
        assertEquals(list.size(), startSize - 1);

        list.remove("b");
        assertEquals(list.size(), startSize - 2);

        list.remove("notThere");
        assertEquals(list.size(), startSize - 2);

        list.remove(null);
        assertEquals(list.size(), startSize - 3);

        int elNum = 10000;
        addElements(list, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(list, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 10.000 elements from head: " + endTime + "ms.");
    }

    @Test
    void testContainsAll() {
        assertTrue(list.containsAll(Arrays.asList("a", "b", "k", "s")));
        assertFalse(list.containsAll(Arrays.asList("a", "b", "k", "s", "notThere")));
    }

    @Test
    void testAddAll() {
        List<String> copy = getDefaultList();
        copy.add("a");
        copy.add("b");
        copy.add("c");
        list.addAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, list);
    }

    @Test
    void testAddAllWithParam() {
        List<String> copy = getDefaultList();
        copy.add(5, "c");
        copy.add(5, "b");
        copy.add(5, "a");
        list.addAll(5, Arrays.asList("a", "b", "c"));
        assertEquals(copy, list);
    }

    @Test
    void testRemoveAll() {
        List<String> copy = getDefaultList();
        copy.remove("a");
        copy.remove("b");
        copy.remove("c");
        list.removeAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, list);
    }

    @Test
    void testRetainAll() {
        List<String> newList = new MyArrayList<>();
        newList.add("k");
        newList.add("s");
        newList.add("z");
        list.retainAll(newList);
        assertEquals(newList, list);
    }

    @Test
    void testClear() {
        list.clear();
        assertEquals(0, list.size());
        list.add("a");
        assertEquals(1, list.size());
    }

    @Test
    void testGet() {
       assertEquals("a", list.get(0));
       assertEquals("z", list.get(9));
    }

    @Test
    void testSet() {
        int listSize = list.size();
        assertEquals("a", list.get(0));
        assertEquals(listSize, list.size());
        list.set(0, "b");
        assertEquals("b", list.get(0));
        assertEquals(listSize, list.size());
    }

    @Test
    void testAddByIndex() {
        int listSize = list.size();
        assertEquals("a", list.get(0));
        assertEquals(listSize, list.size());
        list.add(0, "b");
        assertEquals("b", list.get(0));
        assertEquals(listSize + 1, list.size());
    }

    @Test
    void testRemoveByIndex() {
        int listSize = list.size();
        assertEquals("a", list.get(0));
        assertEquals(listSize, list.size());
        list.remove(0);
        assertEquals("b", list.get(0));
        assertEquals(listSize - 1, list.size());
    }

    @Test
    void testIndexOf() {
        assertEquals(0, list.indexOf("a"));
        assertEquals(9, list.indexOf("z"));
    }

    @Test
    void testLastIndexOf() {
        assertEquals(4, list.lastIndexOf("a"));
        assertEquals(9, list.lastIndexOf("z"));
        assertEquals(5, list.lastIndexOf(null));
    }

    @Test
    void testListIterator() {
        assertThrows(UnsupportedOperationException.class, () -> list.listIterator());
    }

    @Test
    void testListIteratorByIndex() {
        assertThrows(UnsupportedOperationException.class, () -> list.listIterator(0));
    }

    @Test
    void testSubList() {
        List<String> strings = list.subList(0, 3);
        assertEquals(strings, Arrays.asList("a", "b", "a"));
    }

}
