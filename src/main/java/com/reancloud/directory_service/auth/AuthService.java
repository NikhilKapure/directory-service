package com.reancloud.directory_service.auth;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.reancloud.directory_service.Config;

/**
 * 
 * @author Nikhil S. Kapure
 *
 */
public class AuthService {

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize(String credFilePath) throws IOException {
		// Load client secrets.
		InputStream in = null;
		if (credFilePath != null && credFilePath.trim().length() != 0) {
			in = new FileInputStream(credFilePath);
		} else {
			in = AuthService.class.getResourceAsStream("/client_secret.json");
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				Config.JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				Config.HTTP_TRANSPORT, Config.JSON_FACTORY, clientSecrets,
				Config.SCOPES).setDataStoreFactory(Config.DATA_STORE_FACTORY)
				.setAccessType(Config.ACCESS_TYPE_OFFLINE).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to "
				+ Config.DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}
}
