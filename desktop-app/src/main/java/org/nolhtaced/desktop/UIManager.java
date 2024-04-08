package org.nolhtaced.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.nolhtaced.desktop.controllers.forms.BaseFormController;
import org.nolhtaced.desktop.enumerators.EAppArea;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;

import java.io.IOException;
import java.util.HashMap;

public class UIManager {
    private static Class<? extends Application> applicationClass = null;
    private static Stage stageInstance = null;
    private static HashMap<EAppArea, String> SCREENS_LOCATION_MAP = new HashMap<>();

    static {
        SCREENS_LOCATION_MAP.put(EAppArea.LOGIN, "/screens/login/login.fxml");
        SCREENS_LOCATION_MAP.put(EAppArea.APPLICATION, "/screens/application/application.fxml");
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

    public static Node getViewNode(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(file));
        return fxmlLoader.load();
    }

    public static <T> void openForm(String file) throws IOException {
        Stage modalStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(file));
        Parent parent = fxmlLoader.load();
        BaseFormController<T> controller = fxmlLoader.getController();
        controller.setFormStage(modalStage);
        modalStage.setOnCloseRequest(controller.onCloseRequest());
        modalStage.setScene(new Scene(parent));
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.showAndWait();
    }

    public static <T> void openFormWithData(String file, T data) throws IOException {
        Stage modalStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(file));
        Parent parent = fxmlLoader.load();
        BaseFormController<T> controller = fxmlLoader.getController();
        controller.setInitialData(data);
        controller.setFormStage(modalStage);
        modalStage.setOnCloseRequest(controller.onCloseRequest());
        modalStage.setScene(new Scene(parent));
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.showAndWait();
    }
}
