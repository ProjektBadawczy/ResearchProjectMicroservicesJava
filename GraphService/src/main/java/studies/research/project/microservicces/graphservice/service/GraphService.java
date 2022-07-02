package studies.research.project.microservicces.graphservice.service;

import org.springframework.stereotype.Service;
import studies.research.project.microservicces.graphservice.model.Graph;
import studies.research.project.microservicces.graphservice.repository.GraphRepository;

@Service
public class GraphService {

    private final GraphRepository graphRepository;

    public GraphService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public Graph getGraph(int id) {
        return graphRepository.getGraph(id);
    }
}
