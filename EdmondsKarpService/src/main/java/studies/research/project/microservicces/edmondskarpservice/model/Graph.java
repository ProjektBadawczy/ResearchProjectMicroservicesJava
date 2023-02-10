package studies.research.project.microservicces.edmondskarpservice.model;

public record Graph(
        int id,
        int numberOfVertices,
        int[][] adjacencyMatrix
) {

    @Override
    public Graph clone() {
        int [][] newAdjacencyMatrix = new int[this.adjacencyMatrix.length][];
        for(int i = 0; i < this.adjacencyMatrix.length; i++)
            newAdjacencyMatrix[i] = this.adjacencyMatrix[i].clone();
        return new Graph(this.id, this.numberOfVertices, newAdjacencyMatrix);
    }
}
