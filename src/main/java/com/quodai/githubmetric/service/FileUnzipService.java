package com.quodai.githubmetric.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class FileUnzipService {
	
	public static FileUnzipService newInstance() {
		return new FileUnzipService();
	}
	
	public List<String> unzipFileAndReturnDescendants(String fileName) throws IOException {
		File file = new File(fileName);
		try(ZipFile zipFile = new ZipFile(file);) {
			Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
			List<String> descendantFiles = new ArrayList<>();
			while (entries.hasMoreElements()) {
				ZipArchiveEntry zipArchiveEntry = entries.nextElement();
				InputStream inputStream = zipFile.getInputStream(zipArchiveEntry);
				descendantFiles.add(zipArchiveEntry.getName());
			}
		}
	
		return Collections.emptyList();
	}
}
