package ua.maidan.notifymaidan.tasks;

import static ua.maidan.notifymaidan.utils.SystemUtils.BASE_URL;
import static ua.maidan.notifymaidan.utils.SystemUtils.REGISTRATION_PUSH_URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import ua.maidan.notifymaidan.utils.SystemUtils;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.util.Log;

public class SendPushId extends AsyncTask<Void, Void, Boolean> {
	private String pushId;
	private Context context;

	public SendPushId(String pushId, Context context) {
		super();
		this.pushId = pushId;
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			String req = String.format(BASE_URL + REGISTRATION_PUSH_URL, deviceId, pushId);
			Log.i("TAG", "Req : " + req);
			HttpPost post = new HttpPost(req);
			post.setHeader("Content-Type", "application/json");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(post);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				String data = SystemUtils.streamToString(response.getEntity().getContent());
				Log.i("TAG", "Reg data: " + data);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}
	
	

}
