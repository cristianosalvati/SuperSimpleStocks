package test.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import test.unit.beans.CompanyBeanUnitTest;
import test.unit.beans.StockBeanUnitTest;
import test.unit.beans.TradeOrderUnitTest;
import test.unit.beans.TradeUnitTest;
import test.unit.beans.UserBeanUnitTest;
import test.unit.controllers.OrderControllerUnitTest;
import test.unit.controllers.StockControllerUnitTest;
import test.unit.controllers.TradeControllerUnitTest;
import test.unit.controllers.UserControllerUnitTest;
import test.unit.dao.DaoUnitTest;

@RunWith(Suite.class)
@SuiteClasses({DaoUnitTest.class, UserControllerUnitTest.class, TradeControllerUnitTest.class, StockControllerUnitTest.class, OrderControllerUnitTest.class, CompanyBeanUnitTest.class, StockBeanUnitTest.class, TradeOrderUnitTest.class, TradeUnitTest.class, UserBeanUnitTest.class})

/**
 * @author  Cristiano Salvati
 * @version 1.0
 * @since   2016-02-25 
 * 
 * RUN ALL TEST!
 **/
public class AllTests {


}
