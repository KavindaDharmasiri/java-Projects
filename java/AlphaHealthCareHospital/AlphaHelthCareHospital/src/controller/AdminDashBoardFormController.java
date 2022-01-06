package controller;

import Tm.docPatApoinmentDetail;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashBoardFormController implements Initializable {
    public ImageView Exit;
    public Label Menu;
    public Label MenuClose;
    public AnchorPane slider;
    public AnchorPane adminDashBoard;
    public AnchorPane adminAnchrPan;
    public Label txtAdminName;
    public Label txtName;
    public Circle circle;
    public PieChart pieChart;
    private String temp;
    private double dailyincome = 0;
    private double monthlyincome = 0;
    private double yearlyincome = 0;


    public void min(javafx.scene.input.MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        date();

        String day = temp.substring(0, 10);
        String month = temp.substring(3, 10);
        String year = temp.substring(6, 10);

        try {
            setDayValue(day);
            setMonthValue(month);
            setYearValue(year);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        initPieChart();

        Image img = new Image("/sample/Images/download (1).jpeg");
        circle.setFill(new ImagePattern(img));

        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        adminDashBoard.setOnMouseDragged(event -> event.setDragDetect(true));

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
    }

    private void setYearValue(String year) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            String yearDay = doc.get(i).getDate().substring(6, 10);
            if (yearDay.equals(year)) {
                yearlyincome += doc.get(i).getPrice();
            }
        }
    }

    private void setMonthValue(String month) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            String monthDay = doc.get(i).getDate().substring(3, 10);
            if (monthDay.equals(month)) {
                monthlyincome += doc.get(i).getPrice();
            }
        }
    }

    private void setDayValue(String day) throws SQLException, ClassNotFoundException {
        List<docPatApoinmentDetail> doc = hospitalDetailController.getFinalFromSuccess("Success");
        for (int i = 0; i < doc.size(); i++) {
            if (doc.get(i).getDate().equals(day)) {
                dailyincome += doc.get(i).getPrice();
            }
        }
    }

    private void initPieChart() {
        ObservableList<PieChart.Data> pieChartdate = FXCollections.observableArrayList(
                new PieChart.Data("Daily", dailyincome),
                new PieChart.Data("Monthly", monthlyincome),
                new PieChart.Data("Yearly", yearlyincome)
        );
        pieChart.setData(pieChartdate);
    }


    private void date() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        temp = formatter.format(date);
    }


    public void logOut(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/adminDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) adminDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();

        loginFormController.prime.show();

        notification();
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Admin Log Out Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(4));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void medicineLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/medicalForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void equpmentLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/equpmentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void clothLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/clothForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void drinkLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/drinkForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void reportLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/reportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void homeLoad(ActionEvent actionEvent) throws IOException {
        AdminHomeFormController.name = txtName.getText();
        URL resource = getClass().getResource("../view/adminHomeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void doctorLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/doctorForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void nurseLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/nurseForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void roomLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/roomForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void labLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/labForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }

    public void parkingLoad(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/parkForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminAnchrPan.getChildren().clear();
        adminAnchrPan.getChildren().add(load);
    }
}
