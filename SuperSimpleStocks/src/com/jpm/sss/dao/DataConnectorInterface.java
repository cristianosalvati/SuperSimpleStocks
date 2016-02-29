package com.jpm.sss.dao;

import java.util.List;
import com.jpm.sss.models.EntityAbstract;


/**
*  Interface for a data connector.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/


public interface DataConnectorInterface {

	public List<EntityAbstract> values() throws DaoException;;
	public EntityAbstract get(String c) throws DaoException;;
	public void remove(String s ) throws DaoException;;
	public void put(String s, EntityAbstract c) throws DaoException;;
	public void clear() throws DaoException;;
	public Integer size() throws DaoException;;
	public boolean containsKey(String k) throws DaoException;;
}
