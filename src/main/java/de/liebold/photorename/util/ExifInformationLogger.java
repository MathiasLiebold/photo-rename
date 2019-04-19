package de.liebold.photorename.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * @see see https://drewnoakes.com/code/exif/
 */
public class ExifInformationLogger {

    private static final Logger LOG = Logger.getLogger(ExifInformationLogger.class.getName());

    /**
     * Logging output for Debugging purposes.
     */
    public void logAll(InputStream inputStream) {
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(inputStream);
        } catch (ImageProcessingException | IOException e) {
            LOG.severe(e.getMessage());
        }
        if (metadata == null) {
            return;
        }

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.format("[%s] - %s = %s", directory.getName(), tag.getTagName(), tag.getDescription());
                System.out.println("");
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                    System.out.println("");
                }
            }
        }
    }

}
