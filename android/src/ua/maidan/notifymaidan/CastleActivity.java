package ua.maidan.notifymaidan;

import static ua.maidan.notifymaidan.utils.SystemUtils.PREF_LOGIN;
import ua.maidan.notifymaidan.R;
import ua.maidan.notifymaidan.fragments.LoginDialog;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CastleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_castle);
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
}
