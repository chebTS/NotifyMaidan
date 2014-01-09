package ua.maidan.notifymaidan.utils;

import java.io.InputStream;
import java.io.InputStreamReader;

public class SystemUtils {

	public static String PREF_LOGIN = "pref_login";
	public static final int ID_TOWER = 1;
	public static final int ID_CASTLE = 2;
	//url for pushid http://warbrothers.org/maydan/push.php?deviceId=5b1a094e45d07333&pushId=5554
	
	public enum USER_TYPE {
		User, Tower, Castle
	}

	public static USER_TYPE USER = USER_TYPE.User;

	public static String BASE_URL = "http://warbrothers.org/maydan/";
	public static String REGISTRATION_URL = "registration.php?password=%s&deviceId=%s";
	public static String REGISTRATION_PUSH_URL = "push.php?deviceId=%s&pushId==%s";

	public static String streamToString(InputStream content) throws Exception {
		StringBuilder stringBuffer = new StringBuilder();
		InputStreamReader inputStreamReader = new InputStreamReader(content);
		char[] buffer = new char[1];
		while (inputStreamReader.read(buffer) != -1) {
			stringBuffer.append(buffer);
		}
		content.close();
		return stringBuffer.toString();
	}
}
