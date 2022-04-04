package com.example.projet_bibliotheque;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistelivreController implements Initializable {

    @FXML
    private TableView<ouvrage> Tableview;

    @FXML
    private TableColumn<ouvrage, String> ColonneTitre;

    @FXML
    private TableColumn<ouvrage, String> ColonneAuteur;

    @FXML
    private TableColumn<ouvrage, String> ColonnePresentation;

    @FXML
    private TableColumn<ouvrage, String> ColonneParution;

    @FXML
    private TableColumn<ouvrage, Integer> ColonneColonne;

    @FXML
    private TableColumn<ouvrage, Integer> ColonneRangé;

    @FXML
    private TableColumn<ouvrage, String> ColonneResume;

    @FXML
    private TableColumn<ouvrage, String> ColonneURL;

    @FXML
    private TableColumn<ouvrage, String> ColonneEtat;

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
    private TextField urlinput;

    @FXML
    private TextField appercuinput11;

    @FXML
    private TextField résuméinput;

    @FXML
    private CheckBox etatinput;

    @FXML
    private Button fonctionvalide;

    @FXML
    private Button fonctionssupprime;

    @FXML
    private Button fonctionmodifier;

    @FXML
    private Label messagederreurformulaire;


    @FXML
    void mettreàjourlesdonnées(ActionEvent event) {

    }

    @FXML
    void modifierlesdonnées(ActionEvent event) {

    }

    @FXML
    void supprimerlaselection(ActionEvent event) {
        ouvrageArrayList.remove(Seletionerlaligne());
        Tableview.setItems(observableList());
        reinitialiser();
    }

    @FXML
    void valerderlesinfos(ActionEvent event) {

        if (titreinput.getText().isBlank() == false && auteurinput.getText().isBlank() == false && presentationinput.getText().isBlank() == false  && colonneinput.getText().isBlank() == false && rangéeinput.getText().isBlank() == false && résuméinput.getText().isBlank() == false  &&  etatinput.getText().isBlank() == false) {

        if (!auteurinput.getText().equals("")) {
            ouvrageArrayList.add(new ouvrage(titreinput.getText(), auteurinput.getText(), presentationinput.getText(), parutioninput.getText(), Integer.parseInt(colonneinput.getText()), Integer.parseInt(rangéeinput.getText()) ,urlinput.getText(), résuméinput.getText(),etatinput.getText()));
            Tableview.setItems(observableList());
            registerLivre();
        }
        reinitialiser();
        }
        else {
         messagederreurformulaire.setText("Veillez entrer les informations");
        }
    }
    public void reinitialiser(){
        titreinput.setText("");
        auteurinput.setText("");
        presentationinput.setText("");
        parutioninput.setText("");
        colonneinput.setText("");
        rangéeinput.setText("");
        urlinput.setText("");
        résuméinput.setText("");
        appercuinput11.setText("");
        etatinput.setText("");
    }

    ArrayList<ouvrage> ouvrageArrayList = new ArrayList<>();
    ObservableList<ouvrage> livre;

    public ObservableList<ouvrage> observableList(){
        livre= FXCollections.observableArrayList();
        livre.addAll(ouvrageArrayList);
        return livre;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ColonneTitre.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("titrec"));
        ColonneAuteur.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("auteurc"));
        ColonnePresentation.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("présentationc"));
        ColonneParution.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("parutionc"));
        ColonneColonne.setCellValueFactory(new PropertyValueFactory<ouvrage, Integer>("colonnec"));
        ColonneRangé.setCellValueFactory(new PropertyValueFactory<ouvrage, Integer>("rangéec"));
        ColonneEtat.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("etatc"));
        ColonneURL.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("urlc"));
        ColonneResume.setCellValueFactory(new PropertyValueFactory<ouvrage, String>("resumec"));

        Tableview.setItems(observableList());
    }

    public int Seletionerlaligne(){
        Tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return  Tableview.getSelectionModel().getSelectedIndex();
    }

    public  void registerLivre(){
        databaseconnection connectNow = new databaseconnection();
        Connection connectDB = connectNow.getConnection();

        String Titre =titreinput.getText();
        String Auteur =auteurinput.getText();
        String Presentation =presentationinput.getText();
        String Parution=parutioninput.getText();
        String Colones =colonneinput.getText();
        String Rangees =rangéeinput.getText();
        String Resumee=résuméinput.getText();
        String ETAT =etatinput.getText();
        String URL = urlinput.getText();


        String insertFields ="INSERT INTO livre_aicha (Titre, Auteur, Presentation, Parution, Colones, Rangees, Resumee, ETAT, URL) VALUES ('";
        String insertValues = Titre +"','"+ Auteur +"','"+ Presentation + "','" + Parution + "','" + Colones+ "','" + Rangees+","+Resumee +"," +ETAT + "','" +URL +"')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement =connectDB.createStatement();
            statement.executeLargeUpdate(insertToRegister);

            messagederreurformulaire.setText("les données ont été bien enregistrer dans la base de donnée!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
