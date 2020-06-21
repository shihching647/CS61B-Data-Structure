package laboratory.lab6;

/**
 *  Implements an exception that should be thrown for bad transaction.
 **/
public class BadTransactionException extends Exception{

    public BadTransactionException(String msg) {
        super(msg);
    }
}
