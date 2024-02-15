package com.graph.cs308group8;

import java.util.*;

public interface ModelADT {
    List<PairADT<Integer, List<NodeADT>>> calcRoutes(final NodeADT start, final NodeADT destination);
    TreeMap<NodeADT, LinkedHashSet<EdgeADT>> getGraph();
    Pair<NodeADT, NodeADT> createRandomPair();
    void printGraph();
}
