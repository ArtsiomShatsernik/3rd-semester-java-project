package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuController extends MyJavaFXApp {
    public VBox mainMenu;
    @FXML
    public Button ComputeButton;
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