package textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure;

import textbook.sixth_edition.ch10_map.Map;
import textbook.sixth_edition.ch10_map.ProbeHashMap;
import textbook.sixth_edition.ch7_list.LinkedPositionalList;
import textbook.sixth_edition.ch7_list.Position;
import textbook.sixth_edition.ch7_list.PositionalList;


public class AdjacencyMapGraph<V,E> implements Graph<V,E> {
    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private PositionalList<Edge<E>> edges = new LinkedPositionalList<>();

    public AdjacencyMapGraph(boolean isDirected) { this.isDirected = isDirected; }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return edges;
    }

    @Override
    public int outDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.outgoing.size();
    }

    @Override
    public int inDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.incoming.size();
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.outgoing.values();
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        return vertex.incoming.values();
    }

    @Override
    /** Returns the edge from u to v, or null if they are not adjacent. */
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> origin = validate(u);
        return origin.outgoing.get(validate(v));
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> innerEdge = validate(e);
        return innerEdge.endPoints;
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        validate(v);
        InnerEdge<E> innerEdge = validate(e);
        Vertex<V>[] endPoints = innerEdge.endPoints;
        if(endPoints[0] == v)
            return endPoints[1];
        else if(endPoints[1] == v)
            return endPoints[0];
        else
            throw new IllegalArgumentException("v is not incident to this edge");
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> innerVertex = new InnerVertex<>(element, isDirected);
        innerVertex.setPosition(vertices.addLast(innerVertex));
        return innerVertex;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        InnerVertex<V> origin = validate(v);
        InnerVertex<V> dest = validate(u);
        if(getEdge(origin, dest) == null) {
            InnerEdge<E> edge = new InnerEdge<>(origin, dest, element);
            edge.setPosition(edges.addLast(edge));
            origin.getOutgoing().put(dest, edge);
            dest.getIncoming().put(origin, edge);
            return edge;
        } else
            throw new IllegalArgumentException("Edge from u to v exists");
    }

    @Override
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vertex = validate(v);
        for (Edge<E> edge : vertex.getIncoming().values())
            removeEdge(edge);
        for (Edge<E> edge : vertex.getOutgoing().values())
            removeEdge(edge);
        vertices.remove(vertex.getPosition());
    }

    @Override
    public void removeEdge(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        // remove this edge from vertices' adjacencies
        Vertex<V>[] verts = edge.getEndPoints();
        ((InnerVertex<V>)verts[0]).getOutgoing().remove(verts[1]);
        ((InnerVertex<V>)verts[1]).getIncoming().remove(verts[0]);
        // remove this edge from the list of edges
        edges.remove(edge.getPosition());
        edge.setPosition(null);             // invalidates the edge
    }

    private InnerVertex<V> validate(Vertex<V> v) {
        if (!(v instanceof InnerVertex)) throw new IllegalArgumentException("Invalid vertex");
        InnerVertex<V> vert = (InnerVertex<V>) v;     // safe cast
        if (!vert.validate(this)) throw new IllegalArgumentException("Invalid vertex");
        return vert;
    }

    private InnerEdge<E> validate(Edge<E> e) {
        if (!(e instanceof InnerEdge)) throw new IllegalArgumentException("Invalid edge");
        InnerEdge<E> edge = (InnerEdge<E>) e;     // safe cast
        if (!edge.validate(this)) throw new IllegalArgumentException("Invalid edge");
        return edge;
    }


    //---------------- nested Vertex class ----------------
    /** A vertex of an adjacency map graph representation. */
    private class InnerVertex<V> implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> position;  // the position storing in graph's vertices
        private Map<Vertex<V>,Edge<E>> outgoing, incoming;

        /** Constructs a new InnerVertex instance storing the given element. */
        public InnerVertex(V element, boolean graphIsDirected) {
            this.element = element;
            outgoing = new ProbeHashMap<>();
            if(graphIsDirected)
                incoming = new ProbeHashMap<>();
            else
                incoming = outgoing;        // if undirected, alias outgoing map
        }

        public V getElement() { return element;}

        public void setPosition(Position<Vertex<V>> position) { this.position = position;}

        public Position<Vertex<V>> getPosition() { return position;}

        public Map<Vertex<V>,Edge<E>> getOutgoing() { return outgoing;}

        public Map<Vertex<V>,Edge<E>> getIncoming() { return incoming;}

        /** Validates that this vertex instance belongs to the given graph. */
        public boolean validate(Graph<V,E> graph) {
            return (AdjacencyMapGraph.this == graph && position != null);
        }

    }

    //---------------- nested InnerEdge class ----------------
    /** An edge between two vertices. */
    public class InnerEdge<E> implements Edge<E> {
        private E element;
        private Position<Edge<E>> position; //the position storing in graph's edges
        Vertex<V>[] endPoints;

        public InnerEdge(Vertex<V> u, Vertex<V> v, E element ) {
            this.element = element;
            endPoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        public E getElement() { return element;}

        public void setPosition(Position<Edge<E>> position) { this.position = position;}

        public Position<Edge<E>> getPosition() { return position;}

        public Vertex<V>[] getEndPoints() { return endPoints;}

        /** Validates that this edge instance belongs to the given graph. */
        public boolean validate(Graph<V,E> graph) {
            return (AdjacencyMapGraph.this == graph && position != null);
        }

    }
}
