package pl.michal_baniowski.coding_forum.exception;

public class QueryException extends RuntimeException {
    public QueryException(String message){
        super(message);
    }
}
