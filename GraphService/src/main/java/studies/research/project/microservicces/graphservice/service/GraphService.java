package studies.research.project.microservicces.GraphService.service;

import graph.model.DirectedGraph;
import graph.model.Graph;
import org.springframework.stereotype.Service;
import studies.research.project.microservicces.GraphService.repository.GraphRepository;

@Service
public class GraphService {

    private final GraphRepository graphRepository;

    public GraphService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public Graph getGraph(int id) {
        return graphRepository.getGraph(id);
    }

    public DirectedGraph getDirectedGraph(int id) {
        return graphRepository.getDirectedGraph(id);
    }
}
