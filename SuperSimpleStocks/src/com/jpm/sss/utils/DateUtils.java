package com.jpm.sss.utils;

import java.util.Calendar;
import java.util.Date;


/**
* This class implements some static methods as application utils.
* 
*
* @author  Cristiano Salvati
* @version 1.0
* @since   2016-02-25 
*/
public class DateUtils {

	
	/**
	 * This method get a new date starting from another adding days, hours, minutes and seconds.
	 * 
	 * 
	 * @param startAt
	 * @param days    from the 'startAt' for the new date.
	 * @param hours   for the new date.
	 * @param minutes for the new date.
	 * @param seconds for the new date.
	 * @return a new date computed as 'startAt' + (days, hours, minutes, seconds)
	 */
	  public static Date date2NewDate(Date startAt, Integer days, Integer hours, Integer minutes, Integer seconds)
	  	{
			Calendar timer		= Calendar.getInstance(); 
			timer.setTime(startAt);
		 
			timer.set(Calendar.HOUR_OF_DAY, 	0);
		  	timer.set(Calendar.MINUTE, 		0);
		  	timer.set(Calendar.SECOND, 		0);
	  	
			timer.add(Calendar.DATE, days);
			timer.add(Calendar.HOUR_OF_DAY, hours);
		  	timer.add(Calendar.MINUTE, minutes);
		  	timer.add(Calendar.SECOND, seconds);
		  
		  	return timer.getTime();
	  	}
	  
}
