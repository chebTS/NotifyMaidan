package ua.maidan.notifymaidan.tasks;

import static ua.maidan.notifymaidan.utils.SystemUtils.*;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import ua.maidan.notifymaidan.CastleActivity;
import ua.maidan.notifymaidan.TowerActivity;
import ua.maidan.notifymaidan.fragments.LoginDialog;
import ua.maidan.notifymaidan.utils.SystemUtils;
import ua.maidan.notifymaidan.utils.SystemUtils.USER_TYPE;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginTask extends AsyncTask<Void, Void, USER_TYPE> {
	private String pass;
	private ProgressBar progressBar;
	private Context context;
	private LoginDialog loginDialog;
	private Button btnSend;
	private Activity activity;

	public LoginTask(String pass, ProgressBar progressBar, Activity activity, LoginDialog loginDialog, Button btnSend) {
		super();
		this.pass = pass;
		this.progressBar = progressBar;
		this.activity = activity;
		this.context = (Context)activity;
		this.loginDialog = loginDialog;
		this.btnSend = btnSend;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	protected USER_TYPE doInBackground(Void... params) {
		try {
			String id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			Log.i("TAG", "Id : " + id);
			String req = String.format(BASE_URL + REGISTRATION_URL, pass, id);
			Log.i("TAG", "Req : " + req);
			HttpPost post = new HttpPost(req);
			post.setHeader("Content-Type", "application/json");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(post);
			int status = response.getStatusLine().getStatusCode();
			Log.i("TAG", "Reg : " + String.valueOf(status));

			if (status == HttpStatus.SC_OK) {
				String data = SystemUtils.streamToString(response.getEntity().getContent());
				Log.i("TAG", "Reg data: " + data);
				JSONObject jRoot = new JSONObject(data);
				int role = jRoot.optInt("role");
				Log.i("TAG", "Reg role : " + role);
				if (role == ID_TOWER) {
					return USER_TYPE.Tower;
				}
				if (role == ID_CASTLE) {
					return USER_TYPE.Castle;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return USER_TYPE.User;
	}

	@Override
	protected void onPostExecute(USER_TYPE result) {
		super.onPostExecute(result);
		SharedPreferences pref =  context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
		progressBar.setVisibility(View.GONE);
		btnSend.setEnabled(true);
		switch (result) {
		case User:
			Toast.makeText(context, "Wrong password", Toast.LENGTH_LONG).show();
			break;
		case Tower:
			pref.edit().putInt( PREF_LOGIN, ID_TOWER).commit();
			context.startActivity(new Intent(context, TowerActivity.class));
			loginDialog.dismiss();
			activity.finish();
			break;
		case Castle:
			pref.edit().putInt( PREF_LOGIN, ID_CASTLE).commit();
			context.startActivity(new Intent(context, CastleActivity.class));
			loginDialog.dismiss();
			activity.finish();
			break;
		default:
			break;
		}
	}
}