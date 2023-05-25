package studies.research.project.microservicces.graphservice.controller;

import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studies.research.project.microservicces.graphservice.model.DirectedGraph;
import studies.research.project.microservicces.graphservice.model.Graph;
import studies.research.project.microservicces.graphservice.service.GraphService;

import static org.springframework.http.HttpStatus.*;

@RestController
public class GraphController {

    private final GraphService graphService;


    public GraphController(GraphService graphService) {
        this.graphService = graphService;

    }

    @GetMapping("/graph")
    public ResponseEntity<Graph> getGraph(@RequestParam("id") String id) {
//        Graph graph = graphService.getGraph(Integer.parseInt(id));
//        if(graph != null){
//            new ResponseEntity<>(graph, OK);
//        }
//        return new ResponseEntity<>(NOT_FOUND);
        return Try.of(() -> Integer.parseInt(id))
                .map(graphService::getGraph)
                .map(graph -> new ResponseEntity<>(graph, OK))
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/directedGraph")
    public ResponseEntity<DirectedGraph> getDirectedGraph(@RequestParam("id") String id) {
        return Try.of(() -> Integer.parseInt(id))
                .map(graphService::getDirectedGraph)
                .map(graph -> new ResponseEntity<>(graph, OK))
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(NOT_FOUND));
    }

}
