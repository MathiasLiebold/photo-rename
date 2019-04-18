package de.liebold.imagerename.ui.component;

import java.util.List;

import de.liebold.imagerename.logic.bean.FileInfo;
import de.liebold.imagerename.logic.event.StartEventHandler;
import de.liebold.imagerename.logic.event.StartEventHandler.Status;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartButton implements CustomUI {

	private VBox root = new VBox();

	private Button button = new Button("Start / Run");
	private Label statusLabel = new Label(Status.READY.toString());

	public StartButton(List<FileInfo> sourceFiles) {
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(button, statusLabel);

		button.setOnAction(new StartEventHandler(sourceFiles, button, statusLabel));
	}

	@Override
	public Node draw() {
		return root;
	}

}
