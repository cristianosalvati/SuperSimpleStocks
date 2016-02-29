package test.unit.controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.controllers.ControllerAbstract;
import com.jpm.sss.controllers.ControllerException;
import com.jpm.sss.controllers.StockController;
import com.jpm.sss.controllers.TradeController;
import com.jpm.sss.controllers.UserController;
import com.jpm.sss.dao.DaoException;
import com.jpm.sss.dao.StocksDao;
import com.jpm.sss.dao.TradersDao;
import com.jpm.sss.dao.TradesDao;
import com.jpm.sss.models.EntityException;
import com.jpm.sss.models.bean.Order;
import com.jpm.sss.models.bean.Stock;
import com.jpm.sss.models.bean.Trade;
import com.jpm.sss.models.bean.Trader;
import com.jpm.sss.models.bean.Order.OrderStates;
import com.jpm.sss.models.bean.Order.OrderTypes;

public class TradeControllerUnitTest {
	TradeController controller;
	String[] stocksName;
	String[] users;
	float stockPrice0=0;
	
	@Before
	public void setUp() throws Exception {
		try {
			controller = (TradeController)TradeController.getInstance();
			assertTrue(controller.isInit());
			
			stocksName = StockController.getStockListAsSingleton();
			assertNotNull(stocksName);
			assertTrue(stocksName.length > 0);
			
			users = UserController.getUserListAsSingleton();
			assertNotNull(users);
			assertTrue(users.length > 0);
			
			/*OK let's record some transactions*/
			TradesDao traderdao = (TradesDao) TradesDao.getInstance();
			StocksDao stockdao  = (StocksDao) StocksDao.getInstance();	
			Trader userA 		= new Trader("aaaaaaa", "dsfgfdgsdfgfsgsg");
			Trader userB 		= new Trader("bbbbbbb", "dsfgfdgsdfgfsgsg");
				
			/*Record some transaction for first symbol, then compute stockPrice*/
			Stock s = stockdao.getStockBySymbol(stocksName[0]);
			float heavyTradePriceSum=0;
			float quantitySum       =0; 

			for (int i=0; i< randInt(2,10); i++){
				int randI 		= randInt(1,10);
				float randF		=(float)randInt(50, 100);
				Order sellOrder 	= new Order(Order.OrderTypes.SELL, Order.OrderStates.WAITING, userA, s, randI, randF);
				Order buyOrder 		= new Order(Order.OrderTypes.BUY,  Order.OrderStates.WAITING, userB, s, randI, randF);	
				quantitySum+=randI;
				heavyTradePriceSum+=((float)randI*randF);
				System.out.println("shares =>"+randI+" price=>"+randF);
				traderdao.insert(new Trade(buyOrder, sellOrder));

			}
			stockPrice0 = heavyTradePriceSum/quantitySum;
			System.out.println(traderdao);
		}
		catch(Throwable e){
			fail(e.getMessage());
			e.printStackTrace();
		}
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testPutStockPriceCalculatedOnLast15Min() {
	
		try {
			for(String symbols  : stocksName){
				String[] params = {"some_username", symbols};
				String result = controller.putStockPriceCalculatedOnLast15Min(params);
				assertEquals(result, "putStockPrice");
				Double d = (Double)controller.getSessionParam("stockPriceCalculated"+"_"+symbols);
				assertNotNull(d);
				assertEquals(stockPrice0, d.doubleValue(), 0.05);
				}
			
		} catch (ControllerException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	

	@Test
	public void testPutNewTradeOrder() {
		try {
			String userId =users[0];
			String stockName = stocksName[0];
			
			TradersDao traders = (TradersDao) TradersDao.getInstance();
			Trader t = (Trader)traders.get(users[0]);
			assertTrue( traders.count() > 0);
			assertNotNull(t);
			
			
			StocksDao stocks = (StocksDao)StocksDao.getInstance();
			Stock s = stocks.getStockBySymbol(stockName);

			Order o = new Order(OrderTypes.BUY, OrderStates.WAITING, t, s, 100, (float)10);
			
			controller.setSessionParam("setNewOrder_"+stockName+"_"+userId, o);
			
			String[] params = {userId, stockName};
			String result = controller.putNewTradeOrder(params);
			assertEquals(result, ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
	
		} catch (ControllerException | DaoException | EntityException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private  int randInt(int min, int max) {

	    int randomNum = (int) Math.round(Math.random() * Math.abs(max-min)) + min;

	    return randomNum;
	}
	

}

