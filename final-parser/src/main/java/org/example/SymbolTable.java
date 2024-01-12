package org.example;

public class SymbolTable {
    private HashTable<Integer, Object> symbolTable;
    private int counter = 0;

    public SymbolTable() {
        symbolTable = new HashTable<>();
    }

    public void put(Object value) {
        if (getPosition(value) == -1) {
            int key = counter++;
            symbolTable.put(key, value);
        }
    }

    public int getPosition(Object value) {
        for (Integer key : symbolTable.getKeys()) {
            if (symbolTable.get(key).equals(value)) {
                return key;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return symbolTable.toString();
    }
}