package test.unit.controllers;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.controllers.ControllerException;
import com.jpm.sss.controllers.OrderController;
import com.jpm.sss.controllers.StockController;
import com.jpm.sss.controllers.TradeController;
import com.jpm.sss.controllers.UserController;
import com.jpm.sss.models.bean.Order;

public class OrderControllerUnitTest {

	OrderController controller;
	String[] stocksName = null;
	String[] users = null;
	
	@Before
	public void setUp() throws Exception {
		controller = (OrderController)OrderController.getInstance();
		assertTrue(controller.isInit());
		
		stocksName = StockController.getStockListAsSingleton();
		assertNotNull(stocksName);
		assertTrue(stocksName.length > 0);
		
		users = UserController.getUserListAsSingleton();
		assertNotNull(users);
		assertTrue(users.length > 0);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSetNewOrder() {
		String[] orderType ={"BUY","SELL"};
		for (String stockSymbol : stocksName){
			
			int userIndex = randInt(0, users.length-1);
			String shares = randInt(0, 100)+"";
			String price  = randInt(0, 100)+"";
			
			String[] params = {users[userIndex], stockSymbol, orderType[0], shares, price};
			try {
				
				assertEquals(controller.setNewOrder(params), "putNewTradeOrder");
				String sessionParamName= "setNewOrder_"+stockSymbol+"_"+users[userIndex];
				Object o = controller.getSessionParam(sessionParamName);
				assertNotNull(o);
				assertTrue(o instanceof Order);
				controller.removeSessionParam(sessionParamName);
				o = controller.getSessionParam(sessionParamName);
				assertNull(o);
				} 
			catch (ControllerException e) {
				fail(e.getMessage());
				}
			}
		
	}

		
	private  int randInt(int min, int max) {

	    int randomNum = (int) Math.round(Math.random() * Math.abs(max-min)) + min;

	    return randomNum;
	}
	

}
