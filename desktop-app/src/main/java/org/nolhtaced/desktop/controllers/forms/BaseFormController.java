package org.nolhtaced.desktop.controllers.forms;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class BaseFormController<T> {
    protected T data;
    protected Stage stage;
    protected boolean isEditing;

    protected abstract void onDataReceived(T data);

    public abstract EventHandler<WindowEvent> onCloseRequest();

    protected abstract T getFormData();

    public T getData() {
        return this.data;
    }

    public void setInitialData(T data) {
        this.data = data;
        this.isEditing = true;
        this.onDataReceived(data);
    }

    public void closeForm() {
        stage.close();
    }

    public void setFormStage(Stage stage) {
        this.stage = stage;
    }
}
