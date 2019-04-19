package de.liebold.photorename.logic.service;

import de.liebold.photorename.logic.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    private static final String DUMMY_FILE_NOT_TO_BE_RENAMED = "1x1px.jpg";

    @Autowired
    private FileResolver fileResolver;

    @Autowired
    private FileHandler fileHandler;

    public List<String> getSupportedFileEndings() {
        return FileResolver.IMAGE_FILE_TYPES;
    }

    public Path getCurrentDirectoryPath() {
        return Paths.get(".");
    }

    public List<FileInfo> getSourceFiles() {
        return fileHandler.getSourceFiles();
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

}
