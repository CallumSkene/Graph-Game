package com.graph.cs308group8;

public record Node(int value) implements Comparable<Node>, NodeADT {
    public int compareTo(Node node) {
        return this.value - node.value;
    }
    public String toString() {
        return String.format("Node %d", this.value);
    }
}
