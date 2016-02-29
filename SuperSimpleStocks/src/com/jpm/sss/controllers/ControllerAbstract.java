package com.jpm.sss.controllers;

import java.util.concurrent.ConcurrentHashMap;


/**
*  Abstract class for a Controller. Implements only common methods for session managing.
*  The class is not a concrete controller, needs to be extend even if is not defined
*  any abstract methods. Every concrete class is a singleton.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public abstract class ControllerAbstract implements ControllerInterface{
	
	public static final String REDIRECT_TO_OTHER_ACTION = "REDIRECT_TO_OTHER_ACTION";
	public static final String REDIRECT_TO_PAGE_ERROR = "REDIRECT_TO_PAGE_ERROR";
	public static final String REDIRECT_TO_LOGOUT_NEXTSTEP = "logIn"; 
	public static final String LOGIN_ACTION = "logIn";
	public static final String LOGOUT_ACTION = "logOut";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_KO = "KO";

	
	/*Memory for save and share objects through the concrete Controllers*/
	private static ConcurrentHashMap<String, Object> session = new ConcurrentHashMap<String, Object>();

	
	public static synchronized ControllerAbstract getInstance() throws ControllerException{
		throw new ControllerException("concrete Controller object have to implements getInstace");
	}
	
	public String init() throws ControllerException {
		 throw new ControllerException("Abstract Controller cannot be initialized!");
	}
	
	/**
	 * Methods used to managing the session.
	 * @param k key to set/get or remove an object to the session
	 * @param v the object stored in the session
	 **/
	public void setSessionParam(String k, Object v){
		session.put(k, v);
	} 
	
	public Object getSessionParam(String k){
		return session.get(k);
	} 
	
	public void removeSessionParam(String k){
		session.remove(k);
	}

	public void clearSession(){
		session.clear();
	}
	
}
