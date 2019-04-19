package de.liebold.photorename.ui.javafx;

import de.liebold.photorename.logic.FileInfo;
import de.liebold.photorename.logic.service.FileService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartEventHandlerService implements EventHandler<ActionEvent> {

    @Autowired
    private FileService fileService;

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        button.setDisable(true);

        fileService.renameSourceFiles();

        button.setText("Done");
    }
}
