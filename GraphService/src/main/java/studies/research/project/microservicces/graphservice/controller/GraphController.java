package studies.research.project.microservicces.GraphService.controller;


import graph.model.DirectedGraph;
import graph.model.Graph;
import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import studies.research.project.microservicces.GraphService.service.GraphService;

import static org.springframework.http.HttpStatus.*;

@RestController
public class GraphController {

    private final GraphService graphService;


    public GraphController(GraphService graphService) {
        this.graphService = graphService;

    }

    @GetMapping("/graphs/{id}")
    public ResponseEntity<Graph> getGraph(@PathVariable("id") String id) {
        return Try.of(() -> Integer.parseInt(id))
                .map(graphService::getGraph)
                .map(graph -> new ResponseEntity<>(graph, OK))
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/directedGraphs/{id}")
    public ResponseEntity<DirectedGraph> getDirectedGraph(@PathVariable("id") String id) {
        return Try.of(() -> Integer.parseInt(id))
                .map(graphService::getDirectedGraph)
                .map(graph -> new ResponseEntity<>(graph, OK))
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(NOT_FOUND));
    }

}
