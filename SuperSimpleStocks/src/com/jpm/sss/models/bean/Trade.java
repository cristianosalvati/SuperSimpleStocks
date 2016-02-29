package com.jpm.sss.models.bean;

import java.util.Date;
import java.util.UUID;
import com.jpm.sss.models.bean.Order;
import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.EntityException;


/**
* 
* Entity bean for a trade.
* A trade (or transaction) is record that put together a 'Buy' and a 'Sell' orders
* from different traders and that match shares and price.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public class Trade  extends EntityAbstract{
	
	private static final long serialVersionUID = 1L;
	private String tradeId;
	private Order buyer;
	private Order seller;
	private Date  time;
	
	public Trade(Order buyOrder, Order sellOrder) throws EntityException{
		
		Order swap=  null;
		if(buyOrder.isSellOrder() && sellOrder.isBuyOrder()){
			swap = buyOrder;
			buyOrder = sellOrder;
			sellOrder = swap;
			}
		
		if (null == buyOrder ||
			null == sellOrder ||
			!buyOrder.isBuyOrder() ||
			!sellOrder.isSellOrder() ||
			 buyOrder.getSharesAmount() > sellOrder.getSharesAmount() ||	
			!buyOrder.getStockId().equals(sellOrder.getStockId()) ||
			!buyOrder.getTradePrice().equals(sellOrder.getTradePrice()) ||
			!buyOrder.getStatus().equals(Order.OrderStates.WAITING.toString()) ||
			!sellOrder.getStatus().equals(Order.OrderStates.WAITING.toString()) ||
			 buyOrder.getUserId().equals(sellOrder.getUserId()) 
			) throw new EntityException("Invalid Trade definition");
		
		tradeId= UUID.randomUUID().toString();
		buyer  = buyOrder;
		seller = sellOrder;
		time   = new Date();
		
	}

	public String getTradeId() {
		return tradeId;
	}

	public Order getBuyingOrder() {
		return buyer;
	}

	public Order getSellingOrder() {
		return seller;
	}

	@Override
	public Date getCreatedAt() {
		return time;
	}
	
	@Override 
	public boolean equals(Object o){
		if ( null != o &&
				 o instanceof Trade){
				Trade u = (Trade)o;
				if (this.getTradeId().equals( u.getTradeId()) &&
					this.getBuyingOrder().equals(u.getBuyingOrder()) &&
					this.getSellingOrder().equals(u.getSellingOrder()) &&
					this.getCreatedAt().equals(u.getCreatedAt()) 
						){return true;}
				
				}	
		
		return false;
	}
	
	@Override
	public int hashCode()
		{
		int result = 1; 
		result= result * 101  + (this.tradeId	!= null ? this.tradeId.hashCode() : 0);
		result= result * 101  + (this.buyer		!= null ? this.buyer.hashCode()   : 0);
		result= result * 101  + (this.seller	!= null ? this.seller.hashCode()  : 0);
		result= result * 101  + (this.time		!= null ? this.time.hashCode()    : 0);
		
		return result;
		}

	@Override
	public String getPrimaryKey() {
		return this.getTradeId();
	}


}
