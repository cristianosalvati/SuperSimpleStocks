package com.jpm.sss.models.bean;

import java.util.Date;
import java.util.UUID;

import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.EntityException;

public class Stock extends EntityAbstract{


/**
* Entity bean for a Stock. This bean define a Stock category by a stockId and stockSymbol and keep
* informations as stockPrice, fixedDividend, lastDividend, parValue.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/
	
	private static final long serialVersionUID = 1L;
	public static enum StockTypes{ COMMON, PREFERED};
	private String  stockId;
	private String  stockSymbol;
	private String  type;
	private Company company;
	private Double  stockPrice;
	private Double  fixedDividend;
	private Double  lastDividend;
	private Integer parValue;
	private Date 	createdAt;
	
	
	public Stock(String stockSym, Stock.StockTypes stockType, Company company, Double lastDividend,  Double fixedDividend,Integer parValue, Double stockPrice) throws EntityException{
		this(stockSym, stockType, company);	
		this.setLastDividend(lastDividend);
		this.setFixedDividend(fixedDividend);
		this.setParValue(parValue);
		this.setStockPrice(stockPrice);
	}
	
	public Stock(String stockSym, Stock.StockTypes stockType, Company company) throws EntityException
		{
		if (null == stockSym ||
			null == stockType ||
//			null == marketP ||
			null == company) throw new EntityException("Invalid Stock definition");
		
		this.stockId = UUID.randomUUID().toString(); 
		this.type = stockType.toString();
		this.stockSymbol = stockSym;
		this.company =  company;
		this.fixedDividend = 0.0;
		this.lastDividend  = 0.0;
		this.parValue = 0;
		this.createdAt = new Date();
		this.stockPrice =0.0;
		}
	
	public void setFixedDividend(Double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public void setLastDividend(Double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public void setParValue(Integer parValue) {
		this.parValue = parValue;
	}

	public String getStockId() {
		return stockId;
	}

	public Double getFixedDividend() {
		return fixedDividend;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public String getStockType() {
		return type;
	}

	public Double getLastDividend() {
		return lastDividend;
	}

	public Integer getParValue() {
		return parValue;
	}

	public String getCompanyId() {
		return company.getCompanyId();
	}

//	public Object getStockMarketPlace() {
//		return marketPlace;
//	}

	public Double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}
	@Override
	public String getPrimaryKey() {
		
		return this.getStockId();
	}

	@Override
	public Date getCreatedAt() {
		
		return createdAt;
	}
	
	@Override
	public boolean equals(Object o){
		if ( null != o &&
			 o instanceof Stock){
			Stock u = (Stock)o;
			if (this.type.equals( u.getStockType()) &&
				this.stockSymbol.equals(u.getStockSymbol()) &&
//				this.marketPlace.equals(u.getStockMarketPlace()) &&
				this.company.getCompanyId().equals(u.getCompanyId()) &&
				this.fixedDividend.equals(u.getFixedDividend()) &&
				this.lastDividend.equals(u.getLastDividend()) &&
				this.parValue.equals(u.getParValue()) &&
				this.stockId.equals(u.getStockId())){
				return true;
			}
			
		}	
		return false;
	}
	
	
	@Override
	public int hashCode()
		{
		int result = 1;
		
		 //101 103    107    109    113 	127 	131		137    	139
		result = result * 101  + (this.type 			!= null ? this.type.hashCode() : 		0);
		result = result * 103  + (this.stockSymbol 		!= null ? this.stockSymbol.hashCode() : 0);
//		result = result * 107  + (this.marketPlace 		!= null ? this.marketPlace.hashCode() : 0);
		result = result * 109  + (this.company 			!= null ? this.company.hashCode() : 	0);
		result = result * 113  + (this.fixedDividend 	!= null ? this.fixedDividend.hashCode(): 0);
		result = result * 127  + (this.lastDividend 	!= null ? this.lastDividend.hashCode() : 0);
		result = result * 131  + (this.parValue 		!= null ? this.parValue.hashCode() : 	0);
		result = result * 137  + (this.stockId 			!= null ? this.stockId.hashCode() : 	0);

		return result;
		}


	


}
