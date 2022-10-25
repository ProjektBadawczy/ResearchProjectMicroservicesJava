package studies.research.project.microservicces.GraphService.model;

import graph.model.DirectedGraph;
import graph.model.Graph;
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
