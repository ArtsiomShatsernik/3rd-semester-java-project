package UI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MyJavaFXApp extends Application {
    private Stage primaryStage;
    private Stage fileBuildStage;
    private Stage computeStage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showPrimaryStage();
    }

    protected void showPrimaryStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyJavaFXApp.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            InputStream iconStream = getClass().getResourceAsStream("/images/cat.png");
            Image image = new Image(iconStream);
            primaryStage.getIcons().add(image);
            primaryStage.setTitle("My project");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(alertText);
        alert.setTitle("Alert");
        alert.show();
    }
    protected void showFileBuildWindow() {
        if (fileBuildStage == null || !fileBuildStage.isShowing()) {
            try {
                fileBuildStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(MyJavaFXApp.class.getResource("FileBuild.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                InputStream iconStream = getClass().getResourceAsStream("/images/sad_cat.png");
                Image image = new Image(iconStream);
                fileBuildStage.getIcons().add(image);
                fileBuildStage.setTitle("File Build");
                fileBuildStage.initOwner(primaryStage);
                fileBuildStage.initModality(Modality.WINDOW_MODAL);
                fileBuildStage.setScene(scene);
                fileBuildStage.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("File Build window is already opened.");
        }
    }
    protected void showComputeWindow() {
        if (computeStage == null || !computeStage.isShowing()) {
            try {
                computeStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(MyJavaFXApp.class.getResource("Compute.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                InputStream iconStream = getClass().getResourceAsStream("/images/happy_cat.jpg");
                Image image = new Image(iconStream);
                computeStage.getIcons().add(image);
                computeStage.setTitle("Compute");
                computeStage.initOwner(primaryStage);
                computeStage.initModality(Modality.WINDOW_MODAL);
                computeStage.setScene(scene);
                computeStage.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Compute window is already opened.");
        }
    }
    public static void launchApp() {
        Application.launch();
    }
}