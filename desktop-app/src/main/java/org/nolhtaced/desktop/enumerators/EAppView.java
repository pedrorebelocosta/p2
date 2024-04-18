package org.nolhtaced.desktop.enumerators;

public enum EAppView {
    USER_MANAGEMENT("Users"),
    CUSTOMER_MANAGEMENT("Customers"),
    PRODUCT_MANAGEMENT("Products"),
    SERVICE_MANAGEMENT("Services"),
    CATEGORY_MANAGEMENT("Categories"),
    SALES_MANAGEMENT("Sales"),
    ORDERS_MANAGEMENT("Orders"),
    REPAIRS_MANAGEMENT("Repairs");

    public final String label;

    EAppView(String label) {
        this.label = label;
    }
}
