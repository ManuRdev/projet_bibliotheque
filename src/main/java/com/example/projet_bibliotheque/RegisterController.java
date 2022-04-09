package com.example.projet_bibliotheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import  java.io.File;
import  java.net.URL;
import  java.sql.Connection;
import java.sql.DriverManager;
import  java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {


    @FXML
    private TextField nominput;

    @FXML
    private TextField mailinputenregistrement;

    @FXML
    private TextField prenoninput;

    @FXML
    private Button buttonvalider;

    @FXML
    private Button buttonretour;

    @FXML
    private PasswordField motdepasseinputenregistrement;

    @FXML
    private PasswordField confirmotdepasseinput;

    @FXML
    private Label Messageerreurpagecreation;

    @FXML
    private ImageView REGISTERImageView;

    @FXML
    void actionenregistrement(ActionEvent event) {

        if(nominput.getText().isBlank() == false && mailinputenregistrement.getText().isBlank() == false && prenoninput.getText().isBlank() == false && motdepasseinputenregistrement.getText().isBlank() == false && confirmotdepasseinput.getText().isBlank() == false){

            if(motdepasseinputenregistrement.getText().equals(confirmotdepasseinput.getText())){

                registerUser();

            } else {
                Messageerreurpagecreation.setText("Mot de passe différent");
            }
        }
        else{
            Messageerreurpagecreation.setText("Veillez entrer vos identifiants");
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File REGISTERFile = new File("images/REGISTER.jpg");
        Image REGISTERImage = new Image( REGISTERFile.toURI().toString());
        REGISTERImageView.setImage(REGISTERImage);
    }


    public  void registerUser(){
        //databaseconnection connectNow = new databaseconnection();
        //Connection ConnectDB = connectNow.getConnection();

        String NOM =nominput.getText();
        String PRENOM =prenoninput.getText();
        String MAIL =mailinputenregistrement.getText();
        String MOT_DE_PASSE=motdepasseinputenregistrement.getText();

        String insertValues = NOM +"','"+ PRENOM +"','"+ MAIL + "','" + MOT_DE_PASSE + "')";
        String insertFields = "INSERT INTO utilisateur_aicha (NOM, PRENOM, MAIL, MOT_DE_PASSE) VALUES ('";
        String insertToRegister = insertFields + insertValues;

        try{
            Connection ConnectDB = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aichaexemple?serverTimezone=UTC", "root", "root1234");
            Statement statement =ConnectDB.createStatement();
            statement.executeLargeUpdate(insertToRegister);

            Messageerreurpagecreation.setText("vous avez été enregistrer avec succès!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void actionretour(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("page_connexion_biblio.fxml"));
            Stage bibliothequeStage = new Stage();
            bibliothequeStage.setTitle("CONNEXION");
            bibliothequeStage.setScene(new Scene(root, 585, 382));
            bibliothequeStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
