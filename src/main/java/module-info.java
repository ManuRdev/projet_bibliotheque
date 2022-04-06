module com.example.projet_bibliotheque {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.xml.bind;

    opens com.example.projet_bibliotheque to javafx.fxml,java.xml.bind;
    exports com.example.projet_bibliotheque;
}