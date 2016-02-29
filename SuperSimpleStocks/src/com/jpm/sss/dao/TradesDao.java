package com.jpm.sss.dao;


import java.util.Date;
import java.util.List;
import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.bean.Trade;
import com.jpm.sss.utils.DateUtils;


/** 
 * @author Salvati 
 * 
 * Concrete DAO implementation for Trades managing.  
 * Trade is defined in package 'bean'
 */


public class TradesDao extends DaoAbstract{

	private static TradesDao 	dao;
	DataConnector2Map trades;
	
	private TradesDao(){
		trades = new DataConnector2Map();
	}
	
	public static synchronized DaoAbstract getInstance(){
		if (null == dao){
			dao = new TradesDao();
		}
		return dao;
	}
	
	protected  DataConnectorInterface getData(){
		/*return dataConnector implementation*/
		return this.trades;
	}
	
	/*sincronizzare questo metodo???*/
	public  Double calculateStockPriceOnLast15Min(String stockSymbol) throws DaoException{
		
		List<EntityAbstract> tradeList = this.getAll();
		double heavyTradeSummatory    = 0;
		double quantitySummatory = 0;
		
		if (tradeList != null && tradeList.size() > 0){
			Date now = new Date();
			Date fifthteenMinsAgo = DateUtils.date2NewDate(now, 0, 0, -15, 0);
			
			for (EntityAbstract e : tradeList){
					Trade t =(Trade)e;
					if (t.getSellingOrder().getStockSymbol().equals(stockSymbol) && t.getCreatedAt().compareTo(fifthteenMinsAgo)>0 && t.getCreatedAt().compareTo(now) <0){
						heavyTradeSummatory += t.getSellingOrder().getTradePrice() * t.getSellingOrder().getSharesAmount();
						quantitySummatory   += t.getSellingOrder().getSharesAmount();
					}
				}
			}
		
		if (quantitySummatory == 0 ) throw new DaoException("Unable to divide per 0 with no elements");	
		
		return heavyTradeSummatory/quantitySummatory;
		
	}
	/*XXX calcolare sugli stock*/
//	public Double calculateGeometricMean4AllStocks() throws DaoException{
//			
//		double result=1;
//		List<EntityAbstract> tradeList = this.getAll();
//		if (tradeList != null && tradeList.size() > 0){
//			for (EntityAbstract e : tradeList){
//				Trade t =(Trade)e;
//				result *= t.getSellingOrder().getTradePrice();
//				}
//			return Math.pow(result, 1/tradeList.size());	
//			}
//		
//		throw new DaoException("Unable to compute geometric mean with 0 stocks");	
//		}
}
