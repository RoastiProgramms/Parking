package main.parkinglot;

public class Exceptions extends RuntimeException {

    private static final long serialVersionUID = 9026290603804836446L;
    private String message;

    public Exceptions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
