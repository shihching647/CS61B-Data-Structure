package textbook.sixth_edition.ch14_graph.ch14_4_transirive_closure;

import textbook.sixth_edition.ch10_map.Map;
import textbook.sixth_edition.ch10_map.ProbeHashMap;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.AdjacencyMapGraph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Graph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Vertex;

/**
 * Transitive closure definition:
 * The transitive closure of a directed graph G is itself a directed graph G∗ such that the
 * vertices of G∗ are the same as the vertices of G, and G∗ has an edge (u,v), whenever G
 * has a directed path from u to v (including the case where (u,v) is an edge of
 * the original G).
 *
 * Floyd-Warshall Algorithm:
 * Floyd-Warshall algorithm to compute the transitive closure of G which is in O(n^3)
 * The algorithm is particularly well suited for the use of an adjacency matrix (dense graph).
 * However, when the graph is sparse and represented using an adjacency list or adjacency
 * map. A single DFS runs in O(n+m) time, and so the transitive
 * closure can be computed in O(n^2 +nm) time, which is preferable to O(n^3)
 */

public class TransitiveClosure {

    public static void main(String[] args) {
        //以p.645的graph當作範例
        Graph<String, String> graph = new AdjacencyMapGraph<>(true);
        //用頂點名稱快速存取vertex
        Map<String, Vertex<String>> map = new ProbeHashMap<>(16);

        map.put("LAX", graph.insertVertex("LAX"));
        map.put("SFO", graph.insertVertex("SFO"));
        map.put("DFW", graph.insertVertex("DFW"));
        map.put("ORD", graph.insertVertex("ORD"));
        map.put("MIA", graph.insertVertex("MIA"));
        map.put("JFK", graph.insertVertex("JFK"));
        map.put("BOS", graph.insertVertex("BOS"));

        graph.insertEdge(map.get("DFW"), map.get("SFO"), "DFW -> SFO");
        graph.insertEdge(map.get("DFW"), map.get("ORD"), "DFW -> ORD");
        graph.insertEdge(map.get("DFW"), map.get("LAX"), "DFW -> LAX");
        graph.insertEdge(map.get("ORD"), map.get("DFW"), "ORD -> DFW");
        graph.insertEdge(map.get("JFK"), map.get("DFW"), "JFK -> DFW");
        graph.insertEdge(map.get("MIA"), map.get("DFW"), "MIA -> DFW");
        graph.insertEdge(map.get("JFK"), map.get("SFO"), "JFK -> SFO");
        graph.insertEdge(map.get("LAX"), map.get("ORD"), "LAX -> ORD");
        graph.insertEdge(map.get("MIA"), map.get("LAX"), "MIA -> LAX");
        graph.insertEdge(map.get("JFK"), map.get("MIA"), "JFK -> MIA");
        graph.insertEdge(map.get("BOS"), map.get("MIA"), "BOS -> MIA");
        graph.insertEdge(map.get("BOS"), map.get("JFK"), "BOS -> JFK");
        graph.insertEdge(map.get("JFK"), map.get("BOS"), "JFK -> BOS");
        transitiveClosure(graph);
    }

    public static <V,E> void transitiveClosure(Graph<V,E> g) {
        int index = 0;
        for (Vertex<V> k : g.vertices()) {
            System.out.println("Compute G" + (++index));
            for (Vertex<V> i : g.vertices()) {
                // verify that edge (i,k) exists in the partial closure
                if (i != k && g.getEdge(i, k) != null) {
                    for (Vertex<V> j : g.vertices()) {
                        // verify that edge (k,j) exists in the partial closure
                        if ( i != j && k != j && g.getEdge(k, j) != null) {
                            // if (i,j) not yet included, add it to the closure
                            if (g.getEdge(i, j) == null) {
                                g.insertEdge(i, j, null);
                                System.out.println("Insert new Edge: " + i.getElement() + " -> " + j.getElement());
                            }
                        }
                    }
                }
            }
            System.out.println(g);
            System.out.println("=======================================================================");
        }
    }
}
