package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Graph implementation with hashmap.
 * 
 */
public class Graph<V extends Comparable<V>, L> implements AbstractGraph<V, L> {
    private Set<V> nodes;
    private Map<V, Set<AbstractEdge<V, L>>> edges;
    private boolean isDirected;
    private boolean isLabelled;

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
        this.isLabelled = true;
        this.nodes = new HashSet<>();
        this.edges = new HashMap<>();
    }

    @Override
    public boolean isDirected() {
        return isDirected;
    }

    @Override
    public boolean isLabelled() {
        return isLabelled;
    }

    @Override
    public boolean addNode(V a) {
        if (!nodes.contains(a)) {
            nodes.add(a);
            return true;
        }

        return false;
    }

    @Override
    public boolean addEdge(V a, V b, L l) {
        if (!nodes.contains(a) || !nodes.contains(b)) {
            return false;
        }

        if (!edges.containsKey(a)) {
            edges.put(a, new HashSet<>());
        }

        if (!isDirected) {
            if (!edges.containsKey(b)) {
                edges.put(b, new HashSet<>());
            }
            Edge<V, L> reverseEdge = new Edge<>(b, a, l);
            edges.get(b).add(reverseEdge);
        }

        Edge<V, L> newEdge = new Edge<>(a, b, l);
        edges.get(a).add(newEdge);

        if (l == null || l.equals("")) {
            isLabelled = false;
        }

        return true;
    }

    @Override
    public boolean containsNode(V a) {
        return nodes.contains(a);
    }

    @Override
    public boolean containsEdge(V a, V b) {
        if (edges.containsKey(a)) {
            for (AbstractEdge<V, L> edge : edges.get(a)) {
                if (edge.getStart().equals(a) && edge.getEnd().equals(b)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean removeNode(V a) {
        if (!nodes.contains(a)) {
            return false;
        }

        edges.get(a).removeAll(edges.get(a));
        for (Set<AbstractEdge<V, L>> edgeSet : edges.values()) {
            edgeSet.removeIf(edge -> edge.getEnd().equals(a));
        }

        return true;
    }

    @Override
    public boolean removeEdge(V a, V b) {
        if (!edges.containsKey(a)) {
            return false;
        }

        if (!isDirected) {
            if (edges.containsKey(b)) {
                edges.get(b).removeIf(edge -> edge.getStart().equals(b) && edge.getEnd().equals(a));
            }
        }

        if (edges.containsKey(a)) {
            edges.get(a).removeIf(edge -> edge.getStart().equals(a) && edge.getEnd().equals(b));
        }

        return true;
    }

    @Override
    public int numNodes() {
        return nodes.size();
    }

    @Override
    public int numEdges() {
        int size = 0;
        for (V node : nodes) {
            size += edges.get(node).size();
        }

        if (!isDirected) {
            size = size / 2;
        }

        return size;
    }

    @Override
    public Collection<V> getNodes() {
        return nodes;
    }

    @Override
    public Collection<? extends AbstractEdge<V, L>> getEdges() {
        List<AbstractEdge<V, L>> allEdges = new ArrayList<>();

        for (Set<AbstractEdge<V, L>> edgeSet : edges.values()) {
            allEdges.addAll(edgeSet);
        }

        return allEdges;
    }

    @Override
    public Collection<V> getNeighbours(V a) {
        Set<V> neighbours = new HashSet<>();

        if (edges.containsKey(a)) {
            for (AbstractEdge<V, L> edge : edges.get(a)) {
                neighbours.add(edge.getEnd());
            }
        }

        return neighbours;
    }

    @Override
    public L getLabel(V a, V b) {
        if (edges.containsKey(a)) {
            for (AbstractEdge<V, L> edge : edges.get(a)) {
                if (edge.getStart().equals(a) && edge.getEnd().equals(b)) {
                    return edge.getLabel();
                }
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Graph [nodes=" + nodes + ", edges=" + edges + ", isDirected=" + isDirected + ", isLabelled="
                + isLabelled + "]";
    }
}