module com.main.grand_task {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires json.simple;
    requires java.xml;


    opens UI to javafx.fxml;
    exports UI;
}