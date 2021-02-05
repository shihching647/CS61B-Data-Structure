package textbook.sixth_edition.ch14_graph.ch14_6_shortest_path;

import textbook.sixth_edition.ch10_map.Map;
import textbook.sixth_edition.ch10_map.ProbeHashMap;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.AdjacencyMapGraph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Edge;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Graph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Vertex;
import textbook.sixth_edition.ch9_priorityQueue.AdaptablePriorityQueue;
import textbook.sixth_edition.ch9_priorityQueue.Entry;
import textbook.sixth_edition.ch9_priorityQueue.HeapAdaptablePriorityQueue;

/**
 *  Use Dijkstra’s algorithm to calculate the shortest path problem.
 *
 *  Analyze time complexity:
 *
 *  • n insertions into Q.
 *  • n calls to the removeMin method on Q.
 *  • m calls to the replaceKey method on Q.
 *
 *  The key factor is how your adaptable priority queue is implemented.
 *  If Q is an adaptable priority queue implemented as a heap, then each of the
 *  above operations run in O(logn), and so the overall running time for Dijkstra’s
 *  algorithm is O((n+m) logn). On the other hand, adaptable priority queue Q using
 *  an unsorted sequence. This, of course, requires that we spend O(n) time to extract
 *  the minimum element, but it affords very fast key updates, provided Q supports location-aware
 *  entries we can implement each key update done in a relaxation step in O(1)time.
 *  Hence, this implementation results in a running time that is O(n^2 +m), which can
 *  be simplified to O(n2) since G is simple.
 *
 *  Looking only at these worst-case times, we prefer
 *  the heap implementation when the number of edges in the graph is small (that is,
 *  when m < n2/log n), and we prefer the sequence implementation when the number
 *  of edges is large (that is, when m > n2/log n).
 *
 */
public class ShortestPath {

    public static void main(String[] args) {
        //以p.654的graph當作範例
        Graph<String,Integer> graph = new AdjacencyMapGraph<>(false);
        //用頂點名稱快速存取vertex
        Map<String, Vertex<String>> map = new ProbeHashMap<>(16);

        map.put("LAX", graph.insertVertex("LAX"));
        map.put("SFO", graph.insertVertex("SFO"));
        map.put("DFW", graph.insertVertex("DFW"));
        map.put("ORD", graph.insertVertex("ORD"));
        map.put("MIA", graph.insertVertex("MIA"));
        map.put("JFK", graph.insertVertex("JFK"));
        map.put("BOS", graph.insertVertex("BOS"));
        map.put("BWI", graph.insertVertex("BWI"));
        map.put("PVD", graph.insertVertex("PVD"));

        graph.insertEdge(map.get("SFO"), map.get("LAX"), 337);
        graph.insertEdge(map.get("SFO"), map.get("DFW"), 1464);
        graph.insertEdge(map.get("LAX"), map.get("DFW"), 1235);
        graph.insertEdge(map.get("LAX"), map.get("MIA"), 2342);
        graph.insertEdge(map.get("SFO"), map.get("BOS"), 2704);
        graph.insertEdge(map.get("SFO"), map.get("ORD"), 1846);
        graph.insertEdge(map.get("DFW"), map.get("ORD"), 802);
        graph.insertEdge(map.get("DFW"), map.get("JFK"), 1391);
        graph.insertEdge(map.get("DFW"), map.get("MIA"), 1121);
        graph.insertEdge(map.get("ORD"), map.get("BOS"), 867);
        graph.insertEdge(map.get("ORD"), map.get("PVD"), 849);
        graph.insertEdge(map.get("ORD"), map.get("JFK"), 740);
        graph.insertEdge(map.get("ORD"), map.get("BWI"), 621);
        graph.insertEdge(map.get("MIA"), map.get("BWI"), 946);
        graph.insertEdge(map.get("MIA"), map.get("JFK"), 1090);
        graph.insertEdge(map.get("MIA"), map.get("BOS"), 1258);
        graph.insertEdge(map.get("JFK"), map.get("BOS"), 187);
        graph.insertEdge(map.get("JFK"), map.get("PVD"), 144);
        graph.insertEdge(map.get("JFK"), map.get("BWI"), 184);

        Map<Vertex<String>, Integer> d = shortestPathLengths(graph, map.get("BWI"));
        for (Entry<Vertex<String>, Integer> entry : d.entrySet()) {
            System.out.println(entry.getKey().getElement() + " = " + entry.getValue());
        }
        Map<Vertex<String>,Edge<Integer>> spTree = spTree(graph, map.get("BWI"), d);
        for (Entry<Vertex<String>, Edge<Integer>> entry : spTree.entrySet()) {
            System.out.println("reach " + entry.getKey().getElement() + " by " + entry.getValue().getElement());
        }

    }

    public static <V> Map<Vertex<V>, Integer> shortestPathLengths(Graph<V, Integer> g, Vertex<V> src) {
        // map reachable v to its d value
        Map<Vertex<V>,Integer> cloud = new ProbeHashMap<>();
        // d.get(v) is upper bound on distance from src to v
        Map<Vertex<V>,Integer> d = new ProbeHashMap<>();
        // pq will have vertices as elements, with d.get(v) as key
        AdaptablePriorityQueue<Integer,Vertex<V>> pq = new HeapAdaptablePriorityQueue<>();
        // maps from vertex to its pq locator (快速找到Entry,使用在pq.replaceKey())
        Map<Vertex<V>,Entry<Integer,Vertex<V>>> pqToken = new ProbeHashMap<>();

        // for each vertex v of the graph, add an entry to the priority queue, with
        // the source having distance 0 and all others having infinite distance
        for (Vertex<V> v : g.vertices()) {
            if(v == src) d.put(v, 0);
            else d.put(v, Integer.MAX_VALUE);
            pqToken.put(v, pq.insert(d.get(v), v));  // save entry for future updates
        }

        // now begin adding reachable vertices to the cloud
        while (!pq.isEmpty()) {
            Entry<Integer,Vertex<V>> entry = pq.removeMin();
            int key = entry.getKey();
            Vertex<V> u = entry.getValue();
            cloud.put(u, key);              // this is actual distance to u
            pqToken.remove(u);
            for (Edge<Integer> e : g.outgoingEdges(u)) {
                Vertex<V> v = g.opposite(u, e);
                if (cloud.get(v) == null) {
                    // perform relaxation step on edge (u,v)
                    int wgt = d.get(v);
                    if (key + e.getElement() < wgt) {
                        d.put(v, key + e.getElement());         // update the distance
                        pq.replaceKey(pqToken.get(v), key + e.getElement());// update the pq entry
                    }
                }
            }
        }
        return cloud;
    }

    /**
     * Reconstructs a shortest-path tree rooted at vertex s, given distance map d.
     * The tree is represented as a map from each reachable vertex v (other than s)
     * to the edge e = (u,v) that is used to reach v from its parent u in the tree.
     */
    public static <V> Map<Vertex<V>,Edge<Integer>> spTree(Graph<V,Integer> g, Vertex<V> s, Map<Vertex<V>,Integer> d) {
        Map<Vertex<V>,Edge<Integer>> tree = new ProbeHashMap<>();
        for (Vertex<V> v: d.keySet()) {
            if (v != s) {
                for (Edge<Integer> e : g.incomingEdges(v)) {  // consider INCOMING edges
                    Vertex<V> u = g.opposite(v, e);
                    if (d.get(u) + e.getElement() == d.get(v))
                        tree.put(v, e);                     // edge is is used to reach v
                }
            }
        }
        return tree;
    }
}
