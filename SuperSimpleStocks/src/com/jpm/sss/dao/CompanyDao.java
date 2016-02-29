package com.jpm.sss.dao;


/**
* Dao for the entity Company.
* Company is defined in package bean.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public class CompanyDao extends DaoAbstract{

	private static CompanyDao 	dao;
	DataConnector2Map 			company;
	
	private CompanyDao(){
		company = new DataConnector2Map();
	}
	
	public static synchronized DaoAbstract getInstance(){
		if (null == dao){
			dao = new CompanyDao();
		}
		return dao;
	}
	
	protected  DataConnectorInterface getData(){
		/*return dataConnector implementation*/
		return this.company;
	}
	
}

