package com.jpm.sss.controllers;


/**
* Interface for Controller's implementation.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public interface ControllerInterface {

	public String init() throws ControllerException;
	public boolean isInit() throws ControllerException;
	public void setSessionParam(String k, Object v) throws ControllerException;
	public Object getSessionParam(String k) throws ControllerException;
	public void removeSessionParam(String k) throws ControllerException;
	
}
