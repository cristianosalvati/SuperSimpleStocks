package com.jpm.main.runnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jpm.sss.controllers.ActionProcessor;
import com.jpm.sss.controllers.StockController;
import com.jpm.sss.controllers.UserController;
import com.jpm.sss.models.bean.Order.OrderTypes;

public class ClientFactory {
	
	/*some mock params used from the factory
	 *TODO: implemet another class to set client configuration params*/
	private static final Logger log = Logger.getLogger( ClientFactory.class.getName() );
	public static   int    MAX_CLIENTS 			  			= 20;
	public static   int    DEFAULT_CLIENT_CONNECTION_TIME 	= 2000;
	private static  int    CURRENT_CLIENTS		  			= 0;
	private static final String[] pricesParams ={ "75", "270", "550", "1150"};
	private static final String[] sharesParams ={  "50",   "200", "500", "1000", "2000"};
	private static final String[] typesParams = {OrderTypes.BUY.toString(), OrderTypes.SELL.toString()};
	private static String[]	stockSymbolParams = null;
	private static String[] userList		  = null;
	private static String[] actionsList       = null; 
	private static List<Client> 	clientList 		  = Collections.synchronizedList(new ArrayList<Client>());
	
	/*Initialize some params at first call of Client*/
	static{
		try{
			stockSymbolParams 	= StockController.getStockListAsSingleton();
			userList		  	= UserController.getUserListAsSingleton();
			actionsList       	= ActionProcessor.getActions();
			log.log(Level.FINE, String.format("StockController initialization = %s", StockController.getInstance().isInit() ));
			log.log(Level.FINE, String.format("UserController initialization = %s" , UserController.getInstance().isInit() ));	
		}catch(Throwable t){
			log.log( Level.SEVERE, "Error initializing ClientFactory "+t.toString(), t );
			throw new RuntimeException(t);
		}
	}
	
	public static Client getClient(String id, Integer d){
		if (CURRENT_CLIENTS< MAX_CLIENTS){
			CURRENT_CLIENTS++;
			Client c = new Client(id, d);
			clientList.add(c);
			return c;
			}
		else throw new IllegalArgumentException("Number of client cannot be up to "+MAX_CLIENTS);
		}
	
	public static Client getClient(){
		String user = getRandomParam(userList);
		return getClient(user, DEFAULT_CLIENT_CONNECTION_TIME);
		}
	
	public static String getMockSharesAmountRandomParam(){
		return getRandomParam(sharesParams);
	}
	
	public static String getMockStockTypeRandomParam(){
		return getRandomParam(typesParams);
	}
	
	public static String getMockStockPriceRandomParam(){
		return getRandomParam(pricesParams);
	}
	
	public static String getMockStockSymbolParam(){
		return getRandomParam(stockSymbolParams);
	}
	
	public static String getRandomAction(){
		return getRandomParam( actionsList );
	}
	
	/*just a mock 'free' method */
	public static void freeClientResources(){
		if (CURRENT_CLIENTS>0)
				synchronized (clientList) {
					clientList.clear();
				}
		}
	
	private static String getRandomParam(String[] params){
		return params[getRandomIndex(params.length)];
	}
	
	private static int getRandomIndex(int max)	{
		int randIndex = ((int)Math.round(Math.random() * max) -1) ;
		if (randIndex < 0)
			randIndex=0;
		
		return randIndex;
		}
    

}
