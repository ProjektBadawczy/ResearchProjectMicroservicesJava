package studies.research.project.microservicces.edmondskarpservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import studies.research.project.microservicces.edmondskarpservice.service.EdmondsKarpService;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class EdmondsKarpController {

    private final EdmondsKarpService edmondsKarpService;

    public EdmondsKarpController(EdmondsKarpService edmondsKarpService) {
        this.edmondsKarpService = edmondsKarpService;
    }

    @GetMapping("/edmondsKarpMaxGraphFlow")
    public ResponseEntity<Integer> getEdmondsKarpMaxGraphFlow(@RequestParam("id") String id, @RequestParam String source, @RequestParam String destination) {
        int maxFlow = (edmondsKarpService.calculateMaxFlow(Integer.parseInt(id), Integer.parseInt(source), Integer.parseInt(destination)));
        return new ResponseEntity<>(maxFlow, OK);
    }
}
