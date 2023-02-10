package studies.research.project.microservicces.edmondskarpservice.model;

public record BFSRequest(
        Graph graph,
        int source,
        int destination
) {
}
