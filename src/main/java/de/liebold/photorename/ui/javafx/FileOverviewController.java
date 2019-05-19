package de.liebold.photorename.ui.javafx;

import de.liebold.photorename.logic.fileinfo.FileInfo;
import de.liebold.photorename.logic.FileService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileOverviewController {

    @Autowired
    private FileService fileService;

    @Autowired
    private StartRenamingEventHandler startRenamingEventHandler;

    @Value("${application.description}")
    private String applicationDescription;

    @Value("${photo-rename.instructions}")
    private String photoRenameInstructions;

    @FXML
    private Label descriptionLabel, instructionsLabel, directoryLabel, fileEndingsLabel;

    @FXML
    private TableView<FileInfo> fileTableView;

    @FXML
    private Button startButton;

    // TODO document how this method is called
    public void initialize() {
        initializeLabels();
        initializeFileOverview();
        initializeControls();
    }

    private void initializeLabels() {
        descriptionLabel.setText(applicationDescription);
        instructionsLabel.setText(photoRenameInstructions);
        directoryLabel.setText(fileService.getCurrentDirectoryPath().toUri().toString());

        String fileEndingsCommaSeparated = String.join(", ", fileService.getSupportedFileEndings());
        fileEndingsLabel.setText(fileEndingsCommaSeparated);
    }

    private void initializeFileOverview() {
        ObservableList<FileInfo> observableList = FXCollections.observableArrayList(fileService.getFileInfos());
        fileTableView.setItems(observableList);
    }

    private void initializeControls() {
        startButton.setOnAction(startRenamingEventHandler);
    }

}
