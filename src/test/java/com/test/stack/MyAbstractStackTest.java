package com.test.stack;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public abstract class MyAbstractStackTest {

    private SimpleStack<String> stack;
    private String[] startArr = {"a", "b", "a", "d", "a", null, "k", "", "s", "z"};
    private static final int MAX_SIZE = 12;

    @Before
    public void setUp() throws Exception {
        stack = getDefaultStack();
    }

    private SimpleStack<String> getDefaultStack() {
        SimpleStack<String> result = initStack(MAX_SIZE);
        fillWithDefaultData(result);
        return result;
    }

    protected abstract SimpleStack<String> initStack(int maxSize);

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

    @Test
    public void testSize() throws Exception {
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
    public void testContains() throws Exception {
        assertFalse(stack.contains("notThere"));
        assertTrue(stack.contains(""));
        assertTrue(stack.contains(null));
        assertTrue(stack.contains("a"));
    }

    @Test
    public void testIterator() throws Exception {
        Object[] objects = stack.toArray();
        Iterator<String> iterator = stack.iterator();
        for (Object object : objects) {
            assertTrue(iterator.hasNext());
            assertEquals(object, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testToArray() throws Exception {
        Object[] objects = stack.toArray();
        assertArrayEquals(objects, startArr);
        assertTrue(Arrays.equals(objects, startArr));
    }

    @Test
    public void testToArrayWithParam() throws Exception {
        String[] result = new String[startArr.length];
        String[] objects = stack.toArray(result);
        assertArrayEquals(objects, startArr);
        assertTrue(Arrays.equals(objects, startArr));
        assertTrue(result == objects);
    }

    @Test
    public void testAdd() throws Exception {
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

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        stack.remove("a");
    }

    @Test
    public void testAddAll() throws Exception {
        SimpleStack<String> copy = getDefaultStack();
        copy.add("a");
        copy.add("b");
        stack.addAll(Arrays.asList("a", "b"));
        assertEquals(copy, stack);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() throws Exception {
        stack.removeAll(Collections.EMPTY_LIST);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() throws Exception {
        stack.retainAll(Collections.EMPTY_LIST);
    }

    @Test
    public void testClear() throws Exception {
        stack.clear();
        assertEquals(0, stack.size());
        stack.add("a");
        assertEquals(1, stack.size());
    }

    @Test
    public void testPush() throws Exception {
        int size = stack.size();
        stack.push("a");
        size++;
        assertEquals(size, stack.size());
        stack.push("a");
        size++;
        assertEquals(size, stack.size());
        stack.add("c");
    }

    @Test(expected = NoSuchElementException.class)
    public void testPop() throws Exception {
        assertEquals("a", stack.pop());
        assertEquals("b", stack.pop());
        assertEquals("a", stack.pop());
        stack.clear();
        stack.pop();
    }

    @Test
    public void testOffer() throws Exception {
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
    public void testPoll() throws Exception {
        assertTrue(stack.poll());
        assertTrue(stack.poll());
        assertTrue(stack.poll());
        stack.clear();
        assertFalse(stack.poll());
    }

    @Test(expected = NoSuchElementException.class)
    public void testElement() throws Exception {
        assertEquals("a", stack.element());
        assertEquals("b", stack.element());
        assertEquals("a", stack.element());
        stack.clear();
        stack.element();
    }

    @Test
    public void testPeek() throws Exception {
        assertEquals("a", stack.peek());
        assertEquals("b", stack.peek());
        assertEquals("a", stack.peek());
        stack.clear();
        assertNull(stack.peek());
    }

}