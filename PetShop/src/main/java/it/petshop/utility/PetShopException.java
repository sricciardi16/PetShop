package it.petshop.utility;

public class PetShopException extends RuntimeException {
	private final int errorCode;

	public PetShopException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public PetShopException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public PetShopException(String message, int errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
