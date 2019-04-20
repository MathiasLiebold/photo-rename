package de.liebold.photorename.logic;

import de.liebold.photorename.logic.fileinfo.FileInfo;
import de.liebold.photorename.logic.fileinfo.FileInfoCreator;
import de.liebold.photorename.logic.filename.FileNameAnalyzer;
import de.liebold.photorename.logic.filename.FileNameResolver;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

@Service
public class FileService {

    private static final Logger LOG = Logger.getLogger(FileService.class.getName());
    private static final String DUMMY_FILE_NOT_TO_BE_RENAMED = "1x1px.jpg";

    @Autowired
    private FileNameAnalyzer fileNameAnalyzer;

    @Autowired
    private FileNameResolver fileNameResolver;

    @Autowired
    private FileInfoCreator fileInfoCreator;

    @Value("#{'${photo-rename.file.image.endings}'.split(',')}")
    private List<String> imageFileEndings;

    @Value("#{'${photo-rename.file.image.raw.endings}'.split(',')}")
    private List<String> imageRawFileEndings;

    @Value("#{'${photo-rename.file.video.endings}'.split(',')}")
    private List<String> videoFileEndings;

    // Initialized in PostConstruct
    @Getter
    private List<String> supportedFileEndings;

    @PostConstruct
    public void init() {
        supportedFileEndings = new LinkedList<>();
        supportedFileEndings.addAll(imageFileEndings);
        supportedFileEndings.addAll(imageRawFileEndings);
        supportedFileEndings.addAll(videoFileEndings);
    }

    public Path getCurrentDirectoryPath() {
        return Paths.get(".");
    }

    public List<FileInfo> getSourceFiles() {
        List<FileInfo> result = new ArrayList<>();

        for (URL url : getFromCurrentDirectory()) {
            if (isValid(url.toString())) {
                FileInfo fileInfo = fileInfoCreator.analyze(url);
                if (fileInfo != null) {
                    result.add(fileInfo);
                }
            }
        }

        fileNameResolver.proposeNames(result);

        return fileNameResolver.getModified();
    }

    public boolean isValid(String filePathOrUrl) {
        return supportedFileEndings.stream()
                .anyMatch(fileNameAnalyzer.getFileEndingUpperCase(filePathOrUrl.toUpperCase())::equalsIgnoreCase);
    }

    public void renameSourceFiles() {
        for (FileInfo fileInfo : getSourceFiles()) {

            if (DUMMY_FILE_NOT_TO_BE_RENAMED.equals(fileInfo.getOriginalName())) {
                throw new IllegalStateException("Dummy file may not be renamed. " + DUMMY_FILE_NOT_TO_BE_RENAMED);
            }

            File originalFile = fileInfo.getOriginalPath().toFile();

            // TODO random name for target dir

            File targetDir = new File(originalFile.getParentFile().getAbsolutePath() + File.separatorChar + "target");
            File newFile = new File(targetDir.getAbsolutePath() + File.separatorChar + fileInfo.getProposedName());

            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            // TODO move back from target dir

            originalFile.renameTo(newFile);
        }
    }

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
                if (isValid(path.toUri().toString())) {
                    result.add(path.toUri().toURL());
                }

            } catch (MalformedURLException e) {
                LOG.severe(e.getMessage());
            }
        }

        return result;
    }

}
