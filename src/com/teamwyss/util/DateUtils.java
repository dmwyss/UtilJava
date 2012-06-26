package com.teamwyss.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * General date utilities.
 * Note: Always use the full namespace of any Date classes, for example java.util.Date.
 * @author david.wyss
 */
public class DateUtils {

	public Date convertFromJapaneseDateString(String sJap) {
		StringUtils su = new StringUtils();
		sJap = su.replaceSubstring(sJap, "/", "");
		sJap = su.replaceSubstring(sJap, "-", "");
		sJap = su.replaceSubstring(sJap, " ", "");
		sJap = su.replaceSubstring(sJap, " ", "");
		//sJap = su.replaceSubstring(su.replaceSubstring(sJap, "-", " "), "/", "");
		return toJavaUtilDate(sJap, "yyyyMMdd", (new java.util.Date()));
	}

	public String convertToJapaneseDateString(Date dtToFormat) {
		return this.toString(dtToFormat, "yyyy MM dd");
	}

	/**
	 * Convert a String to a java.util.Date object
	 * @param sDate String to be converted.
	 * @param sFormat Format, for example, dd-MM-yyyy.
	 * @return Date representation of the String passed. Returns null, if the String in can not be converted to a valid Date object.
	 */
	public java.util.Date toJavaUtilDate(String sDate, String sFormat) {
		return toJavaUtilDate(sDate, sFormat, null);
	}

	/**
	 * Convert a String to a java.util.Date object
	 * @param sDate String to be converted.
	 * @param sFormat Format, for example, dd-MM-yyyy.
	 * @param dateDefault If the date cannot be converted, return this instead.
	 * @return Date representation of the String passed. Returns null, if the String in can not be converted to a valid Date object.
	 */
	public java.util.Date toJavaUtilDate(String sDate, String sFormat, java.util.Date dateDefault) {
		if((sDate == null) || (sDate.length() == 0)){
			return dateDefault;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
			return sdf.parse(sDate);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return dateDefault;
	}

	/**
	 * Convert a date to a string in the given format.
	 * @param dtToFormat Input date to be returned.
	 * @param sFormat Format instruction, for example "dd-MM-yyyy hh:mm:ss".
	 * @return String in the specified format.
	 */
	public String toString(java.util.Date dtToFormat, String sFormat){
		if(dtToFormat == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
		return sdf.format(dtToFormat);
	}

	/**
	 * Return the given date, less the hours specified.
	 * For example: ([2009/10/23 10:10], 2) would return [2009/10/23 08:10]
	 * For example: ([2009/10/23 10:10], 24) would return [2009/10/22 08:10]
	 * For example: ([2009/10/23 10:10], -2) would return [2009/10/23 12:10]
	 * @param dtIn Date on which to operate.
	 * @param iHoursOffset Hours to remove thus making the date earlier.
	 * @return Date, moved by the specified number of hours.
	 */
	public Date getDateTimeMinusHours(Date dtIn, int iHoursOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dtIn);
		int iHr = cal.get(Calendar.HOUR_OF_DAY) - iHoursOffset;
		cal.set(Calendar.HOUR_OF_DAY, iHr);
		return cal.getTime();
	}

	/**
	 * Compute nr of minutes between the two dates.
	 * For example: ([2009/10/23 10:10], [2009/10/23 10:10]) would return 0.
	 * For example: ([2009/10/23 10:10], [2009/10/23 20:10]) would return 600.
	 * @param dtEarlier Earlier of the two dates. If this is later, the result will be a negative value.
	 * @param dtLater Later of the two dates.
	 * @return Nr of minss difference.
	 */
	public long getMinutesDifference(Date dtEarlier, Date dtLater) {
		long longDiff = dtLater.getTime() - dtEarlier.getTime();
		return (long)(longDiff / (60000));
	}

	/**
	 * Convert from date to long, while catching any nulls.
	 * @param dtIn Date to convert.
	 * @return long representing the date.
	 */
	public long toSafeLong(java.util.Date dtIn) {
		return toSafeLong(dtIn, 0L);
	}

	/**
	 * Convert from date to long, while catching any nulls.
	 * @param dtIn Date to convert.
	 * @param longDefault What to return if it is null.
	 * @return long representing the date.
	 */
	public long toSafeLong(java.util.Date dtIn, long longDefault) {
		if(dtIn == null){
			return longDefault;
		}
		return dtIn.getTime();
	}

	public long toDint(int iYear, int iMonth, int iDay, int iHour, int iMinute) {
		return (iYear * 100000000L) + (iMonth * 1000000L) + (iDay * 10000L) + (iHour * 100L) + iMinute;
	}

	public long toDint(Date dtToConvert) {
		if(dtToConvert == null){
			return 0L;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dtToConvert);
		return toDint(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}

	public String getDifferenceString(Date dtEarlier, Date dtLater) {
		if(dtEarlier.after(dtLater)){
			Date dtTemp = (Date)dtEarlier.clone();
			dtEarlier = (Date)dtLater.clone();
			dtLater = dtTemp;
		}
		long lngDiff = this.getMinutesDifference(dtEarlier, dtLater);
		long lngMins = (lngDiff % 60);
		lngDiff = Math.round((lngDiff - lngMins) / 60);
		long lngHrs = (lngDiff % 60);
		lngDiff = Math.round((lngDiff - lngHrs) / 60);
		//String sOut = (new StringUtils()).
		StringFormatUtils sfu = new StringFormatUtils();
		String sOut = sfu.getWithLeadingZeros(lngDiff, 2)
				+ " " + sfu.getWithLeadingZeros(lngHrs, 2)
				+ ":" + sfu.getWithLeadingZeros(lngMins, 2);
		return sOut;
	}

	public Date floor(Date dtIn) {
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date)dtIn.clone());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public long getDaysDifference(Date dtOne, Date dtTwo) {
		long lngOne = floor(dtOne).getTime();
		long lngTwo = floor(dtTwo).getTime();
		return Math.round((lngTwo - lngOne) / 86400000);
	}

	public Date createDate(int iY, int ixM, int iD, int iH, int iM) {
		Calendar calTemp = Calendar.getInstance();
		calTemp.set(Calendar.YEAR, iY);
		calTemp.set(Calendar.MONTH, ixM);
		calTemp.set(Calendar.DATE, iD);
		calTemp.set(Calendar.HOUR_OF_DAY, iH);
		calTemp.set(Calendar.MINUTE, iM);
		calTemp.set(Calendar.SECOND, 0);
		calTemp.set(Calendar.MILLISECOND, 0);
		return calTemp.getTime();
	}

}
