package UI;

import FileActions.FileParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.main.MathExpressions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ComputeController {
    @FXML
    ChoiceBox<String> InputType;
    @FXML
    TextArea InputValue;
    @FXML
    TextArea OutputValue;
    @FXML
    Text ErrorWindow;
    @FXML
    Button ComputeButton;
    @FXML
    PasswordField SecretKey;

    @FXML
    protected void initialize() {
        InputType.getItems().add("Enter data");
        InputType.getItems().add("Enter filePath");
        InputType.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onComputeButtonClicked() {
        String fileName;
        try {
            String checked = checkAll();
            if (checked.equals("true")) {
                ErrorWindow.setText("No error");
                ErrorWindow.setFill(Color.GREEN);
            } else {
                ErrorWindow.setText(checked);
                ErrorWindow.setFill(Color.RED);
                return;
            }
            ArrayList<String> result, data;
            if (InputType.getSelectionModel().getSelectedItem().equals("Enter data")) {
                String temp = InputValue.getText();
                data = new ArrayList<>(Arrays.asList(temp.split(",")));
            } else {
                fileName = InputValue.getText();
                FileParser parser = new FileParser(fileName);
                if (!(SecretKey.getText().equals(""))) {
                    parser.changeEncryptionKey(SecretKey.getText());
                }
                data = parser.parse();
            }
            MathExpressions mathExpressions = new MathExpressions(data);
            result = mathExpressions.compute();
            StringBuilder outputString = new StringBuilder();
            for (String next: result) {
                outputString.append(next).append(", ");
            }
            OutputValue.setText(outputString.toString());
        } catch (RuntimeException e) {
            ErrorWindow.setText(e.getMessage());
            ErrorWindow.setFill(Color.RED);
        }
    }

    private String checkAll() {
        String inputText = InputValue.getText();
        String inputType = InputType.getSelectionModel().getSelectedItem();
        if (inputText.equals("")) {
            return inputType + " first";
        }
        if (inputType.equals("Enter filePath")) {
            File file = new File(inputText);
            if (!file.exists()) {
                return "Incorrect file path";
            }
        }
        return "true";
    }
}
