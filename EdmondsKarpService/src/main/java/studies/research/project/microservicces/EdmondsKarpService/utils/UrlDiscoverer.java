package studies.research.project.microservicces.EdmondsKarpService.utils;

import graph.model.Graph;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;


@Service
public class UrlDiscoverer {

    private final DiscoveryClient discoveryClient;
    private final String GRAPH_SERVICE_NAME = "GRAPH-SERVICE";
    private final String BFS_SERVICE_NAME = "BFS-SERVICE";

    public UrlDiscoverer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public String getGraphServiceUrl(String graphId) {
        String baseUrl = getServiceBaseUrl(GRAPH_SERVICE_NAME);
        return baseUrl + "/graph?id=" + graphId;
    }

    public String getDirectedGraphServiceUrl(String graphId) {
        String baseUrl = getServiceBaseUrl(GRAPH_SERVICE_NAME);
        return baseUrl + "/directedGraph?id=" + graphId;
    }

    public String getBfsServiceUrl() {
        String baseUrl = getServiceBaseUrl(BFS_SERVICE_NAME);
        return baseUrl + "/bfs";
    }

    private String getServiceBaseUrl(String serviceName) {
        return discoveryClient.getInstances(serviceName)
                .stream()
                .findFirst()
                .orElseThrow()
                .getUri()
                .toString();
    }
}
