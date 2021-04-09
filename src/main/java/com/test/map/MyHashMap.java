package com.test.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.test.set.MyHashSet;

public class MyHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size;
    private Entry[] table;
    private int capacity;
    private int threshold;

    public MyHashMap(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        table = new Entry[capacity];
        threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return entryByKey(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Collection values = values();
        return values.contains(value);
    }

    @Override
    public V get(Object key) {
        Entry<K, V> entry = entryByKey(key);
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size == threshold) {
            resize();
        }

        Entry<K, V> entry = entryByKey(key);
        V oldValue = null;
        if (entry == null) {
            add(key, value);
        } else {
            oldValue = entry.getValue();
            entry.setValue(value);
        }
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }

        int index = getBucketIndex(key);
        Entry<K, V> head = table[index];
        if (head.next == null) {
            V value = head.getValue();
            table[index] = null;
            size--;
            return value;
        }

        if (key == null) {
            return removeNullEntry();
        } else {
            return removeEntry(key, index);
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            if (entry != null) {
                K key = entry.getKey();
                V value = entry.getValue();
                put(key, value);
            }
        }
    }

    @Override
    public void clear() {
        for (Entry entry : table) {
            for (Entry current = entry; current != null; ) {
                Entry next = current.next;
                current.key = null;
                current.value = null;
                current.next = null;
                current = next;
            }
        }
        table = new Entry[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keySet() {
        final Set<K> keySet = new MyHashSet<>();
        for (Entry<K, V> entry : table) {
            for (Entry<K, V> current = entry; current != null; current = current.next) {
                keySet.add(current.getKey());
            }
        }
        return keySet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<V> values() {
        final Set<V> values = new MyHashSet<>();
        for (Entry<K, V> entry : table) {
            for (Entry<K, V> current = entry; current != null; current = current.next) {
                values.add(current.getValue());
            }
        }
        return values;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> entrySet = new MyHashSet<>();
        for (Entry<K, V> entry : table) {
            for (Entry<K, V> current = entry; current != null; current = current.next) {
                entrySet.add(current);
            }
        }
        return entrySet;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
        final StringBuilder builder = new StringBuilder();
        builder.append("{");
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            K key = entry.getKey();
            V value = entry.getValue();
            builder.append(key).append("=").append(value);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private int getBucketIndex(Object key) {
        if (key == null) {
            return 0;
        }
        int hashCode = key.hashCode();
        return hashCode % capacity;
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V> entryByKey(Object key) {
        int index = getBucketIndex(key);
        if (table[index] == null) {
            return null;
        } else {
            Entry head = table[index];
            if (key == null) {
                for (Entry start = head; start != null; start = start.next) {
                    if (start.key == null) {
                        return start;
                    }
                }
            } else {
                int hashCode = key.hashCode();
                for (Entry start = head; start != null; start = start.next) {
                    if (start.hashCode == hashCode && key.equals(start.key)) {
                        return start;
                    }
                }
            }
        }
        return null;
    }

    private void add(K key, V value) {
        int index = getBucketIndex(key);
        if (table[index] == null) {
            table[index] = new Entry<K, V>(key, value, null);
        } else {
            @SuppressWarnings("unchecked")
            Entry<K, V> head = table[index];
            Entry<K, V> newEntry = new Entry<K, V>(key, value, head);
            table[index] = newEntry;
        }
        size++;
    }

    @SuppressWarnings("unchecked")
    private V removeEntry(Object key, int index) {
        int hashCode = key.hashCode();
        Entry<K, V> previous = null;
        for (Entry<K, V> current = table[index]; current != null; current = current.next) {
            if (hashCode == current.hashCode && key.equals(current.key)) {
                V value = current.getValue();
                if (previous == null) {
                    table[index] = current.next;
                } else if (current.next == null) {
                    previous.next = null;
                } else {
                    previous.next = current.next;
                }
                size--;
                return value;
            } else {
                previous = current;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private V removeNullEntry() {
        Entry<K, V> previous = null;
        for (Entry<K, V> current = table[0]; current != null; current = current.next) {
            if (current.key == null) {
                V value = current.getValue();
                if (previous == null) {
                    table[0] = current.next;
                } else if (current.next == null) {
                    previous.next = null;
                } else {
                    previous.next = current.next;
                }
                size--;
                return value;
            } else {
                previous = current;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity = capacity * 2;
        threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
        Entry[] newTable = new Entry[capacity];

        for (Entry<K, V> entry : table) {
            for (Entry<K, V> current = entry; current != null; current = current.next) {
                K key = current.getKey();
                V value = current.getValue();
                int index = getBucketIndex(key);
                if (newTable[index] == null) {
                    newTable[index] = new Entry<K, V>(key, value, null);
                } else {
                    Entry<K, V> head = newTable[index];
                    Entry<K, V> newEntry = new Entry<K, V>(key, value, head);
                    newTable[index] = newEntry;
                }
            }
        }
        table = newTable;
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {

        int hashCode;
        K key;
        Entry<K, V> next;
        V value;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            hashCode = (key == null) ? 0 : key.hashCode();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

    }

}
