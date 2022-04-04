package com.example.projet_bibliotheque;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    @FXML
    private TextField mailinput;

    @FXML
    private PasswordField motdepasseinput;

    @FXML
    private Button buttonconnecter;

    @FXML
    private Button buttonenregistrer;

    @FXML
    private Label messageerreuridentifiantincorets;


    @FXML
    void actionseconnecter(ActionEvent event) {

        if(mailinput.getText().isBlank() == false && motdepasseinput.getText().isBlank() == false){
        Loginvalider();
        }
        else{
         messageerreuridentifiantincorets.setText("Veillez entrer vos identifiants");
        }
    }

    @FXML
    void actionsenregistrer(ActionEvent event) {
        createAccountForm();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Loginvalider(){
        databaseconnection connectNow = new databaseconnection();
        Connection  connectDB = connectNow.getConnection();

        String verifyLogin ="SELECT count(1) FROM utilisateur_aicha WHERE MAIL = '" + mailinput.getText() + "' AND MOT_DE_PASSE ='" + motdepasseinput.getText() +"'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt( 1) == 1){
                    interfaceaplication();
                }else{
                    messageerreuridentifiantincorets.setText("Compte inexistant: Pas de pannique.\nCliquer sur S'enregistrer pour créer un compte" +
                            "");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public  void  createAccountForm(){

        try{
            Parent root =  FXMLLoader.load(getClass().getResource("page_denregistrement_biblio.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("MEDIABIBLIOTHEQUE D'ESIEE-IT");
            registerStage.setScene(new Scene(root, 600, 500));
            registerStage.show();

        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    public  void  interfaceaplication(){

        try{
            Parent root =  FXMLLoader.load(getClass().getResource("page_bibliothe_biblio.fxml"));
            Stage bibliothequeStage = new Stage();
            bibliothequeStage.setTitle("MEDIABIBLIOTHEQUE D'ESIEE-IT");
            bibliothequeStage.setScene(new Scene(root, 1100, 900));
            bibliothequeStage.show();

        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
}

