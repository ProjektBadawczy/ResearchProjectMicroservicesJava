package studies.research.project.microservicces.graphservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GraphsContainer {
    private Graph graph;
    private DirectedGraph directedGraph;
}
