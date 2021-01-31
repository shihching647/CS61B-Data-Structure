package textbook.sixth_edition.ch14_graph.ch14_2_graph_data_structure;

/**
 * An edge of a graph.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public interface Edge<E> {
    /** Returns the element associated with the edge. */
    E getElement();
}
