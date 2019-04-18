package de.liebold.photorename.ui.javafx;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.liebold.photorename.logic.bean.FileInfo;
import de.liebold.photorename.logic.service.FileAnalyzer;
import de.liebold.photorename.logic.service.FileNameResolver;
import de.liebold.photorename.logic.service.FileResolver;
import de.liebold.photorename.ui.javafx.component.FileTableView;
import de.liebold.photorename.ui.javafx.component.StartButton;
import de.liebold.photorename.ui.javafx.component.SupportedFileInfo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//TODO Video time is not correct -2 h

public class Main extends Application {

	@Autowired
	private FileAnalyzer fileAnalyzer;

	@Autowired
	private FileResolver fileResolver;

	@Autowired
	private FileNameResolver fileNameResolver;

	public static void main(String[] args) {
		launch(args);
	}

	public Main() {
		ConfigurableApplicationContext context = null;

		try {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
			AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
			acbFactory.autowireBean(this);
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
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

			List<FileInfo> sourceFiles = getSourceFiles();

			leftSide.getChildren().add(new FileTableView(sourceFiles).draw());
			leftSide.getChildren().add(new SupportedFileInfo().draw());

			rightSide.getChildren().add(new StartButton(sourceFiles).draw());
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));

			root.getChildren().add(new Label(errors.toString()));
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Image Rename");
		stage.getIcons().add(new Image("/icon_500x500.png"));
		stage.show();
	}

	private List<FileInfo> getSourceFiles() {
		List<FileInfo> result = new ArrayList<>();

		for (URL url : fileResolver.getFromCurrentDirectory()) {
			FileInfo fileInfo = fileAnalyzer.analyze(url);
			if (fileInfo != null) {
				result.add(fileInfo);
			}
		}

		fileNameResolver.proposeNames(result);

		return fileNameResolver.getModified();
	}

}
