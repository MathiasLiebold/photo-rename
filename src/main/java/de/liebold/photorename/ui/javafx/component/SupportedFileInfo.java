package de.liebold.photorename.ui.javafx.component;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import de.liebold.photorename.logic.service.FileResolver;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SupportedFileInfo implements CustomUI {

	private VBox root = new VBox();

	public SupportedFileInfo() {
		root.getChildren().add(getSupportedFilesLabel("Photos", FileResolver.IMAGE_FILE_TYPES));
		root.getChildren().add(getSupportedFilesLabel("Videos", FileResolver.VIDEO_FILE_TYPES));
	}

	public Label getSupportedFilesLabel(String mediaCategory, List<String> fileTypes) {
		Optional.of(mediaCategory);
		Optional.of(fileTypes);

		Label label = new Label(getSupportedFilesText(mediaCategory, fileTypes));
		label.setMaxWidth(900);
		label.setWrapText(true);
		return label;
	}

	public String getSupportedFilesText(String mediaCategory, List<String> fileTypes) {
		Optional.of(mediaCategory);
		Optional.of(fileTypes);

		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append(mediaCategory + ": ");

		Iterator<String> it = fileTypes.iterator();
		while (it.hasNext()) {
			String fileType = it.next();
			resultBuilder.append(fileType);
			if (it.hasNext()) {
				resultBuilder.append(", ");
			}
		}

		return resultBuilder.toString();
	}

	@Override
	public Node draw() {
		return root;
	}

}
