package com.jpm.sss.dao;
import java.util.List;

import com.jpm.sss.models.EntityAbstract;


/**
* Interface for DAO implementation
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public interface DaoInterface {

	public EntityAbstract get(String key) throws DaoException;
	public Integer count() throws DaoException;
	public List<EntityAbstract> getAll() throws DaoException;;
	public List<EntityAbstract> select(String... params)  throws DaoException;;
	public void update(EntityAbstract e) throws DaoException;;
	public void delete(EntityAbstract e) throws DaoException;;
	public void insert(EntityAbstract e) throws DaoException;;
	public void drop() throws DaoException;;
	
	
}
