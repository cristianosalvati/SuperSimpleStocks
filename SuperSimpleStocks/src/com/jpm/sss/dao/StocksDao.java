package com.jpm.sss.dao;

import java.util.ArrayList;
import java.util.List;
import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.bean.Stock;


/** 
 * @author Salvati 
 * 
 * Concrete DAO implementation for Stocks managing.  
 * Object Stock is defined in package 'bean'
  */


public class StocksDao extends DaoAbstract{

	private static StocksDao 	dao;
	DataConnector2Map 			stocks;
	
	private StocksDao(){
		stocks = new DataConnector2Map();
	}
	
	public static synchronized DaoAbstract getInstance(){
		if (null == dao){
			dao = new StocksDao();
		}
		return dao;
	}
	
	
	protected  DataConnectorInterface getData(){
		/*return dataConnector implementation*/
		return this.stocks;
	}
	
	public Stock getStockBySymbol(String symbol) throws DaoException{
		
		List<EntityAbstract> tempList = this.getAll();
		
		
		if (tempList != null && tempList.size() > 0){
			
			for (EntityAbstract e : tempList){
				Stock s = (Stock)e;
				if (s.getStockSymbol().equals(symbol)){
					return s;
					}
				}
			}
		throw new DaoException("Unable to find this stock");	
		
		}
	
	
	public ArrayList<String> getStockSymbolList() throws DaoException{
		
		List<EntityAbstract> tempList = this.getAll();
		ArrayList<String> list = new ArrayList<String>();
		
		if (tempList != null && tempList.size() > 0){
			
			for (EntityAbstract e : tempList){
				Stock s = (Stock)e;
				list.add(s.getStockSymbol());
				}
			return list;
			}
		throw new DaoException("Unable to list stocks Symbol");	
		}
	
	
	public Double calculateGeometricMean4AllStocks() throws DaoException{
		
		double result=1;
		List<EntityAbstract> stockList = this.getAll();
		if (stockList != null && stockList.size() > 0){
			for (EntityAbstract e : stockList){
				Stock s =(Stock)e;
				result *= s.getStockPrice();
				}
			return Math.pow(result, (double)1/(double)stockList.size());	
			}
		
		throw new DaoException("Unable to compute geometric mean with 0 stocks");	
	}
	
	
}
