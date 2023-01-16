package UI;

import FileActions.FileFormer;
import enums.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tools.ToolsLib;
import tools.TxtLib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class FileBuildWindowController {
    @FXML
    protected TextField InputValue;
    @FXML
    protected Text ErrorWindow;
    @FXML
    protected ChoiceBox<String> InputType;
    @FXML
    protected ChoiceBox<String> FileType;
    @FXML
    protected ChoiceBox<String> FirstOperation;
    @FXML
    protected ChoiceBox<String> SecondOperation;
    @FXML
    protected Button BuildButton;
    @FXML
    protected void initialize() {
        InputType.getItems().add("Enter data");
        InputType.getItems().add("Enter filePath");
        InputType.getSelectionModel().selectFirst();
        for (FileTypes type: enums.FileTypes.values()) {
            FileType.getItems().add(type.toString());
        }
        FileType.getSelectionModel().selectFirst();
        FirstOperation.getItems().add("None");
        SecondOperation.getItems().add("None");
        for (FileOperations operation: FileOperations.values()) {
            FirstOperation.getItems().add(operation.toString());
            SecondOperation.getItems().add(operation.toString());
        }
        FirstOperation.getSelectionModel().selectFirst();
        SecondOperation.getSelectionModel().selectFirst();
    }

    @FXML
    protected void onBuildButtonClicked() {
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
            if (InputType.getSelectionModel().getSelectedItem().equals("Enter data")) {
                String temp = InputValue.getText();
                ArrayList<String> data = new ArrayList<>(Arrays.asList(temp.split(" ")));
                fileName = TxtLib.txtForm(data);
            } else {
                fileName = InputValue.getText();
            }
            FileTypes fileType = FileTypes.valueOf(FileType.getSelectionModel().getSelectedItem());
            String firstOp = FirstOperation.getSelectionModel().getSelectedItem();
            String secondOp = SecondOperation.getSelectionModel().getSelectedItem();
            FileOperations firstOperation, secondOperation;
            if (firstOp.equals("None")) {
                firstOperation = null;
            } else {
                firstOperation = FileOperations.valueOf(firstOp);
            }
            if (secondOp.equals("None")) {
                secondOperation = null;
            } else {
                secondOperation = FileOperations.valueOf(secondOp);
            }
            FileFormer former = new FileFormer(fileName, fileType, firstOperation, secondOperation);
            former.form();
            File file = new File("data.txt");
            if (file.exists()) {
                Files.delete(Path.of("data.txt"));
            }
            fileName = former.fileName;
            ErrorWindow.setText("Formed file " + fileName);
            ErrorWindow.setFill(Color.GREEN);
        } catch (RuntimeException | IOException e) {
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
            if (ToolsLib.getLastExtension(InputValue.getText()).equals(FileType.getSelectionModel().getSelectedItem())
                    && FirstOperation.getSelectionModel().getSelectedItem().equals("None")
                    && SecondOperation.getSelectionModel().getSelectedItem().equals("None")) {
                return ".txt file already exists";
            }
        }
        return "true";
    }
}
