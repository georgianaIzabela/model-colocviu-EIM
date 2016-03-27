package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PracticalTest01SecondaryActivity extends Activity {
	private Button ok = null, cancel = null;
	private TextView textView = null;
	ButtonListener buttonListener = new ButtonListener();
	
	class ButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				// ok
				setResult(Activity.RESULT_OK);
				break;
			case R.id.button2:
				// cancel
				setResult(Activity.RESULT_CANCELED);
				break;
			}
			
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_secondary);
		ok = (Button)findViewById(R.id.button1);
		cancel = (Button)findViewById(R.id.button2);
		textView = (TextView)findViewById(R.id.number_of_clicks_text_view);
		ok.setOnClickListener(buttonListener);
		cancel.setOnClickListener(buttonListener);
		Intent intent = getIntent();
		if (intent != null) {
			if (intent.getExtras().containsKey("nrOfClicks")) {
				textView.setText(String.valueOf(intent.getExtras().get("nrOfClicks")));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_secondary, menu);
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
}
