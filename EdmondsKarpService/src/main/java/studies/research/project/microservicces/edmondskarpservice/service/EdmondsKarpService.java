package studies.research.project.microservicces.edmondskarpservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import studies.research.project.microservicces.edmondskarpservice.model.*;

import static java.lang.Math.min;

@Service
public class EdmondsKarpService {

    public EdmondsKarpService() {

    }

    public int calculateMaxFlow(int id, int source, int destination) {
        Mono<Graph> graphMono = WebClient.create("http://graph-service:80")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/graph")
                        .queryParam("id", Integer.toString(id))
                        .build())
                .retrieve()
                .bodyToMono(Graph.class);


        Graph graph = graphMono.block();

        if(graph != null){
            int u, v;
            Graph residualGraph = graph.clone();
            int max_flow = 0;

            Mono<BFSResult> bfsResultMono = WebClient.create("http://bfs-service:80")
                    .post()
                    .uri(uriBuilder -> uriBuilder.path("/bfs")
                            .build())
                    .body(BodyInserters.fromValue(new BFSRequest(residualGraph, source, destination)))
                    .retrieve()
                    .bodyToMono(BFSResult.class);
            BFSResult bfsResult = bfsResultMono.block();

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

                bfsResultMono = WebClient.create("http://bfs-service:80")
                        .post()
                        .uri(uriBuilder -> uriBuilder.path("/bfs")
                                .build())
                        .body(BodyInserters.fromValue(new BFSRequest(residualGraph, source, destination)))
                        .retrieve()
                        .bodyToMono(BFSResult.class);
                bfsResult = bfsResultMono.block();
            }

            return max_flow;
        }
        return 0;

    }

    private boolean areSourceAndGraphParametersValid(Graph graph, int source, int destination) {
        return source >= 0 && source < graph.numberOfVertices() && destination >= 0 && destination < graph.numberOfVertices();
    }
}
