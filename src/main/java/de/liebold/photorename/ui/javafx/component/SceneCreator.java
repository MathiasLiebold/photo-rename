package de.liebold.photorename.ui.javafx.component;

import de.liebold.photorename.logic.bean.FileInfo;
import de.liebold.photorename.logic.service.FileHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Component
public class SceneCreator {

    @Autowired
    private FileHandler fileHandler;

    public Parent create() {
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);

        VBox leftSide = new VBox();
        leftSide.setPadding(new Insets(10));
        leftSide.setSpacing(10);

        VBox rightSide = new VBox();
        rightSide.setMaxWidth(300);
        rightSide.setPadding(new Insets(10));
        rightSide.setSpacing(10);
        rightSide.setAlignment(Pos.CENTER);

        try {
            root.getChildren().add(leftSide);
            root.getChildren().add(rightSide);

            List<FileInfo> sourceFiles = fileHandler.getSourceFiles();

            leftSide.getChildren().add(new FileTableView(sourceFiles).draw());
            leftSide.getChildren().add(new SupportedFileInfo().draw());

            rightSide.getChildren().add(new StartButton(sourceFiles).draw());
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));

            root.getChildren().add(new Label(errors.toString()));
        }

        return root;
    }

}
