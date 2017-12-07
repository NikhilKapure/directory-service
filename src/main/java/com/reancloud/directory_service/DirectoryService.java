package com.reancloud.directory_service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Groups;
import com.google.api.services.admin.directory.model.Member;
import com.google.api.services.admin.directory.model.Members;
import com.reancloud.directory_service.auth.AuthService;

/**
 * 
 * @author Nikhil S. Kapure
 *
 */
public class DirectoryService {

	/**
	 * Build and return an authorized Admin SDK Directory client service.
	 * 
	 * @param credential
	 *            file path
	 * @return an authorized Directory client service
	 * @throws IOException
	 */
	public static Directory getDirectoryService(String credFilePath)
			throws IOException {
		Credential credential = AuthService.authorize(credFilePath);
		return new Directory.Builder(Config.HTTP_TRANSPORT,
				Config.JSON_FACTORY, credential).setApplicationName(
				Config.APPLICATION_NAME).build();
	}

	public static Map<String, Map<String, List<String>>> fetchDomainWithGroupAndMembers(
			String domain, String credFilePath) throws IOException {
		Map<String, Map<String, List<String>>> domainTree = new LinkedHashMap<String, Map<String, List<String>>>();
		Map<String, List<String>> groupMembers = new LinkedHashMap<String, List<String>>();
		// Build a new authorized API client service.
		Directory service = getDirectoryService(credFilePath);
		System.out.println("Fetching groups and their members for " + domain
				+ " ...");
		System.out.println("Wait for some time to complete this operation ...");
		String grpNextToken = null;
		do {
			Groups groups = service.groups().list().setDomain(domain)
					.setMaxResults(500).setPageToken(grpNextToken).execute();
			if (groups == null || groups.size() == 0) {
				System.out.println("No groups found.");
			} else {
				for (Group group : groups.getGroups()) {
					if (group != null) {
						List<String> nameList = new LinkedList<String>();
						String nextToken = null;
						do {
							Members members = service.members()
									.list(group.getId()).setMaxResults(500)
									.setPageToken(nextToken).execute();
							if (members != null) {
								List<Member> memberList = members.getMembers();
								if (memberList != null) {
									for (Member m : memberList) {
										if (m != null) {
											nameList.add(m.getEmail() + " ("
													+ m.getRole() + ")");
										}
									}
								}
							}
							nextToken = members.getNextPageToken();
						} while (nextToken != null);
						groupMembers
								.put(group.getEmail() + " (" + nameList.size()
										+ ")", nameList);
					}
				}
			}
			grpNextToken = groups.getNextPageToken();
		} while (grpNextToken != null);
		domainTree.put("reancloud.com (" + groupMembers.keySet().size() + ")",
				groupMembers);
		System.out
				.println("All groups and their members are fetched successfully ...");
		return domainTree;
	}

	/**
	 * Create json file
	 * 
	 * @param domainTree
	 * @param fileName
	 * @param jsonPath
	 * @throws IOException
	 */
	public static void createDomainTreeJson(Object domainTree, String fileName,
			String jsonPath) throws IOException {
		System.out.println("Creating json for domain tree ...");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		Path path = Paths.get(jsonPath, fileName);
		mapper.writeValue(path.toFile(), domainTree);
		System.out.println(fileName + " file created at " + jsonPath);
		System.out.println("");
		System.out.println("Execution completed successfully ...");
	}

}
