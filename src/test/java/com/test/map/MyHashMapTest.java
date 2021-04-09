package com.test.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyHashMapTest {

    private final Map<String, String> map = new MyHashMap<>();

    @BeforeEach
    void setUp() {
        fillWithDefaultData(map);
    }

    private void fillWithDefaultData(Map<String, String> map) {
        map.put("a", "AAA");
        map.put("b", "BBB");
        map.put("a", "AAA");
        map.put("d", "DDD");
        map.put(null, null);
        map.put("k", "KKK");
        map.put("", "");
        map.put("s", "SSS");
        map.put("z", "ZZZ");
    }

    private void putElements(Map<String, String> map, int num) {
        for (int i = 0; i < num; i++) {
            String key = String.valueOf((char) i);
            String value = String.valueOf((char) i % 250);
            map.put(key, value);
        }
    }

    private void removeElements(Map<String, String> map, int num) {
        for (int i = 0; i < num; i++) {
            String key = String.valueOf((char) i);
            map.remove(key);
        }
    }

    @Test
    void testSize() {
        assertEquals(8, map.size());
        map.put("AAA", "BBB");
        map.put("BBB", "CCC");
        assertEquals(10, map.size());
        map.remove("AAA");
        map.remove("BBB");
        map.remove(null);
        map.remove("notThere");
        assertEquals(7, map.size());
        map.clear();
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @Test
    void testIsEmpty() {
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
        map.put("a", "a");
        assertFalse(map.isEmpty());
    }

    @Test
    void testContainsKey() {
        assertTrue(map.containsKey("a"));
        assertTrue(map.containsKey("b"));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsKey(""));
        assertFalse(map.containsKey("AAA"));
        assertFalse(map.containsKey("BBB"));
        assertFalse(map.containsKey("DDD"));
    }

    @Test
    void testContainsValue() {
        assertFalse(map.containsValue("a"));
        assertFalse(map.containsValue("b"));
        assertTrue(map.containsValue(null));
        assertTrue(map.containsValue(""));
        assertTrue(map.containsValue("AAA"));
        assertTrue(map.containsValue("BBB"));
        assertTrue(map.containsValue("DDD"));
    }

    @Test
    void testGet() {
        assertEquals("AAA", map.get("a"));
        assertEquals("BBB", map.get("b"));
        assertEquals("", map.get(""));
        assertNull(map.get(null));
        assertNull(map.get("notThere"));
    }

    @Test
    void testPut() {
        assertFalse(map.containsKey("c"));
        assertFalse(map.containsValue("CCC"));
        assertNull(map.put("c", "CCC"));
        assertTrue(map.containsKey("c"));
        assertTrue(map.containsValue("CCC"));
        assertEquals("CCC", map.put("c", "newValue"));
        assertTrue(map.containsKey("c"));
        assertFalse(map.containsValue("CCC"));
        assertTrue(map.containsValue("newValue"));

        long startTime = System.currentTimeMillis();
        putElements(map, 1_000_000);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Adding 1.000.000 elements: " + endTime + "ms.");
    }

    @Test
    void testRemove() {
        assertEquals(8, map.size());
        assertEquals("AAA", map.remove("a"));
        assertEquals("BBB", map.remove("b"));
        assertEquals("", map.remove(""));
        assertNull(map.remove(null));
        assertNull(map.remove("notThere"));
        assertEquals(4, map.size());
        assertFalse(map.containsKey("a"));
        assertFalse(map.containsKey("b"));
        assertFalse(map.containsKey(""));
        assertFalse(map.containsKey(null));

        int elNum = 1_000_000;
        putElements(map, elNum);
        long startTime = System.currentTimeMillis();
        removeElements(map, elNum);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Removing 1.000.000 elements from map: " + endTime + "ms.");
    }

    @Test
    void testPutAll() {
        Map<String, String> newMap = new HashMap<>();
        newMap.put("m", "MMM");
        newMap.put("n", "NNN");
        newMap.put("o", "OOO");
        assertEquals(8, map.size());
        map.putAll(newMap);
        assertEquals(11, map.size());
        assertTrue(map.containsKey("m"));
        assertTrue(map.containsKey("n"));
        assertTrue(map.containsKey("o"));
        assertTrue(map.containsValue("MMM"));
        assertTrue(map.containsValue("NNN"));
        assertTrue(map.containsValue("OOO"));
    }

    @Test
    void testClear() {
        assertEquals(8, map.size());
        assertFalse(map.isEmpty());
        map.clear();
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @Test
    void testKeySet() {
        Set<String> keySet = map.keySet();
        assertEquals(map.size(), keySet.size());
        for (String key : keySet) {
            assertTrue(map.containsKey(key));
        }
    }

    @Test
    void testValues() {
        Collection<String> values = map.values();
        assertEquals(map.size(), values.size());
        for (String value : values) {
            assertTrue(map.containsValue(value));
        }
    }

    @Test
    void testEntrySet() {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        assertEquals(map.size(), entrySet.size());
        for (Map.Entry<String, String> entry : entrySet) {
            assertTrue(map.containsKey(entry.getKey()));
            assertTrue(map.containsValue(entry.getValue()));
        }
    }

}
