package com.test.tree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyTreeSetTest {

    private static final List<String> TRAVERSAL_LIST = Arrays.asList("F", "D", "J", "B", "E", "G", "K", "A", "C", "I", "H");

    private Set<String> set;

    @BeforeEach
    void setUp() {
        set = getDefaultSet();
    }

    private Set<String> getDefaultSet() {
        Set<String> set = new MyTreeSet<>();
        fillWithDefaultData(set);
        return set;
    }

    private MyTreeSet<String> getTraversalSet() {
        MyTreeSet<String> stringSet = new MyTreeSet<>();
        stringSet.addAll(TRAVERSAL_LIST);
        return stringSet;
    }

    private void fillWithDefaultData(Set<String> set) {
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
            set.remove(String.valueOf((char) i));
        }
    }

    @Test
    void testLevelOrderTraversal() {
        System.out.println("LevelOrderTraversal");
        MyTreeSet<String> stringSet = getTraversalSet();
        List<String> strings = stringSet.levelOrderTraversal();
        System.out.println(strings);
    }

    @Test
    void testPreOrderTraversal() {
        System.out.println("PreOrderTraversal");
        MyTreeSet<String> stringSet = getTraversalSet();
        List<String> strings = stringSet.preOrderTraversal();
        System.out.println(strings);
    }

    @Test
    void testInOrderTraversal() {
        System.out.println("InOrderTraversal");
        MyTreeSet<String> stringSet = getTraversalSet();
        List<String> strings = stringSet.inOrderTraversal();
        System.out.println(strings);
    }

    @Test
    void postInOrderTraversal() {
        System.out.println("PostOrderTraversal");
        MyTreeSet<String> stringSet = getTraversalSet();
        List<String> strings = stringSet.postOrderTraversal();
        System.out.println(strings);
    }

    @Test
    void testSize() {
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
    void testContains() {
        assertFalse(set.contains("notThere"));
        assertTrue(set.contains("b"));
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

        set.add("a");
        assertTrue(set.contains("a"));
        assertEquals(set.size(), startSize + 1);

        long startTime = System.currentTimeMillis();
        addElements(set, 10_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 10.000 elements: " + endTime + "ms.");
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

        int elNum = 10_000;
        addElements(set, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(set, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 10.000 elements: " + endTime + "ms.");
    }

    @Test
    void testContainsAll() {
        assertTrue(set.containsAll(Arrays.asList("a", "b", "z", "s")));
        assertFalse(set.containsAll(Arrays.asList("a", "b", "z", "s", "notThere")));
    }

    @Test
    void testAddAll() {
        Set<String> copy = getDefaultSet();
        copy.add("k");
        copy.add("l");
        copy.add("m");
        copy.add("a");
        set.addAll(Arrays.asList("a", "l", "k", "m"));
        assertEquals(copy, set);
    }

    @Test
    void testRetainAll() {
        Set<String> newSet = new HashSet<>();
        newSet.add("a");
        newSet.add("b");
        set.retainAll(newSet);
        assertEquals(newSet, set);
    }

    @Test
    void testRemoveAll() {
        Set<String> copy = getDefaultSet();
        copy.remove("a");
        copy.remove("b");
        copy.remove("s");
        set.removeAll(Arrays.asList("s", "b", "a"));
        assertEquals(copy, set);
    }

    @Test
    void testClear() {
        set.clear();
        assertEquals(0, set.size());
        set.add("a");
        assertEquals(1, set.size());
        set.clear();
        assertEquals(0, set.size());
    }

}