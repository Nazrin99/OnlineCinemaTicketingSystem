module com.example.testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens OnlineCinemaTicketingSystem to javafx.fxml;
    exports OnlineCinemaTicketingSystem;
}