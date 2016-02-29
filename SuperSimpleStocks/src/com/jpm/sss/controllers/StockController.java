package com.jpm.sss.controllers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jpm.sss.dao.DaoException;
import com.jpm.sss.dao.StocksDao;
import com.jpm.sss.models.EntityException;
import com.jpm.sss.models.bean.Company;
import com.jpm.sss.models.bean.Stock;
import com.jpm.sss.models.bean.Stock.StockTypes;


/**
 * @author  Cristiano Salvati
 * @version 1.0
 * @since   2016-02-25 
 * 
 * This class implements the controller for managing stocks.
 * 
 **/
public class StockController extends ControllerAbstract{
		
	private static final Logger log = Logger.getLogger( StockController.class.getName() );
	private static StockController controller;
	private StocksDao dao;
	
	public static synchronized ControllerAbstract getInstance()  throws ControllerException{
		if (null == controller){ 
			controller = new StockController();
			if ( !controller.isInit() ) controller.init();
			}
		return controller;
		}
	
	private StockController ()
		{
		super();
		dao = (StocksDao) StocksDao.getInstance();
		}
		
	public static synchronized String[] getStockListAsSingleton() throws ControllerException{
		return ((StockController)StockController.getInstance()).getStockSymbolList();
		}

	/**A-1
	 * The method getStockDividendYield for a given stock calculate the dividend yield
	 **/
	public synchronized String getStockDividendYield(String... params) throws ControllerException{
		try{
			String stockSymbol = params[1];
			
			Stock s = dao.getStockBySymbol(stockSymbol);
			Double dividendYield = getDividendValue(s);
			setSessionParam("getStockDividendYield_"+stockSymbol, dividendYield);
			
			return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
		}
		catch(DaoException e){
			throw new ControllerException(e);
			}
		}
	
	/**
	 * A-2
	 * The method getStockPeRadio set for a given stock calculate the P/E Ratio.
	 **/
	public synchronized String getStockPeRatio(String... params) throws ControllerException{
		try{
			if (params == null || params.length < 2) throw new ControllerException("Invalid parameters");
			
			String stockSymbol = params[1];
			
			Stock s = dao.getStockBySymbol(stockSymbol);
			if (null != s) {
				Double dividendYield = s.getStockPrice()/getDividendValue(s);
				setSessionParam("getPeRatio_"+stockSymbol, dividendYield);
				}
			return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
		}
		catch(DaoException e){
			throw new ControllerException(e);
			}
	}
	
	/** 
	 * B
	 * this method calculate the GBCE All Share Index using the geometric mean of prices for all stocks
	 **/
	public synchronized String putGeometricMean(String... params)  throws ControllerException{
		
		String userId = params[0];
		try{
			Double gm = dao.calculateGeometricMean4AllStocks();
			setSessionParam("geometricMean_"+userId, gm);
			log.log(Level.FINE, String.format(" ->User %s calcolate geometric mean = %s", userId, gm));
		}catch(DaoException e){
			throw new ControllerException(e);
		}
		return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
	}
	
	
	/**
	 * 
	 * This method set new stock price calculated on current trades
	 */
	public synchronized String putStockPrice(String... params) throws ControllerException{
		try{
			String userId = params[0];
			String stockSymbol = params[1];
			Stock s = dao.getStockBySymbol(stockSymbol);
			if (s != null){
				
				Object price = getSessionParam("stockPriceCalculated_"+stockSymbol);
				if (price != null)
					s.setStockPrice((Double)price);
				
				log.log(Level.FINE, String.format(" ->"+"User %s setStockPrice %s", userId, stockSymbol));
				}
			}
		catch(DaoException e){
			throw new ControllerException(e);
			}
		return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
	}
	
	/**
	 * This method log stocks state.
	 **/
	public synchronized String getStocksList(String... params) throws ControllerException{
		try{
			log.log(Level.FINE, String.format(" ->%s\n ->count=%s" ,dao, dao.count()));
			return ControllerAbstract.REDIRECT_TO_OTHER_ACTION;
		}
		catch(Throwable t){
			throw new ControllerException(t);
			}
	}

	/**
	 * the init method set some mock data.
	 **/
	public synchronized String init() throws ControllerException{		
		try{
			Company aCompany = new Company("the Global Beverage Corporation Exchange" , "Beverage");
		
			/*
			 *	TEA	Common			0			100	
			 *	POP	Common			8			100	
			 *	ALE	Common			23			60	
		 	 *	GIN	Preferred		8		2%	100	
			 *	JOE	Common			13			250	
			 *
			 * 	Stock Symbol	,	Type	,		company ,				Last Dividend,	Fixed Dividend,	Par Value, Trade Price
			 *
			 */
			
			dao.insert( new Stock("TEA",  Stock.StockTypes.COMMON,   aCompany,		0.0,  	null, 		100,  		0.0) );
			dao.insert( new Stock("POP",  Stock.StockTypes.COMMON,   aCompany, 		8.0,  	null, 		100,  		0.0) );
			dao.insert( new Stock("ALE",  Stock.StockTypes.COMMON,   aCompany, 		23.0, 	null, 		100,  		0.0) );
			dao.insert( new Stock("GIN",  Stock.StockTypes.PREFERED,   aCompany, 	8.0,  	2.0, 		100, 		0.0) );
			dao.insert( new Stock("JOE",  Stock.StockTypes.COMMON,   aCompany, 		8.0,  	null, 		100,  		0.0) );
			log.log(Level.FINE, String.format(" ->%s", dao));
			return ControllerAbstract.STATUS_OK;
			}
		catch(DaoException | EntityException e){
			throw new ControllerException(e);
			}
		}

	@Override
	public synchronized boolean isInit() throws ControllerException{
		try{
		if (null != dao && dao.count() >0)
			return true;
			}
		catch(DaoException e){
			throw new ControllerException(e);
			}
		return false;
	}

	protected synchronized  String[] getStockSymbolList() throws ControllerException{
		try{	 
			ArrayList<String> list = dao.getStockSymbolList();
			String[] array = new String[list.size()];
			array = list.toArray(array);
			
			return array;
		}
		catch(DaoException e){
			throw new ControllerException(e);
			}
		}
	
	private Double getDividendValue(Stock s) throws ControllerException{
		Double dividendYield = (double)0.0;
		try{
			if (s!= null && s.getStockType().equals(StockTypes.COMMON.toString())){
				dividendYield = s.getLastDividend()/s.getStockPrice();
			}
			else dividendYield = s.getFixedDividend()*s.getParValue()/s.getStockPrice();
		}catch(Throwable e){
			new ControllerException(e);
		}
		return dividendYield;	
	}
	
}
