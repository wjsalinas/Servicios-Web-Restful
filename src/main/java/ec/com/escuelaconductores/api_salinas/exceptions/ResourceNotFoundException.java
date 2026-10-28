package ec.com.escuelaconductores.api_salinas.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " con id " + id + " no existe o está inactivo");
    }
}
