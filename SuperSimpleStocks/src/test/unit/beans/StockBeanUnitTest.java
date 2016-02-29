package test.unit.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.models.bean.Company;
import com.jpm.sss.models.bean.Stock;

public class StockBeanUnitTest {

	
	Stock st;
	Company company;
	
	@Before
	public void setUp() throws Exception {
		company = new Company("Cocacola" , "Beverage");
		st = new Stock("TEA",  Stock.StockTypes.COMMON,   company);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStockDefinition() {
		assertNotNull(st.getStockId());
		assertNotNull(st.getStockSymbol());
		assertNotNull(st.getStockType());
		assertNotNull(st.getLastDividend());
		assertNotNull(st.getFixedDividend());
		assertNotNull(st.getParValue());
		assertNotNull(st.getCompanyId());
//		assertNotNull(st.getStockMarketPlace());
	}
	
	@Test
	public void testEquals(){
		try{
		Stock st1 = new Stock("TEA",  Stock.StockTypes.COMMON,  company);
		assertNotEquals(st.hashCode(), st1.hashCode());
		assertTrue(!st.equals(st1));
		}catch(Throwable t){
			t.printStackTrace();
			fail();}
	}

}
