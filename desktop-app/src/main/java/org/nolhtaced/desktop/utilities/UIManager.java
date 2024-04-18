package org.nolhtaced.desktop.utilities;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.nolhtaced.desktop.controllers.forms.FormController;
import org.nolhtaced.desktop.enumerators.EAppArea;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;

import java.io.IOException;
import java.util.HashMap;

public class UIManager {
    private static Class<? extends Application> applicationClass = null;
    private static Stage stageInstance = null;
    private static HashMap<EAppArea, String> SCREENS_LOCATION_MAP = new HashMap<>();

    static {
        SCREENS_LOCATION_MAP.put(EAppArea.LOGIN, "/areas/login.fxml");
        SCREENS_LOCATION_MAP.put(EAppArea.APPLICATION, "/areas/application.fxml");
    }

    private UIManager() {}

    public static void initialize(Class<? extends Application> appClass, Stage stage) {
        applicationClass = appClass;
        stageInstance = stage;
    }

    public static void setScreen(EAppArea area) throws UIManagerNotInitializedException, IOException {
        if (applicationClass == null || stageInstance == null) {
            throw new UIManagerNotInitializedException();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(SCREENS_LOCATION_MAP.get(area)));
        stageInstance.setScene(new Scene(fxmlLoader.load()));
        stageInstance.show();
    }

    // TODO handle UIManagerNotInitializedException
    public static Node getViewNode(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(file));
        return fxmlLoader.load();
    }

    // TODO handle UIManagerNotInitializedException
    public static <T> void openForm(String file, Callback<T, Void> onSuccess) throws IOException {
        Stage modalStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(file));
        Parent parent = fxmlLoader.load();
        FormController<T> controller = fxmlLoader.getController();
        controller.setFormStage(modalStage);
        controller.setOnSuccessCallback(onSuccess);
        modalStage.setOnCloseRequest(controller.onCloseRequest());
        modalStage.setScene(new Scene(parent));
        modalStage.setResizable(false);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.showAndWait();
    }

    // TODO handle UIManagerNotInitializedException
    public static <T> void openForm(String file, T data, Callback<T, Void> onSuccess) throws IOException {
        Stage modalStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(file));
        Parent parent = fxmlLoader.load();
        FormController<T> controller = fxmlLoader.getController();
        controller.setInitialData(data);
        controller.setFormStage(modalStage);
        controller.setOnSuccessCallback(onSuccess);
        modalStage.setOnCloseRequest(controller.onCloseRequest());
        modalStage.setScene(new Scene(parent));
        modalStage.setResizable(false);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.showAndWait();
    }
}
