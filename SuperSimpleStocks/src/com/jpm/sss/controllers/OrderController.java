package com.jpm.sss.controllers;

import com.jpm.sss.dao.DaoException;
import com.jpm.sss.dao.OrdersDao;
import com.jpm.sss.dao.StocksDao;
import com.jpm.sss.dao.TradersDao;
import com.jpm.sss.models.EntityException;
import com.jpm.sss.models.bean.Stock;
import com.jpm.sss.models.bean.Order;
import com.jpm.sss.models.bean.Trader;
import com.jpm.sss.models.bean.Order.OrderStates;
import com.jpm.sss.models.bean.Order.OrderTypes;

/**
 * @author  Cristiano Salvati
 * @version 1.0
 * @since   2016-02-25 
 * 
 * This class implements the controller for managing stocks's Orders.
 * The method setNewOrder implements an action that set a new a stock's order for 
 * a trader in terms of order type (buy or sell), shares amount and price.
 * 
 **/

public class OrderController  extends ControllerAbstract{
	
	private static OrderController controller;
	private OrdersDao orders;
	private TradersDao  users;
	private StocksDao  stocks;

	public synchronized static ControllerAbstract getInstance()  throws ControllerException{
		if (null == controller) {
			controller = new OrderController();
				if ( !controller.isInit() ) controller.init();
			}
		return controller;
		}
	
	private OrderController(){
		orders = (OrdersDao)OrdersDao.getInstance();
		users =  (TradersDao)TradersDao.getInstance();
		stocks = (StocksDao)StocksDao.getInstance();
		}
	
	public synchronized String setNewOrder(String... params )  throws ControllerException{
		try{
			String userId=params[0]; 
			String stockSymbol=params[1];  
			String type=params[2];  
			String shares=params[3]; 
			String price=params[4]; 
	
			Stock stock = stocks.getStockBySymbol(stockSymbol);
			Trader  user  = (Trader)users.get(userId);
			Order order = null;
			if (type.equals(OrderTypes.BUY.toString())){
				order =  new Order(OrderTypes.BUY, OrderStates.WAITING, user, stock, Integer.parseInt(shares), Float.parseFloat(price));
			}else{
				order =  new Order(OrderTypes.SELL, OrderStates.WAITING, user, stock, Integer.parseInt(shares), Float.parseFloat(price));
				}
			
			setSessionParam("setNewOrder_"+stockSymbol+"_"+userId, order);
			
			return "putNewTradeOrder";
		}
		catch(DaoException | EntityException e){
			throw new ControllerException(e);
			}
		
		}
	
	@Override
	public synchronized boolean isInit()  throws ControllerException{
		try{
		if ( null != this.orders &&
			 null != this.stocks &&
			 null != this.users)	return true;
		}
		catch(Throwable  e){
			throw new ControllerException(e);
			}
		return false;
	}

	

}
