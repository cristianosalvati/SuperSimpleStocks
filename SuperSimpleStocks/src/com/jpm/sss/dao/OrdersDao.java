package com.jpm.sss.dao;

import java.util.ArrayList;
import java.util.List;
import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.bean.Order;

/**
* Concrete DAO implementation for Orders managing.  
* Object Order is defined in package 'bean'.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public class OrdersDao extends DaoAbstract{

	private static OrdersDao 	dao;
	DataConnector2Map 			orders;
	
	private OrdersDao(){
		orders = new DataConnector2Map();
	}
	
	public static synchronized DaoAbstract getInstance(){
		if (null == dao){
			dao = new OrdersDao();
		}
		return dao;
	}
	
	protected  DataConnectorInterface getData(){
		return this.orders;
	}
	
	/**
	* Query for BUYING ORDERS waiting a match with a SELL ORDER
	* a-3.	For a given stock record a trade, with timestamp, 
	* quantity of shares, buy or sell indicator and price
	*/
	public  ArrayList<Order> getOrdersListInWaiting(String stockId, String type) throws DaoException{
		
		ArrayList<Order> result = new ArrayList<Order>();
		
		List<EntityAbstract> orderList= this.getAll();
		for(EntityAbstract entity: orderList){
			Order order = (Order)entity;
			if (
				order.getStockId().equals(stockId)			&&
				order.getType().equals(type.toString()) 	&&
				order.getStatus().equals(Order.OrderStates.WAITING.toString())
				){
				result.add(order);
				}	
			}
		
		return result;
	}
	
	
	
}
