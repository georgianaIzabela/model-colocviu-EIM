package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends Activity {

	private EditText editText1 = null, editText2 = null;
	private Button press1 = null, press2 = null, navigate = null;
	private ButtonListener buttonListener = new ButtonListener();
	private int serviceStatus = Constants.SERVICE_STOPPED;
	
	class ButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int leftNumberOfClicks = Integer.parseInt(editText1.getText().toString());
			int rightNumberOfClicks = Integer.parseInt(editText2.getText().toString());
			switch (v.getId()) {
			case R.id.pres1:
		        leftNumberOfClicks++;
		        editText1.setText(String.valueOf(leftNumberOfClicks));
		        break;
			case R.id.pres2:
		        rightNumberOfClicks++;
		        editText2.setText(String.valueOf(rightNumberOfClicks));
		    	break;
			case R.id.nav:
				Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
				
				intent.putExtra("nrOfClicks", leftNumberOfClicks + rightNumberOfClicks);
				startActivityForResult(intent, 2016);
			}
			if (leftNumberOfClicks + rightNumberOfClicks > Constants.PRAG && serviceStatus == Constants.SERVICE_STOPPED) {
				Intent intent = new Intent(getApplicationContext(), MyService.class);
				intent.putExtra("firstNumber", leftNumberOfClicks);
				intent.putExtra("secondNumber", rightNumberOfClicks);
				getApplicationContext().startService(intent);
				serviceStatus = Constants.SERVICE_STARTED;
			}
			
		}
		
	}
	
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
	private class MessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("[Message]", intent.getStringExtra("message"));
		}
	}
	
	private IntentFilter intentFilter = new IntentFilter();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_main);
		editText1 = (EditText)findViewById(R.id.editText1);
		editText2 = (EditText)findViewById(R.id.editText2);
		
		press1 = (Button)findViewById(R.id.pres1);
		press2 = (Button)findViewById(R.id.pres2);
		navigate = (Button)findViewById(R.id.nav);
		editText1.setText(String.valueOf(0));
		editText2.setText(String.valueOf(0));
		
		press1.setOnClickListener(buttonListener);
		press2.setOnClickListener(buttonListener);
		navigate.setOnClickListener(buttonListener);
		
		for (int index = 0; index < Constants.actions.length; index++) {
			intentFilter.addAction(Constants.actions[index]);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 @Override
	  protected void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    savedInstanceState.putString("editText1", editText1.getText().toString());
	    savedInstanceState.putString("editText2", editText2.getText().toString());
	  }
	 
	  @Override
	  protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    if (savedInstanceState.containsKey("editText1")) {
			editText1.setText(savedInstanceState.getString("editText1"));
		} else {
			editText1.setText(String.valueOf(0));
		}
	    if (savedInstanceState.containsKey("editText2")) {
			editText2.setText(savedInstanceState.getString("editText2"));
		} else {
			editText2.setText(String.valueOf(0));
		}
	  }
	  
	  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();;
	}
	  
	  @Override
		protected void onResume() {
			super.onResume();
			registerReceiver(messageBroadcastReceiver, intentFilter);
		}
		
		@Override
		protected void onPause() {
			unregisterReceiver(messageBroadcastReceiver);
			super.onPause();
		}
		@Override
		protected void onDestroy() {
			Intent intent = new Intent(this, MyService.class);
			stopService(intent);
			super.onDestroy();
		}

	 
}
