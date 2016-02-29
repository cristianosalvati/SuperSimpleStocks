package com.jpm.sss.models;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
* Abstract definition of a relational Entity. Every class in package 'bean' implements this class.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public abstract class EntityAbstract implements EntityInterface, Comparable<EntityAbstract>, Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger( EntityAbstract.class.getName() );
	
	/**
	 * This method override toString to show field values for a generic bean.
	 */
	
	@Override
	public String toString()
	{
		Class<?> c = this.getClass();
		
		Method[] methods = c.getDeclaredMethods();
		StringBuffer toStringOut = new StringBuffer();
		Integer methodsCount  =0;
		toStringOut.append("{");
	    for (Method method : methods) 
	    	{
		      String methName = method.getName();
		      if (methName.startsWith("get") && methName.length() >3 && method.getParameterTypes().length ==0)	
		      	{
		    	  toStringOut.append(methName.substring(3)); 
		    	  toStringOut.append("=");
		    	  try 
		    	  	{
		    		toStringOut.append("'");
		    		Object o = method.invoke(this);
		    		if (null != o ) toStringOut.append(o.toString());
		    		toStringOut.append("'");
		    	  	} 
		    	  catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		    	  	{
		    		toStringOut.append("'"+e.getMessage()+"'");
		    	  	}
		    	  catch(Throwable t){
		    		  log.log( Level.SEVERE, t.toString(), t ); 
		    	  	}
		    	  finally{
		    		  if (methodsCount < methods.length-1) toStringOut.append(", ") ;
			    		methodsCount++;
		    	  	}
		      	}
		      
		
	    	}
	    toStringOut.append("}");
	
	    return toStringOut.toString();
	}
	
	
	/*
	 * Definition for the compareTo method. Every entity is compared using the date field "createdAt".
	 */
	@Override
	public int compareTo(EntityAbstract c)
		{	
			try {
				EntityAbstract temp = (EntityAbstract)c;
				return this.getCreatedAt().compareTo(temp.getCreatedAt());
			} catch (EntityException ex) {	
				log.log( Level.WARNING, ex.toString(), ex );
				return 0;
			}
			
		}
}
