package com.quodai.githubmetric.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class GithubEventDownloadingService {

	public static GithubEventDownloadingService newInstance() {
		return new GithubEventDownloadingService();
	}

	public InputStream getGitHubEvent(String url) throws IOException {
		HttpGet request = new HttpGet(url);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(request)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new ZipInputStream(entity.getContent());
			}
		}
		return null;
	}

}
