package studies.research.project.microservicces.graphservice.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import studies.research.project.microservicces.graphservice.model.Graph;

@Service
public class EdmondsKarpServiceProxy {

    private final RestTemplate restTemplate;

    public EdmondsKarpServiceProxy(RestTemplateBuilder restTemplateBuilder){
        restTemplate = restTemplateBuilder.build();
    }

    public int calculateMaxFlow(Graph graph, int source, int destination) {
        if (!areSourceAndGraphParametersValid(graph, source, destination)) {
            throw new IllegalArgumentException("Invalid source or destination parameter!\n Number of vertices: %s\n Source: %s\n Destination: %s\n".formatted(graph.numberOfVertices(), source, destination));
        }

        String url = "http://localhost:8080/";
    }

    private boolean areSourceAndGraphParametersValid(Graph graph, int source, int destination) {
        return source >= 0 && source < graph.numberOfVertices() && destination >= 0 && destination < graph.numberOfVertices();
    }
}
