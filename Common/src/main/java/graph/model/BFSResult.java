package graph.model;

public record BFSResult(
        int[] parents,
        boolean success
) {
}
