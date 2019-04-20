package de.liebold.photorename.logic.fileinfo;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class FileInfoCreator {
    private static final Logger LOG = Logger.getLogger(FileInfoCreator.class.getName());

    private Path path;

    private BasicFileAttributes attrs;

    public FileInfo analyze(URL resource) {
        initialize(resource);

        return runAnalysis();
    }

    private void initialize(URL resource) {
        try {
            path = Paths.get(resource.toURI());
            attrs = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (URISyntaxException | IOException e) {
            LOG.severe(e.getMessage());
            return;
        }
    }

    private FileInfo runAnalysis() {
        FileInfo fileInfo = new FileInfo(path);

        fileInfo.setCreationTime(new Date(attrs.creationTime().toMillis()));
        fileInfo.setLastModifiedTime(new Date(attrs.lastModifiedTime().toMillis()));
        fileInfo.setPhotoTime(extractPhotoTime());

        return fileInfo;
    }

    /**
     * @see https://drewnoakes.com/code/exif/
     */
    private Date extractPhotoTime() {
        Date result = null;
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(path, StandardOpenOption.READ);
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);

            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            if (directory == null) {
                return null;
            }
            result = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        } catch (IOException | ImageProcessingException e) {
            LOG.severe(e.getMessage());
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.severe(e.getMessage());
                }
            }
        }
        return result;
    }

}
