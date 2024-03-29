package studies.research.project.microservicces.bfsservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studies.research.project.microservicces.bfsservice.model.*;
import studies.research.project.microservicces.bfsservice.service.BfsService;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class BfsController {

    private final BfsService bfsService;

    public BfsController(BfsService bfsService) {
        this.bfsService = bfsService;
    }

    @PostMapping("/bfs")
    public ResponseEntity<BFSResult> getDirectedGraph(@RequestBody BFSRequest request) {
        return new ResponseEntity<>(bfsService.bfs(request.graph(), request.source(), request.destination()), OK);
    }
}
