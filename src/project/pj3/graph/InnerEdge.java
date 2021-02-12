package project.pj3.graph;

import project.pj3.list.DListNode;

public class InnerEdge {
    //node1, node2為次edge在各別adjacencyList的位置
    protected DListNode node1;
    protected DListNode node2;
    //endPoint1,endPoint2為InnerVertex物件, (node1.item != endPoint1)
    protected InnerVertex endPoint1;
    protected InnerVertex endPoint2;
    protected int weight;

    protected InnerEdge(InnerVertex endPoint1, InnerVertex endPoint2, DListNode node1, DListNode node2, int weight) {
        this.endPoint1 = endPoint1;
        this.endPoint2 = endPoint2;
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    /**
     * returns 此edge在innverVertex的adjacency的位置
     * @param innerVertex 欲查詢的innerVertex
     * @return  adjacency的位置
     */
    protected DListNode getAdjacencyNode(InnerVertex innerVertex) {
        if (innerVertex.element.equals(endPoint1.element)) return node1;
        else if (innerVertex.element.equals(endPoint2.element)) return node2;
        else return null;
    }

    /**
     *  returns the opposite end point of the input end point
     * @param endPoint input end point
     * @return the opposite end point
     */
    protected InnerVertex getOpposite(InnerVertex endPoint) {
        if (endPoint.element.equals(endPoint1.element)) {
            return endPoint2;
        } else if (endPoint.element.equals(endPoint2.element)) {
            return endPoint1;
        } else {
            return null;
        }
    }
}
