package com.quodai.githubmetric.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class FileUnzipService {

	public static FileUnzipService newInstance() {
		return new FileUnzipService();
	}

	public String unzipToJsonFile(String compressedFile) throws IOException {
		String decompressedFile = compressedFile.replace(".gz", ".json");
		byte[] buffer = new byte[1024];
		try (GZIPInputStream gZIPInputStream = new GZIPInputStream(new FileInputStream(compressedFile));
				FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);) {
			int bytes_read;
			while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, bytes_read);
			}
		}
		return decompressedFile;
	}
}
