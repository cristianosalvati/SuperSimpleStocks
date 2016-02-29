package test.unit.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.dao.DaoException;
import com.jpm.sss.dao.TradesDao;
import com.jpm.sss.models.EntityAbstract;
import com.jpm.sss.models.EntityException;
import com.jpm.sss.models.bean.Company;
import com.jpm.sss.models.bean.Stock;
import com.jpm.sss.models.bean.Trade;
import com.jpm.sss.models.bean.Order;
import com.jpm.sss.models.bean.Trader;

public class DaoUnitTest {

	TradesDao dao;
	Trade tr;
	Stock 	stock;
	Trader 	userA;
	Trader 	userB;
	Order sellOrder;
	Order buyOrder;
	Company company;
	
	@Before
	public void setUp() throws Exception {
	 	 dao = (TradesDao) TradesDao.getInstance();
		 company 	= new Company("Cocacola" , "Beverage");
		 stock		= new Stock("TEA",  Stock.StockTypes.COMMON, company);
		 userA 		= new Trader("aaaaaaa", "dsfgfdgsdfgfsgsg");
		 userB 		= new Trader("bbbbbbb", "dsfgfdgsdfgfsgsg");
		 sellOrder 	= new Order(Order.OrderTypes.SELL, Order.OrderStates.WAITING, userA, stock, 10, (float) 0.1);
		 buyOrder 	= new Order(Order.OrderTypes.BUY,  Order.OrderStates.WAITING, userB, stock, 10, (float) 0.1);	
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddSome() {
		try{
			tr = new Trade(buyOrder, sellOrder);
			Trade tr2 = new Trade(buyOrder, sellOrder);

			dao.insert(tr);
			
			assertTrue(dao.count() == 1);
			dao.insert(tr2);
			
			assertTrue(dao.count() == 2);
			System.out.println("Test add some");
			System.out.println(dao.toString());
			
			//testing select function
			List<EntityAbstract> e = dao.select(tr.getPrimaryKey());
			assertTrue(e.size() == 1);
			
			System.out.printf("\ndelete %s",tr.getPrimaryKey());
			dao.delete(tr);
			assertTrue(dao.count() == 1);
			System.out.println(dao.toString());
			System.out.println(e.toString());
		}	
		 catch (EntityException | DaoException e) {
				e.printStackTrace();
				fail();
			}
		System.out.println("-End-");
		
		
	}
	
	@Test
	public void testRemoveAll(){
		try {
			dao.drop();
			assertTrue(dao.count() ==0);
			}
		catch (DaoException e) {
			e.printStackTrace();
			fail();
			}
		
		}
		
	

}
