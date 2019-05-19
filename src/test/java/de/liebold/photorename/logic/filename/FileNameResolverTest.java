package de.liebold.photorename.logic.filename;

import de.liebold.photorename.AppConfig;
import de.liebold.photorename.logic.fileinfo.FileInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.text.ParseException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class FileNameResolverTest {

    private static final String oldestDateTimeAsText = "2019-01-15 15:01:00 CEST";
    private static final String mediumDateTimeAsText = "2019-01-16 16:01:00 CEST";
    private static final String newestDateTimeAsText = "2019-01-17 17:01:00 CEST";

    @Autowired
    private FileNameResolver fileNameResolver;

    @Test
    public void proposeNames_PhotoTimeIsSet_NameMatchesPhotoTime() throws ParseException {
        // given
        FileInfo fileInfo = FileInfo.builder()
                .originalName("anyName.jpg")
                .creationTimeFromText(newestDateTimeAsText)
                .lastModifiedTimeFromText(mediumDateTimeAsText)
                .photoTimeFromText(oldestDateTimeAsText)
                .build();

        // when
        String proposedName = propose(fileInfo);

        // then
        assertThat(proposedName, is("2019-01-15 15.01.00 ().jpg"));
    }

    @Test
    public void proposeNames_DescriptionIsPresent_DescriptionIsKept() throws ParseException {
        // given
        FileInfo fileInfo = FileInfo.builder()
                .originalName("anyName (Any Description).jpg")
                .creationTimeFromText(newestDateTimeAsText)
                .lastModifiedTimeFromText(mediumDateTimeAsText)
                .photoTimeFromText(oldestDateTimeAsText)
                .build();

        // when
        String proposedName = propose(fileInfo);

        // then
        assertThat(proposedName, is("2019-01-15 15.01.00 (Any Description).jpg"));
    }

    @Test
    public void proposeName_NoPhotoTime_ProposedNameMatchesOldestTime() throws ParseException {
        // given
        FileInfo fileInfo = FileInfo.builder()
                .originalName("anyName.jpg")
                .creationTimeFromText(newestDateTimeAsText)
                .lastModifiedTimeFromText(oldestDateTimeAsText)
                .photoTime(null)
                .build();

        // when
        String proposedName = propose(fileInfo);

        // then
        assertThat(proposedName, is("2019-01-15 15:01:00 ().jpg"));
    }

    /**
     * Utility method to shorten test code.
     */
    private String propose(FileInfo fileInfo) {
        return fileNameResolver.proposeNewNames(Collections.singletonList(fileInfo)).get(0).getProposedName();
    }

}
