package com.githubapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GitHubApiClient {

    private static final String BASE_URL = "https://api.github.com/users/";

    public static void fetchRepositories(String username) {
        try {
            // Fetch user repositories
            String url = BASE_URL + username + "/repos";
            String jsonResponse = getJsonResponse(url);

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Iterate over each repository
            for (JsonNode repo : rootNode) {
                String repoName = repo.get("name").asText();
                int commitCount = fetchCommitCount(username, repoName);
                System.out.println("Repo: " + repoName + " | Number of commits: " + commitCount);
            }
        } catch (IOException e) {
            System.out.println("Error fetching repositories: " + e.getMessage());
        }
    }

    private static int fetchCommitCount(String username, String repoName) {
        try {
            // Fetch commit data for the repository
            String url = "https://api.github.com/repos/" + username + "/" + repoName + "/commits";
            String jsonResponse = getJsonResponse(url);

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode commitNodes = objectMapper.readTree(jsonResponse);
            
            return commitNodes.size(); // Number of commits
        } catch (IOException e) {
            System.out.println("Error fetching commits: " + e.getMessage());
            return 0;
        }
    }

    private static String getJsonResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new IOException("HTTP error: " + conn.getResponseCode());
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }

    public static void main(String[] args) {
        fetchRepositories("richkempinski");  // Example GitHub username
    }
}
