package com.example.projet_bibliotheque;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerPageaccueil {

    @FXML
    private Button modedeconnecte;

    @FXML
    private Button modeconnecte;

    @FXML
    void actionmodeconnecte(ActionEvent event) {
        interfaceaplicationconnecte();
    }

    @FXML
    void actionmodedeconnecte(ActionEvent event) {
        aplicationdeconnecte();
    }


    public  void  interfaceaplicationconnecte() {

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
        public  void  aplicationdeconnecte(){

            try{
                Parent root =  FXMLLoader.load(getClass().getResource("page_denregistrement_deconnecter_biblio.fxml"));
                Stage deconnecteStage = new Stage();
                deconnecteStage.setTitle("INTERFACE BIBLIOTHEQUE: DECONNECTION");
                deconnecteStage.setScene(new Scene(root, 1100, 900));
                deconnecteStage.show();

            }catch (Exception e)
            {
                e.printStackTrace();
                e.getCause();
            }
    }

}