package test.unit.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.models.bean.Company;

public class CompanyBeanUnitTest {
	
	Company co;
	@Before
	public void setUp() throws Exception {
		 co = new Company("abc", "it");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompanyDefinition() {
		
		assertNotNull(co.getCompanyId());
		assertNotNull(co.getCompanyName());
		assertNotNull(co.getTradeArea());

	}
	
	@Test
	public void testEquals(){
		Company co1 = new Company("abc", "it");
		assertNotEquals(co, co1);
		assertNotEquals(co.hashCode(), co1.hashCode());
	}

	
}
