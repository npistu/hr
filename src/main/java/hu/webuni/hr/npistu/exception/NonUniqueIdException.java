package hu.webuni.hr.npistu.exception;

public class NonUniqueIdException extends RuntimeException{
    public NonUniqueIdException() {
        super("ID should be unique!");
    }
}
