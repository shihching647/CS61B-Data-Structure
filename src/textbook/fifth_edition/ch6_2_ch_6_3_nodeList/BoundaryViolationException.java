package textbook.fifth_edition.ch6_2_ch_6_3_nodeList;

/*Thrown if an attempt is made at accessing an element whose position is outside
* the range of positions of the list.*/
public class BoundaryViolationException extends RuntimeException {
    public BoundaryViolationException(String msg) {
        super(msg);
    }
}
