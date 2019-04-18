package de.liebold.imagerename.logic.event;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.liebold.imagerename.logic.bean.FileInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StartEventHandler implements EventHandler<ActionEvent> {

	public enum Status {
		READY, RUNNING, DONE;
	}

	private List<FileInfo> sourceFiles;
	private Button button;
	private Label statusLabel;

	public StartEventHandler(List<FileInfo> sourceFiles, Button button, Label statusLabel) {
		this.sourceFiles = sourceFiles;
		this.button = button;
		this.statusLabel = statusLabel;
	}

	@Override
	public void handle(ActionEvent event) {
		button.setDisable(true);
		statusLabel.setText(Status.RUNNING.toString());

		for (FileInfo fileInfo : sourceFiles) {
			fileInfo.move();
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {

		}

		statusLabel.setText(Status.DONE.toString());
	}

}
