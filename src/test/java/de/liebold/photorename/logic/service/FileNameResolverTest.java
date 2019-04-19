package de.liebold.photorename.logic.service;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FileNameResolverTest {

    @Autowired
    private FileNameResolver fileNameResolver;

    // @Test
    // public void getNewName_NormalFile() {
    // FileInfo fileInfo = null;
    // //TODO
    //
    // assertEquals("", fileNameResolver.getNewName(fileInfo));
    // }

    @Test(expected = IllegalArgumentException.class)
    public void getNewName_NullParameter_ThrowsException() {
        fileNameResolver.getProposedName(null, Optional.empty());
    }

}
