package studies.research.project.microservicces.bfsservice.model;

public record BFSRequest(
        Graph graph,
        int source,
        int destination
) {
}
