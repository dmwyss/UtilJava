package com.teamwyss.util;

import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import javax.print.attribute.standard.MediaSize.ISO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilsTest {

	DateUtils du = new DateUtils();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		java.util.Date dtEarlier = new java.util.Date();
		java.util.Date dtLater = new java.util.Date();
		String sExpected = "00 00:00";
		String sResult = du.getDifferenceString(dtEarlier, dtLater);
		if(!sExpected.equals(sResult)){
			fail("Fail:" + sResult);
		} else {
			System.out.println("GOT: " + sResult);
		}

		Calendar calTemp = Calendar.getInstance();
		calTemp.set(Calendar.YEAR, 2010);
		calTemp.set(Calendar.MONTH, 9);
		calTemp.set(Calendar.DATE, 8);
		calTemp.set(Calendar.HOUR_OF_DAY, 7);
		calTemp.set(Calendar.MINUTE, 6);
		calTemp.set(Calendar.SECOND, 5);
		dtEarlier = calTemp.getTime();
		calTemp.set(Calendar.HOUR_OF_DAY, 8);
		dtLater = calTemp.getTime();
		sExpected = "00 01:00";
		sResult = du.getDifferenceString(dtEarlier, dtLater);
		if(!sExpected.equals(sResult)){
			fail("Fail:" + sResult);
		} else {
			System.out.println("GOT: " + sResult);
		}

		calTemp.set(Calendar.HOUR_OF_DAY, 2);
		calTemp.set(Calendar.MINUTE, 49);
		dtLater = calTemp.getTime();
		sExpected = "00 04:17";
		sResult = du.getDifferenceString(dtEarlier, dtLater);
		if(!sExpected.equals(sResult)){
			fail("Fail:" + sResult);
		} else {
			System.out.println("GOT: " + sResult);
		}
		
		calTemp.set(Calendar.YEAR, 2011);
		dtLater = calTemp.getTime();
		sExpected = "145 55:43";
		sResult = du.getDifferenceString(dtEarlier, dtLater);
		if(!sExpected.equals(sResult)){
			fail("Fail:" + sResult);
		} else {
			System.out.println("GOT: " + sResult);
		}

	}

	@Test
	public void testRounding(){
		Date dtEvent = du.createDate(2011, 8, 30, 13, 30);
		Date dtNow = du.createDate(2011, 8, 29, 6, 30);
		int iSol  = -1;
		int iIst = (int)du.getDaysDifference(dtEvent, dtNow);
		if(iSol != iIst){
			fail("iSol:" + iSol + ", iIst:" + iIst);
		}
		
		dtEvent = du.createDate(2011, 8, 30, 13, 30);
		dtNow =   du.createDate(2011, 8, 30, 6, 30);
		iSol  = 0;
		iIst = (int)du.getDaysDifference(dtEvent, dtNow);
		if(iSol != iIst){
			fail("iSol:" + iSol + ", iIst:" + iIst);
		}
		
		dtEvent = du.createDate(2011, 8, 30, 23, 59);
		dtNow =   du.createDate(2011, 8, 30, 0, 0);
		iSol = 0;
		iIst = (int)du.getDaysDifference(dtEvent, dtNow);
		if(iSol != iIst){
			fail("iSol:" + iSol + ", iIst:" + iIst);
		}
		
		dtEvent = du.createDate(2011, 8, 29, 23, 59);
		dtNow =   du.createDate(2011, 8, 30, 0, 0);
		iSol = 1;
		iIst = (int)du.getDaysDifference(dtEvent, dtNow);
		if(iSol != iIst){
			fail("iSol:" + iSol + ", iIst:" + iIst);
		}
		
		dtEvent = du.createDate(2011, 8, 20, 23, 59);
		dtNow =   du.createDate(2011, 8, 30, 0, 0);
		iSol = 10;
		iIst = (int)du.getDaysDifference(dtEvent, dtNow);
		if(iSol != iIst){
			fail("iSol:" + iSol + ", iIst:" + iIst);
		}
		
		dtEvent = du.createDate(2011, 8, 30, 23, 59);
		dtNow =   du.createDate(2011, 9, 1, 0, 0);
		iSol = 1;
		iIst = (int)du.getDaysDifference(dtEvent, dtNow);
		if(iSol != iIst){
			fail("iSol:" + iSol + ", iIst:" + iIst);
		}
		
		dtEvent = du.createDate(2012, 0, 1, 0, 0);
		dtNow =   du.createDate(2011, 11, 31, 23, 59);
		iSol = -1;
		iIst = (int)du.getDaysDifference(dtEvent, dtNow);
		if(iSol != iIst){
			fail("iSol:" + iSol + ", iIst:" + iIst);
		}
	}
}
