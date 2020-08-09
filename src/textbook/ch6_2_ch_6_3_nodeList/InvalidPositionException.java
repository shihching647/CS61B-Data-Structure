package textbook.ch6_2_ch_6_3_nodeList;

/*Thrown if a position provided as argument is no valid.*/
public class InvalidPositionException extends RuntimeException {
    public InvalidPositionException(String msg) {
        super(msg);
    }
}
