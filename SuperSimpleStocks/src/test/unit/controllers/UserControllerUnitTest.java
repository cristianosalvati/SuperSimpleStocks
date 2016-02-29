package test.unit.controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.sss.controllers.ControllerAbstract;
import com.jpm.sss.controllers.ControllerException;
import com.jpm.sss.controllers.UserController;

public class UserControllerUnitTest {

	UserController controller;
	String[] users = null;

	@Before
	public void setUp() throws Exception {	
		controller = (UserController) UserController.getInstance();
		users = UserController.getUserListAsSingleton();
		assertNotNull(users);
		assertTrue(users.length>0);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testLogOut() {
		String[] params = {"userid"};
		String test;
		try {
			test = controller.logOut( params );
			assertEquals(test, ControllerAbstract.REDIRECT_TO_LOGOUT_NEXTSTEP);
		} catch (ControllerException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testLogIn() {
		String[] params = {"userid", "password"};
		String test;
		try {
			test = controller.logIn( params );
			assertEquals(test, ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
		} catch (ControllerException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEditProfile() {
		String[] userProfile={"username", "useremail", "password", "name", "surname"};
		try {
			assertEquals(controller.editProfile(userProfile), ControllerAbstract.REDIRECT_TO_OTHER_ACTION);
		} catch (Throwable e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
		
	}	
	
	@Test
	public void testViewProfile(){
		String[] userProfile={"username", "useremail", "password", "name", "surname"};
		try {
			controller.viewProfile(userProfile);
		} catch (ControllerException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
