package project.pj3.graph;


import project.pj3.list.DList;

public class InnerVertex {
    protected Object element;
    protected DList adjacencyList; //Edge list
    protected int degree;

    protected InnerVertex(Object element) {
        this.element = element;
        adjacencyList = new DList();
    }

}
