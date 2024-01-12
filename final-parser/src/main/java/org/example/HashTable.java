package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class HashTable<K, V> {
    private LinkedList<Pair<K, V>>[] table;
    private int size;

    public HashTable() {
        this.table = new LinkedList[10];
        this.size = 0;
    }

    public HashTable(int capacity) {
        this.table = new LinkedList[capacity];
        this.size = 0;
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Pair<K, V> pair : table[index]) {
            if (pair.getKey().equals(key)) {
                pair.setValue(value);
                return;
            }
        }

        table[index].add(new Pair<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = getIndex(key);
        if (table[index] != null) {
            for (Pair<K, V> pair : table[index]) {
                if (pair.getKey().equals(key)) {
                    return pair.getValue();
                }
            }
        }
        return null;
    }

    public void remove(K key) {
        int index = getIndex(key);
        if (table[index] != null) {
            LinkedList<Pair<K, V>> list = table[index];
            for (Pair<K, V> pair : list) {
                if (pair.getKey().equals(key)) {
                    list.remove(pair);
                    size--;
                    return;
                }
            }
        }
    }

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode) % table.length;
        return index;
    }

    public List<K> getKeys() {
        List<K> keys = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Pair<K, V> pair : table[i]) {
                    keys.add(pair.getKey());
                }
            }
        }
        return keys;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        boolean first = true;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Pair<K, V> pair : table[i]) {
                    if (!first) {
                        sb.append(", ");
                    } else {
                        first = false;
                    }
                    sb.append(pair.getKey()).append(" = ").append(pair.getValue());
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }
}