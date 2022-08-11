module com.example.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens Controlers to javafx.fxml;
    exports Controlers;
    exports Dao;
    opens Dao to javafx.fxml;
}