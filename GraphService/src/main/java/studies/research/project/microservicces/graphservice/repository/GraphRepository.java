package studies.research.project.microservicces.GraphService.repository;

import graph.model.DirectedGraph;
import graph.model.Graph;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GraphRepository {
    List<Graph> graphs;
    List<DirectedGraph> directedGraphs;

    @PostConstruct
    private void initializeGraphs() {
        graphs = new ArrayList<>();
//        int[][] adjMatrix = {{0, 2, 3, 4},
//                {1, 0, 3, 4},
//                {1, 2, 0, 4},
//                {1, 2, 3, 0}};
        int[][] adjMatrix = {
                {0, 16, 13, 0, 0, 0},
                {0, 0, 10, 12, 0, 0},
                {0, 4, 0, 0, 14, 0},
                {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4},
                {0, 0, 0, 0, 0, 0}
        };
        Graph graph = new Graph(0, 6, adjMatrix);
        graphs.add(graph);

        directedGraphs = new ArrayList<>();
        int vertices = 6;
        int source = 0;
        int sink = 5;

        DirectedGraph dg = new DirectedGraph(vertices, 0);

        dg.addEdge(0, 1, 16);
        dg.addEdge(0, 2, 13);
        dg.addEdge(1, 2, 10);
        dg.addEdge(2, 1, 4);
        dg.addEdge(1, 3, 12);
        dg.addEdge(3, 2, 9);
        dg.addEdge(2, 4, 14);
        dg.addEdge(4, 5, 4);
        dg.addEdge(4, 3, 7);
        dg.addEdge(3, 5, 20);
        directedGraphs.add(dg);
    }

    public Graph getGraph(int id) {
        return graphs.get(id);
    }
    public DirectedGraph getDirectedGraph(int id) {
        return directedGraphs.get(id);
    }
}
