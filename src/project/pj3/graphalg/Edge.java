package project.pj3.graphalg;



class Edge {
    int weight;
    Object endPoint1;
    Object endPoint2;

    protected Edge(Object endPoint1, Object endPoint2, int weight) {
        this.endPoint1 = endPoint1;
        this.endPoint2 = endPoint2;
        this.weight = weight;
    }

}
