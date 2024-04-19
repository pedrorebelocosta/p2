package org.nolhtaced.desktop.controllers.areas;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.nolhtaced.core.enumerators.UserRoleEnum;
import org.nolhtaced.desktop.UserSession;
import org.nolhtaced.desktop.enumerators.EAppArea;
import org.nolhtaced.desktop.exceptions.UIManagerNotInitializedException;
import org.nolhtaced.desktop.utilities.UIManager;
import org.nolhtaced.desktop.enumerators.EAppView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ApplicationController {
    @FXML
    public AnchorPane contentPane;
    @FXML
    public VBox menuItemsVBox;
    @FXML
    public Label logoutBtn;
    @FXML
    public Label logoutButtonImg;

    private static final HashMap<EAppView, String> VIEWS_LOCATION_MAP = new LinkedHashMap<>();

    static {
        VIEWS_LOCATION_MAP.put(EAppView.USER_MANAGEMENT, "/views/user-view.fxml");
        VIEWS_LOCATION_MAP.put(EAppView.CUSTOMER_MANAGEMENT, "/views/customer-view.fxml");
        VIEWS_LOCATION_MAP.put(EAppView.PRODUCT_MANAGEMENT, "/views/product-view.fxml");
        VIEWS_LOCATION_MAP.put(EAppView.SERVICE_MANAGEMENT, "/views/service-view.fxml");
        VIEWS_LOCATION_MAP.put(EAppView.CATEGORY_MANAGEMENT, "/views/category-view.fxml");
        VIEWS_LOCATION_MAP.put(EAppView.SALES_MANAGEMENT, "/views/sales-view.fxml");
        VIEWS_LOCATION_MAP.put(EAppView.ORDERS_MANAGEMENT, "/views/order-view.fxml");
        VIEWS_LOCATION_MAP.put(EAppView.REPAIRS_MANAGEMENT, "/views/repair-view.fxml");
    }

    @FXML
    public void initialize() {
        logoutBtn.setOnMouseClicked(e -> logout());
        FontIcon icon = new FontIcon(FontAwesomeSolid.SIGN_OUT_ALT);
        icon.setIconSize(32);
        logoutButtonImg.setGraphic(icon);
        logoutButtonImg.setOnMouseClicked(e -> logout());
        changeView(EAppView.SALES_MANAGEMENT);
        menuItemsVBox.getChildren().addAll(buildNavigationOptions());
    }

    private void logout() {
        try {
            UserSession.getSession().setCurrentUser(null);
            UIManager.setScreen(EAppArea.LOGIN);
        } catch (UIManagerNotInitializedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Node> buildNavigationOptions() {
        List<Node> options = new ArrayList<>();
        UserRoleEnum role = UserSession.getSession().getCurrentUser().get().getRole();
        List<EAppView> availableViews = new ArrayList<>();

        if (role == UserRoleEnum.ADMINISTRATOR || role == UserRoleEnum.MANAGER) {
            availableViews.addAll(VIEWS_LOCATION_MAP.keySet());
        }

        if (role == UserRoleEnum.SALES_REPRESENTATIVE) {
            availableViews.add(EAppView.CUSTOMER_MANAGEMENT);
            availableViews.add(EAppView.SALES_MANAGEMENT);
            availableViews.add(EAppView.ORDERS_MANAGEMENT);
            availableViews.add(EAppView.REPAIRS_MANAGEMENT);
        }

        if (role == UserRoleEnum.MECHANIC) {
            availableViews.add(EAppView.CUSTOMER_MANAGEMENT);
            availableViews.add(EAppView.REPAIRS_MANAGEMENT);
        }

        availableViews.forEach(key -> {
            Label label = new Label();
            label.setCursor(Cursor.HAND);
            label.setText(key.label);
            label.setOnMouseClicked(mouseEvent -> changeView(key));
            options.add(label);
        });

        return options;
    }

    private void changeView(EAppView view) {
        try {
            if (!contentPane.getChildren().isEmpty()){
                contentPane.getChildren().clear();
            }

            Node node = UIManager.getViewNode(VIEWS_LOCATION_MAP.get(view));
            contentPane.getChildren().add(node);

            // attach the projected node to the bounds of layout anchorpane
            AnchorPane.setTopAnchor(node, (double) 0);
            AnchorPane.setRightAnchor(node, (double) 0);
            AnchorPane.setBottomAnchor(node, (double) 0);
            AnchorPane.setLeftAnchor(node, (double) 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UIManagerNotInitializedException e) {
            throw new RuntimeException(e);
        }
    }
}
