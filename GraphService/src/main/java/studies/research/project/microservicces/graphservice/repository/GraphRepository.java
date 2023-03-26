package studies.research.project.microservicces.graphservice.repository;

import org.springframework.stereotype.Repository;
import studies.research.project.microservicces.graphservice.graphloader.GraphLoader;
import studies.research.project.microservicces.graphservice.model.DirectedGraph;
import studies.research.project.microservicces.graphservice.model.Graph;
import studies.research.project.microservicces.graphservice.model.GraphsContainer;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Repository
public class GraphRepository {
    List<Graph> graphs;
    List<DirectedGraph> directedGraphs;
    private final GraphLoader graphLoader;

    public GraphRepository(GraphLoader graphLoader) {
        this.graphLoader = graphLoader;
    }

    @PostConstruct
    private void initializeGraphs() throws IOException {
        List<GraphsContainer> graphsFromFile = graphLoader.loadGraphsFromFile("random_set.json");
        graphs = graphsFromFile.stream()
                .map(GraphsContainer::getGraph)
                .toList();
        directedGraphs = graphsFromFile.stream()
                .map(GraphsContainer::getDirectedGraph)
                .toList();
    }

    public Graph getGraph(int id) {
        return graphs.stream()
                .filter(graph -> graph.id() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Graph with id %d does not exist!".formatted(id)));
    }
    public DirectedGraph getDirectedGraph(int id) {
        return directedGraphs.stream()
                .filter(directedGraph -> directedGraph.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("DirectedGraph with id %d does not exist!".formatted(id)));
    }
}
