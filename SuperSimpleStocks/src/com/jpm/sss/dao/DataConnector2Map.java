package com.jpm.sss.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.jpm.sss.models.EntityAbstract;

/**
*  Implementation for a data connector that use an HashMap to store data.
*  This implementation can be changed to another that use a Driver to connect
*  to a real database.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/


public class DataConnector2Map implements DataConnectorInterface{
	
	private ConcurrentHashMap<String, EntityAbstract> data;

	public DataConnector2Map(){
		data= new  ConcurrentHashMap<String, EntityAbstract>();
	}
	
	@Override
	public List<EntityAbstract> values() {
		
		return new ArrayList<EntityAbstract>(data.values());
		
	}

	@Override
	public EntityAbstract get(String c) {
		return data.get(c);
	}

	@Override
	public void remove(String s) {
		data.remove(s);
		
	}

	@Override
	public void put(String s, EntityAbstract c) {
		data.put(s, c);	
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Integer size() {
		return data.size();
	}

	@Override
	public boolean containsKey(String k) {
		return data.containsKey(k);
	}
	
	@Override 
	public String toString(){
		return data.toString();
	}

}
