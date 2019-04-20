package de.liebold.photorename.logic.filename;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class FileNameAnalyzer {

    // TODO create RAW folder and move raw files
    // TODO support whatsapp filenames

    private static final Pattern FILE_NAME_GENERIC_PATTERN = Pattern.compile(".*\\.(.*?)");

    public String getDescriptionInBrackets(String fileName) {
        Optional.of(fileName);

        int begin = fileName.indexOf('(');
        int end = fileName.lastIndexOf(')');

        if (begin > 0 && end > 0) {
            return fileName.substring(begin + 1, end);
        }

        return "";
    }

    public String getUpperCaseFileSuffix(String fileName) {
        Optional.of(fileName);

        Matcher matcher = getMatcher(FILE_NAME_GENERIC_PATTERN, fileName);

        if (matcher == null || !matcher.matches()) {
            return "";
        }

        return matcher.group(1).toUpperCase();
    }

    /**
     * Gets the suffix / ending of the file without the dot. An input of
     * "AnyFile.JPG" will return "JPG".
     */
    public String getFileEndingUpperCase(String fileName) {
        Optional.of(fileName);

        return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()).toUpperCase();
    }

    private Matcher getMatcher(Pattern pattern, String fileName) {
        if (fileName == null) {
            return null;
        }

        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.matches()) {
            return null;
        }

        return matcher;
    }

}
