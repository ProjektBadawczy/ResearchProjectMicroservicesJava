package studies.research.project.microservicces.PushRelabelService.controller;

import graph.model.DirectedGraph;
import io.vavr.control.Try;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import studies.research.project.microservicces.PushRelabelService.service.PushRelabelService;
import studies.research.project.microservicces.PushRelabelService.utils.UrlDiscoverer;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class PushRelabelController {

    private final PushRelabelService pushRelabelService;
    private final UrlDiscoverer urlDiscoverer;

    public PushRelabelController(PushRelabelService pushRelabelService, UrlDiscoverer urlDiscoverer) {
        this.pushRelabelService = pushRelabelService;
        this.urlDiscoverer = urlDiscoverer;
    }

    @GetMapping("/graphs/pushRelabelMaxFlow/{id}")
    public ResponseEntity<Integer> getPushRelabelMaxGraphFlow(@PathVariable("id") String id, @RequestParam String source, @RequestParam String destination) {

        return Try.of(() -> new RestTemplate().getForEntity(urlDiscoverer.getDirectedGraphServiceUrl(id), DirectedGraph.class).getBody())
                .map(graph -> {
                    int s = Integer.parseInt(source);
                    int d = Integer.parseInt(destination);
                    return new ResponseEntity<>(pushRelabelService.calculateMaxFlow(graph, s, d), OK);
                })
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(BAD_REQUEST));
    }
}
