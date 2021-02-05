package textbook.sixth_edition.ch14_graph.ch14_5_directed_acyclic_graph;

import textbook.fifth_edition.ch5_1.ArrayStack;
import textbook.fifth_edition.ch5_1.Stack;
import textbook.sixth_edition.ch10_map.Map;
import textbook.sixth_edition.ch10_map.ProbeHashMap;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.AdjacencyMapGraph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Edge;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Graph;
import textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure.Vertex;
import textbook.sixth_edition.ch7_list.LinkedPositionalList;
import textbook.sixth_edition.ch7_list.PositionalList;

/**
 * Directed Acyclic Graph (DAG)
 * A directed graph G has a topological ordering if and only if it is acyclic.
 *
 * A topological ordering of directed graph G is an ordering v1, . . . ,vn of the vertices
 * of G such that for every edge (vi,vj) of G, it is the case that i < j.
 *
 * The topological sorting algorithm runs in O(n+m) time using O(n) auxiliary space,
 * and either computes a topological ordering of G or fails to include some vertices,
 * which indicates that G has a directed cycle.
 */
public class TopologicalSort {

    public static void main(String[] args) {
        //以p.648的graph當作範例
        Graph<Character, String> graph = new AdjacencyMapGraph<>(true);
        Map<Character, Vertex<Character>> map = new ProbeHashMap<>();
        for (char i = 'A'; i <= 'H'; i++) {
            map.put(i, graph.insertVertex(i));
        }
        graph.insertEdge(map.get('A'), map.get('C'), "A->C");
        graph.insertEdge(map.get('A'), map.get('D'), "A->D");
        graph.insertEdge(map.get('B'), map.get('D'), "B->D");
        graph.insertEdge(map.get('B'), map.get('F'), "B->F");
        graph.insertEdge(map.get('C'), map.get('D'), "C->D");
        graph.insertEdge(map.get('D'), map.get('F'), "D->F");
        graph.insertEdge(map.get('C'), map.get('E'), "C->E");
        graph.insertEdge(map.get('E'), map.get('G'), "E->G");
        graph.insertEdge(map.get('G'), map.get('H'), "G->H");
        graph.insertEdge(map.get('F'), map.get('G'), "F->G");
        graph.insertEdge(map.get('F'), map.get('H'), "F->H");
        graph.insertEdge(map.get('C'), map.get('H'), "C->H");
        System.out.println(graph);
        System.out.print("Topological ordering: ");
        for (Vertex<Character> v : topologicalSort(graph)) {
            System.out.print(v.getElement() + " ");
        }

    }

    public static <V,E> PositionalList<Vertex<V>> topologicalSort(Graph<V,E> g) {
        // list of vertices placed in topological order
        PositionalList<Vertex<V>> topo = new LinkedPositionalList<>();
        // container of vertices that have no remaining constraints
        Stack<Vertex<V>> ready = new ArrayStack<>();
        // map keeping track of remaining in-degree for each vertex(can record in Vertex filed directly)
        Map<Vertex<V>, Integer> inCount = new ProbeHashMap<>();

        for (Vertex<V> u : g.vertices()) {
            inCount.put(u, g.inDegree(u)); // initialize with actual in-degree
            if(g.inDegree(u) == 0) ready.push(u); // if u has no incoming edges, it is free of constraints
        }

        while (!ready.isEmpty()) {
            Vertex<V> u = ready.pop();
            topo.addLast(u);
            for (Edge<E> e : g.outgoingEdges(u)) { // consider all outgoing neighbors of u
                Vertex<V> v = g.opposite(u, e);
                inCount.put(v, inCount.get(v) - 1); // v has one less constraint without u
                if (inCount.get(v) == 0) ready.push(v);
            }
        }

        return topo;
    }
}
