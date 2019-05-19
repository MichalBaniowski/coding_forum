package pl.michal_baniowski.coding_forum.exception;

public class WrongArgumentException extends QueryException {
    public WrongArgumentException(String message){
        super(message);
    }
}
