package de.liebold.imagerename.logic.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;

import org.springframework.stereotype.Service;

@Service("fileNameAnalyzer")
public class FileNameAnalyzer {

	// TODO create RAW folder and move raw files

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

	public String getLowerCaseFileSuffix(String fileName) {
		Optional.of(fileName);

		Matcher matcher = getMatcher(FILE_NAME_GENERIC_PATTERN, fileName);

		if (matcher == null || !matcher.matches()) {
			return "";
		}

		return matcher.group(1).toLowerCase();
	}

	/**
	 * Gets the suffix / ending of the file without the dot. An input of
	 * "AnyFile.JPG" will return "JPG".
	 *
	 * @param file
	 *            The file to analyze.
	 * @return The suffix / ending of the file.
	 */
	public String getFileEnding(String fileName) {
		Optional.of(fileName);

		return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()).toLowerCase();
	}

	public void asdf(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		String a1 = null;
		String a2 = "asdf";
		if (a1.equals(a2)) {
			// fail
		} else if (c == 4) {

		}

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
