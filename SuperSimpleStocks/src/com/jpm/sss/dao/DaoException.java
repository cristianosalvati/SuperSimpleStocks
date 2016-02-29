package com.jpm.sss.dao;

/**
* Exception raised from objects defined in the 'Dao' layer.
* 
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/


public class DaoException extends Exception{

	private static final long serialVersionUID = 1L;

	public DaoException(Throwable t){
		super (t);
	}
	
	public DaoException(String s){
		super (s);
	}
	
}
