package com.test.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class MyListTest {

    private List<String> list;
    private String[] startArr = {"a", "b", "a", "d", "a", null, "k", "", "s", "z"};

    @Before
    public void setUp() throws Exception {
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
    public void testSize() throws Exception {
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
    public void testIsEmpty() throws Exception {
        assertEquals(false, list.isEmpty());
        list.clear();
        assertEquals(true, list.isEmpty());
        list.add("a");
        assertEquals(false, list.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        assertEquals(false, list.contains("notThere"));
        assertEquals(true, list.contains(""));
        assertEquals(true, list.contains(null));
        assertEquals(true, list.contains("a"));
    }

    @Test
    public void testIterator() throws Exception {
        Object[] objects = list.toArray();
        Iterator<String> iterator = list.iterator();
        for (Object object : objects) {
            assertEquals(true, iterator.hasNext());
            assertEquals(object, iterator.next());
            iterator.remove();
        }
        assertEquals(false, iterator.hasNext());
        assertEquals(0, list.size());
    }

    @Test
    public void testToArray() throws Exception {
        Object[] objects = list.toArray();
        assertEquals(true, Arrays.equals(objects, startArr));
    }

    @Test
    public void testToArrayWithParam() throws Exception {
        String[] result = new String[startArr.length];
        String[] objects = list.toArray(result);
        assertEquals(true, Arrays.equals(objects, startArr));
        assertEquals(true, result == objects);
    }

    @Test
    public void testAdd() throws Exception {
        int startSize = list.size();
        list.add("newValue");
        assertEquals(true, list.contains("newValue"));
        assertEquals(true, list.size() == startSize + 1);
        assertEquals("newValue", list.get(list.size() - 1));

        list.add(null);
        assertEquals(true, list.contains(null));
        assertEquals(true, list.size() == startSize + 2);
        assertEquals(null, list.get(list.size() - 1));

        long startTime = System.currentTimeMillis();
        addElements(list, 1_000_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 1.000.000 elements: " + endTime + "ms.");
    }

    @Test
    public void testRemove() throws Exception {
        int startSize = list.size();
        list.remove("a");
        assertEquals(true, list.size() == startSize - 1);

        list.remove("b");
        assertEquals(true, list.size() == startSize - 2);

        list.remove("notThere");
        assertEquals(true, list.size() == startSize - 2);

        list.remove(null);
        assertEquals(true, list.size() == startSize - 3);

        int elNum = 10000;
        addElements(list, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(list, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 10.000 elements from head: " + endTime + "ms.");
    }

    @Test
    public void testContainsAll() throws Exception {
        assertEquals(true, list.containsAll(Arrays.asList("a", "b", "k", "s")));
        assertEquals(false, list.containsAll(Arrays.asList("a", "b", "k", "s", "notThere")));
    }

    @Test
    public void testAddAll() throws Exception {
        List<String> copy = getDefaultList();
        copy.add("a");
        copy.add("b");
        copy.add("c");
        list.addAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, list);
    }

    @Test
    public void testAddAllWithParam() throws Exception {
        List<String> copy = getDefaultList();
        copy.add(5, "c");
        copy.add(5, "b");
        copy.add(5, "a");
        list.addAll(5, Arrays.asList("a", "b", "c"));
        assertEquals(copy, list);
    }

    @Test
    public void testRemoveAll() throws Exception {
        List<String> copy = getDefaultList();
        copy.remove("a");
        copy.remove("b");
        copy.remove("c");
        list.removeAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, list);
    }

    @Test
    public void testRetainAll() throws Exception {
        List<String> newList = new MyArrayList<>();
        newList.add("k");
        newList.add("s");
        newList.add("z");
        list.retainAll(newList);
        assertEquals(newList, list);
    }

    @Test
    public void testClear() throws Exception {
        list.clear();
        assertEquals(0, list.size());
        list.add("a");
        assertEquals(1, list.size());
    }

    @Test
    public void testGet() throws Exception {
       assertEquals("a", list.get(0));
       assertEquals("z", list.get(9));
    }

    @Test
    public void testSet() throws Exception {
        int listSize = list.size();
        assertEquals("a", list.get(0));
        assertEquals(listSize, list.size());
        list.set(0, "b");
        assertEquals("b", list.get(0));
        assertEquals(listSize, list.size());
    }

    @Test
    public void testAddByIndex() throws Exception {
        int listSize = list.size();
        assertEquals("a", list.get(0));
        assertEquals(listSize, list.size());
        list.add(0, "b");
        assertEquals("b", list.get(0));
        assertEquals(listSize + 1, list.size());
    }

    @Test
    public void testRemoveByIndex() throws Exception {
        int listSize = list.size();
        assertEquals("a", list.get(0));
        assertEquals(listSize, list.size());
        list.remove(0);
        assertEquals("b", list.get(0));
        assertEquals(listSize - 1, list.size());
    }

    @Test
    public void testIndexOf() throws Exception {
        assertEquals(0, list.indexOf("a"));
        assertEquals(9, list.indexOf("z"));
    }

    @Test
    public void testLastIndexOf() throws Exception {
        assertEquals(4, list.lastIndexOf("a"));
        assertEquals(9, list.lastIndexOf("z"));
        assertEquals(5, list.lastIndexOf(null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testListIterator() throws Exception {
        list.listIterator();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testListIteratorByIndex() throws Exception {
        list.listIterator(0);
    }

    @Test
    public void testSubList() throws Exception {
        List<String> strings = list.subList(0, 3);
        assertEquals(strings, Arrays.asList("a", "b", "a"));
    }

}