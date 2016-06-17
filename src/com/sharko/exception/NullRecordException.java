package com.sharko.exception;

public class NullRecordException extends Exception {

	public NullRecordException() {
		System.out.println("!!! Can't add null record.");
	}
}
