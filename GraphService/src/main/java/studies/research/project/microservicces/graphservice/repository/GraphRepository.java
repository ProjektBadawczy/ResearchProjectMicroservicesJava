package studies.research.project.microservicces.graphservice.repository;

import org.springframework.stereotype.Repository;
import studies.research.project.microservicces.graphservice.model.Graph;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GraphRepository {
    List<Graph> graphs;

    @PostConstruct
    private void initializeGraphs() {
        graphs = new ArrayList<>();
        int[][] adjMatrix = {{0, 2, 3, 4},
                {1, 0, 3, 4},
                {1, 2, 0, 4},
                {1, 2, 3, 0}};
        Graph graph = new Graph(0, 4, adjMatrix);
        graphs.add(graph);
    }

    public Graph getGraph(int id) {
        return graphs.get(id);
    }
}

