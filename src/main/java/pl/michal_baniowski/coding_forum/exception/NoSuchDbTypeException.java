package pl.michal_baniowski.coding_forum.exception;

public class NoSuchDbTypeException extends RuntimeException {
    public NoSuchDbTypeException(String message){
        super(message);
    }
}
