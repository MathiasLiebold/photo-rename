package de.liebold.photorename.logic.datetext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class DateTextConverter {

    @Value("${photo-rename.dateTime.pattern}")
    private String dateTimePattern;

    @Value("${photo-rename.dateTime.locale.language}")
    private String dateTimeLocaleLanguage;

    @Value("${photo-rename.dateTime.locale.country}")
    private String dateTimeLocaleCountry;

    // Initialized in PostConstruct
    private SimpleDateFormat dateTimeFormat;

    @PostConstruct
    private void init() {
        Locale locale = new Locale(dateTimeLocaleLanguage, dateTimeLocaleCountry);
        dateTimeFormat = new SimpleDateFormat(dateTimePattern, locale);
        //        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public String getText(Date date) {
        if (date == null) {
            return null;
        }

        return dateTimeFormat.format(date);
    }

    public Date getDate(FileTime fileTime) {
        if (fileTime == null) {
            return null;
        }

        return new Date(fileTime.toMillis());
    }

}
