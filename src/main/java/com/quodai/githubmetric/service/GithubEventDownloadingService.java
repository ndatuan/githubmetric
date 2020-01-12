package com.quodai.githubmetric.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

	public String downloadFileAndReturnFilePath(String url) throws IOException {
		HttpGet request = new HttpGet(url);
		String filePath = "src/main/resources/" + UUID.randomUUID().toString() + ".zip";
		File file = FileUtils.getFile(filePath);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request);
				OutputStream outputStream = new FileOutputStream(file)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					byte[] buffer = new byte[8 * 1024];
					int bytesRead;
					InputStream content = entity.getContent();
					while ((bytesRead = content.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
					}
					return filePath;
				}
			}
		}
		return StringUtils.EMPTY;
	}
}
