package org.nolhtaced.desktop.controllers.forms;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.synedra.validatorfx.Validator;

import java.util.Optional;

public abstract class FormController<T> {
    protected T data;
    protected Stage stage;
    public Validator validator = new Validator();
    protected boolean isEditing = false;
    // TODO check if there's a more "OO" way of doing this
    protected Callback<T, Void> successCallback;

    protected abstract void onDataReceived(T data);

    protected abstract T getFormData();

    public EventHandler<WindowEvent> onCloseRequest() {
        return windowEvent -> {
            if (isEditing && hasChanges()) {
                Optional<ButtonType> buttonType = showCancelConfirmation();

                if (buttonType.get() == ButtonType.CANCEL) {
                    windowEvent.consume();
                }
            }
        };
    }

    public void setInitialData(T data) {
        this.data = data;
        this.isEditing = true;
        this.onDataReceived(data);
    }

    public void requestFormClose(boolean askForConfirmation) {
        if (askForConfirmation && isEditing && hasChanges()) {
            Optional<ButtonType> buttonType = showCancelConfirmation();

            if (buttonType.get() == ButtonType.CANCEL) {
                return;
            }
        }

        stage.close();
    }

    public void setOnSuccessCallback(Callback<T, Void> successCallback) {
        this.successCallback = successCallback;
    }

    public boolean hasChanges() {
        // 🔨
        if (validator.containsErrors()) return true;
        return !data.equals(getFormData());
    }

    public Optional<ButtonType> showCancelConfirmation() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Are you sure you want to cancel?");
        confirmationAlert.setContentText("There are unsaved changes.");
        return confirmationAlert.showAndWait();
    }

    public void setFormStage(Stage stage) {
        this.stage = stage;
    }
}
