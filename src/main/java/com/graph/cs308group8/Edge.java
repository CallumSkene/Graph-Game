package com.graph.cs308group8;

public record Edge(NodeADT destination, int weight) implements EdgeADT {
    @Override
    public String toString() {
        return String.format("Edge (%s) %d", this.destination, this.weight);
    }
}


