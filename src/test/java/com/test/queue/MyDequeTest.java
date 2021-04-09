package com.test.queue;

import java.util.Arrays;
import java.util.Deque;
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

class MyDequeTest {

    private final String[] startArr = {"a", "b", "a", "d", "a", null, "k", "", "s", "z"};
    private Deque<String> deque;

    @BeforeEach
    void setUp() {
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
    void testAddFirst() {
        int startSize = deque.size();
        deque.addFirst("newValue");
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getFirst());

        deque.addFirst(null);
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertNull(deque.getFirst());
    }

    @Test
    void testAddLast() {
        int startSize = deque.size();
        deque.addLast("newValue");
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getLast());

        deque.addLast(null);
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertNull(deque.getLast());
    }

    @Test
    void testOfferFirst() {
        int startSize = deque.size();
        assertTrue(deque.offerFirst("newValue"));
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getFirst());

        assertTrue(deque.offerFirst(null));
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertNull(deque.getFirst());
    }

    @Test
    void testOfferLast() {
        int startSize = deque.size();
        assertTrue(deque.offerLast("newValue"));
        assertTrue(deque.contains("newValue"));
        assertEquals(startSize + 1, deque.size());
        assertEquals("newValue", deque.getLast());

        assertTrue(deque.offerLast(null));
        assertTrue(deque.contains(null));
        assertEquals(startSize + 2, deque.size());
        assertNull(deque.getLast());
    }

    @Test
    void testRemoveFirst() {
        assertEquals("a", deque.removeFirst());
        assertEquals("b", deque.removeFirst());
        assertEquals("a", deque.removeFirst());
        assertEquals("d", deque.removeFirst());
        assertEquals("a", deque.removeFirst());
        assertNull(deque.removeFirst());
        assertEquals("k", deque.removeFirst());
        assertEquals("", deque.removeFirst());
        assertEquals("s", deque.removeFirst());
        assertEquals("z", deque.removeFirst());

        assertThrows(NoSuchElementException.class, () -> deque.removeFirst());
    }

    @Test
    void testRemoveLast() {
        assertEquals("z", deque.removeLast());
        assertEquals("s", deque.removeLast());
        assertEquals("", deque.removeLast());
        assertEquals("k", deque.removeLast());
        assertNull(deque.removeLast());
        assertEquals("a", deque.removeLast());
        assertEquals("d", deque.removeLast());
        assertEquals("a", deque.removeLast());
        assertEquals("b", deque.removeLast());
        assertEquals("a", deque.removeLast());

        assertThrows(NoSuchElementException.class, () -> deque.removeLast());
    }

    @Test
    void testPollFirst() {
        assertEquals("a", deque.pollFirst());
        assertEquals("b", deque.pollFirst());
        assertEquals("a", deque.pollFirst());
        assertEquals("d", deque.pollFirst());
        assertEquals("a", deque.pollFirst());
        assertNull(deque.pollFirst());
        assertEquals("k", deque.pollFirst());
        assertEquals("", deque.pollFirst());
        assertEquals("s", deque.pollFirst());
        assertEquals("z", deque.pollFirst());
        assertNull(deque.pollFirst());
    }

    @Test
    void testPollLast() {
        assertEquals("z", deque.pollLast());
        assertEquals("s", deque.pollLast());
        assertEquals("", deque.pollLast());
        assertEquals("k", deque.pollLast());
        assertNull(deque.pollLast());
        assertEquals("a", deque.pollLast());
        assertEquals("d", deque.pollLast());
        assertEquals("a", deque.pollLast());
        assertEquals("b", deque.pollLast());
        assertEquals("a", deque.pollLast());
        assertNull(deque.pollLast());
    }

    @Test
    void testGetFirst() {
        assertEquals("a", deque.getFirst());
        assertEquals("a", deque.getFirst());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.getFirst());
        assertEquals("newValue", deque.getFirst());
        deque.clear();

        assertThrows(NoSuchElementException.class, () -> deque.getFirst());
    }

    @Test
    void testGetLast() {
        assertEquals("z", deque.getLast());
        assertEquals("z", deque.getLast());
        deque.addLast("newValue");
        assertEquals("newValue", deque.getLast());
        assertEquals("newValue", deque.getLast());
        deque.clear();

        assertThrows(NoSuchElementException.class, () -> deque.getLast());
    }

    @Test
    void testPeekFirst() {
        assertEquals("a", deque.peekFirst());
        assertEquals("a", deque.peekFirst());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.peekFirst());
        assertEquals("newValue", deque.peekFirst());
        deque.clear();
        assertNull(deque.peekFirst());
    }

    @Test
    void testPeekLast() {
        assertEquals("z", deque.peekLast());
        assertEquals("z", deque.peekLast());
        deque.addLast("newValue");
        assertEquals("newValue", deque.peekLast());
        assertEquals("newValue", deque.peekLast());
        deque.clear();
        assertNull(deque.peekLast());
    }

    @Test
    void testRemoveFirstOccurrence() {
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
    void testRemoveLastOccurrence() {
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
    void testAdd() {
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
    void testOffer() {
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

    @Test
    void testRemove() {
        assertEquals("a", deque.remove());
        assertEquals("b", deque.remove());
        assertEquals("a", deque.remove());
        deque.clear();

        assertThrows(NoSuchElementException.class, () -> deque.remove());
    }

    @Test
    void testPoll() {
        assertEquals("a", deque.poll());
        assertEquals("b", deque.poll());
        assertEquals("a", deque.poll());
        deque.clear();
        assertNull(deque.poll());
    }

    @Test
    void testElement() {
        assertEquals("a", deque.element());
        assertEquals("a", deque.element());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.element());
        assertEquals("newValue", deque.element());
        deque.clear();

        assertThrows(NoSuchElementException.class, () -> deque.element());
    }

    @Test
    void testPeek() {
        assertEquals("a", deque.peek());
        assertEquals("a", deque.peek());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.peek());
        assertEquals("newValue", deque.peek());
        deque.clear();
        assertNull(deque.peek());
    }

    @Test
    void testPush() {
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
        assertNull(deque.getFirst());
        assertEquals(size, deque.size());
    }

    @Test
    void testPop() {
        assertEquals("a", deque.pop());
        assertEquals("b", deque.pop());
        deque.addFirst("newValue");
        assertEquals("newValue", deque.pop());
        assertEquals("a", deque.pop());
        deque.clear();

        assertThrows(NoSuchElementException.class, () -> deque.pop());
    }

    @Test
    void testRemoveWithParam() {
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
    void testAddAll() {
        Deque<String> copy = getDefaultDeque();
        copy.add("a");
        copy.add("b");
        copy.add("c");
        deque.addAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, deque);
    }

    @Test
    void testRemoveAll() {
        Deque<String> copy = getDefaultDeque();
        copy.remove("a");
        copy.remove("b");
        copy.remove("c");
        deque.removeAll(Arrays.asList("a", "b", "c"));
        assertEquals(copy, deque);
    }

    @Test
    void testRetainAll() {
        Deque<String> newDeque = new MyDeque<>();
        newDeque.add("k");
        newDeque.add("s");
        newDeque.add("z");
        deque.retainAll(newDeque);
        assertEquals(newDeque, deque);
    }

    @Test
    void testClear() {
        deque.clear();
        assertEquals(0, deque.size());
        deque.add("a");
        assertEquals(1, deque.size());
    }

    @Test
    void testContains() {
        assertFalse(deque.contains("notThere"));
        assertTrue(deque.contains(""));
        assertTrue(deque.contains(null));
        assertTrue(deque.contains("a"));
    }

    @Test
    void testSize() {
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
    void testIterator() {
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
    void testToArray() {
        Object[] objects = deque.toArray();
        assertArrayEquals(objects, startArr);
    }

    @Test
    void testToArrayWithParam() {
        String[] result = new String[startArr.length];
        String[] objects = deque.toArray(result);
        assertArrayEquals(objects, startArr);
        assertSame(result, objects);
    }

    @Test
    void testDescendingIterator() {
        assertThrows(UnsupportedOperationException.class, () -> deque.descendingIterator());
    }

}
