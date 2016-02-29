package test.unit.controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.controllers.ControllerAbstract;
import com.jpm.sss.controllers.ControllerException;
import com.jpm.sss.controllers.OrderController;
import com.jpm.sss.controllers.StockController;
import com.jpm.sss.dao.StocksDao;
import com.jpm.sss.models.bean.Order;
import com.jpm.sss.models.bean.Stock;
import com.jpm.sss.models.bean.Trade;

public class StockControllerUnitTest {

	StockController controller;
	String[] stocksName = null;
	String[] users = null;
	double geometricMean =1;
	
	
	@Before
	public void setUp() throws Exception {
		controller = (StockController)StockController.getInstance();
		stocksName = StockController.getStockListAsSingleton();
		assertNotNull(stocksName);
		assertTrue(stocksName.length > 0);
		assertTrue(controller.isInit());
		
		StocksDao stockdao  = (StocksDao) StocksDao.getInstance();	
		/*Record some transaction (one per stock's symbol)...adding some noise*/
		double count =0;
		for(String symbol  : stocksName){
			Stock s = stockdao.getStockBySymbol(symbol);
			stockdao.delete(s);
			double randF		=(double)randInt(50, 100);
			System.out.println("price=>"+randF);
			s.setStockPrice(randF);
			stockdao.insert(s);
			geometricMean*=randF;
			count++;
		}
		geometricMean = Math.pow(geometricMean, (double)1/count);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStockSymbolList() {
		try {	
			assertNotNull(stocksName);
			assertTrue(stocksName.length > 0);
		} catch (Throwable e) {
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testStockPeRatio() {
		
		for (String stockName : stocksName){
			String[] params ={null, stockName};

			try {
				assertEquals(controller.getStockPeRatio(params),ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
				Object o = controller.getSessionParam("getPeRatio_"+stockName);
				assertNotNull(o);
				assertTrue(o instanceof Double);
				
			} catch (Throwable e) {
				fail(e.getMessage());
			}
		}
		
	}
	
	@Test
	public void testStockDividendYield() {
		for (String stockName : stocksName){
			String[] params ={null, stockName};

			try {
				assertEquals(controller.getStockDividendYield(params),ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
				Object o = controller.getSessionParam("getStockDividendYield_"+stockName);
				assertNotNull(o);
				assertTrue(o instanceof Double);
				
			} catch (Throwable e) {
				fail(e.getMessage());
			}
		}
	}
	
	@Test
	public void testPutGeometricMean() {
		
		try {
			String userId ="username";
			String[] params = {userId};
			String result = controller.putGeometricMean(params);
			assertEquals(result, ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
			
			Double d = (Double)controller.getSessionParam("geometricMean_"+userId);
			assertNotNull(d);
			assertEquals(d, geometricMean, 0.05);
		} catch (ControllerException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStockPrice() {
		StocksDao dao = (StocksDao) StocksDao.getInstance();
		for (String stockName : stocksName){
			String[] params ={"Test", stockName};

			try {
				Double testValue = 10.0;
				controller.setSessionParam("stockPriceCalculated_"+stockName, testValue);
				assertEquals(controller.putStockPrice(params),ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
				Stock s1 = dao.getStockBySymbol(stockName);
				assertEquals(s1.getStockPrice(), testValue);
			} catch (Throwable e) {
				fail(e.getMessage());
			}
		}
	}
	
	@Test
	public void testStocksList() {
		
		try {
			assertEquals(controller.getStocksList(null),ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
		} catch (ControllerException e) {
			fail(e.getMessage());
		}
		
		
	}
	
	

	private  int randInt(int min, int max) {

	    int randomNum = (int) Math.round(Math.random() * Math.abs(max-min)) + min;

	    return randomNum;
	}


}
