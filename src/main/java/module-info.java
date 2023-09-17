module com.example.kolokiwum_ii_2021 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kolokiwum_ii_2021 to javafx.fxml;
    exports com.example.kolokiwum_ii_2021;
}