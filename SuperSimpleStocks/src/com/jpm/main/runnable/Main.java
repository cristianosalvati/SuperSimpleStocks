package com.jpm.main.runnable;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Salvati
 * 
 * This class called 'Main' is used to run a java application that implements a number (MAX_CLIENTS) of concurrent clients that
 * simulates traders behavior, calling for controller's action.
 * Actions are mapped in methods defined as super classes of AbstractController Class and listed in the property file called:
 * "actions.properties".
 * 
 * Run it as java application.
 * 
 * 
 **/


public class Main {
	
	private static final Logger log = Logger.getLogger( Main.class.getName() );

	static{

		 log.setLevel(Level.FINE);
	}
	
	public static void main(String[] args){
		
		try{
			log.log(Level.FINE, String.format( "Start..." ));
			
			/*define Client configuration and make Clients instances*/
			Runtime.getRuntime().addShutdownHook(new ClosingMessage());		
			ClientFactory.DEFAULT_CLIENT_CONNECTION_TIME = 2000;
			ClientFactory.MAX_CLIENTS = 50;
			
			/**TODO define a thread pool (https://it.wikipedia.org/wiki/Thread_pool)
			 to better managing client threads*/
			for(int clientCounter=0; clientCounter < ClientFactory.MAX_CLIENTS; clientCounter++)
				{
				Client client = ClientFactory.getClient();	
				client.start();
				}	
			} 

		catch(Throwable ex){
			log.log( Level.SEVERE, ex.toString(), ex );
			}

		}
	
	/*Listener called when program quits (CTRL+Z in Windows).*/
	 static class ClosingMessage extends Thread {
  	   public void run() {
  		   	  log.log(Level.FINE, "...end program" );
			  ClientFactory.freeClientResources();
  		   
  	   		}
  	   }
	
	
}
