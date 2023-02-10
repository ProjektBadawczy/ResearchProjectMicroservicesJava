package studies.research.project.microservicces.pushrelabelservice.controller;

import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import studies.research.project.microservicces.pushrelabelservice.model.DirectedGraph;
import studies.research.project.microservicces.pushrelabelservice.service.PushRelabelService;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class PushRelabelController {

    private final PushRelabelService pushRelabelService;

    public PushRelabelController(PushRelabelService pushRelabelService) {
        this.pushRelabelService = pushRelabelService;
    }

    @GetMapping("/pushRelabelMaxGraphFlow")
    public ResponseEntity<Integer> getPushRelabelMaxGraphFlow(@RequestParam("id") String id, @RequestParam String source, @RequestParam String destination) {

        String directedGraphServiceUrl = "http://graph-service:80/directedGraph?id=" + id;
        return Try.of(() -> new RestTemplate().getForEntity(directedGraphServiceUrl, DirectedGraph.class).getBody())
                .map(graph -> {
                    int s = Integer.parseInt(source);
                    int d = Integer.parseInt(destination);
                    return new ResponseEntity<>(pushRelabelService.calculateMaxFlow(graph, s, d), OK);
                })
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(BAD_REQUEST));
    }
}
