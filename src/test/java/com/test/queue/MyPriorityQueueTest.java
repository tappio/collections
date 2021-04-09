package com.test.queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyPriorityQueueTest {

    private final Integer[] startArr = {100, 12, 10, 7, 5, 2, 0, -5, -23};
    private Queue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = getDefaultQueue();
    }

    @Test
    void testSize() {
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
    void testContains() {
        assertFalse(queue.contains(100500));
        assertTrue(queue.contains(10));
        assertTrue(queue.contains(5));
        assertTrue(queue.contains(2));
    }

    @Test
    void testIterator() {
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
    void testToArray() {
        Object[] result = queue.toArray();
        assertArrayEquals(startArr, result);
    }

    @Test
    void testToArrayWithParam() {
        Integer[] result = new Integer[startArr.length];
        Integer[] objects = queue.toArray(result);
        assertArrayEquals(startArr, objects);
        assertSame(result, objects);
    }

    @Test
    void testAdd() {
        int startSize = queue.size();
        queue.add(100500);
        assertTrue(queue.contains(100500));
        assertEquals(queue.size(), startSize + 1);

        queue.add(100);
        assertTrue(queue.contains(100));
        assertEquals(queue.size(), startSize + 2);

        long startTime = System.currentTimeMillis();
        addElements(queue, 10_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 10.000 random elements: " + endTime + "ms.");
    }

    @Test
    void testRemove() {
        int startSize = queue.size();
        queue.remove(10);
        assertEquals(queue.size(), startSize - 1);

        queue.remove(5);
        assertEquals(queue.size(), startSize - 2);

        queue.remove(100500);
        assertEquals(queue.size(), startSize - 2);

        int elNum = 10_000;
        addElements(queue, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(queue, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 10.000 elements from head: " + endTime + "ms.");
    }

    @Test
    void testAddAll() {
        Queue<Integer> copy = getDefaultQueue();
        copy.add(300);
        copy.add(400);
        copy.add(500);
        queue.addAll(Arrays.asList(400, 300, 500));
        assertEquals(copy, queue);
    }

    @Test
    void testRemoveAll() {
        Queue<Integer> copy = getDefaultQueue();
        copy.remove(10);
        copy.remove(5);
        copy.remove(2);
        queue.removeAll(Arrays.asList(2, 5, 10));
        assertEquals(copy, queue);
    }

    @Test
    void testRetainAll() {
        Queue<Integer> newQueue = new MyPriorityQueue<>(new IntegerComparator());
        newQueue.add(10);
        newQueue.add(5);
        newQueue.add(2);
        queue.retainAll(newQueue);
        assertEquals(newQueue, queue);
    }

    @Test
    void testClear() {
        queue.clear();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        queue.add(100);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void testOffer() {
        int startSize = queue.size();
        queue.offer(100500);
        assertTrue(queue.contains(100500));
        assertEquals(queue.size(), startSize + 1);

        queue.offer(100);
        assertTrue(queue.contains(100));
        assertEquals(queue.size(), startSize + 2);

        queue.offer(100);
        assertTrue(queue.contains(100));
        assertEquals(queue.size(), startSize + 3);
    }

    @Test
    void testRemoveWithoutParam() {
        assertEquals(100, queue.remove().intValue());
        assertEquals(12, queue.remove().intValue());
        assertEquals(10, queue.remove().intValue());
        assertEquals(7, queue.remove().intValue());
    }

    @Test
    void testRemoveException() {
        queue.clear();
        assertTrue(queue.isEmpty());

        assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    @Test
    void testPoll() {
        assertEquals(100, queue.poll().intValue());
        assertEquals(12, queue.poll().intValue());
        assertEquals(10, queue.poll().intValue());
        assertEquals(7, queue.poll().intValue());
        queue.clear();
        assertNull(queue.poll());
    }

    @Test
    void testElement() {
        int size = queue.size();
        assertEquals(100, queue.element().intValue());
        assertEquals(size, queue.size());
        assertEquals(100, queue.poll().intValue());
        assertEquals(12, queue.element().intValue());
        assertEquals(size - 1, queue.size());
    }

    @Test
    void testElementException() {
        queue.clear();
        assertTrue(queue.isEmpty());

        assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    void testPeek() {
        int size = queue.size();
        assertEquals(100, queue.peek().intValue());
        assertEquals(size, queue.size());
        assertEquals(100, queue.poll().intValue());
        assertEquals(12, queue.peek().intValue());
        assertEquals(size - 1, queue.size());
        queue.clear();
        assertNull(queue.peek());
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

    private static class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }

    }

}
