package com.jpm.sss.models;

import java.util.Date;


/**
* Interface for a generical relational Entity. 
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public interface EntityInterface {
	
	public String getPrimaryKey() throws EntityException;
	public Date getCreatedAt()  throws EntityException;

}
