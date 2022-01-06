package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DocHomeFormController {
    public static String name;
    public Label txtDocName;
    public AnchorPane docHome;

    public void initialize() {
        txtDocName.setText(name);
    }
}
