package textbook.ch6_2_nodeList;

/*Thrown if a position provided as argument is no valid.*/
public class InvalidPositionException extends RuntimeException {
    public InvalidPositionException(String msg) {
        super(msg);
    }
}
