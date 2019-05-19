package de.liebold.photorename.logic;

// TODO implement spring exception handler to display any type of error on UI
public class FileAnalysisException extends RuntimeException {

    public FileAnalysisException(Throwable throwable) {
        super(throwable);
    }

}
