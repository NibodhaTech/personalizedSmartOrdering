package com.nibodha.ordering.exception;

public class CannotAccessFacebookException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493268145123074410L;

	public CannotAccessFacebookException() {
		super();
	}

	public CannotAccessFacebookException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CannotAccessFacebookException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotAccessFacebookException(String message) {
		super(message);
	}

	public CannotAccessFacebookException(Throwable cause) {
		super(cause);
	}

}
