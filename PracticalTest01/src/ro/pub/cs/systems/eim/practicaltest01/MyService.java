package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
	 
	  private int startMode;
	  private ProcessingThread processingThread = null;
	  @Override
	  public void onCreate() {
	    super.onCreate();
	    // ...
	  }
	 
	  @Override
	  public int onStartCommand(Intent intent, 
	                            int flags,
	                            int startId) {
	    int firstNr = intent.getExtras().getInt("firstNumber");
	    int secondNr = intent.getExtras().getInt("secondNumber");
	    ProcessingThread processingThread = new ProcessingThread(this, firstNr, secondNr);
	    processingThread.start();
	    
	    return START_REDELIVER_INTENT;
	  }
	 
	  @Override
	  public IBinder onBind(Intent intent) {
	    // ...
	    return null;
	  }
	 
	  @Override
	  public void onDestroy() {
		  processingThread.stopThread();
		  super.onDestroy();
	  }
	}
