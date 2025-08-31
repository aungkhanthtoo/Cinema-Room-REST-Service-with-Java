package cinema.controller;

public class InvalidSeatNumberException extends RuntimeException {

    public InvalidSeatNumberException() {
        super("The number of a row or a column is out of bounds!");
    }
}
