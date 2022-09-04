package graph.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectedGraph implements Serializable {

    private List<List<Vertex>> adjacencyList;
    private int vertices;
    private int id;

    public DirectedGraph(int verticesNumber, int identificator)
    {
        id = identificator;

        vertices = verticesNumber;

        adjacencyList = new ArrayList<>(vertices);

        for (int i = 0; i < vertices; i++)
        {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight)
    {
        adjacencyList.get(u).add(new Vertex(v, weight));
    }

    public boolean hasEdge(int u, int v)
    {
        if (u >= vertices)
            return false;

        return adjacencyList.get(u)
                .stream()
                .anyMatch(vertex -> vertex.getI() == v);
    }

    // Returns null if no edge
    // is found between u and v
    public Vertex getEdge(int u, int v)
    {
        return adjacencyList.get(u)
                .stream()
                .filter(vertex -> vertex.getI() == v)
                .findFirst()
                .orElse(null);
    }
}
