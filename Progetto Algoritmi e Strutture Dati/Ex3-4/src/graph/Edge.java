package graph;

/**
 * Edge implementation used for graph.
 *
 */
public class Edge<V, L> implements AbstractEdge<V, L> {
    private V start;
    private V end;
    private L label;

    /**
     * Constructor of Edge class
     * 
     * @param start Source node value
     * @param end   Destination node value
     * @param label Label of edge
     */
    public Edge(V start, V end, L label) {
        this.start = start;
        this.end = end;
        this.label = label;
    }

    // Getter and Setter of source
    public V getStart() {
        return this.start;
    }

    public void setStart(V start) {
        this.start = start;
    }

    // Getter and Setter of destination
    public V getEnd() {
        return this.end;
    }

    public void setEnd(V end) {
        this.end = end;
    }

    // Getter and Setter of label
    public L getLabel() {
        return this.label;
    }

    public void setLabel(L label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Edge [start=" + start + ", end=" + end + ", label=" + label + "]";
    }
}