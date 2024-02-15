package com.graph.cs308group8;

public record Pair<K, V>(K key, V value) implements PairADT<K, V> {
    @Override
    public String toString() {
        return String.format("Pair %s %s", this.key, this.value);
    }
}
