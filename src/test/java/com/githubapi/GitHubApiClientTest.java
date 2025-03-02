package com.githubapi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GitHubApiClientTest {

    @Test
    void testFetchRepositories() {
        assertDoesNotThrow(() -> GitHubApiClient.fetchRepositories("richkempinski"));
    }

    @Test
    void testInvalidUser() {
        assertDoesNotThrow(() -> GitHubApiClient.fetchRepositories("invalidusername123456"));
    }
}
