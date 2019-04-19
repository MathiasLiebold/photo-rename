package de.liebold.photorename.logic.service;

import de.liebold.photorename.logic.bean.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// TODO combine FileHandler with FileResolver into FileResolver
@Service
public class FileHandler {

    @Autowired
    private FileAnalyzer fileAnalyzer;

    @Autowired
    private FileResolver fileResolver;

    @Autowired
    private FileNameResolver fileNameResolver;

    public List<FileInfo> getSourceFiles() {
        List<FileInfo> result = new ArrayList<>();

        for (URL url : fileResolver.getFromCurrentDirectory()) {
            FileInfo fileInfo = fileAnalyzer.analyze(url);
            if (fileInfo != null) {
                result.add(fileInfo);
            }
        }

        fileNameResolver.proposeNames(result);

        return fileNameResolver.getModified();
    }

}
