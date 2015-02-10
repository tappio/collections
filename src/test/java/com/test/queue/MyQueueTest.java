package com.test.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.Assert.*;

public class MyQueueTest {

    private Queue<String> queue;
    private String[] startArr = {"a", "b", "a", "d", "a", null, "k", "", "s", "z"};

    @Before
    public void setUp() throws Exception {
        queue = getDefaultQueue();
    }

    private Queue<String> getDefaultQueue() {
        Queue<String> result = new MyQueue<>();
        fillWithDefaultData(result);
        return result;
    }

    private void fillWithDefaultData(Queue<String> queue) {
        queue.add("a");
        queue.add("b");
        queue.add("a");
        queue.add("d");
        queue.add("a");
        queue.add(null);
        queue.add("k");
        queue.add("");
        queue.add("s");
        queue.add("z");
    }

    private void addElements(Queue<String> queue, int num) {
        for (int i = 0; i < num; i++) {
            queue.add(String.valueOf((char)i));
        }
    }

    private void removeElements(Queue<String> queue, int num) {
        for (int i = 0; i < num; i++) {
            queue.poll();
        }
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(10, queue.size());
        queue.add("AAA");
        queue.add("BBB");
        assertEquals(12, queue.size());
        queue.remove("a");
        queue.remove("d");
        queue.remove("notThere");
        queue.remove(null);
        assertEquals(9, queue.size());
        queue.clear();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        assertFalse(queue.contains("notThere"));
        assertTrue(queue.contains(""));
        assertTrue(queue.contains(null));
        assertTrue(queue.contains("a"));
    }

    @Test
    public void testIterator() throws Exception {
        Object[] objects = queue.toArray();
        Iterator<String> iterator = queue.iterator();
        for (Object object : objects) {
            assertTrue(iterator.hasNext());
            assertEquals(object, iterator.next());
            iterator.remove();
        }
        assertFalse(iterator.hasNext());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testToArray() throws Exception {
        Object[] result = queue.toArray();
        assertArrayEquals(startArr, result);
    }

    @Test
    public void testToArrayWithParam() throws Exception {
        String[] result = new String[startArr.length];
        String[] objects = queue.toArray(result);
        assertArrayEquals(startArr, objects);
        assertTrue(result == objects);
    }

    @Test
    public void testAdd() throws Exception {
        int startSize = queue.size();
        queue.add("newValue");
        assertTrue(queue.contains("newValue"));
        assertTrue(queue.size() == startSize + 1);

        queue.add(null);
        assertTrue(queue.contains(null));
        assertTrue(queue.size() == startSize + 2);

        long startTime = System.currentTimeMillis();
        addElements(queue, 1_000_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 1.000.000 elements: " + endTime + "ms.");
    }

    @Test
    public void testRemove() throws Exception {
        int startSize = queue.size();
        queue.remove("a");
        assertTrue(queue.size() == startSize - 1);

        queue.remove("b");
        assertTrue(queue.size() == startSize - 2);

        queue.remove("notThere");
        assertTrue(queue.size() == startSize - 2);

        queue.remove(null);
        assertTrue(queue.size() == startSize - 3);

        int elNum = 1_000_000;
        addElements(queue, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(queue, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 1.000.000 elements from head: " + endTime + "ms.");
    }

    @Test
    public void testAddAll() throws Exception {
        Queue<String> copy = getDefaultQueue();
        copy.add("a");
        copy.add("b");
        copy.add("c");
        queue.addAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, queue);
    }

    @Test
    public void testRemoveAll() throws Exception {
        Queue<String> copy = getDefaultQueue();
        copy.remove("a");
        copy.remove("b");
        copy.remove("c");
        queue.removeAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, queue);
    }

    @Test
    public void testRetainAll() throws Exception {
        Queue<String> newQueue = new MyQueue<>();
        newQueue.add("k");
        newQueue.add("s");
        newQueue.add("z");
        queue.retainAll(newQueue);
        assertEquals(newQueue, queue);
    }

    @Test
    public void testClear() throws Exception {
        queue.clear();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        queue.add("a");
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testOffer() throws Exception {
        Queue<String> q = new MyQueue<>(2);
        assertTrue(q.offer("a"));
        assertTrue(q.offer("b"));
        assertFalse(q.offer("c"));
        assertFalse(q.offer("d"));
        assertTrue(q.size() == 2);
    }

    @Test
    public void testRemoveWithoutParam() throws Exception {
        Queue<String> q = new MyQueue<>();
        q.offer("a");
        q.offer("b");
        q.offer("c");
        assertEquals("a", q.remove());
        assertEquals("b", q.remove());
        assertEquals("c", q.remove());
        assertTrue(q.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveException() throws Exception {
        Queue<String> q = new MyQueue<>();
        assertTrue(q.isEmpty());
        q.remove();
    }

    @Test
    public void testPoll() throws Exception {
        Queue<String> q = new MyQueue<>();
        q.offer("a");
        q.offer("b");
        q.offer("c");
        assertEquals("a", q.poll());
        assertEquals("b", q.poll());
        assertEquals("c", q.poll());
        assertTrue(q.isEmpty());
        assertNull(q.poll());
    }

    @Test
    public void testElement() throws Exception {
        Queue<String> q = new MyQueue<>();
        q.offer("a");
        q.offer("b");
        assertEquals("a", q.element());
        assertEquals(2, q.size());
        assertEquals("a", q.poll());
        assertEquals("b", q.element());
        assertEquals(1, q.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testElementException() throws Exception {
        Queue<String> q = new MyQueue<>();
        assertTrue(q.isEmpty());
        q.element();
    }

    @Test
    public void testPeek() throws Exception {
        Queue<String> q = new MyQueue<>();
        q.offer("a");
        q.offer("b");
        assertEquals("a", q.peek());
        assertEquals(2, q.size());
        assertEquals("a", q.poll());
        assertEquals("b", q.peek());
        assertEquals(1, q.size());
        assertEquals("b", q.poll());
        assertTrue(q.isEmpty());
        assertNull(q.peek());
    }

}