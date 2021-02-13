/* Kruskal.java */

package project.pj3.graphalg;

import homework.hw6.dict.HashTableChained;
import project.pj3.graph.*;
import project.pj3.list.DList;
import project.pj3.set.*;
import textbook.sixth_edition.ch9_priorityQueue.HeapAdaptablePriorityQueue;
import textbook.sixth_edition.ch9_priorityQueue.PriorityQueue;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

    /**
     * minSpanTree() returns a WUGraph that represents the minimum spanning tree
     * of the WUGraph g.  The original WUGraph g is NOT changed.
     *
     * @param g The weighted, undirected graph whose MST we want to compute.
     * @return A newly constructed WUGraph representing the MST of g.
     */
    public static WUGraph minSpanTree(WUGraph g) {
        WUGraph tree = new WUGraph();
        DList edges = new DList();
        PriorityQueue<Integer,Edge> pq = new HeapAdaptablePriorityQueue<>();//TODO 暫時用priority queue來排序edge
        HashTableChained disjointSetIndexMap = new HashTableChained();      // vertex -> index (用來對應該vertex在disjoint set裡的位置)
        DisjointSets s = new DisjointSets(g.edgeCount());

        //1.新增vertex到 tree 以及 disjoint set裡面
        for (int i = 0; i < g.getVertices().length; i++) {
            Object vertex = g.getVertices()[i];
            tree.addVertex(vertex);
            disjointSetIndexMap.insert(vertex, i);
        }

        //2 取的所有edges
        HashTableChained map = new HashTableChained(); //用來記錄已經處理過的vertex
        for (Object vertex : tree.getVertices()) {
            map.insert(vertex, vertex);  //每處理過一個vertex加入map
            Object[] neighborList = g.getNeighbors(vertex).neighborList;
            int[] weightList = g.getNeighbors(vertex).weightList;
            for (int i = 0; i < neighborList.length; i++) {
                Object neighborVertex = neighborList[i];
                int weight = weightList[i];
                Edge edge = new Edge(vertex, neighborVertex, weight);
                // 1.self-edge需再次加入 或是 2. neighbor為沒處理過的vertex
                if (vertex.equals(neighborVertex) || map.find(neighborVertex) == null) {
                    edges.insertBack(edge);
                    pq.insert(weight, edge);
                }
            }
        }

        //TODO 自己寫sorting algorithm 排序 edges

        System.out.println("裡面 edge = " + pq.size());
        int size = tree.vertexCount();
        while (tree.edgeCount() != size - 1 && !pq.isEmpty()) {
            Edge edge = pq.removeMin().getValue(); //TODO 暫時用priority queue來排序edge
            Object endPoint1 = edge.endPoint1;
            int index1 = (Integer) disjointSetIndexMap.find(endPoint1).value();
            Object endPoint2 = edge.endPoint2;
            int index2 = (Integer) disjointSetIndexMap.find(endPoint2).value();
            if (!endPoint1.equals(endPoint2)) {
                int root1 = s.find(index1);
                int root2 = s.find(index2);
                // 兩個在不同set的話才需uion
                if (root1 != root2) {
                    s.union(root1,root2);
                    tree.addEdge(endPoint1, endPoint2, edge.weight);
                }
            }
        }

        return tree;
    }
}
