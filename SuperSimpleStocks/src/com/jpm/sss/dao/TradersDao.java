package com.jpm.sss.dao;

import java.util.ArrayList;


/** 
 * @author Salvati 
 * 
 * Concrete DAO implementation for Traders (Users) managing.  
 * Object Trader is defined in package 'bean'
  */


public class TradersDao extends DaoAbstract{

	private static TradersDao 	dao;
	DataConnector2Map users;
	
	private TradersDao(){
		users = new DataConnector2Map();
	}
	
	public static synchronized DaoAbstract getInstance(){
		if (null == dao){
			dao = new TradersDao();
		}
		return dao;
	}
	

	public ArrayList<String> getEntityIdList() throws DaoException{
			return super.getEntityIdList();
		}
	
	
	protected  DataConnectorInterface getData(){
		/*return dataConnector implementation*/
		return this.users;
	}
	
}
