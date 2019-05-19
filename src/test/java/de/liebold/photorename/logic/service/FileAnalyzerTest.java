package de.liebold.photorename.logic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.URL;

import de.liebold.photorename.logic.fileinfo.FileInfoExtractor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import de.liebold.photorename.logic.fileinfo.FileInfo;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
//@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FileAnalyzerTest {

    @Autowired
    private FileInfoExtractor fileInfoCreator;

    @Test
    public void analyze_NonMedia_ReturnsNull() {
        URL resource = getUrl("/99_Textfile.txt");

        assertNull(fileInfoCreator.extractFileInfo(resource));
    }

    // @Test(expected = NullPointerException.class)
    // public void analyze_NullInput_ThrowsException() {
    // fileInfoCreator.extractFileInfo(null);
    // }

    @Test
    public void analyze_JPG_Works() throws IOException {
        URL resource = getUrl("/01_Photo.jpg");
        FileInfo fileInfo = fileInfoCreator.extractFileInfo(resource);

        assertNotNull(fileInfo);
        assertNotNull(fileInfo.getCreationTime());
        assertNotNull(fileInfo.getLastModifiedTime());
        assertNotNull(fileInfo.getPhotoTime());
    }

    @Ignore
    @Test
    public void analyze_RW2_Works() throws IOException {
        URL resource = getUrl("/01_Photo.rw2");
        FileInfo fileInfo = fileInfoCreator.extractFileInfo(resource);

        assertNotNull(fileInfo);
        assertNotNull(fileInfo.getCreationTime());
        assertNotNull(fileInfo.getLastModifiedTime());
        assertNotNull(fileInfo.getPhotoTime());
    }

    @Test
    public void analyze_MediaWithoutPhotoTime_Works() throws IOException {
        URL resource = getUrl("/02_PhotoWithoutPhotoTime.jpg");
        FileInfo fileInfo = fileInfoCreator.extractFileInfo(resource);

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
