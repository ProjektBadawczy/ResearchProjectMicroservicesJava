package studies.research.project.microservicces.EdmondsKarpService.service;

import graph.model.BFSRequest;
import graph.model.BFSResult;
import graph.model.DirectedGraph;
import graph.model.Graph;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import studies.research.project.microservicces.EdmondsKarpService.utils.UrlDiscoverer;

import java.util.Map;

import static java.lang.Math.min;

@Service
public class EdmondsKarpService {

    private final UrlDiscoverer urlDiscoverer;

    public EdmondsKarpService(UrlDiscoverer urlDiscoverer) {
        this.urlDiscoverer = urlDiscoverer;
    }

    public int calculateMaxFlow(Graph graph, int source, int destination) {
        if (!areSourceAndGraphParametersValid(graph, source, destination)) {
            throw new IllegalArgumentException("Invalid source or destination parameter!\n Number of vertices: %s\n Source: %s\n Destination: %s\n".formatted(graph.numberOfVertices(), source, destination));
        }

        int u, v;
        Graph residualGraph = graph.clone();
        int max_flow = 0;
        BFSResult bfsResult = new RestTemplate().postForObject(urlDiscoverer.getBfsServiceUrl(), new BFSRequest(residualGraph, source, destination), BFSResult.class);
        while (bfsResult.success()) {
            int path_flow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = bfsResult.parents()[v]) {
                u = bfsResult.parents()[v];
                path_flow = min(path_flow, residualGraph.adjacencyMatrix()[u][v]);
            }

            for (v = destination; v != source; v = bfsResult.parents()[v]) {
                u = bfsResult.parents()[v];
                residualGraph.adjacencyMatrix()[u][v] -= path_flow;
                residualGraph.adjacencyMatrix()[v][u] += path_flow;
            }

            max_flow += path_flow;
            bfsResult = new RestTemplate().postForObject(urlDiscoverer.getBfsServiceUrl(), new BFSRequest(residualGraph, source, destination), BFSResult.class);
        }

        return max_flow;
    }

    private boolean areSourceAndGraphParametersValid(Graph graph, int source, int destination) {
        return source >= 0 && source < graph.numberOfVertices() && destination >= 0 && destination < graph.numberOfVertices();
    }
}
