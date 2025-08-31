package cinema.controller;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super("Wrong token!");
    }
}
