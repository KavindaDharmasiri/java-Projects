package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CashierHomeController {
    public static String name;

    public AnchorPane cashierHome;
    public Label txtRecepName;

    public void initialize() {
        txtRecepName.setText(name);
    }
}
