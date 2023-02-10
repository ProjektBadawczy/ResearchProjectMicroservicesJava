package studies.research.project.microservicces.edmondskarpservice.controller;

import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import studies.research.project.microservicces.edmondskarpservice.model.Graph;
import studies.research.project.microservicces.edmondskarpservice.service.EdmondsKarpService;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class EdmondsKarpController {

    private final EdmondsKarpService edmondsKarpService;

    public EdmondsKarpController(EdmondsKarpService edmondsKarpService) {
        this.edmondsKarpService = edmondsKarpService;
    }

    @GetMapping("/edmondsKarpMaxGraphFlow")
    public ResponseEntity<Integer> getEdmondsKarpMaxGraphFlow(@RequestParam("id") String id, @RequestParam String source, @RequestParam String destination) {
        String graphServiceUrl = "http://graph-service:80/graph?id=" + id;
        return Try.of(() -> new RestTemplate().getForEntity(graphServiceUrl, Graph.class).getBody())
                .map(graph -> {
                    int s = Integer.parseInt(source);
                    int d = Integer.parseInt(destination);
                    return new ResponseEntity<>(edmondsKarpService.calculateMaxFlow(graph, s, d), OK);
                })
                .onFailure(System.err::println)
                .getOrElseGet(e -> new ResponseEntity<>(BAD_REQUEST));
    }
}
