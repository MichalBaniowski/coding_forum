package pl.michal_baniowski.coding_forum.exception;

public class UpdateFailException extends QueryException {
    public UpdateFailException(String message){
        super(message);
    }
}
