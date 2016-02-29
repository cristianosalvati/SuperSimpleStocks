package com.jpm.sss.controllers;

/**
*  Exception raised from objects defined in the 'Controller' layer.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/


public class ControllerException extends Exception{

	private static final long serialVersionUID = 1L;

	public ControllerException(Throwable t){
		super(t);
		}
	
	public ControllerException(String s){
		super(s);
		}
	
	}
