package studies.research.project.microservicces.bfsservice.model;

public record BFSResult(
        int[] parents,
        boolean success
) {
}
