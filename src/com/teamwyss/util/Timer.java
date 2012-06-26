package com.teamwyss.util;

public class Timer {

	long longStart = 0L;
	long longStop = 0L;
	private long longDuration = 0L;
	private boolean running = false;

	public void startTimer(){
		longStart = (new java.util.Date()).getTime();
		running = true;
	}

	public long stopTimer(){
		if(running){
			longStop = (new java.util.Date()).getTime();
			longDuration  = longStop - this.longStart;
			running = false;
		}
		return longDuration;
	}

	public String getDurationAsString(){
		long longSeconds = longDuration / 1000;
		int iSeconds = (int)(longSeconds % 60);
		longSeconds = Math.round((longSeconds - iSeconds) / 60);
		int iMinutes = (int)(longSeconds % 60);
		return iMinutes + " mins " + iSeconds + " seconds";
	}

	public long getDurationSeconds(){
		return longDuration / 1000;
	}

	public boolean isRunning() {
		return running;
	}
}
