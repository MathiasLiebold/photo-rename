package de.liebold.photorename.logic.fileinfo;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import de.liebold.photorename.logic.FileAnalysisException;
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
public class FileInfoExtractor {
    private static final Logger LOG = Logger.getLogger(FileInfoExtractor.class.getName());

    public FileInfo extractFileInfo(URL resource) {
        Path path = preparePath(resource);
        return analyzeFile(path);
    }

    private Path preparePath(URL resource) {
        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new FileAnalysisException(e);
        }
    }

    private FileInfo analyzeFile(Path path) {
        FileInfo fileInfo = new FileInfo(path);

        BasicFileAttributes basicFileAttributes;
        try {
            basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new FileAnalysisException(e);
        }

        fileInfo.setCreationTime(new Date(basicFileAttributes.creationTime().toMillis()));
        fileInfo.setLastModifiedTime(new Date(basicFileAttributes.lastModifiedTime().toMillis()));
        fileInfo.setPhotoTime(extractPhotoTime(path));

        return fileInfo;
    }

    /**
     * @see https://drewnoakes.com/code/exif/
     */
    private Date extractPhotoTime(Path path) throws FileAnalysisException {
        try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);

            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            if (directory == null) { // TODO check if really required
                return null;
            }
            return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        } catch (IOException | ImageProcessingException e) {
            throw new FileAnalysisException(e);
        }
    }

}
