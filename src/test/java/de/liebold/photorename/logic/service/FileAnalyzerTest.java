package de.liebold.photorename.logic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.liebold.photorename.logic.bean.FileInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FileAnalyzerTest {

	@Autowired
	private FileAnalyzer fileAnalyzer;

	@Test
	public void analyze_NonMedia_ReturnsNull() {
		URL resource = getUrl("/99_Textfile.txt");

		assertNull(fileAnalyzer.analyze(resource));
	}

	// @Test(expected = NullPointerException.class)
	// public void analyze_NullInput_ThrowsException() {
	// fileAnalyzer.analyze(null);
	// }

	@Test
	public void analyze_JPG_Works() throws IOException {
		URL resource = getUrl("/01_Photo.jpg");
		FileInfo fileInfo = fileAnalyzer.analyze(resource);

		assertNotNull(fileInfo);
		assertNotNull(fileInfo.getCreationTime());
		assertNotNull(fileInfo.getLastModifiedTime());
		assertNotNull(fileInfo.getPhotoTime());
	}

	@Ignore
	@Test
	public void analyze_RW2_Works() throws IOException {
		URL resource = getUrl("/01_Photo.rw2");
		FileInfo fileInfo = fileAnalyzer.analyze(resource);

		assertNotNull(fileInfo);
		assertNotNull(fileInfo.getCreationTime());
		assertNotNull(fileInfo.getLastModifiedTime());
		assertNotNull(fileInfo.getPhotoTime());
	}

	@Test
	public void analyze_MediaWithoutPhotoTime_Works() throws IOException {
		URL resource = getUrl("/02_PhotoWithoutPhotoTime.jpg");
		FileInfo fileInfo = fileAnalyzer.analyze(resource);

		assertNotNull(fileInfo);
		assertNotNull(fileInfo.getCreationTime());
		assertNotNull(fileInfo.getLastModifiedTime());
		assertNull(fileInfo.getPhotoTime());
	}

	private URL getUrl(String name) {
		if (name == null) {
			return null;
		}
		return getClass().getResource(name.trim());
	}

}
