package de.liebold.photorename;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PhotoRenameJavaFxApplication extends Application {

    private static final double SCREEN_PADDING = 100;

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    private String applicationName;

    /**
     * Method hook, required for building an executable jar.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PhotoRenameJavaFxApplication.class);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));

        applicationName = context.getEnvironment().getProperty("application.name");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/main.fxml"));
        loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double width = visualBounds.getWidth() - SCREEN_PADDING;
        double height = visualBounds.getHeight() - SCREEN_PADDING;

        Scene scene = new Scene(rootNode, width, height);
        scene.getStylesheets().add("/ui/css/" + context.getEnvironment().getProperty("photo-rename.ui.theme"));
        primaryStage.setScene(scene);

        primaryStage.centerOnScreen();
        primaryStage.setTitle(applicationName);
        primaryStage.getIcons().add(new Image("/ui/img/icon_500x500.png"));
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

}
