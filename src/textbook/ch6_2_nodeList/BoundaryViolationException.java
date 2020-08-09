package textbook.ch6_2_nodeList;

/*Thrown if an attempt is made at accessing an element whose position is outside
* the range of positions of the list.*/
public class BoundaryViolationException extends RuntimeException {
    public BoundaryViolationException(String msg) {
        super(msg);
    }
}
