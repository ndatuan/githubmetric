package com.quodai.githubmetric.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.GZIPInputStream;

public class FileUnzipService {

	public static FileUnzipService newInstance() {
		return new FileUnzipService();
	}

	public List<String> unzipToJsonFile(List<String> compressedFiles) {
		CopyOnWriteArrayList<String> gsonfilePaths = new CopyOnWriteArrayList<>();
		compressedFiles.parallelStream().forEach(compressedFile -> {
			String decompressedFile = compressedFile.replace(".gz", ".json");
			byte[] buffer = new byte[1024];
			try (GZIPInputStream gZIPInputStream = new GZIPInputStream(new FileInputStream(compressedFile));
					FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);) {
				int bytes_read;
				while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, bytes_read);
				}
				gsonfilePaths.add(decompressedFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		return gsonfilePaths;
	}
}
