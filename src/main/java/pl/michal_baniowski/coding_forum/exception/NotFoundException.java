package pl.michal_baniowski.coding_forum.exception;

public class NotFoundException extends QueryException {
    public NotFoundException(String message){
        super(message);
    }
}
