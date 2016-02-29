package com.jpm.sss.controllers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jpm.sss.dao.DaoException;
import com.jpm.sss.dao.TradersDao;
import com.jpm.sss.models.EntityException;
import com.jpm.sss.models.bean.Trader;

/**
 * @author  Cristiano Salvati
 * @version 1.0
 * @since   2016-02-25 
 * 
 * This class implements the controller for managing Traders (system users) 
 * that BUY or SELL some stocks.
 **/


public class UserController extends ControllerAbstract{

	private static final Logger log = Logger.getLogger( UserController.class.getName() );
	private static UserController controller;
	private TradersDao users;

	public synchronized static ControllerAbstract getInstance() throws ControllerException{
	if (null == controller){
		controller = new UserController();
		if ( !controller.isInit() )  controller.init();
		}
	return controller;
	}

	public static synchronized String[] getUserListAsSingleton() throws ControllerException{
		return ((UserController)UserController.getInstance()).getUserIdList();
		}
	
	protected synchronized String[] getUserIdList() throws ControllerException{
		try {
			ArrayList<String> list;
			list = users.getEntityIdList();
			String[] array = new String[list.size()];
			array = list.toArray(array);

			return array;
		} catch (DaoException e) {
			throw new ControllerException(e);
		}	
	}

	private UserController()  throws ControllerException{
		users = (TradersDao)TradersDao.getInstance();
		if (!isInit()){
			init();
			}	
	}
	
	public synchronized String logOut(String... params)  throws ControllerException{
		return ControllerAbstract.REDIRECT_TO_LOGOUT_NEXTSTEP;
	}
	
	public synchronized String logIn( String... params)  throws ControllerException{
		String userId = params[0];
		log.log(Level.FINE, String.format(" ->Welcome %s", userId));
		return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
	}
	
	public synchronized String viewProfile(String... params) throws ControllerException{
		try{
			String userId = params[0];
			Trader u= (Trader) users.get(userId);
			log.log(Level.FINE, String.format(" ->%s", u ));
			
			return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
		}catch(DaoException e){
			throw new ControllerException(e);
		}
	}
	
	public synchronized String editProfile(String... params)  throws ControllerException{
		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * this init method put some mock data.
	 **/
	
	public synchronized String init()  throws ControllerException{
		try{
			users.insert(new Trader("JohnDoe",  "abcdefghi0123459"));
			users.insert(new Trader("MaryJane", "abcabcabc0123459"));
			users.insert(new Trader("KenPar",   "abcdefghi1231233"));
			users.insert(new Trader("Bill51",   "abcabcabc1231233"));
			users.insert(new Trader("TestTest", "abcabcabc9876543"));
			users.insert(new Trader("AxlRose",  "cabcabcab9876543"));
			log.log(Level.FINE, String.format(" ->%s", users));
			}
		catch(DaoException | EntityException e){
			throw new ControllerException(e);
			}
		
		return ControllerAbstract.STATUS_OK;
	}

	@Override
	public synchronized boolean isInit() throws ControllerException {
		try{
			if (users != null && users.count() > 0)
				return true;
		}catch(DaoException e){
			throw new ControllerException(e);
		}
		
		return false;
	}

	
}

