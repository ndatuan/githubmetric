package com.quodai.githubmetric.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.quodai.githubmetric.main.HealthScoreCalculator;

public class GithubEventDownloadingService {

	public static GithubEventDownloadingService newInstance() {
		return new GithubEventDownloadingService();
	}

	public CopyOnWriteArrayList<String> downloadFileAndReturnFilePath(List<String> urls) throws IOException {
		CopyOnWriteArrayList<String> filePaths = new CopyOnWriteArrayList<String>();
		urls.parallelStream().forEach(url -> {
			downloadUrlAndAddDownloadedFilePaths(filePaths, url);
		});
		return filePaths;
	}

	private void downloadUrlAndAddDownloadedFilePaths(CopyOnWriteArrayList<String> filePaths, String url) {
		HttpGet request = new HttpGet(url);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				downLoadSuccessfulUrl(filePaths, url, response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void downLoadSuccessfulUrl(CopyOnWriteArrayList<String> filePaths, String url, CloseableHttpResponse response) throws IOException, FileNotFoundException {
		String filePath = HealthScoreCalculator.RESOURCES_FOLDER + UUID.randomUUID().toString() + ".gz";
		File file = FileUtils.getFile(filePath);
		try(OutputStream outputStream = new FileOutputStream(file)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				InputStream content = entity.getContent();
				while ((bytesRead = content.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}
		}
		filePaths.add(filePath);
	}

}
