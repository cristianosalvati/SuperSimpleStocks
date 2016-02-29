package test.unit.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.models.bean.Company;
import com.jpm.sss.models.bean.Stock;
import com.jpm.sss.models.bean.Order;
import com.jpm.sss.models.bean.Trader;

public class TradeOrderUnitTest {

	Order to;
    Company company;
	Stock stock;
	Trader user;
	
	@Before
	public void setUp() throws Exception {
	    company = new Company("Cocacola" , "Beverage");
		stock = new Stock("TEA",  Stock.StockTypes.COMMON,  company);
		user = new Trader("userXyx", "ABCDEFGHIL123456");
		to = new Order(
	 			Order.OrderTypes.SELL , 
	 			Order.OrderStates.WAITING,
	 			user, 
	 			stock, 
	 			1,
	 			new Float(0.1)
	 			);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTradeOrderDefinition() {
		
		assertNotNull(to.getOrderId());
		assertNotNull(to.getUserId());
		assertNotNull(to.getType()); //buy or sell
		assertNotNull(to.getCreatedAt());
		assertNotNull(to.getSharesAmount());
		assertNotNull(to.getTradePrice());
		assertTrue(   to.getTradePrice() > 0);
		assertTrue(   to.getSharesAmount() > 0);
		assertNotNull(to.getPrimaryKey());
		assertEquals( to.isBuyOrder(), !to.isSellOrder());
	}
	
	@Test
	public void testEquality(){
		try{
			Order to1 = new Order(
		 			Order.OrderTypes.SELL , 
		 			Order.OrderStates.WAITING,
		 			user, 
		 			stock, 
		 			1,
		 			new Float(0.1)
		 			);
		
			assertNotEquals(to, to1);
			assertNotEquals(to.hashCode(), to1.hashCode());
			assertTrue(!to.equals(to1));
		}catch(Throwable t){
			t.printStackTrace();
			fail();}
	}

}
