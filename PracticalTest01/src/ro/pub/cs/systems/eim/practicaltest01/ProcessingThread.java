package ro.pub.cs.systems.eim.practicaltest01;

import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread{

	 private Context context = null;
	 private int firstNr, secondNr;
	 private double artmean, geomean;
	 private boolean isRunning = true;
	 private Random random = new Random();
	 public ProcessingThread (Context context, int firstNr, int secondNr) {
		 this.context = context;
		 this.firstNr = firstNr;
		 this.secondNr = secondNr;
		 artmean = (firstNr + secondNr) / 2;
		 geomean = Math.sqrt(firstNr * secondNr);
	 }
	 @Override
	 public void run () {
		 Log.d("[ProcessingThread]", "Thread has started!");
		    while (isRunning) {
		    	Intent intent = new Intent();
		    	intent.setAction(Constants.actions[random.nextInt(Constants.actions.length)]);
		    	intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + this.artmean + " " + this.geomean);
		        context.sendBroadcast(intent);
		    	sleep();
		    }
		    Log.d("[ProcessingThread]", "Thread has stopped!");
	 }
	 private void sleep() {
	        try {
	            Thread.sleep(10000);
	        } catch (InterruptedException interruptedException) {
	            interruptedException.printStackTrace();
	        }
	    }
	 public void stopThread() {
		 isRunning = false;
	 }
}
