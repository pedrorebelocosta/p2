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
import org.nolhtaced.desktop.enumerators.EAppForm;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;

import java.io.IOException;
import java.util.HashMap;

public class UIManager {
    private static Class<? extends Application> applicationClass = null;
    private static Stage stageInstance = null;
    private static final HashMap<EAppArea, String> SCREENS_LOCATION_MAP = new HashMap<>();
    private static final HashMap<EAppForm, String> FORMS_LOCATION_MAP = new HashMap<>();

    static {
        SCREENS_LOCATION_MAP.put(EAppArea.LOGIN, "/areas/login.fxml");
        SCREENS_LOCATION_MAP.put(EAppArea.APPLICATION, "/areas/application.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.APPOINTMENT_FORM, "/forms/appointment-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.CATEGORY_FORM, "/forms/category-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.CUSTOMER_FORM, "/forms/customer-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.CUSTOMER_BICYCLES_FORM, "/forms/bicycle-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.EMPLOYEE_FORM, "/forms/employee-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.PRODUCT_FORM, "/forms/product-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.REPAIR_FORM, "/forms/repair-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.SALES_FORM, "/forms/sales-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.SERVICE_FORM, "/forms/service-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.TRANSACTION_DETAIL_FORM, "/forms/transaction-detail-view.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.TRANSACTION_STATE_FORM, "/forms/transaction-state-form.fxml");
        FORMS_LOCATION_MAP.put(EAppForm.USER_FORM, "/forms/user-form.fxml");
    }

    private UIManager() {}

    public static void initialize(Class<? extends Application> appClass, Stage stage) {
        applicationClass = appClass;
        stageInstance = stage;
    }

    public static void setScreen(EAppArea area) throws UIManagerNotInitializedException, IOException {
        checkInitialization();
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(SCREENS_LOCATION_MAP.get(area)));
        stageInstance.setScene(new Scene(fxmlLoader.load()));
        stageInstance.show();
    }

    public static Node getViewNode(String file) throws IOException, UIManagerNotInitializedException {
        checkInitialization();
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(file));
        return fxmlLoader.load();
    }

    private static void checkInitialization() throws UIManagerNotInitializedException {
        if (applicationClass == null || stageInstance == null) {
            throw new UIManagerNotInitializedException();
        }
    }

    public static <T> void openForm(EAppForm form, Callback<T, Void> onSuccess) throws IOException, UIManagerNotInitializedException {
        checkInitialization();
        Stage modalStage = new Stage();
        String formPath = FORMS_LOCATION_MAP.get(form);
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(formPath));
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

    public static <T> void openForm(EAppForm form, T data, Callback<T, Void> onSuccess) throws IOException, UIManagerNotInitializedException {
        checkInitialization();
        Stage modalStage = new Stage();
        String formPath = FORMS_LOCATION_MAP.get(form);
        FXMLLoader fxmlLoader = new FXMLLoader(applicationClass.getResource(formPath));
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
