package de.liebold.photorename.ui.javafx;

import de.liebold.photorename.ui.javafx.component.SceneCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@PropertySource("classpath:application.properties")
public class PhotoRenameJavaFxApplication extends Application {

    private String applicationName = "Photo rename"; // TODO set dynamically

    @Autowired
    private SceneCreator sceneCreator;

    public PhotoRenameJavaFxApplication() {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
            AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
            autowireCapableBeanFactory.autowireBean(this);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(sceneCreator.create());

        stage.setScene(scene);
        stage.setTitle(applicationName);
        stage.getIcons().add(new Image("/icon_500x500.png"));

        stage.show();
    }

}
