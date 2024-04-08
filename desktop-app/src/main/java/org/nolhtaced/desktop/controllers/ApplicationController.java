package org.nolhtaced.desktop.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.nolhtaced.desktop.UIManager;
import org.nolhtaced.desktop.enumerators.EAppView;

import java.io.IOException;
import java.util.HashMap;

public class ApplicationController {
    @FXML
    public AnchorPane contentPane;
    private static final HashMap<EAppView, String> VIEWS_LOCATION_MAP = new HashMap<>();

    static {
        VIEWS_LOCATION_MAP.put(EAppView.USER_MANAGEMENT, "/views/user-view.fxml");
    }

    @FXML
    public void initialize() {
        changeView(EAppView.USER_MANAGEMENT);
    }

    private void changeView(EAppView view) {
        try {
            if (!contentPane.getChildren().isEmpty()) {
                contentPane.getChildren().removeAll();
            }

            Node node = UIManager.getViewNode(VIEWS_LOCATION_MAP.get(view));
            contentPane.getChildren().add(node);

            // attach the projected node to the bounds of layout anchorpane
            contentPane.setTopAnchor(node, new Double(0));
            contentPane.setRightAnchor(node, new Double(0));
            contentPane.setBottomAnchor(node, new Double(0));
            contentPane.setLeftAnchor(node, new Double(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
