package de.liebold.photorename.logic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.URL;

import de.liebold.photorename.logic.fileinfo.FileInfoCreator;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.liebold.photorename.logic.fileinfo.FileInfo;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FileAnalyzerTest {

    @Autowired
    private FileInfoCreator fileInfoCreator;

    @Test
    public void analyze_NonMedia_ReturnsNull() {
        URL resource = getUrl("/99_Textfile.txt");

        assertNull(fileInfoCreator.analyze(resource));
    }

    // @Test(expected = NullPointerException.class)
    // public void analyze_NullInput_ThrowsException() {
    // fileInfoCreator.analyze(null);
    // }

    @Test
    public void analyze_JPG_Works() throws IOException {
        URL resource = getUrl("/01_Photo.jpg");
        FileInfo fileInfo = fileInfoCreator.analyze(resource);

        assertNotNull(fileInfo);
        assertNotNull(fileInfo.getCreationTime());
        assertNotNull(fileInfo.getLastModifiedTime());
        assertNotNull(fileInfo.getPhotoTime());
    }

    @Ignore
    @Test
    public void analyze_RW2_Works() throws IOException {
        URL resource = getUrl("/01_Photo.rw2");
        FileInfo fileInfo = fileInfoCreator.analyze(resource);

        assertNotNull(fileInfo);
        assertNotNull(fileInfo.getCreationTime());
        assertNotNull(fileInfo.getLastModifiedTime());
        assertNotNull(fileInfo.getPhotoTime());
    }

    @Test
    public void analyze_MediaWithoutPhotoTime_Works() throws IOException {
        URL resource = getUrl("/02_PhotoWithoutPhotoTime.jpg");
        FileInfo fileInfo = fileInfoCreator.analyze(resource);

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
