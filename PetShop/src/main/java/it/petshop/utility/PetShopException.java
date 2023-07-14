package it.petshop.utility;

public class PetShopException extends RuntimeException {

    public PetShopException() {
        super();
    }

    public PetShopException(String message) {
        super(message);
    }

    public PetShopException(String message, Throwable cause) {
        super(message, cause);
    }
}
