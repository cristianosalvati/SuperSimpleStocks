package com.jpm.sss.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* This Class implements a simple "action processor". The method 'perform' 
* get the correct Controller for the required action and invoke at runtime 
* the method mapped to the actions in the processMap object.
* (see file 'action.properties')
* 
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public class ActionProcessor {
	
	private static final Logger log = Logger.getLogger( ActionProcessor.class.getName() );
	private static ConcurrentHashMap<String, ControllerAbstract> processMap;
	private static Properties actionControllerMap;
	
	/*Action and Controller Map initialization*/
	static{
	    try {
	      actionControllerMap = new Properties();
	      processMap= new  ConcurrentHashMap<String, ControllerAbstract>();
	      actionControllerMap.load(ActionProcessor.class.getResourceAsStream("actions.properties"));
	      for (String key : actionControllerMap.stringPropertyNames()) {   
	    	  if ( !processMap.containsKey(key)){
	    	    String className = actionControllerMap.getProperty(key);
			    Class<?> act = Class.forName("com.jpm.sss.controllers."+className);
			    Method instanceFactory = act.getDeclaredMethod("getInstance");
			    instanceFactory.setAccessible(true);
			    processMap.put(key, (ControllerAbstract)instanceFactory.invoke(null));
	    	  	}
			}
	      if (actionControllerMap.size() == 0 || actionControllerMap.size() != processMap.size())
	    	  throw new ControllerException("actionControllerMap.size()="+actionControllerMap.size()+"; processMap.size()"+processMap.size());
	      
	    }
	    catch(Throwable ex){
	    	String message = "ActionProcessor ERROR: failure loading in file 'action.properties':";
	    	log.log( Level.SEVERE, message + ex.toString(), ex );
	    	throw new RuntimeException(ex);
	    }
	}
	
	public static String perform(String actionName, String... params) throws ControllerException, Exception{
		try {
			if (null == actionName){
				throw new IllegalArgumentException("Action name to perform cannot be null");
			}
			String controllerName = (String)actionControllerMap.get(actionName);
			Class<?> act = Class.forName("com.jpm.sss.controllers."+controllerName);
			ControllerAbstract action = processMap.get(actionName);
			
			Method m = act.getMethod(actionName, new Class[]{String[].class});
		    m.setAccessible(true);
			return (String)m.invoke(  action   , new Object[]{params });
			} 
		catch (SecurityException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException e) {	
			/*Ok something go wrong in the reflection... send it to the client to stop execution*/
			throw new Exception( e ); 
			}
		catch (InvocationTargetException  e){
			/*Ok something go wrong in the Controller's operation... send the messagge to the client to known the original cause.*/
			throw new ControllerException(e.getTargetException());
			}
		catch (Throwable t){
			/*Ok ... some other kind of errors here*/
			throw new ControllerException( t );
		}
		
	}

	public static String[] getActions() {
		int size = processMap.keySet().size();
		return processMap.keySet().toArray(new String[size]);
	}

}
