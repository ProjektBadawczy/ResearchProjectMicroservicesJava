package studies.research.project.microservicces.graphservice.controller;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studies.research.project.microservicces.graphservice.model.Graph;
import studies.research.project.microservicces.graphservice.service.EdmondsKarpServiceProxy;
import studies.research.project.microservicces.graphservice.service.GraphService;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
public class GraphController {

    private final GraphService graphService;
    private final EdmondsKarpServiceProxy edmondsKarpServiceProxy;

    @GetMapping("/graphs/{id}")
    public ResponseEntity<Graph> getGraph(@PathVariable("id") String id) {
        return Try.of(() -> Integer.parseInt(id))
                .map(graphService::getGraph)
                .map(graph -> new ResponseEntity<>(graph, OK))
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/graphs/maxFlow/{id}")
    public ResponseEntity<Integer> getMaxGraphFlow(@PathVariable("id") String id, @RequestParam String source, @RequestParam String destination) {
        return Try.of(() -> Integer.parseInt(id))
                .map(graphService::getGraph)
                .map(graph -> {
                    int s = Integer.parseInt(source);
                    int d = Integer.parseInt(destination);
                    return new ResponseEntity<>(edmondsKarpServiceProxy.calculateMaxFlow(graph, s, d), OK);
                })
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(BAD_REQUEST));
    }
}

