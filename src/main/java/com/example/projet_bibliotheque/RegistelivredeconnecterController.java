package com.example.projet_bibliotheque;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RegistelivredeconnecterController {

    @FXML
    private TableView<?> Tableview;

    @FXML
    private TableColumn<?, ?> ColonneTitre;

    @FXML
    private TableColumn<?, ?> ColonneAuteur;

    @FXML
    private TableColumn<?, ?> ColonnePresentation;

    @FXML
    private TableColumn<?, ?> ColonneParution;

    @FXML
    private TableColumn<?, ?> ColonneColonne;

    @FXML
    private TableColumn<?, ?> ColonneRangé;

    @FXML
    private TextField auteurinput;

    @FXML
    private TextField presentationinput;

    @FXML
    private TextField parutioninput;

    @FXML
    private TextField colonneinput;

    @FXML
    private TextField rangéeinput;

    @FXML
    private TextField titreinput;

    @FXML
    private Label messagederreurformulaire;

    @FXML
    private Button fonctionvalide;

    @FXML
    private Button fonctionssupprime;

    @FXML
    private Button fonctionmodifier;

    @FXML
    void modifierlesdonnées(ActionEvent event) {

    }

    @FXML
    void supprimerlaselection(ActionEvent event) {

    }

    @FXML
    void valerderlesinfos(ActionEvent event) {

    }

}

