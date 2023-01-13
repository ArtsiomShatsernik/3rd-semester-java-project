module com.main.grand_task {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires json.simple;
    requires java.xml;


    opens com.main.grand_task to javafx.fxml;
    exports com.main.grand_task;
}