package com.jpm.sss.models;


/**
* Exception raised from objects defined in the 'Model' layer.
* 
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public class EntityException extends Exception{


	private static final long serialVersionUID = 1L;

	public EntityException(Throwable t){
		super (t);
	}
	
	public EntityException(String s){
		super (s);
	}

}
