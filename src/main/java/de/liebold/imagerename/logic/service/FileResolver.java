package de.liebold.imagerename.logic.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileResolver")
public class FileResolver {

	private static final Logger LOG = Logger.getLogger(FileResolver.class.getName());

	public static final List<String> IMAGE_FILE_TYPES = Collections
			.unmodifiableList(Arrays.asList(new String[] { "JPG", "JPEG", "PNG", "WebP", "GIF", "ICO", "BMP", "TIFF",
					"PSD", "PCX", "CRW", "CR2", "NEF", "ORF", "RAF", "RWL", "SRW", "ARW", "DNG", "X3F" }));

	public static final List<String> RAW_IMAGE_FILE_TYPES = Collections
			.unmodifiableList(Arrays.asList(new String[] { "RAW", "RW2" }));

	public static final List<String> VIDEO_FILE_TYPES = Collections
			.unmodifiableList(Arrays.asList(new String[] { "MOV", "MP4", "M4V", "3G2", "3GP", "3GP" }));

	private static Set<String> allowedFileTypes = new HashSet<>();

	static {
		allowedFileTypes.addAll(IMAGE_FILE_TYPES);
		allowedFileTypes.addAll(VIDEO_FILE_TYPES);
	}

	@Autowired
	private FileNameAnalyzer fileNameAnalyzer;

	public List<URL> getFromCurrentDirectory() {

		List<URL> result = new LinkedList<>();

		Iterator<Path> it = null;
		try {
			it = Files.list(Paths.get(".")).iterator();
		} catch (IOException e) {
			LOG.severe(e.getMessage());
		}
		while (it != null && it.hasNext()) {
			Path path = it.next();
			try {
				if (isValid(path)) {
					result.add(path.toUri().toURL());
				}

			} catch (MalformedURLException e) {
				LOG.severe(e.getMessage());
			}
		}

		return result;
	}

	public boolean isValid(Path path) {
		return allowedFileTypes.stream()
				.anyMatch(fileNameAnalyzer.getFileEnding(path.toFile().getName())::equalsIgnoreCase);
	}

	public URL getCorrespondingRaw(URL plain) {
		return null;
	}

}
