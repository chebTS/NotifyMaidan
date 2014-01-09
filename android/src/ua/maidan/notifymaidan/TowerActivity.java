package ua.maidan.notifymaidan;

import static ua.maidan.notifymaidan.utils.SystemUtils.PREF_LOGIN;
import ua.maidan.notifymaidan.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TowerActivity extends Activity implements OnClickListener {
	private Button btnAlert;
	private EditText fAlert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tower);
		btnAlert = (Button)findViewById(R.id.btn_alarm);
		btnAlert.setOnClickListener(this);
		fAlert = (EditText)findViewById(R.id.f_alarm);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_logout, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout:
			SharedPreferences pref = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
			pref.edit().clear().commit();
			startActivity(new Intent(getApplicationContext(), MainActivityNotify.class));
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_alarm:
			//TODO
			break;
		default:
			break;
		}
	}
}
