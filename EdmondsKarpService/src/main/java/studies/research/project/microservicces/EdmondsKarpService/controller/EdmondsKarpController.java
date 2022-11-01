package studies.research.project.microservicces.EdmondsKarpService.controller;

import graph.model.Graph;
import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import studies.research.project.microservicces.EdmondsKarpService.service.EdmondsKarpService;
import studies.research.project.microservicces.EdmondsKarpService.utils.UrlDiscoverer;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class EdmondsKarpController {

    private final EdmondsKarpService edmondsKarpService;
    private final UrlDiscoverer urlDiscoverer;

    public EdmondsKarpController(EdmondsKarpService edmondsKarpService, UrlDiscoverer urlDiscoverer) {
        this.edmondsKarpService = edmondsKarpService;
        this.urlDiscoverer = urlDiscoverer;
    }

    @GetMapping("/edmondsKarpMaxGraphFlow")
    public ResponseEntity<Integer> getEdmondsKarpMaxGraphFlow(@RequestParam("id") String id, @RequestParam String source, @RequestParam String destination) {
        return Try.of(() -> new RestTemplate().getForEntity(urlDiscoverer.getGraphServiceUrl(id), Graph.class).getBody())
                .map(graph -> {
                    int s = Integer.parseInt(source);
                    int d = Integer.parseInt(destination);
                    return new ResponseEntity<>(edmondsKarpService.calculateMaxFlow(graph, s, d), OK);
                })
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(BAD_REQUEST));
    }
}
