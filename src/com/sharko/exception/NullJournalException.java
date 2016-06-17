package com.sharko.exception;

public class NullJournalException extends Exception {

	public NullJournalException() {
		System.out.println("!!! Can't add a null object as a journal.");
	}
}
