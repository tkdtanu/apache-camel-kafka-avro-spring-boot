package com.tkd.camel.exception;

public class AvroProcessingException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6736249119843952722L;

	public AvroProcessingException() {
		super();
	}

	public AvroProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public AvroProcessingException(String message) {
		super(message);
	}

	public AvroProcessingException(Throwable cause) {
		super(cause);
	}

}
