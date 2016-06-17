package com.sharko.exception;

public class NoSuchIndexException extends Exception {

	public Object NoSuchIndexException() {
		System.out.println("!!! Index either less than zero or exceeds journal dimension.");
		return null;
	}
	
	public NoSuchIndexException(){
		System.out.println("!!! Index either less than zero or exceeds journal dimension.");
	}
	
}
