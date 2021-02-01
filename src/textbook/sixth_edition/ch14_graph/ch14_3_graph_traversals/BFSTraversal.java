package textbook.sixth_edition.ch14_graph.ch14_3_graph_traversals;

import textbook.fifth_edition.ch5_2.ArrayQueue;
import textbook.fifth_edition.ch5_2.Queue;
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

public class BFSTraversal {

    public static void main(String[] args) {
        //以P.633 Fig14.9的 undirected graph 例子
        Graph<Character, String> graph = new AdjacencyMapGraph<>(false);
        //用頂點名稱快速存取vertex
        Map<Character, Vertex<Character>> map = new ProbeHashMap<>(32);
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

        Set<Vertex<Character>> known = new HashSet<>();
        Map<Vertex<Character>,Edge<String>> forset = new ProbeHashMap<>();
        BFSFIFO(graph, map.get('A'), known, forset);
    }

    /** Performs breadth-first search of Graph g starting at Vertex u. */
    public static <V,E> void BFS(Graph<V,E> g, Vertex<V> s, Set<Vertex<V>> known, Map<Vertex<V>,Edge<E>> forest) {
        PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
        int lev = 0;
        System.out.println("Level : "+ lev);
        System.out.println(s.getElement());
        known.add(s);
        level.addLast(s);           // first level includes only s
        while (!level.isEmpty()) {
            PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
            System.out.println("Level : "+ ++lev);
            for (Vertex<V> u : level) {
                for (Edge<E> e : g.outgoingEdges(u)) {
                    Vertex<V> v = g.opposite(u, e);
                    if(!known.contains(v)) {
                        System.out.println(v.getElement());
                        known.add(v);
                        forest.put(v, e);       // e is the tree edge that discovered v
                        nextLevel.addLast(v);   // v will be further considered in next pass
                    }
                }
            }
            level = nextLevel;                  // relabel ’next’ level to become the current
        }
    }

    /** Performs breadth-first search of Graph g starting at Vertex u with FIFO structure. */
    public static <V,E> void BFSFIFO(Graph<V,E> g, Vertex<V> s, Set<Vertex<V>> known, Map<Vertex<V>,Edge<E>> forest) {
        Queue<Vertex<V>> queue = new ArrayQueue<>();
        System.out.println(s.getElement());
        known.add(s);
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            Vertex<V> u = queue.dequeue();
            for (Edge<E> e : g.outgoingEdges(u)) {
                Vertex<V> v = g.opposite(u, e);
                if(!known.contains(v)) {
                    System.out.println(v.getElement());
                    known.add(v);
                    forest.put(v, e);
                    queue.enqueue(v);
                }
            }
        }
    }
}
