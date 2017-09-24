package com.hwadee.bookstore.exception;

/*
 * 自定义异常类
 */
public class DBException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBException() {

	}

	public DBException(String message) {
		super(message);
	}

	public DBException(String message, Exception exception) {
		super(message, exception);
	}
}
