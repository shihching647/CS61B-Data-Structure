package textbook.sixth_edition.ch14_graph.ch14_7_minimun_spanning_trees;

import textbook.fifth_edition.ch8_2_listPriorityQueue.PriorityQueue;
import textbook.fifth_edition.ch8_3_completeBinaryTree_and_Heap.HeapPriorityQueue;
import textbook.sixth_edition.ch10_map.Map;
import textbook.sixth_edition.ch10_map.ProbeHashMap;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.AdjacencyMapGraph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Edge;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Graph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Vertex;
import textbook.sixth_edition.ch7_list.LinkedPositionalList;
import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch7_list.PositionalList;
import textbook.sixth_edition.ch9_priorityQueue.AdaptablePriorityQueue;
import textbook.sixth_edition.ch9_priorityQueue.Entry;
import textbook.sixth_edition.ch9_priorityQueue.HeapAdaptablePriorityQueue;

/**
 *  Kruskal’s algorithm :
 *  the running time of Kruskal’s algorithm is O(mlogn).
 *  =====================================================
 *  Prim-Jarn´ık Algorithm :
 *  With a heap-based priority queue, each operation runs in O(logn) time,
 *  and the overall time for the algorithm is O((n+m) logn), which is O(mlogn) for a connected graph.
 *  Alternatively, we can achieve O(n2) running time by using an unsorted list as a priority queue
 */
public class MinimumSpanningTrees {

    public static void main(String[] args) {
        //以p.665的graph當作範例
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

        System.out.println("Kruskal's Algorithm: ");
        for (Edge<Integer> e : KruskalsMST(graph)) {
            System.out.println(e.getElement());
        }
        System.out.println("===================================");
        System.out.println("Prim-Jarn´ık Algorithm: ");
        for (Edge<Integer> e : PrimJarnikMST(graph, map.get("PVD"))) {
            System.out.println(e.getElement());
        }
    }

    /** Computes a minimum spanning tree of graph g using Kruskal's algorithm. */
    public static <V> PositionalList<Edge<Integer>> KruskalsMST(Graph<V,Integer> g) {
        // tree is where we will store result as it is computed
        PositionalList<Edge<Integer>> tree = new LinkedPositionalList<>();
        // pq entries are edges of graph, with weights as keys
        PriorityQueue<Integer,Edge<Integer>> pq = new HeapPriorityQueue<>();
        // union-find forest of components of the graph
        Partition<Vertex<V>> forest = new Partition<>();
        // map each vertex to the forest position
        Map<Vertex<V>, Position<Vertex<V>>> positions = new ProbeHashMap<>();

        for (Edge<Integer> e : g.edges()) {
            pq.insert(e.getElement(), e);
        }

        for (Vertex<V> v : g.vertices()) {
            positions.put(v, forest.makeCluster(v));
        }

        int size = g.numVertices();
        // while tree not spanning and unprocessed edges remain...
        while (tree.size() != size - 1 && !pq.isEmpty()) {
            Edge<Integer> edge = pq.removeMin().getValue();
            Vertex<V>[] endPoints = g.endVertices(edge);
            Position<Vertex<V>> a = forest.find(positions.get(endPoints[0]));
            Position<Vertex<V>> b = forest.find(positions.get(endPoints[1]));
            if (a != b) {
                forest.union(a, b);
                tree.addLast(edge);
            }
        }
        return tree;
    }

    public static <V> PositionalList<Edge<Integer>> PrimJarnikMST(Graph<V,Integer> g, Vertex<V> src) {
        PositionalList<Edge<Integer>> tree = new LinkedPositionalList<>();
        Map<Vertex<V>,Integer> cloud = new ProbeHashMap<>();
        //connect是達到d所使用的edge
        Map<Vertex<V>,Integer> d = new ProbeHashMap<>();
        Map<Vertex<V>,Edge<Integer>> connect = new ProbeHashMap<>();
        AdaptablePriorityQueue<Integer,Vertex<V>> pq = new HeapAdaptablePriorityQueue<>();
        // maps from vertex to its pq locator (快速找到Entry,使用在pq.replaceKey())
        Map<Vertex<V>, Entry<Integer,Vertex<V>>> pqToken = new ProbeHashMap<>();

        for (Vertex<V> v : g.vertices()) {
            if(v == src) d.put(v, 0);
            else d.put(v,Integer.MAX_VALUE);
            pqToken.put(v, pq.insert(d.get(v), v));
        }

        while (!pq.isEmpty()) {
            Entry<Integer,Vertex<V>> entry = pq.removeMin();
            int key = entry.getKey();
            Vertex<V> u = entry.getValue();
            cloud.put(u, key);
            pqToken.remove(u);
            if(u != src) tree.addLast(connect.get(u));
            for (Edge<Integer> e : g.outgoingEdges(u)) {
                Vertex<V> v = g.opposite(u, e);
                if (cloud.get(v) == null) {
                    int wgt = d.get(v);
                    if (e.getElement() < wgt) {
                        d.put(v, e.getElement());
                        connect.put(v, e);
                        pq.replaceKey(pqToken.get(v), e.getElement());
                    }
                }
            }
        }

        return tree;
    }
 }
