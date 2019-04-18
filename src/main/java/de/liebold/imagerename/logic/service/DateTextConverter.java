package de.liebold.imagerename.logic.service;

import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class DateTextConverter {

	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss", Locale.GERMANY);

	private DateTextConverter() {
		dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public String getText(Date date) {
		if (date == null) {
			return null;
		}

		return dateTimeFormat.format(date);
	}

	public Date getDate(String text) {
		if (text == null) {
			return null;
		}

		try {
			return dateTimeFormat.parse(text);
		} catch (ParseException e) {
			return null;
		}
	}

	public Date getDate(FileTime fileTime) {
		if (fileTime == null) {
			return null;
		}

		return new Date(fileTime.toMillis());
	}

}
