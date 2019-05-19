package de.liebold.photorename.logic.filename;

import java.util.Locale;
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

    /**
     * Gets the suffix / ending of the file without the dot. An input of
     * "AnyFile.jpg" will return "jpg".
     */
    public String getFileEndingLowerCase(String fileName) {
        return getFileEndingUpperCase(fileName).toLowerCase(Locale.US);
    }

    /**
     * Gets the suffix / ending of the file without the dot. An input of
     * "AnyFile.JPG" will return "JPG".
     */
    public String getFileEndingUpperCase(String fileName) {
        Optional.of(fileName);

        return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()).toUpperCase(Locale.US);
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
