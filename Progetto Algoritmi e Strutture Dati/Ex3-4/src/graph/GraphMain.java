package graph;

public class GraphMain {

    public static void main(String[] args) {
        // Create a directed graph
        Graph<String, String> graph = new Graph<>(false);
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "C", "AC");
        graph.addEdge("B", "C", "BC");
        System.out.println("AGGIUNTA ARCO : " + graph.addEdge("A", "B", "AB"));
        System.out.println(graph);
        System.out.println("REMOVE NODE : " + graph.removeNode("A"));
        System.out.println(graph);
    }
}
