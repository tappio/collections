package com.test.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MyPriorityQueueTest {

    private static class IntegerComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    private Queue<Integer> queue;
    private Integer[] startArr = {100, 12, 10, 7, 5, 2, 0, -5, -23};

    @Before
    public void setUp() throws Exception {
        queue = getDefaultQueue();
    }

    private Queue<Integer> getDefaultQueue() {
        Queue<Integer> result = new MyPriorityQueue<>(new IntegerComparator());
        fillWithDefaultData(result);
        return result;
    }

    private void fillWithDefaultData(Queue<Integer> queue) {
        queue.add(10);
        queue.add(5);
        queue.add(2);
        queue.add(7);
        queue.add(100);
        queue.add(12);
        queue.add(-5);
        queue.add(0);
        queue.add(-23);
    }

    private void addElements(Queue<Integer> queue, int num) {
        for (int i = 0; i < num; i++) {
            int random = (int) Math.round(Math.random() * num);
            queue.add(random);
        }
    }

    private void removeElements(Queue queue, int num) {
        for (int i = 0; i < num; i++) {
            queue.poll();
        }
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(9, queue.size());
        queue.add(1);
        queue.add(2);
        queue.add(100);
        assertEquals(12, queue.size());
        queue.remove(10);
        queue.remove(5);
        queue.remove(100500);
        assertEquals(10, queue.size());
        queue.clear();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        assertFalse(queue.contains(100500));
        assertTrue(queue.contains(10));
        assertTrue(queue.contains(5));
        assertTrue(queue.contains(2));
    }

    @Test
    public void testIterator() throws Exception {
        Object[] objects = queue.toArray();
        Iterator<Integer> iterator = queue.iterator();
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
        Integer[] result = new Integer[startArr.length];
        Integer[] objects = queue.toArray(result);
        assertArrayEquals(startArr, objects);
        assertTrue(result == objects);
    }

    @Test
    public void testAdd() throws Exception {
        int startSize = queue.size();
        queue.add(100500);
        assertTrue(queue.contains(100500));
        assertTrue(queue.size() == startSize + 1);

        queue.add(100);
        assertTrue(queue.contains(100));
        assertTrue(queue.size() == startSize + 2);

        long startTime = System.currentTimeMillis();
        addElements(queue, 10_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 10.000 random elements: " + endTime + "ms.");
    }

    @Test
    public void testRemove() throws Exception {
        int startSize = queue.size();
        queue.remove(10);
        assertTrue(queue.size() == startSize - 1);

        queue.remove(5);
        assertTrue(queue.size() == startSize - 2);

        queue.remove(100500);
        assertTrue(queue.size() == startSize - 2);

        int elNum = 10_000;
        addElements(queue, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(queue, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 10.000 elements from head: " + endTime + "ms.");
    }

    @Test
    public void testAddAll() throws Exception {
        Queue<Integer> copy = getDefaultQueue();
        copy.add(300);
        copy.add(400);
        copy.add(500);
        queue.addAll(Arrays.asList(400, 300, 500));
        assertEquals(copy, queue);
    }

    @Test
    public void testRemoveAll() throws Exception {
        Queue<Integer> copy = getDefaultQueue();
        copy.remove(10);
        copy.remove(5);
        copy.remove(2);
        queue.removeAll(Arrays.asList(2, 5, 10));
        assertEquals(copy, queue);
    }

    @Test
    public void testRetainAll() throws Exception {
        Queue<Integer> newQueue = new MyPriorityQueue<>(new IntegerComparator());
        newQueue.add(10);
        newQueue.add(5);
        newQueue.add(2);
        queue.retainAll(newQueue);
        assertEquals(newQueue, queue);
    }

    @Test
    public void testClear() throws Exception {
        queue.clear();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        queue.add(100);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testOffer() throws Exception {
        int startSize = queue.size();
        queue.offer(100500);
        assertTrue(queue.contains(100500));
        assertTrue(queue.size() == startSize + 1);

        queue.offer(100);
        assertTrue(queue.contains(100));
        assertTrue(queue.size() == startSize + 2);

        queue.offer(100);
        assertTrue(queue.contains(100));
        assertTrue(queue.size() == startSize + 3);
    }

    @Test
    public void testRemoveWithoutParam() throws Exception {
        assertEquals(100, queue.remove().intValue());
        assertEquals(12, queue.remove().intValue());
        assertEquals(10, queue.remove().intValue());
        assertEquals(7, queue.remove().intValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveException() throws Exception {
        queue.clear();
        assertTrue(queue.isEmpty());
        queue.remove();
    }

    @Test
    public void testPoll() throws Exception {
        assertEquals(100, queue.poll().intValue());
        assertEquals(12, queue.poll().intValue());
        assertEquals(10, queue.poll().intValue());
        assertEquals(7, queue.poll().intValue());
        queue.clear();
        assertNull(queue.poll());
    }

    @Test
    public void testElement() throws Exception {
        int size = queue.size();
        assertEquals(100, queue.element().intValue());
        assertEquals(size, queue.size());
        assertEquals(100, queue.poll().intValue());
        assertEquals(12, queue.element().intValue());
        assertEquals(size - 1, queue.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testElementException() throws Exception {
        queue.clear();
        assertTrue(queue.isEmpty());
        queue.element();
    }

    @Test
    public void testPeek() throws Exception {
        int size = queue.size();
        assertEquals(100, queue.peek().intValue());
        assertEquals(size, queue.size());
        assertEquals(100, queue.poll().intValue());
        assertEquals(12, queue.peek().intValue());
        assertEquals(size - 1, queue.size());
        queue.clear();
        assertNull(queue.peek());
    }

}