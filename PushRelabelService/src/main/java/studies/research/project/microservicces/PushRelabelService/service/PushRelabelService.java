package studies.research.project.microservicces.PushRelabelService.service;

import graph.model.DirectedGraph;
import graph.model.Vertex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PushRelabelService {

    private DirectedGraph initResidualGraph(DirectedGraph graph)
    {
        DirectedGraph residualGraph = new DirectedGraph(graph.getNumberOfVertices(), graph.getId());

        // Construct residual graph
        for (int u = 0; u < graph.getNumberOfVertices(); u++) {

            for (Vertex v : graph.getAdjacencyList().get(u)) {

                // If forward edge already
                // exists, update its weight
                if (residualGraph.hasEdge(u, v.getI()))
                    residualGraph.getEdge(u, v.getI()).setW(residualGraph.getEdge(u, v.getI()).getW() + v.getW());

                    // In case it does not
                    // exist, create one
                else
                    residualGraph.addEdge(u, v.getI(), v.getW());

                // If backward edge does
                // not already exist, add it
                if (!residualGraph.hasEdge(v.getI(), u))
                    residualGraph.addEdge(v.getI(), u, 0);
            }
        }

        return residualGraph;
    }

    public int calculateMaxFlow(DirectedGraph graph, int source, int destination)
    {
        DirectedGraph residualGraph = initResidualGraph(graph);

        List<Integer> queue = new ArrayList<>();

        // Step 1: Initialize pre-flow
        // to store excess flow
        int[] e = new int[graph.getNumberOfVertices()];

        // to store height of vertices
        int[] h = new int[graph.getNumberOfVertices()];

        boolean[] inQueue = new boolean[graph.getNumberOfVertices()];

        // set the height of source to V
        h[source] = graph.getNumberOfVertices();

        // send maximum flow possible
        // from source to all its adjacent vertices
        for (Vertex v : graph.getAdjacencyList().get(source))
        {
            residualGraph.getEdge(source, v.getI()).setW(0);
            residualGraph.getEdge(v.getI(), source).setW(v.getW());

            // update excess flow
            e[v.getI()] = v.getW();

            if (v.getI() != destination) {
                queue.add(v.getI());
                inQueue[v.getI()] = true;
            }
        }

        // Step 2: Update the pre-flow
        // while there remains an applicable
        // push or relabel operation
        while (queue.size() != 0) {

            // vertex removed from
            // queue in constant time
            int u = queue.get(0);
            queue.remove(0);
            inQueue[u] = false;

            relabel(u, h, residualGraph);
            push(u, e, h, queue, inQueue, residualGraph, source,destination);
        }

        return e[destination];
    }

    private void relabel(int u, int[] h, DirectedGraph residualGraph)
    {
        int minHeight = Integer.MAX_VALUE;

        for(Vertex v : residualGraph.getAdjacencyList().get(u))
        {
            if (v.getW() > 0)
            {
                minHeight = Math.min(h[v.getI()], minHeight);
            }
        }

        h[u] = minHeight + 1;
    }

    private void push(int u, int[] e, int[] h, List<Integer> queue, boolean[] inQueue, DirectedGraph residualGraph, int source, int destination)
    {
        for(Vertex v : residualGraph.getAdjacencyList().get(u))
        {

            // after pushing flow if
            // there is no excess flow,
            // then break
            if (e[u] == 0)
                break;

            // push more flow to
            // the adjacent v if possible
            if (v.getW() > 0 && h[v.getI()] < h[u]) {
                // flow possible
                int f = Math.min(e[u], v.getW());

                v.setW(v.getW() - f);
                residualGraph.getEdge(v.getI(), u).setW(residualGraph.getEdge(v.getI(), u).getW() + f);

                e[u] -= f;
                e[v.getI()] += f;

                // add the new overflowing
                // immediate vertex to queue
                if (!inQueue[v.getI()] && v.getI() != source && v.getI() != destination) {
                    queue.add(v.getI());
                    inQueue[v.getI()] = true;
                }
            }
        }

        // if after sending flow to all the
        // intermediate vertices, the
        // vertex is still overflowing.
        // add it to queue again
        if (e[u] != 0) {
            queue.add(u);
            inQueue[u] = true;
        }
    }
}