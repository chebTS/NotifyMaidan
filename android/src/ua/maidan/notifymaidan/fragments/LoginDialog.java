package ua.maidan.notifymaidan.fragments;

import ua.maidan.notifymaidan.R;
import ua.maidan.notifymaidan.tasks.LoginTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginDialog extends DialogFragment implements OnClickListener {
	private EditText f_pass;
	private ProgressBar progressBar;
	private Button btnSend;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.login_dialog, null);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent);
		((Button) v.findViewById(R.id.btn_cancel)).setOnClickListener(this);
		btnSend = ((Button) v.findViewById(R.id.btn_send));
		btnSend.setOnClickListener(this);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		f_pass = (EditText) v.findViewById(R.id.f_pass);
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			dismiss();
			break;
		case R.id.btn_send:
			btnSend.setEnabled(false);
			String pass = f_pass.getText().toString();
			(new LoginTask(pass, progressBar, getActivity(), this, btnSend)).execute();
			break;
		default:
			break;
		}
	}
}
