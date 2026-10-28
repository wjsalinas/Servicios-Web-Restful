package ec.com.escuelaconductores.api_salinas.exceptions;

public class JwtAuthException extends RuntimeException {

    public JwtAuthException(String message) {
        super(message);
    }
}
