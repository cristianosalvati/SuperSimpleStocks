package com.jpm.sss.models.bean;

import java.util.Date;
import java.util.UUID;

import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.EntityException;

/**c
 * 
* Entity bean for an Order. 
* An order is an instruction to buy or sell on a trading venue such as a stock.
* An order is related to a determinated stock and trader.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/


public class Order extends EntityAbstract{


	private static final long serialVersionUID = 1L;

	public static enum OrderTypes{ BUY, SELL};
	public static enum OrderStates{ WAITING, PENDING, PROCESSED};
	
	private String   	orderId;
	private String  	orderType;
	private Trader 		user;
	private Stock		stock;
	private Integer 	sharesAmmount;
	private Float		price;
	private Date		timeStamp;
	private String		status;
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(OrderStates state) {
		this.status = state.toString();
	}


	public Order(OrderTypes type, OrderStates state, Trader user, Stock stock, Integer shares, Float price) throws EntityException
		{
		if (null == type ||
			null == user ||	
			null == stock || 
			null == shares ||
			null == price ||
			null == state ||
			0 >= shares ||
			0 >= price) throw new EntityException("Unable to define this order");
		
		this.orderId = UUID.randomUUID().toString(); 
		this.orderType = type.toString(); 
		this.user = user;
		this.stock = stock;
		this.sharesAmmount = shares;
		this.price = price;
		this.timeStamp= new Date();
		this.status = state.toString();		
		}

	
	public String getOrderId() {
		return orderId;
	}

	public String getUserId() {
		
		return user.getUserId();
	}

	public String getStockId() {
		return stock.getStockId();
	}
	public String getStockSymbol() {
		return  stock.getStockSymbol();
	}

	public String getType() {
		return orderType;
	}

	@Override
	public Date getCreatedAt() {
	
		return timeStamp;
	}

	public Integer getSharesAmount() {
		
		return this.sharesAmmount;
	}

	public Float getTradePrice() {
		
		return price;
	}

	public boolean isSellOrder() {
		if (OrderTypes.SELL.toString().equals(orderType)) return true;
		
		return false;
	}

	public boolean isBuyOrder() {
		if (OrderTypes.BUY.toString().equals(orderType)) return true;

		return false;
	}

	@Override
	public String getPrimaryKey() {
		
		return getOrderId();
	}
	
	@Override 
	public boolean equals(Object o){
		
		if ( null != o &&
				 o instanceof Order){
				Order u = (Order)o;
				if (this.getOrderId().equals( u.getOrderId()) &&
					this.getType().equals(u.getType()) &&
					this.getUserId().equals(u.getUserId()) &&
					this.getStockId().equals(u.getStockId()) &&
					this.getSharesAmount().equals(u.getSharesAmount()) &&
					this.getTradePrice().equals(u.getTradePrice()) &&
					this.getCreatedAt().equals(u.getCreatedAt())
						){return true;}
				
				}	
			return false;
	}
	
	@Override
	public int hashCode()
		{
		int result = 1; 
		
		result= result * 101  + (this.orderId	!= null ? this.orderId.hashCode() : 0);
		result= result * 103  + (this.orderType	!= null ? this.orderType.hashCode() : 0);
		result= result * 107  + (this.user		!= null ? this.user.hashCode() : 0);
		result= result * 109  + (this.stock		!= null ? this.stock.hashCode() : 0);
		result= result * 113  + (this.sharesAmmount	!= null ? this.sharesAmmount.hashCode() : 0);
		result= result * 127  + (this.price		!= null ? this.price.hashCode() : 0);
		result= result * 131  + (this.timeStamp	!= null ? this.timeStamp.hashCode() : 0);

		return result;
		}

}
