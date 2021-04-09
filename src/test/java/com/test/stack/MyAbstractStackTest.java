package com.test.stack;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class MyAbstractStackTest {

    private static final int MAX_SIZE = 12;

    private final String[] startArr = {"a", "b", "a", "d", "a", null, "k", "", "s", "z"};
    private SimpleStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = getDefaultStack();
    }

    protected abstract SimpleStack<String> initStack(int maxSize);

    @Test
    void testSize() {
        int size = stack.size();
        assertEquals("a", stack.peek());
        size--;
        assertEquals(size, stack.size());
        assertEquals("b", stack.peek());
        size--;
        assertEquals(size, stack.size());
        assertEquals("a", stack.peek());
        size--;
        assertEquals(size, stack.size());
    }

    @Test
    void testContains() {
        assertFalse(stack.contains("notThere"));
        assertTrue(stack.contains(""));
        assertTrue(stack.contains(null));
        assertTrue(stack.contains("a"));
    }

    @Test
    void testIterator() {
        Object[] objects = stack.toArray();
        Iterator<String> iterator = stack.iterator();
        for (Object object : objects) {
            assertTrue(iterator.hasNext());
            assertEquals(object, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void testToArray() {
        Object[] objects = stack.toArray();
        assertArrayEquals(objects, startArr);
        assertArrayEquals(objects, startArr);
    }

    @Test
    void testToArrayWithParam() {
        String[] result = new String[startArr.length];
        String[] objects = stack.toArray(result);
        assertArrayEquals(objects, startArr);
        assertArrayEquals(objects, startArr);
        assertSame(result, objects);
    }

    @Test
    void testAdd() {
        int size = stack.size();
        assertTrue(stack.add("a"));
        size++;
        assertEquals(size, stack.size());
        assertTrue(stack.add("b"));
        size++;
        assertEquals(size, stack.size());
        assertFalse(stack.add("c"));
        assertEquals(size, stack.size());
        assertEquals("b", stack.peek());
        assertEquals("a", stack.peek());
    }

    @Test
    void testRemove() {
        assertThrows(UnsupportedOperationException.class, () -> stack.remove("a"));
    }

    @Test
    void testAddAll() {
        SimpleStack<String> copy = getDefaultStack();
        copy.add("a");
        copy.add("b");
        stack.addAll(Arrays.asList("a", "b"));
        assertEquals(copy, stack);
    }

    @Test
    void testRemoveAll() {
        assertThrows(UnsupportedOperationException.class, () -> stack.removeAll(Collections.emptyList()));
    }

    @Test
    void testRetainAll() {
        assertThrows(UnsupportedOperationException.class, () -> stack.retainAll(Collections.emptyList()));
    }

    @Test
    void testClear() {
        stack.clear();
        assertEquals(0, stack.size());
        stack.add("a");
        assertEquals(1, stack.size());
    }

    @Test
    void testPush() {
        int size = stack.size();
        stack.push("a");
        size++;
        assertEquals(size, stack.size());
        stack.push("a");
        size++;
        assertEquals(size, stack.size());
        stack.add("c");
    }

    @Test
    void testPop() {
        assertEquals("a", stack.pop());
        assertEquals("b", stack.pop());
        assertEquals("a", stack.pop());
        stack.clear();

        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void testOffer() {
        assertTrue(stack.offer("newValue1"));
        assertEquals("newValue1", stack.pop());
        assertTrue(stack.offer("newValue2"));
        assertEquals("newValue2", stack.pop());
        assertTrue(stack.offer("a"));
        assertTrue(stack.offer("b"));
        assertFalse(stack.offer("c"));
        assertEquals(MAX_SIZE, stack.size());
    }

    @Test
    void testPoll() {
        assertTrue(stack.poll());
        assertTrue(stack.poll());
        assertTrue(stack.poll());
        stack.clear();
        assertFalse(stack.poll());
    }

    @Test
    void testElement() {
        assertEquals("a", stack.element());
        assertEquals("b", stack.element());
        assertEquals("a", stack.element());
        stack.clear();

        assertThrows(NoSuchElementException.class, () -> stack.element());
    }

    @Test
    void testPeek() {
        assertEquals("a", stack.peek());
        assertEquals("b", stack.peek());
        assertEquals("a", stack.peek());
        stack.clear();
        assertNull(stack.peek());
    }

    private SimpleStack<String> getDefaultStack() {
        SimpleStack<String> result = initStack(MAX_SIZE);
        fillWithDefaultData(result);
        return result;
    }

    private void fillWithDefaultData(SimpleStack<String> stack) {
        stack.add("z");
        stack.add("s");
        stack.add("");
        stack.add("k");
        stack.add(null);
        stack.add("a");
        stack.add("d");
        stack.add("a");
        stack.add("b");
        stack.add("a");
    }

}