package cinema.controller;

public class SeatAlreadyTakenException extends RuntimeException {

    public SeatAlreadyTakenException() {
        super("The ticket has been already purchased!");
    }
}

