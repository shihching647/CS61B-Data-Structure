/* WUGraph.java */

package project.pj3.graph;

import homework.hw6.dict.Entry;
import homework.hw6.dict.HashTableChained;
import project.pj3.list.DList;
import project.pj3.list.DListNode;


/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
    private int countsVertices;
    private int countsEdges;
    private DList vertices;               // 存innerVertex
    private HashTableChained verticesMap; // vertex -> DListNode (item = innerVertex) map
    private HashTableChained edgesMap;    // VertexPair -> InnerEdge map

    /**
     * WUGraph() constructs a graph having no vertices or edges.
     *
     * Running time:  O(1).
     */
    public WUGraph() {
        vertices = new DList();
        verticesMap = new HashTableChained();
        edgesMap = new HashTableChained();
        countsEdges = countsVertices = 0;
    }

    /**
     * vertexCount() returns the number of vertices in the graph.
     *
     * Running time:  O(1).
     */
    public int vertexCount() {
        return countsVertices;
    }

    /**
     * edgeCount() returns the total number of edges in the graph.
     *
     * Running time:  O(1).
     */
    public int edgeCount() {
        return countsEdges;
    }

    /**
     * getVertices() returns an array containing all the objects that serve
     * as vertices of the graph.  The array's length is exactly equal to the
     * number of vertices.  If the graph has no vertices, the array has length
     * zero.
     *
     * (NOTE:  Do not return any internal data structure you use to represent
     * vertices!  Return only the same objects that were provided by the
     * calling application in calls to addVertex().)
     *
     * Running time:  O(|V|).
     */
    public Object[] getVertices() {
        Object[] result = new Object[countsVertices];
        Object[] allInnerVertices = vertices.getAllElements();
        for (int i = 0; i < result.length; i++) {
            result[i] = ((InnerVertex) (allInnerVertices[i])).element;
        }
        return result;
    }

    /**
     * addVertex() adds a vertex (with no incident edges) to the graph.
     * The vertex's "name" is the object provided as the parameter "vertex".
     * If this object is already a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(1).
     */
    public void addVertex(Object vertex) {
        if (isVertex(vertex)) return;
        InnerVertex innerVertex = new InnerVertex(vertex);
        vertices.insertBack(innerVertex);
        verticesMap.insert(vertex, vertices.back());
        countsVertices++;
    }

    /**
     * removeVertex() removes a vertex from the graph.  All edges incident on the
     * deleted vertex are removed as well.  If the parameter "vertex" does not
     * represent a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public void removeVertex(Object vertex) {
        if (!isVertex(vertex)) return;
        DListNode innerNode = (DListNode) verticesMap.find(vertex).value();
        InnerVertex innerVertex = (InnerVertex) innerNode.item;
        //Remove all edges incident on the vertex
        for (DListNode node : innerVertex.adjacencyList.getAllNodes()) {
            // node.item == InnerEdge
            InnerEdge edge = (InnerEdge) node.item;
            InnerVertex otherInnerVertex = edge.getOpposite(innerVertex);
            Object otherVertex = otherInnerVertex.element;
            removeEdge(vertex, otherVertex);
        }
        //移除vertice本身
        vertices.remove(innerNode);
        verticesMap.remove(vertex);
        countsVertices--;
    }

    /**
     * isVertex() returns true if the parameter "vertex" represents a vertex of
     * the graph.
     *
     * Running time:  O(1).
     */
    public boolean isVertex(Object vertex) {
        return verticesMap.find(vertex) != null;
    }

    /**
     * degree() returns the degree of a vertex.  Self-edges add only one to the
     * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
     * of the graph, zero is returned.
     *
     * Running time:  O(1).
     */
    public int degree(Object vertex) {
        if (!isVertex(vertex)) return 0;
        DListNode node = (DListNode) verticesMap.find(vertex).value();
        return ((InnerVertex) node.item).degree;
    }

    /**
     * getNeighbors() returns a new Neighbors object referencing two arrays.  The
     * Neighbors.neighborList array contains each object that is connected to the
     * input object by an edge.  The Neighbors.weightList array contains the
     * weights of the corresponding edges.  The length of both arrays is equal to
     * the number of edges incident on the input vertex.  If the vertex has
     * degree zero, or if the parameter "vertex" does not represent a vertex of
     * the graph, null is returned (instead of a Neighbors object).
     *
     * The returned Neighbors object, and the two arrays, are both newly created.
     * No previously existing Neighbors object or array is changed.
     *
     * (NOTE:  In the neighborList array, do not return any internal data
     * structure you use to represent vertices!  Return only the same objects
     * that were provided by the calling application in calls to addVertex().)
     *
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public Neighbors getNeighbors(Object vertex) {
        if (!isVertex(vertex)) return null; //special case
        DListNode innerNode = (DListNode) verticesMap.find(vertex).value();
        InnerVertex innerVertex = (InnerVertex) innerNode.item;
        if (innerVertex.degree == 0) return null; //special case
        // general cases
        Object[] neighborList = new Object[innerVertex.degree];
        int[] weightList = new int[innerVertex.degree];
        Object[] edges = innerVertex.adjacencyList.getAllElements();
        for (int i = 0; i < edges.length; i++) {
            InnerEdge edge = (InnerEdge) edges[i];
            neighborList[i] = edge.getOpposite(innerVertex).element;
            weightList[i] = edge.weight;
        }
        Neighbors neighbors = new Neighbors();
        neighbors.neighborList = neighborList;
        neighbors.weightList = weightList;
        return neighbors;
    }

    /**
     * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
     * u and v does not represent a vertex of the graph, the graph is unchanged.
     * The edge is assigned a weight of "weight".  If the graph already contains
     * edge (u, v), the weight is updated to reflect the new value.  Self-edges
     * (where u.equals(v)) are allowed.
     *
     * Running time:  O(1).
     */
    public void addEdge(Object u, Object v, int weight) {
        if (!isVertex(u) || !isVertex(v)) return; //special case

        //已經存在edge
        VertexPair pair = new VertexPair(u, v);
        Entry entry = edgesMap.find(pair);
        if (entry != null) {
            InnerEdge edge = (InnerEdge) entry.value();
            edge.weight = weight;
            return;
        }
        //insert new edge
        DListNode nodeU = (DListNode) verticesMap.find(u).value();
        InnerVertex innerU = (InnerVertex) nodeU.item;
        DListNode nodeV = (DListNode) verticesMap.find(v).value();
        InnerVertex innerV = (InnerVertex) nodeV.item;
        InnerEdge edge = new InnerEdge(innerU, innerV, null, null, weight);
        innerU.adjacencyList.insertBack(edge);
        edge.node1 = innerU.adjacencyList.back();
        innerU.degree++;
        if (!u.equals(v)) {//非self-edge
            innerV.adjacencyList.insertBack(edge);
            edge.node2 = innerV.adjacencyList.back();
            innerV.degree++;
        } else { //self-edge情況
            edge.node2 = edge.node1;
        }
        edgesMap.insert(pair, edge);
        countsEdges++;
    }

    /**
     * removeEdge() removes an edge (u, v) from the graph.  If either of the
     * parameters u and v does not represent a vertex of the graph, the graph
     * is unchanged.  If (u, v) is not an edge of the graph, the graph is
     * unchanged.
     *
     * Running time:  O(1).
     */
    public void removeEdge(Object u, Object v) {
        if (!isEdge(u,v)) return; //special case
        VertexPair pair = new VertexPair(u, v);
        InnerEdge edge = (InnerEdge) edgesMap.find(pair).value();
        InnerVertex innerU = edge.endPoint1;
        InnerVertex innerV = edge.endPoint2;
        innerU.adjacencyList.remove(edge.node1);
        innerU.degree--;
        if (!u.equals(v)) { //self-edge情況
            innerV.adjacencyList.remove(edge.node2);
            innerV.degree--;
        }
        edgesMap.remove(pair);
        countsEdges--;
    }

    /**
     * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
     * if (u, v) is not an edge (including the case where either of the
     * parameters u and v does not represent a vertex of the graph).
     *
     * Running time:  O(1).
     */
    public boolean isEdge(Object u, Object v) {
        if (!isVertex(u) || !isVertex(v)) return false;
        VertexPair pair = new VertexPair(u, v);
        return edgesMap.find(pair) != null;
    }

    /**
     * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
     * an edge (including the case where either of the parameters u and v does
     * not represent a vertex of the graph).
     *
     * (NOTE:  A well-behaved application should try to avoid calling this
     * method for an edge that is not in the graph, and should certainly not
     * treat the result as if it actually represents an edge with weight zero.
     * However, some sort of default response is necessary for missing edges,
     * so we return zero.  An exception would be more appropriate, but also more
     * annoying.)
     *
     * Running time:  O(1).
     */
    public int weight(Object u, Object v) {
        if (!isEdge(u,v)) return 0;
        VertexPair pair = new VertexPair(u, v);
        return ((InnerEdge)edgesMap.find(pair).value()).weight;
    }

}
