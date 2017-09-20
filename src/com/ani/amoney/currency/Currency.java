package com.ani.amoney.currency;

/**
 * @author ashishani
 *
 */
public enum Currency {

	USD ("USD");
	
	private String code;
	
	private Currency(String code) {
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
}
