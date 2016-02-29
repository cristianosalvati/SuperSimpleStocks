package test.unit.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.models.bean.Company;
import com.jpm.sss.models.bean.Stock;
import com.jpm.sss.models.bean.Trade;
import com.jpm.sss.models.bean.Order;
import com.jpm.sss.models.bean.Trader;

public class TradeUnitTest {

	Trade tr;
	Stock 		stock;
	Trader 	userA;
	Trader 	userB;
	Order sellOrder;
	Order buyOrder;
	Company company;
	
	@Before
	public void setUp() throws Exception {
		 company 	= new Company("Cocacola" , "Beverage");
		 stock		= new Stock("TEA",  Stock.StockTypes.COMMON,  company);
		 userA 		= new Trader("aaaaaaa", "dsfgfdgsdfgfsgsg");
		 userB 		= new Trader("bbbbbbb", "dsfgfdgsdfgfsgsg");
		 sellOrder 	= new Order(Order.OrderTypes.SELL, Order.OrderStates.WAITING, userA, stock, 10, (float) 0.1);
		 buyOrder 	= new Order(Order.OrderTypes.BUY,  Order.OrderStates.WAITING, userB, stock, 10, (float) 0.1);	
		tr = new Trade(buyOrder, sellOrder);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTradeLoggerDefinition() {

		
			assertNotNull(tr.getTradeId());
			assertNotNull(tr.getBuyingOrder());
			assertNotNull(tr.getSellingOrder());
			assertNotNull(tr.getCreatedAt());
			
			assertTrue(tr.getBuyingOrder().isBuyOrder());
			assertTrue(tr.getSellingOrder().isSellOrder());
			assertTrue(tr.getSellingOrder().getSharesAmount() >= tr.getBuyingOrder().getSharesAmount()  );
			assertEquals(tr.getSellingOrder().getTradePrice(), tr.getBuyingOrder().getTradePrice());
			
	}
	
	
	@Test
	public void testEquals(){
		try{
			Trade tr1 = new Trade(buyOrder, sellOrder);
			
			assertNotEquals(tr, tr1);
			assertNotEquals(tr.hashCode(), tr1.hashCode());
			assertFalse(tr.equals(tr1));
		}catch(Throwable t){
			t.printStackTrace();
			fail();}
	}


}
