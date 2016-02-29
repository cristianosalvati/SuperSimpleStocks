package test.unit.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.models.bean.Trader;

public class UserBeanUnitTest {

	private Trader tu;
	
	@Before
	public void setUp() throws Exception {
		tu = new Trader("gfsdgfsgdfsgdsf", "dsfgfdgsdfgfsgsg");	
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testUserDefinition() {	
		assertNotNull(tu.getUserName());
		assertNotNull(tu.getUserName());
		assertNotNull(tu.getCreatedAt());
		assertNotNull(tu.getBankAccountNumber());
	}
	
	@Test
	public void testEquals(){
		try{
			Trader tu1 = new Trader("gfsdgfsgdfsgdsf", "dsfgfdgsdfgfsgsg");	
	
			assertNotEquals(tu1, tu);
			assertNotEquals(tu1.hashCode(), tu.hashCode());
			}
		catch(Throwable t){
			t.printStackTrace();
			fail();
		}
	}
	

}
