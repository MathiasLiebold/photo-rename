package de.liebold.photorename.logic.fileinfo;

import lombok.*;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfo implements Comparable<FileInfo> {

    private Path originalPath;

    private String originalName;

    private String proposedName;

    private Date creationTime;

    private Date lastModifiedTime;

    private Date photoTime;

    public FileInfo(Path originalPath) {
        this.originalPath = originalPath;
        originalName = originalPath.toFile().getName();
    }

    public Date getTime() {
        if (photoTime != null) {
            return photoTime;
        }

        List<Date> dates = new ArrayList<>();
        dates.add(creationTime);
        dates.add(lastModifiedTime);
        return Collections.min(dates);
    }

    @Override
    public String toString() {
        return originalName;
    }

    @Override
    public int compareTo(FileInfo o) {
        if (o == null) {
            return 1;
        }

        return getTime().compareTo(o.getTime());
    }

    /**
     * Overriding Lombok Builder defined with @Builder to add more utility methods.
     */
    public static class FileInfoBuilder {

        private SimpleDateFormat dateWithTimeZone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz", Locale.US);

        /**
         * @param dateTimeText matching pattern "yyyy-MM-dd HH:mm:ss zzz"
         */
        public FileInfoBuilder creationTimeFromText(String dateTimeText) throws ParseException {
            creationTime = dateWithTimeZone.parse(dateTimeText);
            return this;
        }

        /**
         * @param dateTimeText matching pattern "yyyy-MM-dd HH:mm:ss zzz"
         */
        public FileInfoBuilder lastModifiedTimeFromText(String dateTimeText) throws ParseException {
            lastModifiedTime = dateWithTimeZone.parse(dateTimeText);
            return this;
        }

        /**
         * @param dateTimeText matching pattern "yyyy-MM-dd HH:mm:ss zzz"
         */
        public FileInfoBuilder photoTimeFromText(String dateTimeText) throws ParseException {
            photoTime = dateWithTimeZone.parse(dateTimeText);
            return this;
        }

    }

}
