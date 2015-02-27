package com.test.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MyDequeTest {

    private Deque<String> deque;
    private String[] startArr = {"a", "b", "a", "d", "a", null, "k", "", "s", "z"};

    @Before
    public void setUp() throws Exception {
        deque = getDefaultDeque();
    }

    private Deque<String> getDefaultDeque() {
        Deque<String> result = new MyDeque<>();
        fillWithDefaultData(result);
        return result;
    }

    private void fillWithDefaultData(Deque<String> deque) {
        deque.add("a");
        deque.add("b");
        deque.add("a");
        deque.add("d");
        deque.add("a");
        deque.add(null);
        deque.add("k");
        deque.add("");
        deque.add("s");
        deque.add("z");
    }

    @Test
    public void testAddFirst() throws Exception {
        int startSize = deque.size();
        deque.addFirst("newValue");
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getFirst());

        deque.addFirst(null);
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertEquals(null, deque.getFirst());
    }

    @Test
    public void testAddLast() throws Exception {
        int startSize = deque.size();
        deque.addLast("newValue");
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getLast());

        deque.addLast(null);
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertEquals(null, deque.getLast());
    }

    @Test
    public void testOfferFirst() throws Exception {
        int startSize = deque.size();
        assertTrue(deque.offerFirst("newValue"));
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getFirst());

        assertTrue(deque.offerFirst(null));
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertEquals(null, deque.getFirst());
    }

    @Test
    public void testOfferLast() throws Exception {
        int startSize = deque.size();
        assertTrue(deque.offerLast("newValue"));
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getLast());

        assertTrue(deque.offerLast(null));
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertEquals(null, deque.getLast());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirst() throws Exception {
        assertEquals("a", deque.removeFirst());
        assertEquals("b", deque.removeFirst());
        assertEquals("a", deque.removeFirst());
        assertEquals("d", deque.removeFirst());
        assertEquals("a", deque.removeFirst());
        assertEquals(null, deque.removeFirst());
        assertEquals("k", deque.removeFirst());
        assertEquals("", deque.removeFirst());
        assertEquals("s", deque.removeFirst());
        assertEquals("z", deque.removeFirst());
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLast() throws Exception {
        assertEquals("z", deque.removeLast());
        assertEquals("s", deque.removeLast());
        assertEquals("", deque.removeLast());
        assertEquals("k", deque.removeLast());
        assertEquals(null, deque.removeLast());
        assertEquals("a", deque.removeLast());
        assertEquals("d", deque.removeLast());
        assertEquals("a", deque.removeLast());
        assertEquals("b", deque.removeLast());
        assertEquals("a", deque.removeLast());
        deque.removeLast();
    }

    @Test
    public void testPollFirst() throws Exception {
        assertEquals("a", deque.pollFirst());
        assertEquals("b", deque.pollFirst());
        assertEquals("a", deque.pollFirst());
        assertEquals("d", deque.pollFirst());
        assertEquals("a", deque.pollFirst());
        assertEquals(null, deque.pollFirst());
        assertEquals("k", deque.pollFirst());
        assertEquals("", deque.pollFirst());
        assertEquals("s", deque.pollFirst());
        assertEquals("z", deque.pollFirst());
        assertNull(deque.pollFirst());
    }

    @Test
    public void testPollLast() throws Exception {
        assertEquals("z", deque.pollLast());
        assertEquals("s", deque.pollLast());
        assertEquals("", deque.pollLast());
        assertEquals("k", deque.pollLast());
        assertEquals(null, deque.pollLast());
        assertEquals("a", deque.pollLast());
        assertEquals("d", deque.pollLast());
        assertEquals("a", deque.pollLast());
        assertEquals("b", deque.pollLast());
        assertEquals("a", deque.pollLast());
        assertNull(deque.pollLast());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetFirst() throws Exception {
        assertEquals("a", deque.getFirst());
        assertEquals("a", deque.getFirst());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.getFirst());
        assertEquals("newValue", deque.getFirst());
        deque.clear();
        deque.getFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetLast() throws Exception {
        assertEquals("z", deque.getLast());
        assertEquals("z", deque.getLast());
        deque.addLast("newValue");
        assertEquals("newValue", deque.getLast());
        assertEquals("newValue", deque.getLast());
        deque.clear();
        deque.getLast();
    }

    @Test
    public void testPeekFirst() throws Exception {
        assertEquals("a", deque.peekFirst());
        assertEquals("a", deque.peekFirst());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.peekFirst());
        assertEquals("newValue", deque.peekFirst());
        deque.clear();
        assertNull(deque.peekFirst());
    }

    @Test
    public void testPeekLast() throws Exception {
        assertEquals("z", deque.peekLast());
        assertEquals("z", deque.peekLast());
        deque.addLast("newValue");
        assertEquals("newValue", deque.peekLast());
        assertEquals("newValue", deque.peekLast());
        deque.clear();
        assertNull(deque.peekLast());
    }

    @Test
    public void testRemoveFirstOccurrence() throws Exception {
        int size = deque.size();
        assertEquals("a", deque.getFirst());
        assertTrue(deque.removeFirstOccurrence("a"));
        assertEquals(size - 1, deque.size());
        assertEquals("b", deque.getFirst());
        assertTrue(deque.removeFirstOccurrence(null));
        assertEquals(size - 2, deque.size());
        assertFalse(deque.removeFirstOccurrence("notThere"));
        deque.clear();
        assertFalse(deque.removeFirstOccurrence("a"));
    }

    @Test
    public void testRemoveLastOccurrence() throws Exception {
        int size = deque.size();
        assertEquals("z", deque.getLast());
        assertTrue(deque.removeLastOccurrence("z"));
        assertEquals(size - 1, deque.size());
        assertEquals("s", deque.getLast());
        assertTrue(deque.removeLastOccurrence(null));
        assertEquals(size - 2, deque.size());
        assertFalse(deque.removeLastOccurrence("notThere"));
        deque.clear();
        assertFalse(deque.removeLastOccurrence("a"));
    }

    @Test
    public void testAdd() throws Exception {
        int size = 0;
        deque.clear();
        assertTrue(deque.add("a"));
        size++;
        assertEquals("a", deque.getLast());
        assertEquals(size, deque.size());
        assertTrue(deque.add("b"));
        size++;
        assertEquals("b", deque.getLast());
        assertEquals(size, deque.size());
    }

    @Test
    public void testOffer() throws Exception {
        int size = 0;
        deque.clear();
        assertTrue(deque.offer("a"));
        size++;
        assertEquals("a", deque.getLast());
        assertEquals(size, deque.size());
        assertTrue(deque.offer("b"));
        size++;
        assertEquals("b", deque.getLast());
        assertEquals(size, deque.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemove() throws Exception {
        assertEquals("a", deque.remove());
        assertEquals("b", deque.remove());
        assertEquals("a", deque.remove());
        deque.clear();
        deque.remove();
    }

    @Test
    public void testPoll() throws Exception {
        assertEquals("a", deque.poll());
        assertEquals("b", deque.poll());
        assertEquals("a", deque.poll());
        deque.clear();
        assertNull(deque.poll());
    }

    @Test(expected = NoSuchElementException.class)
    public void testElement() throws Exception {
        assertEquals("a", deque.element());
        assertEquals("a", deque.element());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.element());
        assertEquals("newValue", deque.element());
        deque.clear();
        deque.element();
    }

    @Test
    public void testPeek() throws Exception {
        assertEquals("a", deque.peek());
        assertEquals("a", deque.peek());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.peek());
        assertEquals("newValue", deque.peek());
        deque.clear();
        assertNull(deque.peek());
    }

    @Test
    public void testPush() throws Exception {
        int size = 0;
        deque.clear();
        deque.push("a");
        size++;
        assertEquals("a", deque.getFirst());
        assertEquals(size, deque.size());
        deque.push("b");
        size++;
        assertEquals("b", deque.getFirst());
        assertEquals(size, deque.size());
        deque.push(null);
        size++;
        assertEquals(null, deque.getFirst());
        assertEquals(size, deque.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPop() throws Exception {
        assertEquals("a", deque.pop());
        assertEquals("b", deque.pop());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.pop());
        assertEquals("a", deque.pop());
        deque.clear();
        deque.pop();
    }

    @Test
    public void testRemoveWithParam() throws Exception {
        int size = deque.size();
        assertEquals("a", deque.getFirst());
        assertTrue(deque.remove("a"));
        assertEquals(size - 1, deque.size());
        assertEquals("b", deque.getFirst());
        assertTrue(deque.remove(null));
        assertEquals(size - 2, deque.size());
        assertFalse(deque.remove("notThere"));
        deque.clear();
        assertFalse(deque.remove("a"));
    }

    @Test
    public void testAddAll() throws Exception {
        Deque<String> copy = getDefaultDeque();
        copy.add("a");
        copy.add("b");
        copy.add("c");
        deque.addAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, deque);
    }

    @Test
    public void testRemoveAll() throws Exception {
        Deque<String> copy = getDefaultDeque();
        copy.remove("a");
        copy.remove("b");
        copy.remove("c");
        deque.removeAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, deque);
    }

    @Test
    public void testRetainAll() throws Exception {
        Deque<String> newDeque = new MyDeque<>();
        newDeque.add("k");
        newDeque.add("s");
        newDeque.add("z");
        deque.retainAll(newDeque);
        assertEquals(newDeque, deque);
    }

    @Test
    public void testClear() throws Exception {
        deque.clear();
        assertEquals(0, deque.size());
        deque.add("a");
        assertEquals(1, deque.size());
    }

    @Test
    public void testContains() throws Exception {
        assertFalse(deque.contains("notThere"));
        assertTrue(deque.contains(""));
        assertTrue(deque.contains(null));
        assertTrue(deque.contains("a"));
    }

    @Test
    public void testSize() throws Exception {
        int size = deque.size();
        assertEquals("a", deque.getFirst());
        assertEquals(size, deque.size());
        deque.add("b");
        size++;
        assertEquals(size, deque.size());
        deque.remove("b");
        size--;
        assertEquals(size, deque.size());
    }

    @Test
    public void testIterator() throws Exception {
        Object[] objects = deque.toArray();
        Iterator<String> iterator = deque.iterator();
        for (Object object : objects) {
            assertTrue(iterator.hasNext());
            assertEquals(object, iterator.next());
            iterator.remove();
        }
        assertFalse(iterator.hasNext());
        assertEquals(0, deque.size());
    }

    @Test
    public void testToArray() throws Exception {
        Object[] objects = deque.toArray();
        assertEquals(true, Arrays.equals(objects, startArr));
    }

    @Test
    public void testToArrayWithParam() throws Exception {
        String[] result = new String[startArr.length];
        String[] objects = deque.toArray(result);
        assertEquals(true, Arrays.equals(objects, startArr));
        assertEquals(true, result == objects);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDescendingIterator() throws Exception {
        deque.descendingIterator();
    }

}