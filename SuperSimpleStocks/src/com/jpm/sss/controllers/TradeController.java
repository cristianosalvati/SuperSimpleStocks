package com.jpm.sss.controllers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jpm.sss.dao.DaoException;
import com.jpm.sss.dao.OrdersDao;
import com.jpm.sss.dao.TradesDao;
import com.jpm.sss.models.EntityException;
import com.jpm.sss.models.bean.Trade;
import com.jpm.sss.models.bean.Order;

/**
 * @author  Cristiano Salvati
 * @version 1.0
 * @since   2016-02-25 
 * 
 * This class implements the controller for managing stocks's transactions (Trades) 
 **/


public class TradeController extends ControllerAbstract{
	
	private static final Logger log = Logger.getLogger( TradeController.class.getName() );
	private static TradeController controller;
	private TradesDao trades;
	private OrdersDao tradeOrders;
	
	public synchronized static ControllerAbstract getInstance() throws ControllerException{
		if (null == controller) {
			controller = new TradeController();
				if ( !controller.isInit() ) controller.init();
			}
		return controller;
		}
	
	private TradeController() throws ControllerException{
		trades = (TradesDao)TradesDao.getInstance();
		tradeOrders = (OrdersDao)OrdersDao.getInstance();
		
	}
	

	/**
	 * A-3
	 * This method record a trade, with timestamp, quantity of shares, buy or sell indicator and price for a given stock.
	 * The trade is recorded only if two orders (buy and sell) match values of shares and price.
	 **/
	public synchronized String putNewTradeOrder(String... params) throws ControllerException{

		String UserId 		= params[0]; 
		String stockSymbol	= params[1];
		
		try{
		Order order = (Order)getSessionParam("setNewOrder_"+stockSymbol+"_"+UserId);
		
		if (null != order )
			{
			String orderType= null;
			//define a kind of order that matches 
			if (  !order.isBuyOrder() )
					orderType = Order.OrderTypes.BUY.toString();
			else 	orderType = Order.OrderTypes.SELL.toString();
			
			//TODO da rinominare
			ArrayList<Order>  orderList= tradeOrders.getOrdersListInWaiting(order.getStockId(), orderType);
			 
			for(Order entity: orderList){
				/*In case of same price, same share's amount and different trader*/
				if (order.getTradePrice().equals(entity.getTradePrice()) 	&&
					order.getSharesAmount().equals(entity.getSharesAmount()) &&
					!order.getUserId().equals(entity.getUserId()) 
						)
					{	
					/*OK, so let's match these orders in one trade*/
					Trade trade = new Trade(entity, order);
					entity.setStatus(Order.OrderStates.PROCESSED);	
					order.setStatus(Order.OrderStates.PROCESSED);
					/*Record this transaction*/
					trades.insert(trade);
					removeSessionParam("setNewOrder_"+stockSymbol+"_"+UserId);
					break;
					}
				}
			
			tradeOrders.insert(order);
			String syso = String.format(" ->Trade order placed for %s\n ->(count=%s)", order.getStockId(), tradeOrders.count() );
			log.log(Level.FINE, syso);
			/*TODO remove syso*/
			System.out.println(syso);
			}
		}catch(DaoException | EntityException e){
			throw new ControllerException(e);
			}
		finally{
			try {
				String syso= String.format(" ->Trades List (count=%s):\n ->%s\n", trades.count(),trades );
				log.log(Level.FINE,syso);
				/*TODO remove syso*/
				System.out.println(syso);
			} catch (DaoException e) {
				throw new ControllerException(e);
			}
		}
		return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
		}

	
	/** A-4
	 *  This method Calculate Stock Price based on trades recorded in past 15 minutes
	 **/
	public synchronized String putStockPriceCalculatedOnLast15Min(String... params) throws ControllerException{
		try{

			String userId 		= params[0]; 
			String stockSymbol	= params[1];
			
			Double sp = trades.calculateStockPriceOnLast15Min(stockSymbol);
			setSessionParam("stockPriceCalculated_"+stockSymbol, sp);
			log.log(Level.FINE, String.format(" ->User %s calcolate price for Stock %s = %s", userId, stockSymbol, sp));
		}catch(DaoException e){
			throw new ControllerException(e);
		}
		
		return "putStockPrice";
	}
	

	
	@Override
	public synchronized boolean isInit()  throws ControllerException{
		if (trades == null || tradeOrders == null)
			return false;
		
		return true;
	}

	

	

}
