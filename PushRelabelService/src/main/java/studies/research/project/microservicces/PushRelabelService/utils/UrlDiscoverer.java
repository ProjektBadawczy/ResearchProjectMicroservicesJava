package studies.research.project.microservicces.PushRelabelService.utils;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;


@Service
public class UrlDiscoverer {

    private final DiscoveryClient discoveryClient;
    private final String GRAPH_SERVICE_NAME = "GRAPH-SERVICE";

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

    private String getServiceBaseUrl(String serviceName) {
        return discoveryClient.getInstances(serviceName)
                .stream()
                .findFirst()
                .orElseThrow()
                .getUri()
                .toString();
    }
}
