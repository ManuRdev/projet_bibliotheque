module com.example.projet_bibliotheque {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.projet_bibliotheque to javafx.fxml;
    exports com.example.projet_bibliotheque;
}