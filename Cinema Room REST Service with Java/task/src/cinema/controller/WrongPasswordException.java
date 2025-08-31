package cinema.controller;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("The password is wrong!");
    }
}
