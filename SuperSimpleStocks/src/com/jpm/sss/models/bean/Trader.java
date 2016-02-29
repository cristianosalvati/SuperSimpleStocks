package com.jpm.sss.models.bean;

import java.util.Date;
import java.util.UUID;


import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.EntityException;


/**
* 
* A trader is a financial entity that buys and sells financial instruments such as stocks.
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/

public class Trader extends EntityAbstract{


	private static final long serialVersionUID = 1L;
	private static final int USERNAME_MIN_LENGHT = 6;
	private static final int BANK_ACCOUNT_LENGHT = 16;
	private String userId;
	private String userName;
	private String bankAccountNumber;
	private Date   createdAt;
	
	public Trader(String userName, String bankAccount) throws EntityException
		{
		if ( null == userName ||
			 null == bankAccount ||
			 USERNAME_MIN_LENGHT > userName.length() ||
			 BANK_ACCOUNT_LENGHT != bankAccount.length()) throw new EntityException("Illegal user definition!");
		
		this.userId =  UUID.randomUUID().toString(); 
		this.userName = userName;
		this.bankAccountNumber = bankAccount;
		this.createdAt = new Date();
		}
	
	public String getUserName() {
		return this.userName;
	}

	@Override
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public String getBankAccountNumber() {
		return this.bankAccountNumber;
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public String getPrimaryKey() {
		
		return getUserId();
	}
	
	@Override
	public boolean equals(Object o){
		if ( null != o &&
			 o instanceof Trader){
			Trader u = (Trader)o;
			if (this.getBankAccountNumber().equals( u.getBankAccountNumber()) &&
				this.getUserId().equals(u.getUserId()) &&
				this.getUserName().equals(u.getUserName())
					){return true;}
			
		}	
		return false;
	}
	
	 @Override
	    public int hashCode() {
		 int result = 1;
		 
		 result= result * 101  + (this.userName 			!= null ? this.userName.hashCode() : 0);
		 result= result * 103  + (this.bankAccountNumber 	!= null ? this.bankAccountNumber.hashCode() : 0);
		 result= result * 109  + (this.userId 				!= null ? this.userId.hashCode() : 0);
		 
		 //101 103    107    109    113 
		 return result;
	 }

}
