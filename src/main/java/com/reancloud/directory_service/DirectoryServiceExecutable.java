package com.reancloud.directory_service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.common.io.Files;

/**
 * 
 * @author Nikhil S. Kapure
 *
 */
public class DirectoryServiceExecutable {
	final static String DOMAIN = "domain";
	final static String USER_NAME = "user_name";
	final static String CLIENT_SECRET = "client_secret";
	final static String OUTPUT_JSON_DIR_PATH = "output_json_dir_path";

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);

		try {
			if (args != null && args.length > 0
					&& (args[0].equalsIgnoreCase("-i"))) {
				System.out.print("Please enter domain name : ");
				String domain = reader.nextLine();
				System.out.println();
				if (domain == null || domain.trim().length() == 0) {
					System.out
							.println("Error : Please enter valid domain name !!");
					System.exit(1);
				}
				System.out.print("Please enter user name : ");
				String user = reader.nextLine();
				System.out.println();
				if (user == null || user.trim().length() == 0) {
					System.out
							.println("Error : Please enter valid user name !!");
					System.exit(1);
				}
				System.out.print("Please enter credential file path : ");
				String credFilePath = reader.nextLine();
				System.out.println();
				if (credFilePath == null || credFilePath.trim().length() == 0
						|| !isJsonFileExist(credFilePath)) {
					System.out
							.println("Error : Please enter valid credential file !!");
					System.exit(1);
				}
				System.out.print("Please enter output json path : ");
				String jsonPath = reader.nextLine();
				System.out.println();
				if (jsonPath == null || jsonPath.trim().length() == 0
						|| !isDirExist(jsonPath)) {
					System.out
							.println("Error : Please enter valid output json directory !!");
					System.exit(1);
				}
				reader.close();

				SimpleDateFormat sdfDate = new SimpleDateFormat(
						"dd-MMM-yyyy HH:mm");
				Date now = new Date();
				String strDate = sdfDate.format(now);
				String fileName = user.trim() + "_" + domain.trim() + "_"
						+ strDate;
				fileName = fileName.replaceAll(" ", "_");
				fileName = fileName.replaceAll("[:]", "-");
				fileName += ".json";
				System.out.println("Output json file : " + fileName);
				Map<String, Map<String, List<String>>> domainTree = DirectoryService
						.fetchDomainWithGroupAndMembers(domain.trim(),
								credFilePath.trim());

				DirectoryService.createDomainTreeJson(domainTree, fileName,
						jsonPath);
			} else if (args != null && args.length == 4) {
				Map<String, String> map = new HashMap<String, String>();
				for (String arg : args) {
					if (arg.contains("=")) {
						// works only if the key doesn't have any '='
						map.put(arg.substring(0, arg.indexOf('=')),
								arg.substring(arg.indexOf('=') + 1));
					} else {
						System.out
								.println("Please enter valid key value for domain, user_name, client_secret and output_json_dir_path !!! ");
						System.out
								.println("Example : java -jar directory-service-0.0.1.jar domain=<domain_name> user_name=<user_name> client_secret=<client_secret> output_json_dir_path=<output_json_dir_path>");
						System.exit(1);
					}
				}
				String domain = map.get(DOMAIN);
				String user = map.get(USER_NAME);

				SimpleDateFormat sdfDate = new SimpleDateFormat(
						"dd-MMM-yyyy HH:mm");
				Date now = new Date();
				String strDate = sdfDate.format(now);
				String fileName = user.trim() + "_" + domain.trim() + "_"
						+ strDate;
				fileName = fileName.replaceAll(" ", "_");
				fileName = fileName.replaceAll("[:]", "-");
				fileName += ".json";
				String credFilePath = map.get(CLIENT_SECRET);
				String jsonPath = map.get(OUTPUT_JSON_DIR_PATH);
				System.out.println("Output json file : " + fileName);
				Map<String, Map<String, List<String>>> domainTree = DirectoryService
						.fetchDomainWithGroupAndMembers(domain.trim(),
								credFilePath.trim());

				DirectoryService.createDomainTreeJson(domainTree, fileName,
						jsonPath);

			} else {
				System.out
						.println("|--------------------------------------------------------------------------|");

				System.out
						.println("!!! Please provide correct arguments for execution !!!");
				System.out
						.println("You can execute jar file either using interactive mode or silent mode using following commands");
				System.out.println("--------------------------------------------------------------------------");
				System.out
						.println("I. Interactive mode : java -jar directory-service-0.0.1.jar -i");
				System.out
						.println("--------------------------------------------------------------------------");
				System.out
						.println("II. Silent mode : java -jar directory-service-0.0.1.jar domain=<domain_name> user_name=<user_name> client_secret=<client_secret> output_json_dir_path=<output_json_dir_path>");
				System.out.println("domain_name   		 : e.g- reancloud.com");
				System.out.println("user_name  			 : e.g- Nikhil.Kapure");
				System.out
						.println("client_secret   		 : e.g- /home/rean-cloud/client_secret.json");
				System.out
						.println("output_json_dir_path   : Location where output json is saved. e.g- /home/rean-cloud");
				System.out
						.println("|--------------------------------------------------------------------------|");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isJsonFileExist(String path) {
		File file = new File(path);
		if (file.exists()) {
			String fileExtension = Files.getFileExtension(path);
			if ("json".equals(fileExtension)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDirExist(String path) {
		File file = new File(path);
		return file.isDirectory();
	}
}
