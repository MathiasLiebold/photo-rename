package de.liebold.photorename.logic;

import de.liebold.photorename.logic.fileinfo.FileInfo;
import de.liebold.photorename.logic.fileinfo.FileInfoExtractor;
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
    private FileInfoExtractor fileInfoExtractor;

    @Value("#{'${photo-rename.file.image.endings}'.split(',')}")
    private List<String> imageFileEndings;

    @Value("#{'${photo-rename.file.image.raw.endings}'.split(',')}")
    private List<String> imageRawFileEndings;

    @Value("#{'${photo-rename.file.video.endings}'.split(',')}")
    private List<String> videoFileEndings;

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

    public List<FileInfo> getFileInfos() {
        List<URL> urls = getAllMediaFilesFromDirectory(".");

        List<FileInfo> fileInfoWithoutNameProposal = analyzeAndPrepareFileInfos(urls);

        List<FileInfo> fileInfosWithNameProposal = fileNameResolver.proposeNewNames(fileInfoWithoutNameProposal);

        return fileInfosWithNameProposal;
    }

    private List<FileInfo> analyzeAndPrepareFileInfos(List<URL> urls) {
        List<FileInfo> fileInfos = new ArrayList<>(urls.size());
        for (URL url : urls) {
            if (isValid(url.toString())) {
                FileInfo fileInfo = fileInfoExtractor.extractFileInfo(url);
                if (fileInfo != null) {
                    fileInfos.add(fileInfo);
                }
            }
        }
        return fileInfos;
    }

    public void renameSourceFiles() {
        for (FileInfo fileInfo : getFileInfos()) {

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

    public List<URL> getAllMediaFilesFromDirectory(String directory) {

        List<URL> result = new LinkedList<>();

        Iterator<Path> it = null;
        try {
            it = Files.list(Paths.get(directory)).iterator();
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

    public boolean isValid(String filePathOrUrl) {
        return supportedFileEndings.stream()
                .anyMatch(fileNameAnalyzer.getFileEndingUpperCase(filePathOrUrl.toUpperCase())::equalsIgnoreCase);
    }

}
