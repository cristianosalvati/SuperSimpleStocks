package com.jpm.sss.models.bean;

import java.util.Date;
import java.util.UUID;

import com.jpm.sss.models.EntityAbstract;


/**
* Entity bean for a Company. A company is a business entity that own different stocks.
* 
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public class Company  extends EntityAbstract{

	private static final long serialVersionUID = 1L;
	private String companyId;
	private String companyName;
	private String tradeArea;
	private Date createdAt;
	
	public Company(String companyName, String tradeArea){
		
		if (null == companyName || 
			null == tradeArea) throw new IllegalArgumentException("Invalid Company definition");
			
		this.companyId   = UUID.randomUUID().toString(); 
		this.companyName = companyName ;
		this.tradeArea   = tradeArea;
		this.createdAt 	 = new Date();
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public Object getCompanyName() {
		return companyName;
	}

	public Object getTradeArea() {
		return tradeArea;
	}
	
	@Override 
	public boolean equals(Object o){
		if ( null != o &&
			 o instanceof Company){
			 Company u = (Company)o;
				if (this.getCompanyId().equals( u.getCompanyId()) &&
					this.getCompanyName().equals( u.getCompanyName()) &&
					this.getTradeArea().equals( u.getTradeArea())
						)
				{
				return true;	
				}
			}
		return false;
	}
	
	@Override
	public int hashCode()
		{

		int result =1;
		result= result * 101  + (this.companyName	!= null ? this.companyName.hashCode() : 0);
		result= result * 103  + (this.companyId		!= null ? this.companyId.hashCode() : 0);
		result= result * 107  + (this.tradeArea		!= null ? this.tradeArea.hashCode() : 0);

		return result;
		}

	@Override
	public String getPrimaryKey() {
		
		return this.getCompanyId();
	}

	@Override
	public Date getCreatedAt() {
		
		return this.createdAt;
	}

}
