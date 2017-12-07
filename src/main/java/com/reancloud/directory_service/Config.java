package com.reancloud.directory_service;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.admin.directory.DirectoryScopes;

/**
 * 
 * @author Nikhil S. Kapure
 *
 */

public abstract class Config {
	/** Application name. */
	public static final String APPLICATION_NAME = "Directory API Java Quickstart";

	/** Directory to store user credentials for this application. */
	public static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"),
			".credentials/admin-directory_v1-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	public static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	public static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	public static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/admin-directory_v1-java-quickstart
	 */
	public static final List<String> SCOPES = Arrays.asList(
			DirectoryScopes.ADMIN_DIRECTORY_GROUP,
			DirectoryScopes.ADMIN_DIRECTORY_GROUP_MEMBER);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	public static final String ACCESS_TYPE_OFFLINE = "offline";

}
