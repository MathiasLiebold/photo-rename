package de.liebold.imagerename.logic.bean;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FileInfo implements Comparable<FileInfo> {

	private Path originalPath;
	private String originalName;
	private String proposedName;

	private Date creationTime, lastModifiedTime, photoTime;

	public FileInfo(Path originalPath) {
		this.originalPath = originalPath;
		this.originalName = originalPath.toFile().getName();
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Date getPhotoTime() {
		return photoTime;
	}

	public void setPhotoTime(Date photoTime) {
		// No validation of null required, photoTime may be null if information
		// is not available.

		this.photoTime = photoTime;
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

	public String getOriginalName() {
		return originalName;
	}

	public String getProposedName() {
		return proposedName;
	}

	public void setProposedName(String proposedName) {
		this.proposedName = proposedName;
	}

	public void move() {
		File originalFile = originalPath.toFile();
		File targetDir = new File(originalFile.getParentFile().getAbsolutePath() + File.separatorChar + "target");
		File newFile = new File(targetDir.getAbsolutePath() + File.separatorChar + proposedName);

		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

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

		return this.getTime().compareTo(o.getTime());
	}

}
