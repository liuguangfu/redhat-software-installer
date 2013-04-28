package com.jware.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseException extends RuntimeException {
	private String messageId;

	public BaseException() {
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String id) {
		messageId = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String id) {
		messageId = id;
	}

	public String getStackTraceString() {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		this.printStackTrace(pw);
		return sw.toString();
	}
	
}

