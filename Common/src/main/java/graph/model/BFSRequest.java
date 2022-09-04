package graph.model;

public record BFSRequest(
        Graph graph,
        int source,
        int destination
) {
}
