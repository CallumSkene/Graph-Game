package com.graph.cs308group8;

import java.io.File;
import java.util.*;

public class Model implements ModelADT {
    private final File file;
    private final TreeMap<NodeADT, LinkedHashSet<EdgeADT>> graph = new TreeMap<>();

    public Model(final String filename) {
        this.file = new File(filename);
        createGraph();
    }

    private Optional<NodeADT> findMatchingNode(final NodeADT x) {
        return this.graph.keySet().stream().filter(node -> node.equals(x)).findFirst();
    }

    private void createGraph() {
        try {
            Scanner scanner = new Scanner(this.file);
            while (scanner.hasNextLine()) {
                // Current line in text file
                final String[] line = scanner.nextLine().replaceAll("\\D+"," ").split(" ");
                for(int i = 0; i < 2; i++) {
                    Edge edge = new Edge(new Node(Integer.parseInt(line[1 - i])), Integer.parseInt(line[2]));
                    // Finds matching object
                    final NodeADT node = new Node(Integer.parseInt(line[i]));
                    final Optional<NodeADT> inGraph = this.findMatchingNode(node);
                    // Adds to existing edges
                    LinkedHashSet<EdgeADT> edges = inGraph.isPresent() ? this.graph.get(inGraph.get()) : new LinkedHashSet<>();
                    edges.add(edge);
                    this.graph.put(inGraph.orElse(node), edges);
                }
            }
            scanner.close();
        } catch (Exception error) { error.printStackTrace(); }
    }

    public static boolean compareRoutes(String input, List<PairADT<Integer, List<NodeADT>>> pairs) {
        if(input.matches(".*[a-z].*")) {
            return false;
        }
        String[] split = input.split(",[ ]*");
        if(Arrays.stream(split).anyMatch(string -> string.contains(" "))) {
            return false;
        }
        if(split.length == 1) {
            for(PairADT<Integer, List<NodeADT>> pairADT : pairs) {
                Pair<Integer, List<NodeADT>> pair = (Pair<Integer, List<NodeADT>>) pairADT;
                if(pair.key().equals(Integer.valueOf(input))) {
                    return true;
                }
            }
        }
        innerLoop:
        for(PairADT<Integer, List<NodeADT>> pairADT : pairs) {
            Pair<Integer, List<NodeADT>> pair = (Pair<Integer, List<NodeADT>>) pairADT;
            if(split.length > pair.value().size()) {
                continue;
            }
            for(int i = 0; i < pair.value().size(); i++) {
                if(i + 1 > split.length) {
                    continue innerLoop;
                }
                Node node = (Node) pair.value().get(i);
                if(node.value() != Integer.parseInt(split[i])) {
                    continue innerLoop;
                }
            }
            return true;
        }
        return false;
    }




    public TreeMap<NodeADT, LinkedHashSet<EdgeADT>> getGraph() { return this.graph; }

    public List<PairADT<Integer, List<NodeADT>>> calcRoutes(final NodeADT start, final NodeADT destination) {
        TreeMap<NodeADT, Integer> distances = new TreeMap<>();
        TreeMap<NodeADT, List<List<NodeADT>>> paths = new TreeMap<>();
        PriorityQueue<NodeADT> pq = new PriorityQueue<>(Comparator.comparingInt(value -> distances.getOrDefault(value, Integer.MAX_VALUE)));

        distances.put(start, 0);
        paths.put(start, new ArrayList<>(List.of(List.of(start))));
        pq.offer(start);

        while (!pq.isEmpty()){
            NodeADT current = pq.poll();

            if (current.equals(destination)){
                break;
            }

            for (EdgeADT edge : this.graph.get(current)){
                Edge castedEdge = (Edge) edge;
                NodeADT nextNode = castedEdge.destination();
                int distanceFromStart = distances.get(current) + castedEdge.weight();

                if (distances.containsKey(nextNode)){
                    int distStartToNext = distances.get(nextNode);
                    if (distanceFromStart < distStartToNext){
                        distances.put(nextNode, distanceFromStart);
                        List<List<NodeADT>> newPaths = new ArrayList<>();
                        for (List<NodeADT> path : paths.get(current)) {
                            List<NodeADT> newPath = new ArrayList<>(path);
                            newPath.add(nextNode);
                            newPaths.add(newPath);
                        }
                        paths.put(nextNode, newPaths);
                        pq.remove(nextNode);
                        pq.offer(nextNode);
                    } else if (distanceFromStart == distStartToNext) {
                        List<List<NodeADT>> existingPaths = paths.getOrDefault(nextNode, new ArrayList<>());
                        for (List<NodeADT> path : paths.get(current)) {
                            List<NodeADT> newPath = new ArrayList<>(path);
                            newPath.add(nextNode);
                            existingPaths.add(newPath);
                        }
                        paths.put(nextNode, existingPaths);
                    }
                } else {
                    distances.put(nextNode, distanceFromStart);
                    List<List<NodeADT>> newPaths = new ArrayList<>();
                    for (List<NodeADT> path : paths.get(current)) {
                        List<NodeADT> newPath = new ArrayList<>(path);
                        newPath.add(nextNode);
                        newPaths.add(newPath);
                    }
                    paths.put(nextNode, newPaths);
                    pq.offer(nextNode);
                }
            }
        }

        List<PairADT<Integer, List<NodeADT>>> shortestPaths = new ArrayList<>();
        int destDistance = distances.get(destination);
        for (List<NodeADT> path : paths.getOrDefault(destination, new ArrayList<>())) {
            shortestPaths.add(new Pair<>(destDistance, path));
        }

        for (PairADT<Integer, List<NodeADT>> shortestPath : shortestPaths) {
            System.out.println(shortestPath);
        }
        return shortestPaths;
    }


    public Pair<NodeADT, NodeADT> createRandomPair() {
        Random random = new Random();

        NodeADT nodeX = (NodeADT) this.graph.keySet().toArray()[0];
        NodeADT nodeY = (NodeADT) this.graph.keySet().toArray()[0];

        while(nodeX.equals(nodeY)) {
            nodeX = (NodeADT) this.graph.keySet().toArray()[random.nextInt(this.graph.keySet().size())];
            nodeY = (NodeADT) this.graph.keySet().toArray()[random.nextInt(this.graph.keySet().size())];
        }
        return new Pair<>(nodeX, nodeY);
    }

    public void printGraph() {
        for(Map.Entry<NodeADT, LinkedHashSet<EdgeADT>> entry : this.graph.entrySet()) {
            System.out.printf("Key: %s; Values: %s\n", entry.getKey(), entry.getValue());
        }
    }
}
