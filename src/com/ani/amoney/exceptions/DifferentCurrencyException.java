package com.ani.amoney.exceptions;

/**
 * @author ashishani
 *
 */
public class DifferentCurrencyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public DifferentCurrencyException() {
		
	}
	
	/**
	 * @param message
	 */
	public DifferentCurrencyException(String message) {
		super(message);
	}

}
