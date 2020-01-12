package com.quodai.githubmetric.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class StreamUnzipService {

	public static StreamUnzipService newInstance() {
		return new StreamUnzipService();
	}
	
	public String convertZipStreamToString(String fileName) throws IOException {
		try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(fileName)));
				) {
			ZipEntry ze;
			while((ze = zis.getNextEntry()) != null) {
				System.out.println("-------------");
			}
		}
		return null;
	}
}
