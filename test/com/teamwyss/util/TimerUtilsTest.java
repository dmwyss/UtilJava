package com.teamwyss.util;

import org.junit.Test;

public class TimerUtilsTest {

	// 1172ms
	@Test
	public void timerSpeedTestString(){
		String sOut = "";
		Timer timer = new Timer();
		timer.startTimer();
		int iCount = 1;
		for (int iLoop = 1; iLoop <= 10000; iLoop++) {
			sOut += " and " + iCount++;
		}
		long lTime = timer.stopTimer();
		int iX = sOut.length();
		System.out.println("\nresult " + lTime + "\n\t(" + iX + " chars)");
	}

	// Result 25ms
	@Test
	public void timerSpeedTestStringBuffer(){
		StringBuffer sbOut = new StringBuffer();
		Timer timer = new Timer();
		timer.startTimer();
		int iCount = 1;
		for (int iLoop = 1; iLoop <= 10000; iLoop++) {
			sbOut.append(" and " + iCount++);
		}
		long lTime = timer.stopTimer();
		int iX = sbOut.length();
		System.out.println("\nresult " + lTime + "\n\t(" + iX + " chars)");
	}

	// Result 9ms
	@Test
	public void timerSpeedTestStringBufferLoop(){
		StringBuffer sbOut = new StringBuffer();
		Timer timer = new Timer();
		timer.startTimer();
		int iCount = 1;
		for (int iLoop = 0; iLoop <= 100; iLoop++) {
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
			sbOut.append(" and " + iCount++);
		}
		long lTime = timer.stopTimer();
		int iX = sbOut.length();
		System.out.println("\nresult " + lTime + " StringBuffer inside loop.\n\t(" + iX + " chars)");
		assert(lTime < 0);
	}

	// Result 9ms
	@Test
	public void timerSpeedTestStringConcat(){
		String sOut = "";
		Timer timer = new Timer();
		timer.startTimer();
		int iCount = 1;
		for (int iLoop = 0; iLoop <= 100; iLoop++) {
			sOut += " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++
					+ " and " + iCount++;
		}
		long lTime = timer.stopTimer();
		int iX = sOut.length();
		System.out.println("\nresult " + lTime + " String inside loop.\n\t(" + iX + " chars)");
		assert(lTime < 0);
	}
}
