package graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Node implementation with hashmap used for graph.
 * 
 */
public class Node<V extends Comparable<V>, L> {
    private V value;
    private Map<V, Edge<V, L>> edges;

    private Node<V, L> predecessor;

    private Double distanceFromSource;

    /**
     * Constructor.
     * 
     * @param value Node value
     */
    public Node(V value) {
        this.value = value;
        this.edges = new HashMap<V, Edge<V, L>>();
    }

    // Getter and Setter for T value
    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    // Getter and Setter for Map<T, Edge<T, K>> edges
    public Map<V, Edge<V, L>> getEdges() {
        return this.edges;
    }

    public void setEdges(HashMap<V, Edge<V, L>> edges) {
        this.edges = edges;
    }

    // Getter and Setter for Node<T,K> predecessor
    public Node<V, L> getPredecessor() {
        return this.predecessor;
    }

    public void setPredecessor(Node<V, L> predecessor) {
        this.predecessor = predecessor;
    }

    // Getter and Setter for Double distanceFromSource
    public Double getDistanceFromSource() {
        return this.distanceFromSource;
    }

    public void setDistanceFromSource(Double distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

}
