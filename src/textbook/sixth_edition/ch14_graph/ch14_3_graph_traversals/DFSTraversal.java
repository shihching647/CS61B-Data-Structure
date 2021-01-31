package textbook.sixth_edition.ch14_graph.ch14_3_graph_traversals;

import textbook.sixth_edition.ch10_map.Map;
import textbook.sixth_edition.ch10_map.ProbeHashMap;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.AdjacencyMapGraph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Edge;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Graph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Vertex;
import textbook.sixth_edition.ch7_list.LinkedPositionalList;
import textbook.sixth_edition.ch7_list.PositionalList;

import java.util.HashSet;
import java.util.Set;

public class DFSTraversal {

    public static void main(String[] args) {
        //以P.633 Fig14.9的 undirected graph 例子
        Graph<Character, String> graph = new AdjacencyMapGraph<>(false);
        //用頂點名稱快速存取vertex
        Map<Character,Vertex<Character>> map = new ProbeHashMap<>(32);
        for(char i = 'A'; i <= 'P'; i++) {
            map.put(i, graph.insertVertex(i));
        }
        graph.insertEdge(map.get('A'), map.get('B'), "AB");
        graph.insertEdge(map.get('A'), map.get('F'), "AF");
        graph.insertEdge(map.get('A'), map.get('E'), "AE");
        graph.insertEdge(map.get('B'), map.get('C'), "BC");
        graph.insertEdge(map.get('B'), map.get('F'), "BF");
        graph.insertEdge(map.get('C'), map.get('D'), "CD");
        graph.insertEdge(map.get('C'), map.get('G'), "CG");
        graph.insertEdge(map.get('D'), map.get('G'), "DG");
        graph.insertEdge(map.get('D'), map.get('H'), "DH");
        graph.insertEdge(map.get('E'), map.get('I'), "EI");
        graph.insertEdge(map.get('E'), map.get('F'), "EF");
        graph.insertEdge(map.get('F'), map.get('I'), "FI");
        graph.insertEdge(map.get('G'), map.get('J'), "GJ");
        graph.insertEdge(map.get('G'), map.get('K'), "GK");
        graph.insertEdge(map.get('G'), map.get('L'), "GL");
        graph.insertEdge(map.get('H'), map.get('L'), "HL");
        graph.insertEdge(map.get('I'), map.get('J'), "IJ");
        graph.insertEdge(map.get('I'), map.get('N'), "IN");
        graph.insertEdge(map.get('I'), map.get('M'), "IM");
        graph.insertEdge(map.get('J'), map.get('K'), "JK");
        graph.insertEdge(map.get('K'), map.get('N'), "KN");
        graph.insertEdge(map.get('K'), map.get('O'), "KO");
        graph.insertEdge(map.get('L'), map.get('P'), "LP");
        graph.insertEdge(map.get('M'), map.get('N'), "MN");

        HashSet<Vertex<Character>> known = new HashSet<>();
        Map<Vertex<Character>, Edge<String>> forest = new ProbeHashMap<>();
        DFS(graph, map.get('A'), known, forest);
        PositionalList<Edge<String>> path = constructPath(graph, map.get('A'), map.get('P'), forest);
        for (Edge<String> e : path) {
            System.out.print(e.getElement() + ",");
        }
        //Computing All Connected Components
        Map<Vertex<Character>,Edge<String>> forestMap = DFSComplete(graph);
        //Graph is connected -> The number of connected edges = 1
        //remove edge:KO -> The number of connected edges = 2
        System.out.println("The number of connected edges = " + (graph.numVertices() - forestMap.size()));

    }

    /** Performs depth-first search of Graph g starting at Vertex u. */
    // runs in O(n + m) time if we can “mark” a vertex as explored or test the status of a vertex in O(1) time
    public static <V,E> void DFS(Graph<V,E> g, Vertex<V> u,
                                 Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest) {
        System.out.println(u.getElement());
        known.add(u);                           // u has been discovered
        for(Edge<E> e : g.outgoingEdges(u)) {   // for every outgoing edge from u
            Vertex<V> v = g.opposite(u, e);
            if(!known.contains(v)) {
                forest.put(v, e);               // e is the tree edge that discovered v
                DFS(g, v,  known, forest);      // recursively explore from v
            }
        }
    }

    /** Returns an ordered list of edges comprising the directed path from u to v. */
    public static <V,E> PositionalList<Edge<E>>
    constructPath(Graph<V,E> g, Vertex<V> u, Vertex<V> v, Map<Vertex<V>,Edge<E>> forest) {
        PositionalList<Edge<E>> path = new LinkedPositionalList<>();
        if(forest.get(v) != null) {    // v was discovered during the search
            Vertex<V> walk = v;        // we construct the path from back to front
            while (walk != u) {
                Edge<E> edge = forest.get(walk);
                path.addFirst(edge);    // add edge to front of path
                walk = g.opposite(walk, edge); // repeat with opposite endpoint
            }
        }
        return path;
    }

    /** Performs DFS for the entire graph and returns the DFS forest as a map. */
    public static <V,E> Map<Vertex<V>,Edge<E>> DFSComplete(Graph<V,E> g) {
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>,Edge<E>> forest = new ProbeHashMap<>();
        for (Vertex<V> u : g.vertices()) {
            if(!known.contains(u))
                DFS(g, u, known, forest);   // (re)start the DFS process at u
        }
        return forest;
    }

}
