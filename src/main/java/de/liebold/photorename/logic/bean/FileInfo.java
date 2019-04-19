package de.liebold.photorename.logic.bean;

import lombok.Data;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
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

    public void move() {
        File originalFile = originalPath.toFile();

        // TODO random name for target dir

        File targetDir = new File(originalFile.getParentFile().getAbsolutePath() + File.separatorChar + "target");
        File newFile = new File(targetDir.getAbsolutePath() + File.separatorChar + proposedName);

        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        // TODO move back from target dir

        originalFile.renameTo(newFile);
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

}
