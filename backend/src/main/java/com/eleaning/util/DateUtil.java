package com.eleaning.util;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.eleaning.bean.TimesBean;

public class DateUtil {
	public final static String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
	public final static String DEFAULT_TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm:ss.SSS";
	public final static String DEFAULT_DATE_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"; //2018-12-19T07:22:41.765
	public final static String UTC = "UTC";

	/*
	 * 
	 */
	public static String convertDateFormatDDMMYYYY(String dateString, String  currentFormat) {
		
		currentFormat = Util.trim(currentFormat); 
		if ( currentFormat != null && Util.trim(dateString) != null) {
			
			currentFormat = currentFormat.split(" ")[0];
			
			if ("mm/dd/yyyy".equals(currentFormat.toLowerCase())){
				String[] mmddyyyy = dateString.split("/");
				if (mmddyyyy.length == 3) {
					 return mmddyyyy[1]+"-"+mmddyyyy[0]+"-"+mmddyyyy[2];
				}
				return dateString;
			}
			if ("mm-dd-yyyy".equals(currentFormat.toLowerCase())){
				String[] mmddyyyy = dateString.split("-");
				if (mmddyyyy.length == 3) {
					 return mmddyyyy[1]+"-"+mmddyyyy[0]+"-"+mmddyyyy[2];
				}
				return dateString;
			}
			if ("yyyy-mm-dd".equals(currentFormat.toLowerCase())){
				String[] mmddyyyy = dateString.split("-");
				if (mmddyyyy.length == 3) {
					 return mmddyyyy[2]+"-"+mmddyyyy[1]+"-"+mmddyyyy[0];
				}
				return dateString;
			}
			if ("yyyy/mm/dd".equals(currentFormat.toLowerCase())){
				String[] mmddyyyy = dateString.split("/");
				if (mmddyyyy.length == 3) {
					 return mmddyyyy[2]+"-"+mmddyyyy[1]+"-"+mmddyyyy[0];
				}
				return dateString;
			}
		}
		return dateString;
	}
	/*
	 * 
	 */
	public static String convertToString(java.sql.Date date) {
		return convertToString(date, DEFAULT_DATE_FORMAT);
	}

	/*
	 * 
	 */
	public static String convertDateToString(java.util.Date date) {
		return convertDateToString(date, DEFAULT_DATE_FORMAT);
	}
	/*
	 * inputddmmyyyy = dd-mm-yyyy
	 */
	public static String convertDateString(String inputddmmyyyy) {
		
		if (inputddmmyyyy != null) {
			String[] result =  inputddmmyyyy.split("-");
			if (result.length == 3) {
				return ""+result[2]+"-"+result[1]+"-"+result[0];
			}
		}
		return null;
	}
	/*
	 * 
	 */
	public static String convertToString(java.sql.Date date, String format) {
		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				return dateFormat.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * https://www.javatpoint.com/java-string-to-date
	 */
	public static String convertDateToString(java.util.Date date, String format) {
		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				return dateFormat.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 *  
	 */
	public static java.util.Date convertToDate(String dateString, String patternFormat) {

		try {
			return new SimpleDateFormat(patternFormat == null ? DEFAULT_DATE_FORMAT : patternFormat).parse(dateString);
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return null;
	}

	/*
	 * 
	 */
	public static java.sql.Date convertToSqlDate(String dateString) {
		return convertToSqlDate(dateString, DEFAULT_DATE_FORMAT);
	}

	/*
	 * 
	 */
	public static String convertTimestampToString(Timestamp date) {
		return convertTimestampToString(date, DEFAULT_DATE_FORMAT);

	}
	public static String convertTimestampToString(Timestamp date, String dateFormatOutput) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatOutput);
		return dateFormat.format(date);

	}
	/*public static Timestamp convertStringToTimestamp(String str_date) {
	    try {
	      DateFormat formatter;
	      formatter = new SimpleDateFormat("dd/MM/yyyy");
	      Date date = (Date) formatter.parse(str_date);
	      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

	      return timeStampDate;
	    } catch (ParseException e) {
	      System.out.println("Exception :" + e);
	      return null;
	    }
	  }*/

	public static Timestamp convertToTimestamp(String dateString, String patternFormat, String timeZone) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(patternFormat==null? DEFAULT_TIMESTAMP_FORMAT: patternFormat);
			if (timeZone != null) {
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
			}
			java.util.Date parsedDate = dateFormat.parse(dateString);
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) { // this generic but you can control another types of exception
			// look the origin of excption
		    System.out.println("Exception :" + e);
		}
		return null;
	}
	/*
	 * 
	 */
	public static Timestamp convertToTimestamp(String dateString) {
		return convertToTimestamp(dateString, null, null); 
	}
	/*
	 * 
	 */
	public static Timestamp convertToTimestampDefaultFormat(String dateString) {
		return convertToTimestamp(dateString, DateUtil.DEFAULT_DATE_FORMAT, null); 
	}
	/*
	 * patternFormat khong su dung duoc DEFAULT_TIMESTAMP_FORMAT cho java.sql.Date (mat time)  
	 */
	public static java.sql.Date convertToSqlDate(String dateString, String patternFormat) {

		try {

			if (dateString != null && !dateString.trim().equals("")) {
				
				/*SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
				df.applyPattern(patternFormat);
				return new java.sql.Date(df.parse(dateString).getTime());*/
				
				SimpleDateFormat dateFormat = new SimpleDateFormat(patternFormat);
				//java.util.Date parsedDate = dateFormat.parse(dateString);
				return new java.sql.Date(dateFormat.parse(dateString).getTime());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * 
	 */
	public static long currentTime() {
		 //Calendar cal = Calendar.getInstance();
		 //return cal.getTimeInMillis();
		 return System.currentTimeMillis();
	}
	/*
	 * 
	 */
	public static LocalDateTime getCurrentTimeWithTimeZone(String zone) {
	    ZoneId zoneId = ZoneId.of(zone);
	    LocalDateTime localDateTime = LocalDateTime.now(zoneId);
	    //LocalTime localTime=LocalTime.now(zoneId);
	//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	  //  String formattedTime=localTime.format(formatter);
	  //  System.out.println("Current time of the day in Los Angeles: " + formattedTime);
	    return localDateTime;

	}

	/*
	 * 
	 */
	public static Timestamp currentTimestamp() {
		return new Timestamp(currentTime());
	}
	/*
	 * 
	 */
	public static Timestamp currentTimestampUtc() {
		
		TimeZone currentZone = TimeZone.getDefault();
	    LocalDateTime localDateTimeUtc = DateUtil.getCurrentTimeWithTimeZone(UTC);
	//	Timestamp convertToTimestamp2 = DateUtil.convertToTimestamp(localDateTimeUtc.toString(),DateUtil.DEFAULT_DATE_ZONE_FORMAT, UTC);
		Timestamp convertToTimestamp2 = DateUtil.convertToTimestamp(localDateTimeUtc.toString(),DateUtil.DEFAULT_DATE_ZONE_FORMAT, null);
		TimeZone.setDefault(currentZone);
		return convertToTimestamp2;
	}
	/*
	 * 
	 */
	public static long currentTimesUtc() {
		return currentTimestampUtc().getTime();
	}
	/*
	 * like function time() in php
	 */
	public static Integer time() {
		return Long.valueOf(currentTime() / 1000).intValue();
	}

	/*
	 * 
	 */
	public static java.sql.Timestamp addDays(java.sql.Timestamp currentDate, int dayNumber) {

		currentDate.setDate(currentDate.getDate() + dayNumber);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		return new Timestamp(cal.getTimeInMillis());
	}
	/*
	 * 
	 */
	public static String transformDateByTimeZone(
			String inputDate, String inputFormat, String inputTimeZone, 
			String outputFormat, String outputTimeZone) {
		
		try {
			if (inputDate != null) {
				
				SimpleDateFormat inDateFormat = new SimpleDateFormat(inputFormat); // "dd-MM-yyyy HH:mm:ss.SSS"
				inDateFormat.setTimeZone(TimeZone.getTimeZone(inputTimeZone));
				Date dateTmp = inDateFormat.parse(inputDate);

				SimpleDateFormat outDateFormat = new SimpleDateFormat(outputFormat); // "yyyy-MM-dd HH:mm:ss.SSSZ"
				outDateFormat.setTimeZone(TimeZone.getTimeZone(outputTimeZone)); // UTC
				String transformedDate = outDateFormat.format(dateTmp);
				return transformedDate;
			}
		}
		catch(ParseException e) {
			System.out.println("ParseException: "+e.getMessage());
		}
		return null;
		
	}
	/*
	 * hourMinutes format = HH:MM
	 */
	public static TimesBean getTimes(String hourMinutes) {
		
		if (hourMinutes != null && hourMinutes.trim().length() <= 5) {
			
			  String[] times = hourMinutes.trim().split(":");
			  if (times.length == 2) {
				  try {
					  
					  int hour = Integer.parseInt(times[0]); 
					  int minute = Integer.parseInt(times[1]);
					  
					  if (hour>=0 && hour <=24 && minute >= 0 && minute <= 59) {
						  
						  TimesBean timeBean = new TimesBean();
						  timeBean.setHour(hour);
						  timeBean.setMinutes(minute);
						  timeBean.setTimes(hour*60 + minute);
						  
						  return timeBean;
					  }
				  }
				  catch (NumberFormatException e) {
					// TODO: handle exception
					  System.out.println("getTimes NumberFormatException:"+e.getMessage());
				}
			  }
		 }
		 return null;
	}
	/*
	 * 
	 */
	public static LocalDateTime getcurrentDateTime() {
		// return LocalDateTime.now(zoneid);
		return LocalDateTime.now();
	}
	/*
	 * 
	 */
	public static int getCurrentMinutes() {
		return getcurrentDateTime().getMinute();
	}
	/*
	 * 
	 */
	public static int getCurrentHour() {
		//System.out.println(getcurrentDateTime().get(ChronoField.HOUR_OF_DAY));
		//System.out.println(getcurrentDateTime().getHour());
		return getcurrentDateTime().getHour();
	}
	
	/*
	 * 
	 */
	public static Integer getDayOfWeek(String dateString, String dateFormat) {
		try {
			// Date date = new SimpleDateFormat(dateFormat ==null? DEFAULT_DATE_FORMAT : dateFormat, java.util.Locale.ENGLISH).parse(dateString);
			Date date = new SimpleDateFormat(dateFormat ==null? DEFAULT_DATE_FORMAT : dateFormat).parse(dateString);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return new Integer(c.get(Calendar.DAY_OF_WEEK));
		} catch (ParseException e) {
			// TODO: handle exception
		}
		return null;
	}
	/*
	 * 
	 */
	public static Integer getDayOfWeek(String dateString) {
		return getDayOfWeek(dateString, null);
	}
	/*
	 *  never test, only reference : https://stackoverflow.com/questions/5270272/how-to-determine-day-of-week-by-passing-specific-date
	 */
	public static String getDateName(String dateString) {
		
		  try {
			  
			  SimpleDateFormat format1=new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			  Date date = format1.parse(dateString);
			  DateFormat format2=new SimpleDateFormat("EEEE"); 
			  return format2.format(date);
			  
			} catch (ParseException e) {
				// TODO: handle exception
			}
			return null;
	}
	/*
	 * 
	 */
	public static boolean isPastTime(Long millisecondsTime) {
		if (millisecondsTime != null) {
			long currentMilliseconds = DateUtil.currentTime()/10000*10000;
			return millisecondsTime.longValue() < currentMilliseconds;
		}
		return true;
	}
}
