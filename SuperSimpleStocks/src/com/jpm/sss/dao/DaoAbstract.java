package com.jpm.sss.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.EntityException;

/**
* Abstract implementation of main methods for a generic DAO.
* The typical methods such as update, delete, get, etc works on a data returned from a 
* data connector, not implemented in this class.  
* 
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public abstract class DaoAbstract implements DaoInterface{


	public static DaoAbstract getInstance() throws DaoException{
		throw new DaoException("Dao object have to implements getInstace");
	}
	
	protected abstract DataConnectorInterface getData();
	
	@Override
	public List<EntityAbstract> getAll()  throws DaoException{
		List<EntityAbstract> temp = getData().values();
		Collections.sort(temp);
		return temp;
	}

	@Override
	public EntityAbstract get(String key)  throws DaoException{
		return (EntityAbstract) getData().get(key);
	}

	@Override
	public void update(EntityAbstract e)  throws DaoException{	
		try {
			getData().remove(e.getPrimaryKey());
			getData().put(e.getPrimaryKey(), e);
			} 
		catch (EntityException ex) {
			throw new DaoException(ex);
		}
	
	}

	@Override
	public void delete(EntityAbstract e)   throws DaoException{
		try {
			getData().remove(e.getPrimaryKey());
		} catch (EntityException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public void insert(EntityAbstract e)  throws DaoException{
		try {
			getData().put(e.getPrimaryKey(), e);
		} catch (EntityException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public void drop()  throws DaoException{
		getData().clear();
	}

	@Override
	public List<EntityAbstract> select(String... params)  throws DaoException{
		
		List<EntityAbstract> temp = new ArrayList<EntityAbstract>();
		
		for(String key : params)
				if (this.getData().containsKey(key))
					temp.add(this.getData().get(key));
		
		return temp;
	}
	
	@Override
	public Integer count()  throws DaoException{	
		return getData().size();
	}
	
	@Override 
	public String toString() {
		return getData().toString();
		}
	
	protected ArrayList<String> getEntityIdList()  throws DaoException{	
		try{
		List<EntityAbstract> tempList = this.getAll();
		ArrayList<String> list = new ArrayList<String>();
		
		if (tempList != null && tempList.size() > 0){
			
			for (EntityAbstract e : tempList){
				list.add(e.getPrimaryKey());
				}
			return list;
			}
		throw new DaoException("Unable to list entity ID");	
		}
		catch (EntityException ex) {
			throw new DaoException(ex);
	 	}
	}

}
