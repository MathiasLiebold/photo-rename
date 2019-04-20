package de.liebold.photorename.logic.filename;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.liebold.photorename.logic.datetext.DateTextConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.liebold.photorename.logic.fileinfo.FileInfo;

/**
 * Resolves file names, based on the file information, e.g. dates and previous
 * name.
 *
 * @author Mathias
 */
@Service
public class FileNameResolver {

    @Autowired
    private DateTextConverter dateTextConverter;

    @Autowired
    private FileNameAnalyzer fileNameAnalyzer;

    private Map<String, FileInfo> proposedNames = new HashMap<>();

    public void proposeNames(List<FileInfo> fileInfos) {

        List<FileInfo> sortedFileInfos = fileInfos;
        Collections.sort(sortedFileInfos);

        for (FileInfo fileInfo : fileInfos) {
            String proposedName = getProposedName(fileInfo, Optional.empty());
            // TODO raw und jpg gleich benennen
            proposedNames.put(proposedName, fileInfo);
        }

        for (String m : proposedNames.keySet()) {
            FileInfo f = proposedNames.get(m);
            f.setProposedName(m);
        }
    }

    public List<FileInfo> getModified() {
        List<FileInfo> result = new ArrayList<>();

        result.addAll(proposedNames.values());

        return result;
    }

    String getProposedName(FileInfo fileInfo, Optional<Integer> counter) {
        if (fileInfo == null) {
            throw new IllegalArgumentException("fileInfo may not be null");
        }

        StringBuilder result = new StringBuilder();

        result.append(dateTextConverter.getText(fileInfo.getTime()));
        if (counter.isPresent()) {
            result.append(" ");
            result.append(fillZeros(counter.get()));
        }
        result.append(" (");
        result.append(fileNameAnalyzer.getDescriptionInBrackets(fileInfo.getOriginalName()));
        result.append(")");
        result.append(".");
        result.append(fileNameAnalyzer.getFileEndingUpperCase(fileInfo.getOriginalName()));

        String proposedName = result.toString();

        if (proposedNames.containsKey(proposedName)) {
            Integer currentCount = counter.isPresent() ? counter.get() + 1 : 1;
            return getProposedName(fileInfo, Optional.of(currentCount));
        }

        return proposedName;
    }

    private String fillZeros(Integer counter) {
        if (counter.intValue() <= 9) {
            return "00" + counter;
        }
        if (counter.intValue() <= 99) {
            return "0" + counter;
        }
        return "" + counter;
    }

}
