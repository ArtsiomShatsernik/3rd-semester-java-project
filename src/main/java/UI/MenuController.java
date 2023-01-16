package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuController extends MyJavaFXApp {
    @FXML
    protected Button BuildFileButton;

    @FXML
    protected void onBuildFileClicked() {
        showFileBuildWindow();
    }
    @FXML
    protected void onComputeButtonClick() {
        showComputeWindow();
    }
}