package com.test.map;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MyHashMapTest {

    private Map<String, String> map = new MyHashMap<>();

    @Before
    public void setUp() throws Exception {
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
    public void testSize() throws Exception {
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
    public void testIsEmpty() throws Exception {
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
        map.put("a", "a");
        assertFalse(map.isEmpty());
    }

    @Test
    public void testContainsKey() throws Exception {
        assertTrue(map.containsKey("a"));
        assertTrue(map.containsKey("b"));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsKey(""));
        assertFalse(map.containsKey("AAA"));
        assertFalse(map.containsKey("BBB"));
        assertFalse(map.containsKey("DDD"));
    }

    @Test
    public void testContainsValue() throws Exception {
        assertFalse(map.containsValue("a"));
        assertFalse(map.containsValue("b"));
        assertTrue(map.containsValue(null));
        assertTrue(map.containsValue(""));
        assertTrue(map.containsValue("AAA"));
        assertTrue(map.containsValue("BBB"));
        assertTrue(map.containsValue("DDD"));
    }

    @Test
    public void testGet() throws Exception {
        assertEquals("AAA", map.get("a"));
        assertEquals("BBB", map.get("b"));
        assertEquals("", map.get(""));
        assertNull(map.get(null));
        assertNull(map.get("notThere"));
    }

    @Test
    public void testPut() throws Exception {
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
    public void testRemove() throws Exception {
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
    public void testPutAll() throws Exception {
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
    public void testClear() throws Exception {
        assertEquals(8, map.size());
        assertFalse(map.isEmpty());
        map.clear();
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @Test
    public void testKeySet() throws Exception {
        Set<String> keySet = map.keySet();
        assertEquals(map.size(), keySet.size());
        for (String key : keySet) {
            assertTrue(map.containsKey(key));
        }
    }

    @Test
    public void testValues() throws Exception {
        Collection<String> values = map.values();
        assertEquals(map.size(), values.size());
        for (String value : values) {
            assertTrue(map.containsValue(value));
        }
    }

    @Test
    public void testEntrySet() throws Exception {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        assertEquals(map.size(), entrySet.size());
        for (Map.Entry<String, String> entry : entrySet) {
            assertTrue(map.containsKey(entry.getKey()));
            assertTrue(map.containsValue(entry.getValue()));
        }
    }

}