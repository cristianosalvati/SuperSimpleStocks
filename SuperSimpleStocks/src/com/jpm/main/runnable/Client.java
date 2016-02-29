package com.jpm.main.runnable;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jpm.sss.controllers.ControllerAbstract;
import com.jpm.sss.controllers.ActionProcessor;
import com.jpm.sss.controllers.ControllerException;


/**
 * @author  Cristiano Salvati
 * @version 1.0
 * @since   2016-02-25 
 * 
 * This class 'Client' Implements the behavior of a hypothetical system's user that call 
 * the 'Action processor' to perform some actions. Client's instances are released by the 
 * ClientFactory.
 **/
public class Client extends Thread {
	
	private static final Logger log = Logger.getLogger( Client.class.getName() );

	private Integer 	delay;
	private String   	nextAction2Perform;
	private String 		userId;

	public Client(String id, Integer d){
		userId = id;
		delay 	= d;
		nextAction2Perform = ControllerAbstract.LOGIN_ACTION;		
	}
	

    public void run() {
    	try{
    		getThreadMessage("start running!");
    		
    		/*While client is not interrupted or next Action is not 'LOGOUT'*/
    		while (!this.isInterrupted() && !nextAction2Perform.equals(ControllerAbstract.LOGOUT_ACTION))
    			{
    			try{
	    			Thread.sleep(delay); 
	    			/*make some random params to submit to the current action*/
	    			String[] params= this.createActionParams(this.userId);
	    			
	    			/*if the controller returns 'REDIRECT_TO_OTHER_ACTION' to the Client then change ramdomly to another action */
	    			if (nextAction2Perform.equals(ControllerAbstract.REDIRECT_TO_OTHER_ACTION) ){
	    				nextAction2Perform =  ClientFactory.getRandomAction();
	    				}
	    					
	    			getThreadMessage("perform "+nextAction2Perform +" - params: "+ Arrays.toString(params));	
	    			/*Determine next action performing current action*/
	    			nextAction2Perform = ActionProcessor.perform(nextAction2Perform, params);
    				}
    			catch(ControllerException ex){	 	
    			 	log.log( Level.WARNING, "Something go wrong at "+nextAction2Perform+" because: "+ex.toString(), ex );
    			 	nextAction2Perform =  ClientFactory.getRandomAction();		
    				}
    			}
    	}
    	catch(Throwable  ex){
    		log.log( Level.SEVERE, ex.toString()+ " - raised by action: " +nextAction2Perform, ex );
    		}
    	finally{
    		/*TODO Closing client connection here*/
    		ClientFactory.freeClientResources();
    		getThreadMessage("closing connection!");
    		
    		}
    	}

    private String[] createActionParams(String userId) {
		
		String[] params = { userId 
							,ClientFactory.getMockStockSymbolParam()
							,ClientFactory.getMockStockTypeRandomParam()
							,ClientFactory.getMockSharesAmountRandomParam()	      
							,ClientFactory.getMockStockPriceRandomParam()};
		return params;
	}
    

	private void getThreadMessage(String message) {
		String threadName = Thread.currentThread().getName(); 
		String formattedMessage = String.format( "%s says: '%s'", threadName, message);
		log.log(Level.FINE, formattedMessage);
		/*TODO suppress this line at deploy time*/
		System.out.println(formattedMessage);
		
    }


	public void close() {
		String threadName = Thread.currentThread().getName(); 
		log.log(Level.WARNING, String.format( "%s says: '%s'", threadName, "this close method is a dummy!!!"));
		
	}
	

}