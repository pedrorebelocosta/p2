package org.nolhtaced.desktop;

import javafx.application.Application;
import javafx.stage.Stage;
import org.nolhtaced.desktop.enumerators.EAppArea;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;

import java.io.IOException;

public class Main extends Application {
    private final Integer SCENE_MIN_WIDTH = 800;
    private final Integer SCENE_MIN_HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, UIManagerNotInitializedException {
        primaryStage.setMinWidth(SCENE_MIN_WIDTH);
        primaryStage.setMinHeight(SCENE_MIN_HEIGHT);
        UIManager.initialize(this.getClass(), primaryStage);
        UIManager.setScreen(EAppArea.LOGIN);
    }
}